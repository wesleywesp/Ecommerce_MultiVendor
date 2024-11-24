package com.wesp.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Set;

public record CarRequestDTO(@NotNull(message = "User ID cannot be null")
                                Long userId,

                            @NotEmpty(message = "Cart items cannot be empty")
                            Set<CartItemRequestDTO> cartItems,

                            @DecimalMin(value = "0.0", inclusive = true, message = "Total selling price must be at least 0")
                            BigDecimal totalSellingPrice,

                            @Min(value = 0, message = "Total items must be at least 0")
                                int totalItems,

                            @Min(value = 0, message = "Total MRP price must be at least 0")
                                BigDecimal totalMrpPrice,

                            @Min(value = 0, message = "Discount must be at least 0")
                                BigDecimal discount,

                            @Pattern(regexp = "^[A-Z0-9]{5,15}$", message = "Invalid coupon code format")
                                String couponCode
){
}
