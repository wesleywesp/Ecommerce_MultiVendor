package com.wesp.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TransactionRequestDTO(@NotNull(message = "O cliente é obrigatório")
                                            Long customerId, // ID do cliente associado à transação

                                    @NotNull(message = "O pedido é obrigatório")
                                            Long orderId, // ID do pedido associado à transação

                                    @NotNull(message = "O vendedor é obrigatório")
                                            Long sellerId, // ID do vendedor associado à transação

                                    @NotNull(message = "A data da transação é obrigatória")
                                    LocalDateTime transactionDate // Data da transação
) {
}
