package main.java.Day8;

import java.util.HashMap;
import java.util.Map;

public class NumberDecoder {

    public Map<Character, Integer> charCount;
    private char high_left;
    private char high_right;
    private char down_left;
    private char down_right;


    public NumberDecoder(String[] values) {
        charCount = new HashMap<>();
        for(String value: values) {
            boolean hasLengthTwo = value.length() == 2;
            for(char c: value.toCharArray()) {
                if(charCount.containsKey(c)) {
                    charCount.replace(c, charCount.get(c) + (hasLengthTwo? 2 : 1));
                } else {
                    // We somehow have to identify the 5er and 6er numbers
                    // This works via knowing the codes of all vertical lines
                    charCount.put(c, (hasLengthTwo? 2 : 1));
                }
            }
        }

        for(char key: charCount.keySet()) {
            if(charCount.get(key) == 4) {
                down_left = key;
            } else if(charCount.get(key) == 10) {
                down_right = key;
            } else if(charCount.get(key) == 6) {
                high_left = key;
            } else if(charCount.get(key) == 9) {
                high_right = key;
            }
        }
    }

    public int decode(String input) {
        if(input.length() == 2) {
            return 1;
        } else if(input.length() == 3) {
            return 7;
        } else if(input.length() == 4) {
            return 4;
        } else if(input.length() == 7) {
            return 8;
        } else {
            // Length is 5 or 6
            return decodeViaBoolean(input);
        }

    }

    private int decodeViaBoolean(String input) {
        boolean[] arr = new boolean[4];
        for (char c : input.toCharArray()) {
            if (c == high_left) {
                arr[0] = true;
            } else if (c == high_right) {
                arr[1] = true;
            } else if (c == down_left) {
                arr[2] = true;
            } else if (c == down_right) {
                arr[3] = true;
            }
        }
        return booleanArrayToNumber(arr);
    }


    private int booleanArrayToNumber(boolean[] arr) {
        if (arr[0] && arr[1] && arr[2] && arr[3]) {
            return 0;
        } else if (!arr[0] && arr[1] && arr[2] && !arr[3]) {
            return 2;
        } else if (!arr[0] && arr[1] && !arr[2] && arr[3]) {
            return 3;
        } else if (arr[0] && !arr[1] && !arr[2] && arr[3] ) {
            return 5;
        } else if (arr[0] && !arr[1] && arr[2] && arr[3]) {
            return 6;
        } else if (arr[0] && arr[1] && !arr[2] && arr[3]) {
            return 9;
        }
        throw new IllegalStateException();
    }
}
