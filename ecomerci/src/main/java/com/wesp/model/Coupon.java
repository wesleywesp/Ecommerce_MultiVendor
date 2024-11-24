package com.wesp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usei IDENTITY para controle explícito de IDs
    private Long id;

    private String code; // Código do cupom

    private BigDecimal discountPercentage; // Corrigido para o nome correto e tipo BigDecimal para precisão

    private LocalDate startDate; // Data de início da validade do cupom
    private LocalDate expirationDate; // Data de expiração do cupom

    private BigDecimal minimumOrderAmount; // Valor mínimo de compra para usar o cupom

    private boolean active = true; // Se o cupom está ativo ou não

    @ManyToMany(mappedBy = "usedCoupons")
    private Set<User> usedByUsers = new HashSet<>(); // Usuários que usaram o cupom
}

