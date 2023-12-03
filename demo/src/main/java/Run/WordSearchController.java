package Run;

import DictionaryApplication.DictionaryManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import model.HighScoreManager;
import model.Score;
import model.WordSearchModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WordSearchController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private Button submitButton;
    @FXML
    private Label foundWordsLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Button restartGameButton;
    @FXML
    private Button deleteWordButton;
    @FXML
    private Button highScoreButton;
    @FXML
    private Button suggestWordButton;
    private static Stage highScoreStage;
    private static Stage winningStage;
    private HighScoreManager highScoreManager;
    private DropShadow highlightEffect = new DropShadow();

    private List<Button> letterButtons = new ArrayList<>();
    private List<Button> selectedLetterButtons = new ArrayList<>();
    private Pair<Integer, Integer> firstSelectButton = new Pair<>(null, null);
    private StringBuilder selectedLetters = new StringBuilder();
    private Score score;

    private WordSearchModel model;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setAlignment(Pos.CENTER);
        highlightEffect.setColor(javafx.scene.paint.Color.YELLOW);
        highScoreManager = new HighScoreManager();
        score = new Score();
        if (model == null) {
            model = new WordSearchModel();
            initModel(model);
        }

        for (int i = 0; i < WordSearchModel.GRID_SIZE; i++) {
            for (int j = 0; j < WordSearchModel.GRID_SIZE; j++) {
                Button button = createLetterButton(i, j);
                letterButtons.add(button);
                gridPane.add(button, j, i);
            }
        }
    }

    @FXML
    private void handleSubmitButtonClick() throws IOException {
        if (selectedLetters.length() > 0) {
            String selectedWord = selectedLetters.toString();

            boolean isCorrectWord = model.checkIfCorrectWord(selectedWord);

            if (isCorrectWord) {
                foundWordsLabel.setText(foundWordsLabel.getText() + "\n" + selectedWord);
                clearSelection();
                model.getSuggestCells().removeIf(pair ->
                        pair.getKey().equals(firstSelectButton.getKey()) &&
                                pair.getValue().equals(firstSelectButton.getValue())
                );
                score.increaseScore();
                updateScoreLabel();
                showAlert("Congratulations!", "You found the word: " + selectedWord);

                if(model.getWords().isEmpty()){
                    winningStage = new Stage();
                    FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getResource("winning.fxml"));
                    Scene scene = new Scene(fxmlLoaderGame.load());
                    winningStage.setScene(scene);
                    winningStage.show();
                    GameWinningController controller = fxmlLoaderGame.getController();
                    controller.setPrimaryStage(winningStage);
                    controller.setWordSearchController(this);
                }
            } else {
                showAlert("Incorrect!", "Try again.");
            }
        } else {
            showAlert("No Selection", "Please select letters before submitting.");
        }
    }

    @FXML
    private void restartGame() {
        model = new WordSearchModel();
        initModel(model);

        gridPane.getChildren().clear();
        letterButtons.clear();
        foundWordsLabel.setText("Đã thấy từ: ");
        highScoreManager.getHighScores().add(score);
        score.setScore(0);
        updateScoreLabel();

        for (int i = 0; i < WordSearchModel.GRID_SIZE; i++) {
            for (int j = 0; j < WordSearchModel.GRID_SIZE; j++) {
                Button button = createLetterButton(i, j);
                letterButtons.add(button);
                gridPane.add(button, j, i);
            }
        }
    }

    public void startNewGame(){
        model = new WordSearchModel();
        initModel(model);

        gridPane.getChildren().clear();
        letterButtons.clear();
        foundWordsLabel.setText("Đã thấy từ: ");

        for (int i = 0; i < WordSearchModel.GRID_SIZE; i++) {
            for (int j = 0; j < WordSearchModel.GRID_SIZE; j++) {
                Button button = createLetterButton(i, j);
                letterButtons.add(button);
                gridPane.add(button, j, i);
            }
        }
    }

    @FXML
    private void restartWord() {
        clearSelection();
    }

    @FXML
    private void showHighScores() throws IOException {
        highScoreStage = new Stage();
        FXMLLoader fxmlLoader_menu = new FXMLLoader(getClass().getResource("high_score.fxml"));
        Scene scene = new Scene(fxmlLoader_menu.load());
        highScoreStage.setScene(scene);
        highScoreStage.show();
    }

    @FXML
    private void showSuggest() {
        Random random = new Random();
        int randomIndex = random.nextInt(model.getSuggestCells().size());
        Pair<Integer, Integer> randomSuggest = model.getSuggestCells().get(randomIndex);
        handleLetterButtonClick(randomSuggest.getKey(), randomSuggest.getValue());
    }

    private boolean isAdjacent(int row, int col) {
        if (selectedLetterButtons.isEmpty()) {
            return true;
        }

        Button lastSelectedButton = selectedLetterButtons.get(selectedLetterButtons.size() - 1);
        int lastSelectedRow = letterButtons.indexOf(lastSelectedButton) / WordSearchModel.GRID_SIZE;
        int lastSelectedCol = letterButtons.indexOf(lastSelectedButton) % WordSearchModel.GRID_SIZE;

        return Math.abs(row - lastSelectedRow) <= 1 && Math.abs(col - lastSelectedCol) <= 1;
    }

    private void clearSelection() {
        selectedLetters.setLength(0);
        selectedLetterButtons.forEach(this::setStyleForButton);
        selectedLetterButtons.clear();
        System.out.println("Selection cleared");
    }


    private void handleLetterButtonClick(int row, int col) {
        Button clickedButton = letterButtons.get(row * WordSearchModel.GRID_SIZE + col);
        if(selectedLetterButtons.contains(clickedButton)){
            return;
        }
        char clickedLetter = model.getBoard().getGrid()[row][col];
        if (!isAdjacent(row, col)) {
            clearSelection();
        }
        clickedButton.setEffect(highlightEffect);
        selectedLetters.append(clickedLetter);
        if (selectedLetterButtons.isEmpty()) {
            firstSelectButton = new Pair<>(row, col);
        }
        selectedLetterButtons.add(clickedButton);

        System.out.println("Selected Letters: " + selectedLetters.toString());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateButtonLabels() {
        for (int i = 0; i < WordSearchModel.GRID_SIZE; i++) {
            for (int j = 0; j < WordSearchModel.GRID_SIZE; j++) {
                char letter = model.getBoard().getGrid()[i][j];
                letterButtons.get(i * WordSearchModel.GRID_SIZE + j).setText(Character.toString(letter));
            }
        }
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score.getScore());
    }

    public void initModel(WordSearchModel model) {
        this.model = model;
    }

    private Button createLetterButton(int row, int col) {
        char letter = model.getBoard().getGrid()[row][col];
        Button button = new Button(Character.toString(letter));
        button.setMinSize(35, 35);
        button.setMaxSize(35, 35);
        button.setOnAction(e -> handleLetterButtonClick(row, col));
        setStyleForButton(button);
        return button;
    }

    private void setStyleForButton(Button button) {
        button.setEffect(null);
        button.setStyle(
                "-fx-background-color: rgba(0, 0, 255, 0.1); " +
                        "-fx-font-size: 15; " +
                        "-fx-font-family: Arial; " +
                        "-fx-text-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%); " +
                        "-fx-stroke: black; " +
                        "-fx-stroke-width: 1;"
        );
    }
}
