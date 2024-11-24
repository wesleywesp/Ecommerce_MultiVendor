package com.wesp.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CartItemRequestDTO(@NotNull(message = "ID do item não pode ser nulo")
                                  Long id,

                                 @NotNull(message = "O ID do carrinho não pode ser nulo")
                                  Long cartId,

                                 @NotNull(message = "O ID do produto não pode ser nulo")
                                  Long productId,

                                 @NotBlank(message = "O tamanho não pode estar vazio")
                                  String size,

                                 @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
                                  int quantity,

                                 @NotNull(message = "O preço MRP não pode ser nulo")
                                  @Min(value = 0, message = "O preço MRP deve ser maior ou igual a 0")
                                 BigDecimal mrpPrice,

                                 @NotNull(message = "O preço de venda não pode ser nulo")
                                  @Min(value = 0, message = "O preço de venda deve ser maior ou igual a 0")
                                  BigDecimal sellingPrice,

                                 @NotNull(message = "O ID do usuário não pode ser nulo")
                                  Long userId
) {
}
