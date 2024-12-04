package com.wesp.model;

import com.wesp.request.BankDetailsRequestDTO;
import com.wesp.request.BusinessDetailsRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDetails {

    private String businessName;
    private String businessEmail;
    private String businessPhone;
    private String businessAddress;
    private String logo;
    private String banner;

    public BusinessDetails(BusinessDetailsRequestDTO businessDetailsRequestDTO) {
        this.businessName = businessDetailsRequestDTO.businessName();
        this.businessEmail = businessDetailsRequestDTO.businessEmail();
        this.businessPhone = businessDetailsRequestDTO.businessPhone();
        this.businessAddress = businessDetailsRequestDTO.businessAddress();
        this.logo = businessDetailsRequestDTO.logo();
        this.banner = businessDetailsRequestDTO.banner();
    }
}