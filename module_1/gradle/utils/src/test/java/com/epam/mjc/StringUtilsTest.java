package com.epam.mjc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

    @Test
    void isTestPositiveNumber() {
        boolean result = StringUtils.isPositiveNumber("1.2");
        Assertions.assertTrue(result);
    }

    @Test
    void isTestNegativeNumber() {
        boolean result = StringUtils.isPositiveNumber("-12");
        Assertions.assertFalse(result);
    }

    @Test
    void isTestSomeWord() {
        boolean result = StringUtils.isPositiveNumber("sd");
        Assertions.assertFalse(result);
    }
}
