package com.epam.esm.validation;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CertificateValidator {

    private static final int MAX_LENGTH_NAME = 40;
    private static final int MIN_LENGTH_NAME = 1;
    private static final int MAX_LENGTH_DESCRIPTION = 50;
    private static final int MAX_DURATION = 366;
    private static final int MIN_DURATION = 1;

    public static void validate(CertificateDto certificateDto) throws ValidationException {
        validateName(certificateDto.getName());
        validateDescription(certificateDto.getDescription());
        validatePrice(certificateDto.getPrice());
        validateDuration(certificateDto.getDuration());
    }

    private static void validateName(String name) throws ValidationException {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new ValidationException("Incorrect certificate name");
        }
    }

    private static void validateDescription(String description) throws ValidationException {
        if (description == null || description.length() > MAX_LENGTH_DESCRIPTION) {
            throw new ValidationException("Incorrect certificate description");
        }
    }

    private static void validatePrice(BigDecimal price) throws ValidationException {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Incorrect price");
        }
    }

    private static void validateDuration(int duration) throws ValidationException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new ValidationException("Incorrect certificate duration");
        }
    }
}
