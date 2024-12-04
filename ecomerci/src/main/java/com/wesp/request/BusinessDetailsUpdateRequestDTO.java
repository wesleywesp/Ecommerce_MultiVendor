package com.wesp.request;

import com.wesp.infra.anotaçãoDeValidação.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record BusinessDetailsUpdateRequestDTO(String businessName,

                                              @Email(message = "Business email must be a valid email address")
                                              @UniqueEmail(message = "Business email is already in use")
                                              String businessEmail,

                                              @Pattern(regexp = "^[0-9]{10}$", message = "Business phone must be a 10-digit number")
                                              String businessPhone,

                                              String businessAddress,

                                              String logo,  // Não é obrigatório, pode ser nulo
                                              String banner  // Não é obrigatório, pode ser nulo
) {
}
