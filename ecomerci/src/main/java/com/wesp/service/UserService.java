package com.wesp.service;

import com.wesp.model.User;

public interface UserService {
     User findByJwtToken(String jwtToken);
        User findUserByEmail(String email);
}
