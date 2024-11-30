package com.wesp.controller;

import com.wesp.model.VerificationCode;
import com.wesp.repository.VerificationCodeRepository;
import com.wesp.request.LoginRequestDTO;
import com.wesp.response.ApiResponse;
import com.wesp.response.AuthResponse;
import com.wesp.service.AuthService;
import com.wesp.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(@RequestBody LoginRequestDTO req) {

        String otp = req.getOtp();

        req.setEmail("seller_" + req.getEmail());
        AuthResponse authResponse = authService.siging(req);


        return ResponseEntity.ok(authResponse);
    }


}

