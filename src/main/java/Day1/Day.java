package main.java.Day1;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.List;

public class Day extends AbstractDay {

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<Integer> values = InputUtils.readInIntLines(getInputStream());
        solveFirstQuestion(values);
        solveSecondQuestion(values);
    }

    private void solveSecondQuestion(List<Integer> values) {
        // We have a_n + a_{n+1} + a_{n+2} < a_{n+1} + a_{n+2} + a_{n+3}
        // iff a_n < a_{n+3}.
        int counter2 = 0;
        for (int i = 3; i < values.size(); i++) {
            if (values.get(i) > values.get(i - 3)) {
                counter2++;
            }
        }
        System.out.println("Solution for 2:");
        System.out.println("Numbers increased intervals: " + counter2);
    }

    private void solveFirstQuestion(List<Integer> values) {
        int counter = 0;
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) > values.get(i - 1)) {
                counter++;
            }
        }
        System.out.println("Solution for 1:");
        System.out.println("Numbers increased: " + counter);
    }
}
