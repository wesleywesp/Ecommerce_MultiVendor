package com.wesp.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record BankDetailsRequestDTO(@NotBlank(message = "Account holder name cannot be empty")
                                    String accountHolderName,

                                    @NotBlank(message = "Account number cannot be empty")
                                    @Size(min = 10, max = 20, message = "Account number must be between 10 and 20 characters")
                                    String accountNumber,

                                    @NotBlank(message = "IFSC code cannot be empty")
                                    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "IFSC code must be in the correct format")
                                    String ifscCode,

                                    @NotBlank(message = "Bank name cannot be empty")
                                    String bankName
) {}

