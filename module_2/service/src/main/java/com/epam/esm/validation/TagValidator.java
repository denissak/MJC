package com.epam.esm.validation;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ParameterException;

public class TagValidator {

    private static final int MAX_LENGTH_NAME = 20;
    private static final int MIN_LENGTH_NAME = 3;

    public static void validate(TagDto tag) throws ParameterException {
        validateName(tag.getName());
    }

    public static void validateName(String name) throws ParameterException {
        if (name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new ParameterException("Incorrect tag name");
        }
    }
}
