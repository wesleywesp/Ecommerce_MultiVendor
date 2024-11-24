package com.wesp.request;

import com.wesp.domain.PaymentStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PaymentDetailsRequestDTO(@NotBlank(message = "O ID do pagamento é obrigatório")
                                       String paymentId, // Identificador no sistema

                                       @NotBlank(message = "O ID do pagamento no MB Way é obrigatório")
                                       String mbWayPaymentId, // Identificador do gateway

                                       @NotBlank(message = "O status do pagamento é obrigatório")
                                       @Pattern(regexp = "PENDING|PAID", message = "O status do pagamento deve ser PENDING ou PAID")
                                       String paymentStatus, // Ex.: PENDING, PAID

                                       @NotBlank(message = "O status do pagamento MB Way é obrigatório")
                                       @Pattern(regexp = "WAITING_FOR_PAYMENT|EXPIRED", message = "O status do MB Way deve ser WAITING_FOR_PAYMENT ou EXPIRED")
                                       String mbWayPaymentStatus, // Ex.: WAITING_FOR_PAYMENT, EXPIRED

                                       @NotNull(message = "O valor do pagamento é obrigatório")
                                       @DecimalMin(value = "0.0", inclusive = false, message = "O valor do pagamento deve ser maior que 0")
                                       BigDecimal amount, // Valor do pagamento

                                       @NotBlank(message = "A moeda é obrigatória")
                                       @Size(min = 3, max = 3, message = "A moeda deve ter 3 caracteres (ex.: EUR)")
                                       String currency, // Moeda (ex.: EUR)

                                       @NotNull(message = "O status do pagamento é obrigatório")
                                       PaymentStatus status // Enum que representa o status do pagamento
) {
}
