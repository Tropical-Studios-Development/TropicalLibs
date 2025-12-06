package org.tropicalstudios.tropicalLibs.utils;

import java.util.HashMap;
import java.util.Map;

public class NumberUtil {

    /**
     * Check whether the provided string represents an integer number
     *
     * @param s the string to check
     * @return true if the string can be parsed as an integer, false otherwise
     */
    public static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Check whether the given number is negative
     *
     * @param number the number to test
     */
    public static boolean isNegativeNumber(int number) {
        return number < 0;
    }

    /**
     * Check whether the provided string represents a negative integer
     *
     * @param s the string to parse
     */
    public static boolean isNegativeNumber(String s) {
        return Integer.parseInt(s) < 0;
    }

    /**
     * Convert an integer to its string representation
     *
     * @param number the integer to convert
     * @return the string representation of the integer
     */
    public static String convertIntToString(int number) {
        return Integer.toString(number);
    }

    /**
     * Convert an integer to a Roman numeral string
     *
     * @param number the integer to convert
     * @return the Roman numeral representation of the integer
     */
    public static String intToRoman(int number) {
        StringBuilder roman = new StringBuilder();
        int i = 0;
        while (number > 0) {
            int k = number / VALUES[i];
            for (int j = 0; j < k; j++) {
                roman.append(SYMBOLS[i]);
                number -= VALUES[i];
            }
            i++;
        }
        return roman.toString();
    }

    /**
     * Convert a Roman numeral string to its integer value
     *
     * @param roman the Roman numeral string (uppercase letters I, V, X, L, C, D, M)
     * @return the integer value of the Roman numeral
     */
    public static int romanToInt(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            int current = romanMap.get(roman.charAt(i));
            if (i < roman.length() - 1 && current < romanMap.get(roman.charAt(i + 1))) {
                result -= current;
            } else {
                result += current;
            }
        }
        return result;
    }

    private static final int[] VALUES = {
            1000, 900, 500, 400,
            100, 90, 50, 40,
            10, 9, 5, 4,
            1
    };
    private static final String[] SYMBOLS = {
            "M", "CM", "D", "CD",
            "C", "XC", "L", "XL",
            "X", "IX", "V", "IV",
            "I"
    };
}
