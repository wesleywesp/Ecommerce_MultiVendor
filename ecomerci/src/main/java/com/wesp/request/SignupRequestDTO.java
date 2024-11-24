package com.wesp.request;

public record SignupRequestDTO(String email, String name, String lastname,
                               String password,
                               String otp,
                               String phone) {
}
