package main.java.Day2;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.List;

public class Day extends AbstractDay {

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> values = InputUtils.readInStringLines(getInputStream());
        solveFirstQuestion(values);
        solveSecondQuestion(values);
    }

    private static void solveSecondQuestion(List<String> values) {
        int[] myPosition = new int[2];
        int aimPosition = 0;

        for (String value : values) {
            String[] tmp = value.split(" ");
            String direction = tmp[0];
            int steps = Integer.parseInt(tmp[1]);

            switch (direction) {
                case "forward":
                    myPosition[0] += steps;
                    myPosition[1] += aimPosition * steps;
                    break;
                case "down":
                    aimPosition += steps;
                    break;
                case "up":
                    aimPosition -= steps;
                    break;
            }
        }
        System.out.println("End Position: " + myPosition[0] + ", " + myPosition[1]);
        System.out.println("Product: " + myPosition[0] * myPosition[1]);
    }

    private static void solveFirstQuestion(List<String> values) {
        int[] position = new int[2];
        for (String value : values) {
            String[] tmp = value.split(" ");
            String direction = tmp[0];
            int steps = Integer.parseInt(tmp[1]);

            switch (direction) {
                case "forward":
                    position[0] += steps;
                    break;
                case "down":
                    position[1] += steps;
                    break;
                case "up":
                    position[1] -= steps;
                    break;
            }
        }
        System.out.println("End Position: " + position[0] + ", " + position[1]);
        System.out.println("Product: " + position[0] * position[1]);
    }

}
