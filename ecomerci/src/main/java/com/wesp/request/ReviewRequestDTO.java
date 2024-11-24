package com.wesp.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewRequestDTO(Long id,

                               @NotBlank(message = "O texto da avaliação não pode estar vazio")
                                 @Size(max = 1000, message = "O texto da avaliação não pode ter mais de 1000 caracteres")
                                 String reviewText,

                               @Min(value = 0, message = "A classificação deve ser pelo menos 0")
                                 @Max(value = 5, message = "A classificação não pode ser maior que 5")
                                 double rating,

                               @Size(max = 5, message = "A avaliação pode conter no máximo 5 imagens")
                               List<@NotBlank(message = "A URL da imagem não pode estar vazia") String> productImages,

                               @NotNull(message = "O ID do produto não pode ser nulo")
                                 Long productId,

                               @NotNull(message = "O ID do usuário não pode ser nulo")
                                 Long userId,

                               @NotNull(message = "A data de criação não pode ser nula")
                               LocalDateTime createdAt) {
}
