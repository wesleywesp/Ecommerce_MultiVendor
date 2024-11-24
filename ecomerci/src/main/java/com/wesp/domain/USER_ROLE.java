package com.wesp.domain;

public enum USER_ROLE {
    ROLE_CUSTOMER("Customer"),
    ROlE_ADMIN("Admin"),
   ROLE_SELLER("Seller");

    private final String role;

    USER_ROLE(String role) {
        this.role = role;
    }

}
