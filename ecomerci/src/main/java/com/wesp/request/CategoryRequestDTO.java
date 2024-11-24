package com.wesp.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(Long id,

                                 @NotBlank(message = "O nome da categoria não pode estar vazio")
                                 @Size(max = 100, message = "O nome da categoria não pode ter mais de 100 caracteres")
                                 String name,

                                 @NotNull(message = "O categoryId não pode ser nulo")
                                 Long categoryId,

                                 Long parentCategoryId, // Apenas o ID da categoria pai para evitar referências cíclicas

                                 @NotNull(message = "O nível da categoria não pode ser nulo")
                                 @Min(value = 0, message = "O nível deve ser maior ou igual a 0")
                                 Integer level,

                                 @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres")
                                 String description,

                                 boolean active
) {}

