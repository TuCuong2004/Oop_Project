package model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreManager {

    private List<Score> highScores;
    private static final int MAX_HIGH_SCORES = 5;
    private static final String FILE_NAME = "demo\\src\\main\\resources\\Utils\\high_score.txt";

    public HighScoreManager() {
        this.highScores = new ArrayList<>();
        loadHighScores();
    }

    public void addScore(Score score) {
        highScores.add(score);
        sortHighScores();
        trimExcessScores();
        saveHighScores();
    }

    public List<Score> getHighScores() {
        return Collections.unmodifiableList(highScores);
    }

    private void sortHighScores() {
        highScores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));
    }

    private void trimExcessScores() {
        if (highScores.size() > MAX_HIGH_SCORES) {
            highScores.subList(MAX_HIGH_SCORES, highScores.size()).clear();
        }
    }

    private void saveHighScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Score score : highScores) {
                writer.println(score.getScore() + "," + formatDateTime(score.getTimestamp()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHighScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int scoreValue = Integer.parseInt(parts[0]);
                    LocalDateTime timestamp = parseDateTime(parts[1]);
                    Score score = new Score(scoreValue, timestamp);
                    highScores.add(score);
                }
            }
        } catch (IOException ignored) {
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
