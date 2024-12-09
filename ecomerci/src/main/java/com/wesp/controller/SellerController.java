package com.wesp.controller;


import com.wesp.domain.AccountStatus;
import com.wesp.infra.exception.SellerException;
import com.wesp.model.Seller;
import com.wesp.model.SellerReport;
import com.wesp.model.VerificationCode;
import com.wesp.repository.VerificationCodeRepository;
import com.wesp.request.LoginRequestDTO;
import com.wesp.request.SellerRequestDTO;
import com.wesp.request.UpdateSellerRequestDTO;
import com.wesp.response.AuthResponse;
import com.wesp.service.AuthService;
import com.wesp.service.EmailService;
import com.wesp.service.SellerReportService;
import com.wesp.service.SellerService;
import com.wesp.util.OtpUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;
    private final SellerReportService sellerReportService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(@RequestBody @Valid LoginRequestDTO req) throws AuthenticationException {

        String otp = req.getOtp();


        req.setEmail("seller_" + req.getEmail());
        System.out.println("otp: " + otp +"email: " + req.getEmail());
        AuthResponse authResponse = authService.siging(req);


        return ResponseEntity.ok(authResponse);
    }
    //verificar ser o codigo otp esta correto e se ele existe.
    @GetMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp) throws SellerException {

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp).orElseThrow(() -> new RuntimeException("Invalid OTP"));
        if(!verificationCode.getOtp().equals(otp)) {
            throw new RuntimeException("Wrong OTP");
        }
        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);
        return ResponseEntity.ok(seller);
    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody @Valid SellerRequestDTO seller) throws SellerException {
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
    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {
        Seller seller = sellerService.getSellerById(id);
        return ResponseEntity.ok(seller);
    }
    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerProfile(@RequestHeader("Authorization") String jwtToken) throws SellerException {
        Seller seller = sellerService.getSellerProfile(jwtToken);
        return ResponseEntity.ok(seller);
    }

    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwtToken) throws SellerException {
        Seller seller = sellerService.getSellerProfile(jwtToken);
        SellerReport sellerReport = sellerReportService.getSellerReport(seller);
        return ResponseEntity.ok(sellerReport);
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(@RequestParam(required = false) AccountStatus status) {
        List<Seller> sellers = sellerService.getAllSellers(status);
        return ResponseEntity.ok(sellers);
    }

    @PutMapping
    public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwtToken, @RequestBody @Valid UpdateSellerRequestDTO seller) throws SellerException {
        Seller profile = sellerService.getSellerProfile(jwtToken);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws SellerException {
        sellerService.desativaSeller(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> desativarSeller(@PathVariable Long id) throws SellerException {
                sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }






}

