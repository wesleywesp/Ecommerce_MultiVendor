package com.wesp.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Coupon(@NotBlank(message = "Code is required")
                     String code,
                     @NotNull(message = "Discount percentage is required")
                     BigDecimal discountPercentage,
                     @NotNull(message = "Start date is required")
                     LocalDate startDate,
                     @NotNull(message = "Expiration date is required")
                     LocalDate expirationDate,
                     @NotNull(message = "Minimum order amount is required")
                     BigDecimal minimumOrderAmount) {
}
