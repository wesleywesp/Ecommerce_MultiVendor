package com.wesp.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wesp.domain.AccountStatus;
import com.wesp.domain.USER_ROLE;
import com.wesp.infra.anotaçãoDeValidação.UniqueEmail;
import com.wesp.model.BankDetails;
import com.wesp.model.BusinessDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record UpdateSellerRequestDTO(String sellerName,

                                     @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$", message = "Seller phone must be a valid phone number")
                                     String sellerPhone,

                                     @Email(message = "Email should be valid")
                                     @UniqueEmail(message = "Email is already in use")
                                     String email,

                                     @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                                     @Size(min = 8, message = "Password must be at least 8 characters long")
                                     String password,
                                     @Valid
                                     BusinessDetailsUpdateRequestDTO businessDetails,

                                     USER_ROLE role,

                                     Boolean isEmailVerified,

                                     Boolean isActive,
                                     @Valid
                                     BankDetailsUpdateRequestDTO bankDetails,

                                     AddressRequestDTO pickupAddress,

                                     @Pattern(regexp = "^[A-Z0-9]{15}$", message = "GSTIN should be a valid 15-character string")
                                     String GSTIN,

                                     AccountStatus accountStatus) {
}
