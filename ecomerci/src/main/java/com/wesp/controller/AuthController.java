package com.wesp.controller;


import com.wesp.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public ResponseEntity<User> registerUser() {
        return null;
    }
}
