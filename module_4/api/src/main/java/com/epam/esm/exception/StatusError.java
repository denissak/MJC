package com.epam.esm.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusError {
    ENTITY_NOT_FOUND(40401),
    ENTITY_DUPLICATE(40402),
    ENTITY_VALIDATION(40403),
    USER_UNAUTHORIZATION(40404);

    @JsonValue
    private final int errorCode;
}
