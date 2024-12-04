package com.wesp.infra.exception;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

public class JwtTokenException extends RuntimeException{
    public JwtTokenException(String message, IllegalArgumentException e) {
        super(message);
    }
    public JwtTokenException(String message, JWTCreationException e) {
        super(message);
    }
    public JwtTokenException(String message,JWTVerificationException e) {
        super(message);
    }
}
