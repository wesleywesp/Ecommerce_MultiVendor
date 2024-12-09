package com.wesp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Street cannot be empty")
    private String street;
    @NotBlank(message = "City cannot be empty")
    private String city;
    @NotBlank(message = "State cannot be empty")
    private String state;
    @NotBlank(message = "Country cannot be empty")
    private String country;
    @Pattern(regexp = "\\d{4,10}", message = "Postal code must be between 4 and 10 digits")
    private String codePostal;
    @Pattern(regexp = "\\d{10,15}", message = "Phone number must be between 10 and 15 digits")
    private String phone;
    @ManyToOne
    @JoinColumn(name = "user_id")// Nome da coluna no banco de dados
    @JsonIgnore
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
