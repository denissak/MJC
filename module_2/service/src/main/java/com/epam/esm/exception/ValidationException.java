package com.epam.esm.exception;

import java.util.function.Supplier;

public class ValidationException extends RuntimeException{

    public static Supplier<ValidationException> incorrectField (String message) {
        return () -> new ValidationException(message);
    }

    public ValidationException(String message) {
        super(message);
    }
}
