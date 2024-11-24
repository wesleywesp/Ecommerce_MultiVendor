package com.wesp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Deal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usei IDENTITY para controle explícito de IDs
    private Long id;

    private BigDecimal discount;

    @OneToOne
    private HomeCategory category;
}