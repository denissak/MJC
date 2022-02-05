package com.epam.esm.exception;

import java.util.function.Supplier;

public class DuplicateException extends RuntimeException {

    public static Supplier<DuplicateException> certificateExists() {
        String message = String.format("Certificate already exists");
        return () -> new DuplicateException(message);
    }

    public static Supplier<DuplicateException> tagExists() {
        String message = String.format("Tag already exists");
        return () -> new DuplicateException(message);
    }

    public DuplicateException(String message) {
        super(message);
    }

    }
