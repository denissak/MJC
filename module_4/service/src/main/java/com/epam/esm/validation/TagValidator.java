package com.epam.esm.validation;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ValidationException;

public class TagValidator {

    private static final int MAX_LENGTH_NAME = 20;
    private static final int MIN_LENGTH_NAME = 3;

    public static void validate(TagDto tag) throws ValidationException {
        validateName(tag.getName());
    }

    public static void validateName(String name) throws ValidationException {
        if (name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new ValidationException("Incorrect tag name");
        }
    }
}
