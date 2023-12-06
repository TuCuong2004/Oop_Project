package game_model;

public class Game<T> {
    protected Board<T> board;

    public Game(Board<T> board) {
        this.board = board;
    }

    public Board<T> getBoard() {
        return board;
    }

    public void setBoard(Board<T> board) {
        this.board = board;
    }
}
