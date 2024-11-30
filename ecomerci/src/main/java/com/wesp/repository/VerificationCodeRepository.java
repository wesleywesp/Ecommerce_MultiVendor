package com.wesp.repository;

import com.wesp.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    VerificationCode findByEmail(String email);

   Optional<VerificationCode> findByOtp(String otp);
}
