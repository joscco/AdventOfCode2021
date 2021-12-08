package main.java.Day4;

public class BingoBoard {

    private Integer[][] board;
    private boolean[][] checkedBoard;
    private int numRows;
    private int numCols;

    public BingoBoard(String[][] board) {
        this.numRows = board.length;
        this.numCols = board[0].length;
        this.board = new Integer[numRows][numCols];
        for (int x = 0; x<numRows; x++){
            for (int y = 0; y<numCols; y++){
                this.board[x][y] = Integer.parseInt(board[x][y]);
            }
        }
        this.checkedBoard = new boolean[numRows][numCols];
    }

    public void checkNumber(int n) {
        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                if (board[x][y] == n) {
                    checkedBoard[x][y] = true;
                    return;
                }
            }
        }
    }

    public int calculateScore(int lastNumber) {
        int preScore = 0;
        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                if(!checkedBoard[x][y]) {
                    preScore += board[x][y];
                }
            }
        }
        return preScore * lastNumber;
    }

    public boolean hasWon() {
        boolean currentWin = checkRows();
        currentWin |= checkColumns();
        return currentWin;
    }

    private boolean checkRows() {
        for (int x = 0; x < numRows; x++) {
            boolean rowIsBingo = true;
            for (int y = 0; y < numCols; y++) {
                rowIsBingo &= checkedBoard[x][y];
            }
            if (rowIsBingo) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int y = 0; y < numCols; y++) {
            boolean colIsBingo = true;
            for (int x = 0; x < numRows; x++) {
                colIsBingo &= checkedBoard[x][y];
            }
            if (colIsBingo) {
                return true;
            }
        }
        return false;
    }

    public void printBoard() {
        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                System.out.print(board[x][y]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

}
