package main.java.Day11;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.Arrays;
import java.util.List;

public class Day extends AbstractDay {

    private static int maxNumberSteps;
    private static int boardHeight;
    private static int boardWidth;
    private static int flashCounter;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInStringLines(getInputStream());
        int[][] octoBoard = buildOctoBoard(lines);
        solveFirstAndSecondQuestion(octoBoard);
    }

    private int[][] buildOctoBoard(List<String> lines) {
        int[][] octoBoard = new int[10][10];
        for (int i = 0; i < 10; i++) {
            octoBoard[i] = Arrays.stream(lines.get(i).split(""))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return octoBoard;
    }

    private void solveFirstAndSecondQuestion(int[][] octoBoard) {
        maxNumberSteps = 500;
        boardHeight = octoBoard.length;
        boardWidth = octoBoard[0].length;
        flashCounter = 0;

        for (int step = 1; step <= maxNumberSteps; step++) {

            processOctoBoard(octoBoard);

            if (step == 100) {
                System.out.println("Part 1:");
                System.out.println("Flashcounter = " + flashCounter);
            }

            if (isFlashingSynchronized(octoBoard)) {
                System.out.println("Part 2:");
                System.out.println("Flashing Synchronized after " + step + " steps.");
                break;
            }
        }
    }

    private void processOctoBoard(int[][] octoBoard) {

        // Increase Octopus Levels
        for (int x = 0; x < boardHeight; x++) {
            for (int y = 0; y < boardWidth; y++) {
                octoBoard[x][y]++;
            }
        }

        boolean needsMoreFlashSteps;

        do {
            needsMoreFlashSteps = false;
            for (int x = 0; x < boardHeight; x++) {
                for (int y = 0; y < boardWidth; y++) {
                    if (octoBoard[x][y] > 9) {
                        flashCounter++;
                        octoBoard[x][y] = 0;
                        needsMoreFlashSteps |= addAndCheckAdjacentsForFlashes(octoBoard, x, y);
                    }
                }
            }
        } while (needsMoreFlashSteps);
    }

    private boolean isFlashingSynchronized(int[][] octoBoard) {
        boolean flashingSynchronized = true;
        for (int x = 0; x < boardHeight; x++) {
            for (int y = 0; y < boardWidth; y++) {
                flashingSynchronized &= octoBoard[x][y] == 0;
            }
        }
        return flashingSynchronized;
    }

    private boolean addAndCheckAdjacentsForFlashes(int[][] octoBoard, int x, int y) {
        boolean needsNewStep = false;
        for (int n = Math.max(0, x - 1); n <= Math.min(x + 1, boardHeight - 1); n++) {
            for (int m = Math.max(0, y - 1); m <= Math.min(y + 1, boardWidth - 1); m++) {
                if (octoBoard[n][m] != 0) {
                    octoBoard[n][m]++;
                }
                if (octoBoard[n][m] >= 9) {
                    needsNewStep = true;
                }
            }
        }
        return needsNewStep;
    }
}
