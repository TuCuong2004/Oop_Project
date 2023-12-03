package model;

public class Board {
    private char[][] grid;

    public Board(int size) {
        this.grid = new char[size][size];
    }

    public char[][] getGrid() {
        return grid;
    }

    public void setCell(int row, int col, char value) {
        grid[row][col] = value;
    }

    public char getCell(int row, int col){
        return grid[row][col];
    }

    public void print(){
        for (char[] chars : grid) {
            for (int j = 0; j < grid.length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.println();
        }
    }
}
