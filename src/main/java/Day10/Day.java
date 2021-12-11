package main.java.Day10;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Day extends AbstractDay {


    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInStringLines(getInputStream());

        solveFirstQuestion(lines);
        solveSecondQuestion(lines);
    }

    private void solveFirstQuestion(List<String> lines) {
        int score = 0;
        for (String line : lines) {
            String corruptBrace = checkCorruptness(line);
            switch (corruptBrace) {
                case ")":
                    score += 3;
                    break;
                case "]":
                    score += 57;
                    break;
                case "}":
                    score += 1197;
                    break;
                case ">":
                    score += 25137;
                    break;
            }
        }
        System.out.println("Part 1:");
        System.out.println(score);
    }

    private void solveSecondQuestion(List<String> lines) {
        List<Long> scores = new ArrayList<>();
        for (String line : lines) {
            String missingPart = checkCorruptness(line);
            if (missingPart.length() > 1) {
                // We add in I to incomplete line endings. So if missingPart.length == 1,
                // the line is corrupt and we can ignore it.
                long score = 0;
                for (int i = 1; i < missingPart.length(); i++) {
                    score *= 5;
                    score += getIncompleteCharValue(missingPart.charAt(i));
                }
                scores.add(score);
            }
        }
        scores.sort(Comparator.reverseOrder());
        System.out.println("Part 2:");
        System.out.println(scores.get((scores.size()-1)/2));
    }

    private long getIncompleteCharValue(char charAt) {
        if (charAt == ')') {
            return 1;
        } else if (charAt == ']') {
            return 2;
        } else if (charAt == '}') {
            return 3;
        } else if (charAt == '>') {
            return 4;
        } else return 0;
    }

    private String checkCorruptness(String line) {
        Stack<Character> charStack = new Stack<>();
        for (char c : line.toCharArray()) {
            if ('(' == c || '[' == c || '{' == c || '<' == c) {
                charStack.push(c);
                continue;
            }

            if (charStack.isEmpty()) {
                return c + "";
            }

            char lastOpening = charStack.lastElement();
            if (c == inverseOf(lastOpening)) {
                charStack.pop();
            } else {
                return c + "";
            }
        }
        StringBuilder closing = new StringBuilder("I");
        for (int i = charStack.size() - 1; i >= 0; i--) {
            closing.append(inverseOf(charStack.get(i)));
        }
        return closing.toString();
    }

    private char inverseOf(char lastOpening) {
        if (lastOpening == '(') {
            return ')';
        } else if (lastOpening == '[') {
            return ']';
        } else if (lastOpening == '{') {
            return '}';
        } else if (lastOpening == '<') {
            return '>';
        } else return '0';
    }
}
