package game_model;

import java.time.LocalDateTime;

public class Score {
    private int score;
    private LocalDateTime timestamp;

    public Score(int score, LocalDateTime timestamp) {
        this.score = score;
        this.timestamp = timestamp;
    }

    public Score() {
        this.score = 0;
    }

    public void increaseScore(){
        score++;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
