package com.wesp.service.impl;

import com.wesp.infra.security.TokenService;
import com.wesp.model.User;
import com.wesp.repository.UserRepository;
import com.wesp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public User findByJwtToken(String jwtToken) {
        String email = tokenService.getSubject(jwtToken);
        User user = this.findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("User not found with email: " + email));
        return user;
    }
}
