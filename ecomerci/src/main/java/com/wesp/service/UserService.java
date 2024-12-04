package com.wesp.service;

import com.wesp.infra.exception.CustumerException;
import com.wesp.model.User;

public interface UserService {
     User findByJwtToken(String jwtToken) throws CustumerException;
        User findUserByEmail(String email) throws CustumerException;
}
