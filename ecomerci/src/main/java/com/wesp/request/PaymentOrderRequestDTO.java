package com.wesp.request;

import com.wesp.domain.PaymentMethod;
import com.wesp.domain.PaymentOrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public record PaymentOrderRequestDTO(Long id,

                                     @NotNull
                                         @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero.")
                                     BigDecimal amount,

                                     @NotNull(message = "O método de pagamento é obrigatório.")
                                     PaymentMethod paymentMethod,

                                     @NotNull(message = "O status do pagamento é obrigatório.")
                                     PaymentOrderStatus status,

                                     @NotNull(message = "O ID do link de pagamento não pode ser nulo.")
                                         @NotBlank(message = "O ID do link de pagamento não pode estar em branco.")
                                         String paymentLinkId,

                                     @NotNull(message = "O usuário associado é obrigatório.")
                                         Long userId,

                                     @NotNull(message = "Os pedidos associados não podem ser nulos.")
                                     Set<Long> orderIds
){
}
