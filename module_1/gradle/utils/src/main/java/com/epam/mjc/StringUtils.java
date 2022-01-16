package com.epam.mjc;

import org.apache.commons.lang3.math.NumberUtils;

public class StringUtils {
    public static boolean isPositiveNumber(String str) {
        if (NumberUtils.isCreatable(str)) {
            return Double.parseDouble(str) > 0;
        }
        return false;
    }
}