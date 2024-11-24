package com.wesp.model;

import com.wesp.domain.HomeCategorySection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HomeCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usei IDENTITY para controle explícito de IDs
    private Long id;

    private String name;
    private String image;
    private String categoryId;
    private HomeCategorySection section;
}