package com.wesp.model;

import com.wesp.request.BankDetailsRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetails {

    private String accountHolderName;

    private String accountNumber;

    private String ifscCode;

    private String bankName;

    public BankDetails(BankDetailsRequestDTO bankDetailsRequestDTO) {
        this.accountHolderName = bankDetailsRequestDTO.accountHolderName();
        this.accountNumber = bankDetailsRequestDTO.accountNumber();
        this.ifscCode = bankDetailsRequestDTO.ifscCode();
        this.bankName = bankDetailsRequestDTO.bankName();
    }
}

