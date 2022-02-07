package com.epam.esm.exception;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {

    private long id;

    public static Supplier<NotFoundException> notFoundWithCertificateId(long id) {
        String message = String.format("There is no certificate with id = %s", id);
        return () -> new NotFoundException(message, id);
    }

    public static Supplier<NotFoundException> notFoundCertificate() {
        String message = "Сertificate not found";
        return () -> new NotFoundException(message);
    }

    public static Supplier<NotFoundException> notFoundWithTagId(long id) {
        String message = String.format("There is no tag with id = %s", id);
        return () -> new NotFoundException(message, id);
    }

    public static Supplier<NotFoundException> notFoundTag() {
        String message = "Tag not found";
        return () -> new NotFoundException(message);
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, long id) {
        super(message);
        this.id = id;
    }
}