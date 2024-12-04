package com.wesp.service.impl;

import com.wesp.domain.USER_ROLE;
import com.wesp.infra.exception.AutenficacaoException;
import com.wesp.infra.security.TokenService;
import com.wesp.model.Cart;
import com.wesp.model.Seller;
import com.wesp.model.User;
import com.wesp.model.VerificationCode;
import com.wesp.repository.CartRepository;
import com.wesp.repository.SellerRepository;
import com.wesp.repository.UserRepository;
import com.wesp.repository.VerificationCodeRepository;
import com.wesp.request.LoginRequestDTO;
import com.wesp.request.SignupRequestDTO;
import com.wesp.response.AuthResponse;
import com.wesp.service.AuthService;
import com.wesp.service.EmailService;
import com.wesp.util.OtpUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final TokenService tokenService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final CustumerServicerImpl custumerServicerImpl;
    private final SellerRepository sellerRepository;

    @Override
    public void sendLoginOtp(String email, USER_ROLE role) {
        final String SIGNIN_PREFIX = "signin_";

        // Remover prefixo "signin_" se presente
        if (email.startsWith(SIGNIN_PREFIX)) {
            email = email.substring(SIGNIN_PREFIX.length());
        }

        // Verificar se o usuário ou vendedor existe
        boolean userExists;
        if (role.equals(USER_ROLE.ROLE_SELLER)) {
            userExists = sellerRepository.findByEmail(email).isPresent();
            if (!userExists) {
                throw new AutenficacaoException("Seller not found with email: " + email);
            }
        } else {
            userExists = userRepository.findByEmail(email).isPresent();
            if (!userExists) {
                throw new AutenficacaoException("User not found with email: " + email);
            }
        }

        // Substituir código de verificação existente, se necessário
       VerificationCode isExist= verificationCodeRepository.findByEmail(email);
        if(isExist != null) {
            verificationCodeRepository.delete(isExist);
        }

        // Gerar novo OTP e salvar no banco
        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        // Configurar email
        String subject = "Wesp eCommerce Login/Signup OTP";
        String text = "Your login/signup OTP is: " + otp;

        // Enviar OTP via email
        emailService.sendVerificationOtpEmail(email, otp, subject, text);
    }


    @Override
    public String createUser(SignupRequestDTO req) {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.email());
        if(verificationCode == null || !verificationCode.getOtp().equals(req.otp())) {
            throw new AutenficacaoException("Invalid OTP");
        }


       if(userRepository.existsByEmail(req.email())) {
           throw new AutenficacaoException("Email already exists");
       }
       var password = passwordEncoder.encode(req.password());
       User user = new User(req,password);

       userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenService.generateToken(authentication);
    }

    @Override
    public AuthResponse siging(LoginRequestDTO req) {
        String userOrSeller= req.getEmail();
        String otp = req.getOtp();
        Authentication authentication = authenticate(userOrSeller, otp);



        VerificationCode verificationCode = verificationCodeRepository.findByEmail(userOrSeller);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateToken(authentication);
        AuthResponse res = new AuthResponse();
        res.setToken(token);
        res.setMessage("User logged in successfully");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        res.setRole(USER_ROLE.valueOf(role));


        return res;

    }

    private Authentication authenticate(String username, String otp) {
        UserDetails userDetails= custumerServicerImpl.loadUserByUsername(username);

        String SELLER_PREFIX = "seller_";
        if(username.startsWith(SELLER_PREFIX)) {
            username= username.substring(SELLER_PREFIX.length());
        }
        System.out.println("username: "+username);
        if(userDetails == null) {
            throw new AutenficacaoException("Invalid username or password");
        }System.out.println("userDetails: "+userDetails);
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);
        if(verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new AutenficacaoException("Invalid OTP");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
