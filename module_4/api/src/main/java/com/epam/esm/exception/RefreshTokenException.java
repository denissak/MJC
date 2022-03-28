package com.epam.esm.exception;

import java.util.function.Supplier;

public class RefreshTokenException extends RuntimeException{
    public static Supplier<RefreshTokenException> refreshTokenFailed() {
        String message = "Refresh token is bad";
        return () -> new RefreshTokenException(message);
    }

    public RefreshTokenException(String message) {
        super(message);
    }
}
