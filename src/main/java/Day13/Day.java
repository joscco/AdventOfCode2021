package main.java.Day13;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;
import main.java.Day5.Tuple;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day extends AbstractDay {

    List<String> foldings = List.of(
            "x=655", "y=447", "x=327", "y=223",
            "x=163", "y=111", "x=81", "y=55",
            "x=40", "y=27", "y=13", "y=6");

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInStringLines(getInputStream());
        Set<Tuple<Integer, Integer>> points = buildPoints(lines);
        solveFirstQuestion(points);
        solveSecondQuestion(points);
    }

    private Set<Tuple<Integer, Integer>> buildPoints(List<String> lines) {
        Set<Tuple<Integer, Integer>> points = new HashSet<>();
        for (String line : lines) {
            int[] coordinates = Arrays.stream(line.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            points.add(new Tuple<>(coordinates[0], coordinates[1]));
        }
        return points;
    }

    private void solveFirstQuestion(Set<Tuple<Integer, Integer>> points) {
        points = fold(foldings.get(0), points);

        System.out.println("Part 1:");
        System.out.println(points.size());
    }

    private void solveSecondQuestion(Set<Tuple<Integer, Integer>> points) {
        for (String folding : foldings) {
            points = fold(folding, points);
        }

        System.out.println("Part 2:");
        printPointBoard(points);
    }

    private Set<Tuple<Integer, Integer>> fold(String foldingInstruction, Set<Tuple<Integer, Integer>> points) {
        String[] foldingInstructions = foldingInstruction.split("=");
        String foldDirection = foldingInstructions[0];
        int foldLine = Integer.parseInt(foldingInstructions[1]);
        if ("x".equals(foldDirection)) {
            points = foldHorizontally(points, foldLine);
        } else {
            points = foldVertically(points, foldLine);
        }
        return points;
    }

    private Set<Tuple<Integer, Integer>> foldVertically(Set<Tuple<Integer, Integer>> points, int y) {
        Set<Tuple<Integer, Integer>> newPoints = new HashSet<>(points);
        for (Tuple<Integer, Integer> point : points) {
            if (point._1 > y) {
                newPoints.remove(point);
                newPoints.add(new Tuple<>(point._0, 2 * y - point._1));
            }
        }
        return newPoints;
    }

    private Set<Tuple<Integer, Integer>> foldHorizontally(Set<Tuple<Integer, Integer>> points, int x) {
        Set<Tuple<Integer, Integer>> newPoints = new HashSet<>(points);
        for (Tuple<Integer, Integer> point : points) {
            if (point._0 > x) {
                newPoints.remove(point);
                newPoints.add(new Tuple<>(2 * x - point._0, point._1));
            }
        }
        return newPoints;
    }

    private void printPointBoard(Set<Tuple<Integer, Integer>> points) {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 40; x++) {
                if (points.contains(new Tuple<>(x, y))) {
                    System.out.print("**");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
