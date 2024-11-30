package com.wesp.service;

import com.wesp.domain.USER_ROLE;
import com.wesp.model.User;
import com.wesp.request.LoginRequestDTO;
import com.wesp.request.SignupRequestDTO;
import com.wesp.response.AuthResponse;

public interface AuthService {
    void sendLoginOtp(String email, USER_ROLE role);
    String createUser(SignupRequestDTO req);
    AuthResponse siging(LoginRequestDTO req);

}
