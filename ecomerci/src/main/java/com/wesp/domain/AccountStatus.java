package com.wesp.model;

public enum AccountStatus {
    PENDING_VERIFICATION, // This is the default status when a new account is created
    ACTIVE, // This status is set when the account is verified
    SUSPENDED,// This status is set when the account is temporarily suspended, possibly due to some issues or violations
    DEACTIVATED,// This status is set when the account is deactivated, user may have chosen to deactivate the account
    BANNED,// This status is set when the account is banned when they don't follow the rules
    BLOCKED,// This status is set when the account is blocked
    CLOSED// This status is set when the account is closed, at the user's request
}
