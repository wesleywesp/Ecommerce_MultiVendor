package com.wesp.controller;

import com.wesp.domain.USER_ROLE;
import com.wesp.model.User;
import com.wesp.request.SignupRequestDTO;
import com.wesp.response.AuthResponse;
import com.wesp.response.UserResponseDTO;
import com.wesp.service.AuthService;
import com.wesp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<UserResponseDTO> getUserbyJwttoken(@RequestHeader("Authorization") String jwtToken) {
        User user = userService.findByJwtToken(jwtToken);
        return ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getPassword(), user.getEmail(), user.getName(), user.getLastName(), user.getPhone()));


    }
}
