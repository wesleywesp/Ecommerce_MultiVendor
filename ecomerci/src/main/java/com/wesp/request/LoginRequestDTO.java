package com.wesp.request;

import lombok.Data;

@Data
public class LoginRequestDTO{
    private String email;
    private String otp;

}
