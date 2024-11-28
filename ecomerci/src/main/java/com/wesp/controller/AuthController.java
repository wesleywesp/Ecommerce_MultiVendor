package com.wesp.controller;


import com.wesp.domain.USER_ROLE;
import com.wesp.model.VerificationCode;
import com.wesp.request.LoginRequestDTO;
import com.wesp.request.SignupRequestDTO;
import com.wesp.response.ApiResponse;
import com.wesp.response.AuthResponse;
import com.wesp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/signin")
    @Transactional
    public ResponseEntity<AuthResponse> registerUser(@RequestBody SignupRequestDTO req) {
        String jwt = authService.createUser(req);
        AuthResponse res = new AuthResponse();
        res.setToken(jwt);
        res.setMessage("User registered successfully");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);


        return ResponseEntity.ok(res);


    }
    @PostMapping("/send/login-singnup-otp")
    @Transactional
    public ResponseEntity<ApiResponse> sendOtpHandler(@RequestBody VerificationCode req) {
       authService.sendLoginOtp(req.getEmail());
        ApiResponse res = new ApiResponse("Otp sent successfully");


        return ResponseEntity.ok(res);


    }
    @PostMapping("/singning")
    @Transactional
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequestDTO req) {
        AuthResponse res = authService.siging(req);
        return ResponseEntity.ok(res);


    }
}
