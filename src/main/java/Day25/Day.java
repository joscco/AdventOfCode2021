package main.java.Day25;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.Arrays;
import java.util.List;

public class Day extends AbstractDay {

    int boardWidth;
    int boardHeight;

    public static void main(String[] args) {
        Day day = new Day();
        long startTime = System.currentTimeMillis();
        day.solveDay();
        long endTime = System.currentTimeMillis();
        System.out.println("Time needed: " + (endTime - startTime) + "ms");
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInStringLines(getInputStream());
        solveFirstQuestion(lines);
    }

    private void solveFirstQuestion(List<String> lines) {
        int[][] board = createBoard(lines);
        int run = 0;
        boolean hasChanged;
        do {
            run++;
            MoveInfo moveInfo = moveCucumbers(board);
            hasChanged = moveInfo.hasChanged();
            board = moveInfo.newBoard();
        } while (hasChanged);

        System.out.println("--- Part 1 ---");
        System.out.println("First non-moving run: " + run);
    }

    private int[][] createBoard(List<String> lines) {
        boardHeight = lines.size();
        boardWidth = lines.get(0).length();
        int[][] result = new int[boardHeight][boardWidth];

        for (int i = 0; i < boardHeight; i++) {
            int[] numberLine = Arrays.stream(lines.get(i).split(""))
                    .mapToInt(this::parseSign)
                    .toArray();
            result[i] = numberLine;
        }
        return result;
    }

    private int parseSign(String sign) {
        return switch (sign) {
            case ">" -> 1;
            case "v" -> -1;
            default -> 0;
        };
    }

    private record MoveInfo(boolean hasChanged, int[][] newBoard) {}

    private MoveInfo moveCucumbers(int[][] initialBoard) {
        MoveInfo eastInfo = moveEast(initialBoard);
        boolean hasChanged = eastInfo.hasChanged();
        int[][] eastMovedBoard = eastInfo.newBoard();

        MoveInfo southInfo = moveSouth(eastMovedBoard);
        hasChanged |= southInfo.hasChanged();
        int[][] southMovedBoard = southInfo.newBoard();

        return new MoveInfo(hasChanged, southMovedBoard);
    }

    private MoveInfo moveEast(int[][] board) {
        boolean hasChanged = false;
        int[][] eastMovedBoard = new int[boardHeight][boardWidth];
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                if (board[y][x % boardWidth] == 1) {
                    if (board[y][(x + 1) % boardWidth] == 0) {
                        eastMovedBoard[y][(x + 1) % boardWidth] = 1;
                        hasChanged = true;
                    } else {
                        eastMovedBoard[y][x] = 1;
                    }
                } else if (board[y][x] == -1) {
                    eastMovedBoard[y][x] = -1;
                }
            }
        }
        return new MoveInfo(hasChanged, eastMovedBoard);
    }

    private MoveInfo moveSouth(int[][] board) {
        boolean hasChanged = false;
        int[][] southMovedBoard = new int[boardHeight][boardWidth];
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                if (board[y][x] == -1) {
                    if (board[(y + 1) % boardHeight][x] == 0) {
                        southMovedBoard[(y + 1) % boardHeight][x] = -1;
                        hasChanged = true;
                    } else {
                        southMovedBoard[y][x] = -1;
                    }
                } else if (board[y][x] == 1) {
                    southMovedBoard[y][x] = 1;
                }
            }
        }
        return new MoveInfo(hasChanged, southMovedBoard);
    }
}
