package com.wesp.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDTO(Long id,
                                  @NotNull(message = "O ID do pedido é obrigatório")
                                          Long orderId, // Representa o ID da entidade Order associada

                                  @NotNull(message = "O ID do produto é obrigatório")
                                          Long productId, // Representa o ID da entidade Product associada

                                  @NotBlank(message = "O tamanho é obrigatório")
                                          String size, // Validação para garantir que o tamanho não seja nulo ou vazio

                                  @NotNull(message = "A quantidade é obrigatória")
                                          @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
                                          Integer quantity,

                                  @NotNull(message = "O preço MRP é obrigatório")
                                          @Min(value = 0, message = "O preço MRP deve ser maior ou igual a zero")
                                          Integer mrpPrice,

                                  @NotNull(message = "O preço de venda é obrigatório")
                                          @Min(value = 0, message = "O preço de venda deve ser maior ou igual a zero")
                                          Integer sellingPrice,

                                  @NotNull(message = "O ID do usuário é obrigatório")
                                          Long userId // Representa o ID do usuário associado ao item do pedido
) {
}
