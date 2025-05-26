package ticpackage;


public final class Board {
    private final int SIZE = 15;
    private final char[][] grid;

    public Board() {
        grid = new char[SIZE][SIZE];
        reset();
    }

    public void reset() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                grid[i][j] = ' ';
    }

    public boolean makeMove(int row, int col, char symbol) {
        if (grid[row][col] == ' ') {
            grid[row][col] = symbol;
            return true;
        }
        return false;
    }

    public boolean checkWin(int row, int col, char symbol) {
        return checkDirection(row, col, symbol, 1, 0) || // Horizontal
               checkDirection(row, col, symbol, 0, 1) || // Vertical
               checkDirection(row, col, symbol, 1, 1) || // Diagonal \
               checkDirection(row, col, symbol, 1, -1);  // Diagonal /
    }

    private boolean checkDirection(int row, int col, char symbol, int dRow, int dCol) {
        int count = 1;
        count += countDirection(row, col, symbol, dRow, dCol);
        count += countDirection(row, col, symbol, -dRow, -dCol);
        return count >= 5;
    }

    private int countDirection(int row, int col, char symbol, int dRow, int dCol) {
        int count = 0;
        int i = row + dRow;
        int j = col + dCol;

        while (i >= 0 && i < SIZE && j >= 0 && j < SIZE && grid[i][j] == symbol) {
            count++;
            i += dRow;
            j += dCol;
        }

        return count;
    }

    public boolean isFull() {
        for (char[] row : grid)
            for (char cell : row)
                if (cell == ' ') return false;
        return true;
    }
}

    

