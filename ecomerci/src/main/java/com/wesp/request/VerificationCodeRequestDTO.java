package com.wesp.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record VerificationCodeRequestDTO(
        Long id, // Opcional para operações de leitura

        @NotBlank(message = "O código OTP é obrigatório")
        String otp, // Código de verificação

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email, // Email associado ao código de verificação

        Long userId, // ID do usuário (opcional, pode ser nulo se não estiver associado a um usuário)

        Long sellerId // ID do vendedor (opcional, pode ser nulo se não estiver associado a um vendedor)
) {
}
