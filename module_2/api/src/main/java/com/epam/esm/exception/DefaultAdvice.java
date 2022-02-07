package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> handleException(DuplicateException e) {
        ResponseError response = new ResponseError(e.getMessage(), StatusError.ENTITY_DUBLICATE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleException(NotFoundException e) {
        ResponseError response = new ResponseError(e.getMessage(), StatusError.ENTITY_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleException(ValidationException e) {
        ResponseError response = new ResponseError(e.getMessage(), StatusError.ENTITY_VALIDATION);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
