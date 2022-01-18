package com.epam.mjc;

public class Utils {
    public static boolean isAllPositiveNumbers(String... str) {
        for (String it : str) {
            if (!StringUtils.isPositiveNumber(it)) {
                return false;
            }
        }
        return true;
    }
}
