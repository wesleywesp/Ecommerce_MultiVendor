package com.wesp.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SellerReportRequestDTO(@NotNull(message = "Seller ID cannot be null")
                                         Long sellerId,

                                     @NotNull(message = "Total sales amount is required")
                                         @DecimalMin(value = "0.0", message = "Total sales cannot be negative")
                                     BigDecimal totalSales,

                                     @NotNull(message = "Total earnings amount is required")
                                         @DecimalMin(value = "0.0", message = "Total earnings cannot be negative")
                                         BigDecimal totalEarnings,

                                     @NotNull(message = "Total refunds amount is required")
                                         @DecimalMin(value = "0.0", message = "Total refunds cannot be negative")
                                         BigDecimal totalRefunds,

                                     @NotNull(message = "Total tax amount is required")
                                         @DecimalMin(value = "0.0", message = "Total tax cannot be negative")
                                         BigDecimal totalTax,

                                     @NotNull(message = "Net earnings amount is required")
                                         BigDecimal netEarnings,

                                     @NotNull(message = "Total orders count is required")
                                         @Min(value = 0, message = "Total orders cannot be negative")
                                         Integer totalOrders,

                                     @NotNull(message = "Cancelled orders count is required")
                                         @Min(value = 0, message = "Cancelled orders cannot be negative")
                                         Integer cancelledOrders,

                                     @NotNull(message = "Total transactions count is required")
                                         @Min(value = 0, message = "Total transactions cannot be negative")
                                         Integer totalTransactions
) {
}
