package com.epam.esm.exception;

import java.util.function.Supplier;

public class AuthorizationException extends RuntimeException{
    public static Supplier<AuthorizationException> authorizationFailed() {
        String message = "Authorization is failed";
        return () -> new AuthorizationException(message);
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
