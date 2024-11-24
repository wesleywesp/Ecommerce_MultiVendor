package com.wesp.request;

import com.wesp.domain.OrderStatus;
import com.wesp.domain.PaymentStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderRequestDTO(

        @NotBlank(message = "Order ID não pode estar vazio")
        String orderId,

        @NotNull(message = "Usuário é obrigatório")
        Long userId, // Representa o ID do usuário associado ao pedido

        @NotNull(message = "Seller ID é obrigatório")
        Long sellerId,

        @NotEmpty(message = "A lista de itens do pedido não pode estar vazia")
        List<OrderItemRequestDTO> orderItems, // Assume que OrderItemDTO seja um DTO aninhado

        @NotNull(message = "O endereço de envio é obrigatório")
        Long shippingAddressId, // Representa o ID do endereço de envio

        @NotNull(message = "Detalhes de pagamento são obrigatórios")
        PaymentDetailsRequestDTO paymentDetails, // DTO para detalhes de pagamento

        @NotNull(message = "O preço total MRP é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "O preço total MRP deve ser maior ou igual a zero")
        BigDecimal totalMrpPrice,

        @NotNull(message = "O preço total do vendedor é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "O preço total do vendedor deve ser maior ou igual a zero")
        BigDecimal totalSellerPrice,

        @NotNull(message = "O desconto é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "O desconto deve ser maior ou igual a zero")
        BigDecimal discount,

        @NotNull(message = "O status do pedido é obrigatório")
        OrderStatus orderStatus,

        @NotNull(message = "O número total de itens é obrigatório")
        @Min(value = 1, message = "O pedido deve conter pelo menos um item")
        int totalItem,

        @NotNull(message = "O status do pagamento é obrigatório")
        PaymentStatus paymentStatus,

        @NotNull(message = "A data do pedido é obrigatória")
        LocalDateTime orderDate,

        @NotNull(message = "A data de entrega é obrigatória")
        LocalDateTime deliveryDate
) {
}
