package com.epam.esm.validation;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    private static final int MAX_LENGTH_NAME = 40;
    private static final int MIN_LENGTH_NAME = 1;

    public static void validate(OrderDto orderDto) throws ValidationException {
        validateName(orderDto.getName());
        validatePrice(orderDto.getCost());
    }

    private static void validateName(String name) throws ValidationException {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new ValidationException("Incorrect certificate name");
        }
    }

    private static void validatePrice(Double cost) throws ValidationException {
        if (cost < 0) {
            throw new ValidationException("Incorrect cost");
        }
    }
}
