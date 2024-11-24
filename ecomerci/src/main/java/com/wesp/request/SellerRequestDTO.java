package com.wesp.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wesp.domain.AccountStatus;
import com.wesp.domain.USER_ROLE;
import com.wesp.infra.anotaçãoDeValidação.UniqueEmail;
import com.wesp.model.BankDetails;
import com.wesp.model.BusinessDetails;
import jakarta.validation.constraints.*;

public record SellerRequestDTO(@NotBlank(message = "Seller name cannot be empty")
                               String sellerName,

                               @NotBlank(message = "Seller phone cannot be empty")
                               @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$", message = "Seller phone must be a valid phone number")
                               String sellerPhone,

                               @Email(message = "Email should be valid")
                               @NotBlank(message = "Email cannot be empty")
                               @UniqueEmail(message = "Email is already in use")
                               String email,

                               @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                               @NotBlank(message = "Password cannot be empty")
                               @Size(min = 8, message = "Password must be at least 8 characters long")
                               String password,

                               @NotNull(message = "Business details cannot be null")
                               BusinessDetails businessDetails,

                               @NotNull(message = "Role cannot be null")
                               USER_ROLE role,

                               @NotNull(message = "Email verification status cannot be null")
                               Boolean isEmailVerified,

                               @NotNull(message = "Active status cannot be null")
                               Boolean isActive,

                               @NotNull(message = "Bank details cannot be null")
                               BankDetailsRequestDTO bankDetails,

                               @NotNull(message = "Pickup address cannot be null")
                               AddressRequestDTO pickupAddress,

                               @NotBlank(message = "GSTIN cannot be empty")
                               @Pattern(regexp = "^[A-Z0-9]{15}$", message = "GSTIN should be a valid 15-character string")
                               String GSTIN,

                               @NotNull(message = "Account status cannot be null")
                               AccountStatus accountStatus
) {

}
