package com.wesp.model;

import lombok.Data;



@Data
public class BankDetails {

    private String accountHolderName;

    private String accountNumber;

    private String ifscCode;

    private String bankName;
}
