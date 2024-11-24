package com.wesp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class SellerReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    private BigDecimal totalSales;
    private BigDecimal totalEarnings;
    private BigDecimal totalRefunds;
    private BigDecimal totalTax;

    private BigDecimal netEarnings;

    private Integer totalOrders;
    private Integer cancelledOrders;
    private Integer totalTransactions;

//for future use
//    private Integer totalProducts;
//    private Integer totalProductsRefunded;
//    private Integer totalProductsReturned;
//    private Integer totalProductsCancelled;
//    private Integer totalProductsSold;

}

