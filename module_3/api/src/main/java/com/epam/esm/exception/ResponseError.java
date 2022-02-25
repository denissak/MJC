package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseError {

    private String errorMessage;
    private StatusError errorCode;
}
