package com.wesp.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class CreateProductRequestDTO {

    @NotBlank(message = "O título não pode estar vazio")
    @Size(max = 100, message = "O título não pode ter mais de 100 caracteres")
    private String title;

    @NotBlank(message = "A descrição não pode estar vazia")
    @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres")
    private String description;

    @NotNull(message = "O preço MRP não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "O preço MRP deve ser maior ou igual a 0")
    private BigDecimal mrpPrice;

    @NotNull(message = "O preço de venda não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "O preço de venda deve ser maior ou igual a 0")
    private BigDecimal sellingPrice;

    @Size(max = 50, message = "A cor não pode ter mais de 50 caracteres")
    private String color;

    @Size(max = 10, message = "O máximo de imagens permitido é 10")
    private List<String> images;
    @NotNull(message = "A categoria não pode ser nula")
    private String category;
    @NotNull(message = "A categoria não pode ser nula")
    private String category2;
    @NotNull(message = "A categoria não pode ser nula")
    private String category3;
    @Size(max = 10, message = "O máximo de tamanhos permitido é 10")
    private String sizes;

}
