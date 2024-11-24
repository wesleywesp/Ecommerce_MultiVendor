package com.wesp.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record BusinessDetailsDTO(
        @NotBlank(message = "Business name cannot be blank")
        String businessName,

        @Email(message = "Invalid business email format")
        String businessEmail,

        @Pattern(regexp = "\\+?[0-9.-]+", message = "Invalid business phone number")
        String businessPhone,

        @NotBlank(message = "Business address cannot be blank")
        String businessAddress,

        String logo, // Optional, add validation if needed
        String banner // Optional, add validation if needed
) {}
