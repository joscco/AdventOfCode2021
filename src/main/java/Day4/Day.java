package main.java.Day4;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.ArrayList;
import java.util.List;

public class Day extends AbstractDay {

    private int[] drawnNumbers = new int[]{17, 2, 33, 86, 38, 41, 4, 34, 91, 61, 11, 81, 3, 59, 29, 71, 26, 44, 54, 89, 46, 9, 85, 62, 23, 76, 45, 24, 78, 14, 58, 48, 57, 40, 21, 49, 7, 99, 8, 56, 50, 19, 53, 55, 10, 94, 75, 68, 6, 83, 84, 88, 52, 80, 73, 74, 79, 36, 70, 28, 37, 0, 42, 98, 96, 92, 27, 90, 47, 20, 5, 77, 69, 93, 31, 30, 95, 25, 63, 65, 51, 72, 60, 16, 12, 64, 18, 13, 1, 35, 15, 66, 67, 43, 22, 87, 97, 32, 39, 82};
    private List<BingoBoard> bingoBoards;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        bingoBoards = new ArrayList<>();
        List<String> values = InputUtils.readInBoardLines(getInputStream());
        initializeBoards(values);
        solveFirstQuestion();
        solveSecondQuestion();
    }

    private void initializeBoards(List<String> values) {
        String[][] currentBoard = new String[5][5];
        int currentRow = 0;
        for (String row : values) {
            if (row == null) {
                return;
            } else if ("".equals(row)) {
                bingoBoards.add(new BingoBoard(currentBoard));
                currentRow = 0;
            } else {
                currentBoard[currentRow] = row.trim().split("[ ]+");
                currentRow++;
            }
        }
    }

    private void solveFirstQuestion() {
        for (int currentNumber : drawnNumbers) {
            for (BingoBoard board : bingoBoards) {
                board.checkNumber(currentNumber);
                if (board.hasWon()) {
                    System.out.println("First Win Score: " + board.calculateScore(currentNumber));
                    return;
                }
            }
        }
    }

    private void solveSecondQuestion() {
        for (int currentNumber : drawnNumbers) {
            // Use copy of in order not to destroy your iterator!
            List<BingoBoard> leftBoards = List.copyOf(bingoBoards);
            for (BingoBoard board : leftBoards) {
                board.checkNumber(currentNumber);
                if (board.hasWon()) {
                    if (bingoBoards.size() > 1) {
                        bingoBoards.remove(board);
                    } else if (bingoBoards.get(0) == board) {
                        System.out.println("Last Win Score: " + board.calculateScore(currentNumber));
                        return;
                    }
                }
            }
        }
    }
}
