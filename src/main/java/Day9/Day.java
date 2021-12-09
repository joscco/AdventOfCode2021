package main.java.Day9;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;
import main.java.Day5.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day extends AbstractDay {

    int[][] flowField;
    int fieldHeight;
    int fieldWidth;
    int riskCounter;
    List<Tuple<Integer, Integer>> flowPoints;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInStringLines(getInputStream());
        buildFlowField(lines);
        buildFlowPoints();

        solveFirstQuestion();
        solveSecondQuestion();
    }

    private void buildFlowField(List<String> lines) {
        fieldHeight = lines.size();
        fieldWidth = lines.get(0).length();
        flowField = new int[fieldHeight][fieldWidth];
        for (int i = 0; i < fieldHeight; i++) {
            int[] numbers = Arrays.stream(lines.get(i)
                    .split(""))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            flowField[i] = numbers;
        }
    }

    private void buildFlowPoints() {
        riskCounter = 0;
        flowPoints = new ArrayList<>();
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                boolean isMinimal = true;
                if (i >= 1 && flowField[i][j] >= flowField[i - 1][j]) {
                    isMinimal = false;
                }
                if (i < fieldHeight - 1 && flowField[i][j] >= flowField[i + 1][j]) {
                    isMinimal = false;
                }
                if (j >= 1 && flowField[i][j] >= flowField[i][j - 1]) {
                    isMinimal = false;
                }
                if (j < fieldWidth - 1 && flowField[i][j] >= flowField[i][j + 1]) {
                    isMinimal = false;
                }

                if (isMinimal) {
                    riskCounter += (1 + flowField[i][j]);
                    flowPoints.add(new Tuple<>(i, j));
                }
            }
        }
    }

    private void solveFirstQuestion() {
        System.out.println(riskCounter);
    }

    private void solveSecondQuestion() {
        List<Integer> basinSizes = new ArrayList<>();
        for (Tuple<Integer, Integer> flowPoint : flowPoints) {
            int currentBasinSize = 1;
            List<Tuple<Integer, Integer>> potentialNeighbors = getPossibleBasinNeighbors(flowPoint);
            List<Tuple<Integer, Integer>> doneNeighbors = new ArrayList<>();
            while (potentialNeighbors.size() > 0) {
                Tuple<Integer, Integer> potentialNeighbor = potentialNeighbors.get(0);
                currentBasinSize += 1;
                potentialNeighbors.remove(potentialNeighbor);
                doneNeighbors.add(potentialNeighbor);
                for (Tuple<Integer, Integer> newNeighbor : getPossibleBasinNeighbors(potentialNeighbor)) {
                    if (flowField[newNeighbor._0][newNeighbor._1] > flowField[potentialNeighbor._0][potentialNeighbor._1]
                            && !potentialNeighbors.contains(newNeighbor)
                            && !doneNeighbors.contains(newNeighbor)) {
                        potentialNeighbors.add(newNeighbor);
                    }
                }
            }
            basinSizes.add(currentBasinSize);
        }
        basinSizes.sort((a, b) -> b - a);
        System.out.println(basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2));
    }

    private List<Tuple<Integer, Integer>> getPossibleBasinNeighbors(Tuple<Integer, Integer> point) {
        List<Tuple<Integer, Integer>> neighbours = new ArrayList<>();
        if (point._0 >= 1 && flowField[point._0 - 1][point._1] < 9) {
            neighbours.add(new Tuple<>(point._0 - 1, point._1));
        }
        if (point._0 < fieldHeight - 1 && flowField[point._0 + 1][point._1] < 9) {
            neighbours.add(new Tuple<>(point._0 + 1, point._1));
        }
        if (point._1 >= 1 && flowField[point._0][point._1 - 1] < 9) {
            neighbours.add(new Tuple<>(point._0, point._1 - 1));
        }
        if (point._1 < fieldWidth - 1 && flowField[point._0][point._1 + 1] < 9) {
            neighbours.add(new Tuple<>(point._0, point._1 + 1));
        }
        return neighbours;
    }
}
