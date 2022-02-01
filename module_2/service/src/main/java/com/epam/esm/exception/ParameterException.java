package com.epam.esm.exception;

import java.util.function.Supplier;

public class ParameterException extends RuntimeException {

    public ParameterException(String message) {
        super(message);
    }

    public static Supplier<ParameterException> incorrect(String message) {
        return () -> new ParameterException(message);
    }
}
