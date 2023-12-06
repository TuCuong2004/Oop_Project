package game_model;

public class Board<T> {
    private Object[][] grid;
    private int size;
    private int row;
    private int column;

    public Board(int size) {
        this.size = size;
        row = column = size;
        this.grid = new Object[size][size];
    }

    public Board(int row, int column) {
        this.row = row;
        this.column = column;
        this.grid = new Object[row][column];
    }

    public T[][] getGrid() {
        return (T[][]) grid;
    }

    public void setCell(int row, int col, T value) {
        grid[row][col] = value;
    }

    public T getCell(int row, int col) {
        return (T) grid[row][col];
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
