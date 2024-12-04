package com.wesp.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BankDetailsUpdateRequestDTO(String accountHolderName,

                                          @Size(min = 10, max = 20, message = "Account number must be between 10 and 20 characters")
                                          String accountNumber,

                                          @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "IFSC code must be in the correct format")
                                          String ifscCode,

                                          String bankName) {
}
