package com.wesp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> carItems = new HashSet<>();

    private BigDecimal totalSellingPrice;

    private int totalItems; // Número total de itens no carrinho
    private BigDecimal totalMrpPrice; // Preço total do MRP (financeiro)
    private BigDecimal discount; // Desconto aplicado (financeiro)


    private String couponCode;



}
