package com.wesp.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProdutosRequestdDTO(Long id,

                                  @NotBlank(message = "O título não pode estar vazio")
                                      @Size(max = 100, message = "O título não pode ter mais de 100 caracteres")
                                      String title,

                                  @NotBlank(message = "A descrição não pode estar vazia")
                                      @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres")
                                      String description,

                                  @NotNull(message = "O preço MRP não pode ser nulo")
                                      @DecimalMin(value = "0.0", inclusive = true, message = "O preço MRP deve ser maior ou igual a 0")
                                  BigDecimal mrpPrice,

                                  @NotNull(message = "O preço de venda não pode ser nulo")
                                      @DecimalMin(value = "0.0", inclusive = true, message = "O preço de venda deve ser maior ou igual a 0")
                                      BigDecimal sellingPrice,

                                  @DecimalMin(value = "0.0", inclusive = true, message = "A porcentagem de desconto deve ser maior ou igual a 0")
                                      BigDecimal discountPercentage,

                                  @Size(max = 50, message = "A cor não pode ter mais de 50 caracteres")
                                      String color,

                                  @NotNull(message = "A quantidade não pode ser nula")
                                      @DecimalMin(value = "0", inclusive = true, message = "A quantidade deve ser maior ou igual a 0")
                                      Integer quantity,

                                  @Size(max = 10, message = "O máximo de imagens permitido é 10")
                                  List<String> images,

                                  int numRating,

                                  @NotNull(message = "A categoria não pode ser nula")
                                      Long categoryId,

                                  @NotNull(message = "O vendedor não pode ser nulo")
                                      Long sellerId,

                                  boolean active,

                                  @Size(max = 10, message = "O máximo de tamanhos permitido é 10")
                                      List<String> sizes,

                                  LocalDateTime createdDate,

                                  LocalDateTime updatedDate) {
}
