package com.epam.esm.exception;

import java.util.function.Supplier;

public class DuplicateException extends RuntimeException {

    public static Supplier<DuplicateException> certificateExists() {
        String message = "Certificate already exists";
        return () -> new DuplicateException(message);
    }

    public static Supplier<DuplicateException> tagExists() {
        String message = "Tag already exists";
        return () -> new DuplicateException(message);
    }

    public static Supplier<DuplicateException> userExists() {
        String message = "User already exists";
        return () -> new DuplicateException(message);
    }

    public DuplicateException(String message) {
        super(message);
    }
}
