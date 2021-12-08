package main.java.Day6;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day extends AbstractDay {

    private Map<Integer, Long> offSpringTable;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        offSpringTable = setUpOffSpringTable(256);
        List<Integer> fish = InputUtils.readInCommaSeparatedInts(getInputStream());
        solveFirstQuestion(fish);
        List<Integer> fish2 = InputUtils.readInCommaSeparatedInts(getInputStream());
        solveSecondQuestion(fish2);
    }

    private Map<Integer, Long> setUpOffSpringTable(int maxNumber) {
        Map<Integer, Long> offSpringTable = new HashMap<>();
        for (int i = -8; i <= 0; i++) {
            offSpringTable.put(i, 0L);
        }

        // If there is one "giving birth" left, we will get one child
        offSpringTable.put(1, 1L);

        for (int n = 1; n < maxNumber; n++) {
            offSpringTable.put(n+1, 1 + offSpringTable.get(n - 6) + offSpringTable.get(n - 8));
        }
        return offSpringTable;
    }

    private void solveFirstQuestion(List<Integer> fishAges) {
        countPopulationInDays(fishAges, 80);
    }

    private void solveSecondQuestion(List<Integer> fishAges) {
        countPopulationInDays(fishAges, 256);
    }

    private void countPopulationInDays(List<Integer> fishAges, int daysLeft) {
        Long fishCounter = 0L;
        for (Integer fishAge : fishAges) {
            fishCounter += 1 + offSpringTable.get(daysLeft - fishAge);
        }
        System.out.println(fishCounter);
    }

}
