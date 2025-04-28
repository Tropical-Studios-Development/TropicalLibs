package org.tropicalstudios.tropicalLibs.utils;

import java.util.HashMap;
import java.util.Map;

public class NumberUtil {

    // Check if a string is a number
    public static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Check if a number is negative
    public static boolean isNegativeNumber(int number) {
        return number < 0;
    }

    // Check if a string is a negative number
    public static boolean isNegativeNumber(String s) {
        return Integer.parseInt(s) < 0;
    }

    // Convert an int to a string
    public static String convertIntToString(int number) {
        return Integer.toString(number);
    }

    // Convert normal numbers to roman numbers
    public static String intToRoman(int num) {
        StringBuilder roman = new StringBuilder();
        int i = 0;
        while (num > 0) {
            int k = num / VALUES[i];
            for (int j = 0; j < k; j++) {
                roman.append(SYMBOLS[i]);
                num -= VALUES[i];
            }
            i++;
        }
        return roman.toString();
    }

    // Convert roman numbers to normal numbers
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
