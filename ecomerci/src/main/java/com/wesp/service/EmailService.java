package com.wesp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text) {
        try {
            // Criação de uma mensagem MIME
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            // Configuração do email
            helper.setSubject(subject);
            helper.setText(text, true); // Define o conteúdo como HTML (use false para texto simples)
            helper.setTo(userEmail);

            // Envio do email
            javaMailSender.send(mimeMessage);

        } catch (MessagingException | MailException e) {
            System.out.println("Failed to send email -- "+  e);
            throw new MailSendException("Failed to send email", e); // Inclui a exceção original para rastreamento
        }
    }

}
