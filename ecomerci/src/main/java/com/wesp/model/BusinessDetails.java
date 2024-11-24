package com.wesp.model;

import lombok.Data;


@Data
public class BusinessDetails {

    private Long id;
    private String businessName;
    private String businessEmail;
    private String businessPhone;
    private String businessAddress;
    private String logo;
    private String banner;
}