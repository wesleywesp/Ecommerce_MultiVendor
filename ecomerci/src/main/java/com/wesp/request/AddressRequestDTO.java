package com.wesp.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressRequestDTO(@NotBlank(message = "Name cannot be empty")
                                String name,
                                @NotBlank(message = "Street cannot be empty")
                                String street,
                                @NotBlank(message = "City cannot be empty")
                                String city,
                                @NotBlank(message = "State cannot be empty")
                                String state,
                                @NotBlank(message = "Country cannot be empty")
                                String country,
                                @Pattern(regexp = "\\d{4,10}", message = "Postal code must be between 4 and 10 digits")
                                String codePostal,
                                @Pattern(regexp = "\\d{10,15}", message = "Phone number must be between 10 and 15 digits")
                                String phone) {
}
