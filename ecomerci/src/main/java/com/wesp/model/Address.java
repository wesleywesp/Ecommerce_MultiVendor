package com.wesp.model;


import com.wesp.request.AddressRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String street;

    private String city;

    private String state;

    private String country;

    private String codePostal;

    private String phone;
    @ManyToOne
    @JoinColumn(name = "user_id") // Nome da coluna no banco de dados
    private User user;

    public Address(AddressRequestDTO addressRequestDTO) {
        this.name = addressRequestDTO.name();
        this.street = addressRequestDTO.street();
        this.city = addressRequestDTO.city();
        this.state = addressRequestDTO.state();
        this.country = addressRequestDTO.country();
        this.codePostal = addressRequestDTO.codePostal();
        this.phone = addressRequestDTO.phone();
    }

}
