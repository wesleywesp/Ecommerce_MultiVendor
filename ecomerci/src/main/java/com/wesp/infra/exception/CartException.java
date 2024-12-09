package com.wesp.infra.exception;

public class CartException extends RuntimeException {
    public CartException(String message) {
        super(message);
    }
}
