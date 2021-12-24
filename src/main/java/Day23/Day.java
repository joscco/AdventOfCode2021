package main.java.Day23;

import main.java.Common.AbstractDay;

import java.util.ArrayList;
import java.util.List;

public class Day extends AbstractDay {


    public static void main(String[] args) {
        Day day = new Day();
        long startTime = System.currentTimeMillis();
        day.solveDay();
        long endTime = System.currentTimeMillis();
        System.out.println("Time needed: " + (endTime - startTime) + "ms");
    }

    public void solveDay() {
        solveFirstQuestion();
        solveSecondQuestion();
    }

    private void solveFirstQuestion() {
        List<String> hallway = createEmptyHallway();
        List<List<String>> slots = new ArrayList<>();
        slots.add(0, List.of("B", "D"));
        slots.add(1, List.of("A", "A"));
        slots.add(2, List.of("B", "D"));
        slots.add(3, List.of("C", "C"));

        MovingState startState = new MovingState(slots, hallway, 0);
        int minCost = startState.calculateMinCosts();

        System.out.println("--- Part 1 ---");
        System.out.println("Final Min Cost: " + minCost);
    }

    private void solveSecondQuestion() {
        List<String> hallway = createEmptyHallway();
        List<List<String>> slots = new ArrayList<>();
        slots.add(0, List.of("B", "D", "D", "D"));
        slots.add(1, List.of("A", "C", "B", "A"));
        slots.add(2, List.of("B", "B", "A", "D"));
        slots.add(3, List.of("C", "A", "C", "C"));

        MovingState startingState = new MovingState(slots, hallway, 0);
        int minCost = startingState.calculateMinCosts();

        System.out.println("--- Part 2 ---");
        System.out.println("Final Min Cost: " + minCost);
    }

    private List<String> createEmptyHallway() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            result.add("");
        }
        return result;
    }
}
