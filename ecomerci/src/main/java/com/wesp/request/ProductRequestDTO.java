package com.wesp.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductRequestDTO(Long id,

                                      @Size(max = 100, message = "O título não pode ter mais de 100 caracteres")
                                      String title,

                                      @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres")
                                      String description,

                                      @DecimalMin(value = "0.0", inclusive = true, message = "O preço MRP deve ser maior ou igual a 0")
                                  BigDecimal mrpPrice,

                                      @DecimalMin(value = "0.0", inclusive = true, message = "O preço de venda deve ser maior ou igual a 0")
                                      BigDecimal sellingPrice,

                                @DecimalMin(value = "0.0", inclusive = true, message = "A porcentagem de desconto deve ser maior ou igual a 0")
                                      BigDecimal discountPercentage,

                                @Size(max = 50, message = "A cor não pode ter mais de 50 caracteres")
                                      String color,

                                @DecimalMin(value = "0", inclusive = true, message = "A quantidade deve ser maior ou igual a 0")
                                      Integer quantity,

                                @Size(max = 10, message = "O máximo de imagens permitido é 10")
                                  List<String> images,

                                Integer numRating,

                                Long categoryId,

                                      Long sellerId,

                                boolean active,

                                @Size(max = 10, message = "O máximo de tamanhos permitido é 10")
                                      List<String> sizes,

                                LocalDateTime createdDate,

                                LocalDateTime updatedDate) {
}
