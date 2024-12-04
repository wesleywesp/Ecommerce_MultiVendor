package com.wesp.infra.exception;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(SellerException.class)
    public ResponseEntity<ErrorDetails> sellerExceptionHandler(SellerException se, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(request.getDescription(false));
        errorDetails.setError(se.getMessage());
        errorDetails.setTimestamp(java.time.LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorDetails> productExceptionHandler(ProductException se, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(request.getDescription(false));
        errorDetails.setError(se.getMessage());
        errorDetails.setTimestamp(java.time.LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustumerException.class)
    public ResponseEntity<ErrorDetails> custumerExceptionHandler(CustumerException se, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(request.getDescription(false));
        errorDetails.setError(se.getMessage());
        errorDetails.setTimestamp(java.time.LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<ErrorDetails> JwtTokenExceptionHandler(JwtTokenException se, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(request.getDescription(false));
        errorDetails.setError(se.getMessage());
        errorDetails.setTimestamp(java.time.LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AutenficacaoException.class)
    public ResponseEntity<ErrorDetails> AutenficacaoExceptionHandler(AutenficacaoException se, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(request.getDescription(false));
        errorDetails.setError(se.getMessage());
        errorDetails.setTimestamp(java.time.LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
