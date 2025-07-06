package org.tropicalstudios.tropicalLibs.convertors;

import java.util.HashMap;
import java.util.Map;

public class TextConvertor {

    private static final Map<Character, Character> SMALL_CAPS_MAP = new HashMap<>();

    static {
        // Mapping regular letters to small caps Unicode characters
        SMALL_CAPS_MAP.put('A', 'ᴀ');
        SMALL_CAPS_MAP.put('B', 'ʙ');
        SMALL_CAPS_MAP.put('C', 'ᴄ');
        SMALL_CAPS_MAP.put('D', 'ᴅ');
        SMALL_CAPS_MAP.put('E', 'ᴇ');
        SMALL_CAPS_MAP.put('F', 'ꜰ');
        SMALL_CAPS_MAP.put('G', 'ɢ');
        SMALL_CAPS_MAP.put('H', 'ʜ');
        SMALL_CAPS_MAP.put('I', 'ɪ');
        SMALL_CAPS_MAP.put('J', 'ᴊ');
        SMALL_CAPS_MAP.put('K', 'ᴋ');
        SMALL_CAPS_MAP.put('L', 'ʟ');
        SMALL_CAPS_MAP.put('M', 'ᴍ');
        SMALL_CAPS_MAP.put('N', 'ɴ');
        SMALL_CAPS_MAP.put('O', 'ᴏ');
        SMALL_CAPS_MAP.put('P', 'ᴘ');
        SMALL_CAPS_MAP.put('Q', 'Q');
        SMALL_CAPS_MAP.put('R', 'ʀ');
        SMALL_CAPS_MAP.put('S', 'ꜱ');
        SMALL_CAPS_MAP.put('T', 'ᴛ');
        SMALL_CAPS_MAP.put('U', 'ᴜ');
        SMALL_CAPS_MAP.put('V', 'ᴠ');
        SMALL_CAPS_MAP.put('W', 'ᴡ');
        SMALL_CAPS_MAP.put('X', 'x');
        SMALL_CAPS_MAP.put('Y', 'ʏ');
        SMALL_CAPS_MAP.put('Z', 'ᴢ');

        // Lowercase letters to small caps
        SMALL_CAPS_MAP.put('a', 'ᴀ');
        SMALL_CAPS_MAP.put('b', 'ʙ');
        SMALL_CAPS_MAP.put('c', 'ᴄ');
        SMALL_CAPS_MAP.put('d', 'ᴅ');
        SMALL_CAPS_MAP.put('e', 'ᴇ');
        SMALL_CAPS_MAP.put('f', 'ꜰ');
        SMALL_CAPS_MAP.put('g', 'ɢ');
        SMALL_CAPS_MAP.put('h', 'ʜ');
        SMALL_CAPS_MAP.put('i', 'ɪ');
        SMALL_CAPS_MAP.put('j', 'ᴊ');
        SMALL_CAPS_MAP.put('k', 'ᴋ');
        SMALL_CAPS_MAP.put('l', 'ʟ');
        SMALL_CAPS_MAP.put('m', 'ᴍ');
        SMALL_CAPS_MAP.put('n', 'ɴ');
        SMALL_CAPS_MAP.put('o', 'ᴏ');
        SMALL_CAPS_MAP.put('p', 'ᴘ');
        SMALL_CAPS_MAP.put('q', 'q');
        SMALL_CAPS_MAP.put('r', 'ʀ');
        SMALL_CAPS_MAP.put('s', 'ꜱ');
        SMALL_CAPS_MAP.put('t', 'ᴛ');
        SMALL_CAPS_MAP.put('u', 'ᴜ');
        SMALL_CAPS_MAP.put('v', 'ᴠ');
        SMALL_CAPS_MAP.put('w', 'ᴡ');
        SMALL_CAPS_MAP.put('x', 'x');
        SMALL_CAPS_MAP.put('y', 'ʏ');
        SMALL_CAPS_MAP.put('z', 'ᴢ');
    }

    // The text that should be converted to small-caps
    public static String toSmallCaps(String text) {
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            Character smallCap = SMALL_CAPS_MAP.get(c);
            if (smallCap != null) {
                result.append(smallCap);
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}
