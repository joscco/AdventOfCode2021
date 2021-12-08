package main.java.Day8;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.List;

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

    private static void solveFirstQuestion(List<String> lines) {
        int counter = 0;
        for (String line : lines) {
            String subline = line.split(" \\| ")[1].trim();
            String[] codes = subline.split(" ");
            for (String code : codes) {
                if (code.length() == 2 || code.length() == 3 || code.length() == 4 || code.length() == 7) {
                    counter++;
                }
            }
        }
        System.out.println("Part 1");
        System.out.println("Counter:" + counter);
    }

    private static void solveSecondQuestion(List<String> lines) {
        long counter = 0;
        for (String line : lines) {
            String[] splitLine = line.split(" \\| ");
            String[] allNumbers = splitLine[0].trim().split(" ");
            String[] endNumbers = splitLine[1].trim().split(" ");

            NumberDecoder decoder = new NumberDecoder(allNumbers);
            long value = 0;
            for(int i = 0; i<4; i++) {
                value += Math.pow(10, 3-i)*decoder.decode(endNumbers[i]);
            }
            counter += value;
        }
        System.out.println("Part 2");
        System.out.println("Counter:" + counter);
    }
}
