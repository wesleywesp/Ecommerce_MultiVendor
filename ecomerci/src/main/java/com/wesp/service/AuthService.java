package com.wesp.service;

import com.wesp.request.LoginRequestDTO;
import com.wesp.request.SignupRequestDTO;
import com.wesp.response.AuthResponse;

public interface AuthService {
    void sendLoginOtp(String email);
    String createUser(SignupRequestDTO req);
    AuthResponse siging(LoginRequestDTO req);
}
