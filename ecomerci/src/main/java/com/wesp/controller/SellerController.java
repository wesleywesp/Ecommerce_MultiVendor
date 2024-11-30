package com.wesp.controller;


import com.wesp.model.Seller;
import com.wesp.model.VerificationCode;
import com.wesp.repository.VerificationCodeRepository;
import com.wesp.request.LoginRequestDTO;
import com.wesp.request.SellerRequestDTO;
import com.wesp.response.AuthResponse;
import com.wesp.service.AuthService;
import com.wesp.service.EmailService;
import com.wesp.service.SellerService;
import com.wesp.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(@RequestBody LoginRequestDTO req) {

        String otp = req.getOtp();

        req.setEmail("seller_" + req.getEmail());
        AuthResponse authResponse = authService.siging(req);


        return ResponseEntity.ok(authResponse);
    }
    //verificar ser o codigo otp esta correto e se ele existe.
    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp) {

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp).orElseThrow(() -> new RuntimeException("Invalid OTP"));
        if(!verificationCode.getOtp().equals(otp)) {
            throw new RuntimeException("Wrong OTP");
        }
        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);
        return ResponseEntity.ok(seller);
    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody SellerRequestDTO seller) {
        // Criação do novo vendedor
        Seller newSeller = sellerService.createSellerProfile(seller);

        // Gerando e salvando o código de verificação
        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(newSeller.getEmail());
        verificationCodeRepository.save(verificationCode);

        // Configurando email de verificação
        String subject = "Wesp ecomerci Email Verification Code";
        String text = "Welcome to Wesp ecomerci, verify your account using this link: ";
        String frontendUrl = "http://localhost:3000/verify-seller/";
        emailService.sendVerificationOtpEmail(newSeller.getEmail(), otp, subject, text + frontendUrl);

        // Criando URI para o cabeçalho "Location"
        URI location = URI.create("/sellers/" + newSeller.getId()); // Ajuste o ID conforme o método de obtenção

        // Retornando a resposta
        return ResponseEntity.created(location).body(newSeller);
    }





}

