package com.wesp.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wesp.infra.anotaçãoDeValidação.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO (@NotBlank(message = "Name is required")
                              String name,
                               @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                               @NotBlank(message = "Password is required")
                               String password,
                              @NotNull
                              @Email(message = "Email is required")
                              @UniqueEmail(message = "Email is already in use")
                              String email,
                              @NotBlank(message = "Last name is required")
                              String lastName,
                              @NotBlank(message = "Phone is required")
                              @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number")
                              String phone){
}
