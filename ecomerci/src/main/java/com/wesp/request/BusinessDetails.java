package com.wesp.request;

import com.wesp.infra.anotaçãoDeValidação.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BusinessDetails(@NotNull(message = "Business name cannot be null")
                              @NotBlank(message = "Business name cannot be empty")
                              String businessName,

                              @NotNull(message = "Business email cannot be null")
                              @NotBlank(message = "Business email cannot be empty")
                              @Email(message = "Business email must be a valid email address")
                              @UniqueEmail(message = "Business email is already in use")
                              String businessEmail,

                              @NotNull(message = "Business phone cannot be null")
                              @NotBlank(message = "Business phone cannot be empty")
                              @Pattern(regexp = "^[0-9]{10}$", message = "Business phone must be a 10-digit number")
                              String businessPhone,

                              @NotNull(message = "Business address cannot be null")
                              @NotBlank(message = "Business address cannot be empty")
                              String businessAddress,

                              String logo,  // Não é obrigatório, pode ser nulo
                              String banner  // Não é obrigatório, pode ser nulo
) {
}
