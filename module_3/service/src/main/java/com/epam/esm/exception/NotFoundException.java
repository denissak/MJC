package com.epam.esm.exception;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {

    private long id;

    public static Supplier<NotFoundException> notFoundWithCertificateId(long id) {
        String message = String.format("There is no certificate with id = %s", id);
        return () -> new NotFoundException(message, id);
    }

    public static Supplier<NotFoundException> notFoundWithTagId(long id) {
        String message = String.format("There is no tag with id = %s", id);
        return () -> new NotFoundException(message, id);
    }

    public static Supplier<NotFoundException> notFoundWithUserId(long id) {
        String message = String.format("There is no user with id = %s", id);
        return () -> new NotFoundException(message, id);
    }

    public static Supplier<NotFoundException> notFoundWithOrderId(long id) {
        String message = String.format("There is no order with id = %s", id);
        return () -> new NotFoundException(message, id);
    }

    public NotFoundException(String message, long id) {
        super(message);
        this.id = id;
    }
}
