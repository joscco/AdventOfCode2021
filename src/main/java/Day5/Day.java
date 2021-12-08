package main.java.Day5;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.Arrays;
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

    private void solveFirstQuestion(List<String> lines) {
        WindBoard board = new WindBoard();
        for (String line : lines) {
            String[] pathEnds = line.split(" -> ");
            int[] beginPath = mapStringToPositionArray(pathEnds[0]);
            int[] endPath = mapStringToPositionArray(pathEnds[1]);
            if (beginPath[0] == endPath[0] || beginPath[1] == endPath[1]) {
                board.addPathToBoard(beginPath, endPath);
            }
        }

        board.countTurbulentFields();
    }

    private void solveSecondQuestion(List<String> lines) {
        WindBoard board = new WindBoard();
        for (String line : lines) {
            String[] pathEnds = line.split(" -> ");
            int[] beginPath = mapStringToPositionArray(pathEnds[0]);
            int[] endPath = mapStringToPositionArray(pathEnds[1]);
            board.addPathToBoard(beginPath, endPath);
        }

        board.countTurbulentFields();
    }

    private int[] mapStringToPositionArray(String stringPosition) {
        return Arrays.stream(stringPosition.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
