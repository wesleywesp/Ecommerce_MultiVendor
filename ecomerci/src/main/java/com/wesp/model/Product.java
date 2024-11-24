package com.wesp.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private BigDecimal mrpPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Column(precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    private String color;

    private Integer quantity;

    @ElementCollection
    @Column(name = "image") // Nome da coluna na tabela secundária
    private List<String> images = new ArrayList<>();

    private int numRating; // Número de avaliações (pode ser calculado automaticamente)

    @ManyToOne
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate;

    private boolean active = true;

    @ElementCollection
    @Column(name = "size") // Nome da coluna na tabela secundária
    private List<String> sizes = new ArrayList<>();


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    // Método para atualizar a data de atualização
    @PreUpdate
    public void updateTimestamp() {
        this.updatedDate = LocalDateTime.now();
    }

    // Método para atualizar o número de avaliações
    public void updateNumRating() {
        this.numRating = reviews.size(); // Contagem de avaliações
    }
}



