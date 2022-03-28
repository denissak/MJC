package com.epam.esm.exception;

import java.util.function.Supplier;

public class ValidationException extends RuntimeException {

    private String message;

    public static Supplier<ValidationException> incorrectField(String message) {
        return () -> new ValidationException(message);
    }

    public static Supplier<ValidationException> incorrectField() {
        return ValidationException::new;
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
        super("Validation failed");
    }
}
