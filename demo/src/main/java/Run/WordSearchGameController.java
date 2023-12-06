package Run;

import DictionaryApplication.DictionaryAlerts.DictionaryAlerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import game_model.Score;
import game_model.WordSearchGameModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WordSearchGameController implements Initializable {

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
    private Button suggestWordButton;
    @FXML
    private ChoiceBox<String> gridSizeChoiceBox;
    @FXML
    private ChoiceBox<Integer> numberOfWordsChoiceBox;
    private DictionaryAlerts dictionaryAlerts;
    private static Stage winningStage;
    private DropShadow highlightEffect = new DropShadow();

    private List<Button> letterButtons = new ArrayList<>();
    private List<Button> selectedLetterButtons = new ArrayList<>();
    private Pair<Integer, Integer> firstSelectButton = new Pair<>(null, null);
    private StringBuilder selectedLetters = new StringBuilder();
    private Score score;
    private final String[] gridSizeList = {"5x5", "8x8", "10x10"};
    private final Integer[] numberOfWordsList = {5, 8, 10};
    private WordSearchGameModel model;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setAlignment(Pos.CENTER);
        gridSizeChoiceBox.getItems().addAll(gridSizeList);
        gridSizeChoiceBox.setValue("10x10");
        gridSizeChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1)
                -> model.getBoard().setSize(getGridSize(gridSizeList[t1.intValue()])));
        numberOfWordsChoiceBox.getItems().addAll(numberOfWordsList);
        numberOfWordsChoiceBox.setValue(10);
        numberOfWordsChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1)
                -> model.setNumberOfWords(getNumberOfWords(numberOfWordsList[t1.intValue()])));
        highlightEffect.setColor(Color.BLUE);
        dictionaryAlerts = new DictionaryAlerts();
        score = new Score();
        if (model == null) {
            try {
                model = new WordSearchGameModel(getGridSize(gridSizeChoiceBox.getValue()), getNumberOfWords(numberOfWordsChoiceBox.getValue()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            initModel(model);
        }
        initTable();
    }

    public int getGridSize(String gridSize) {
        return switch (gridSize) {
            case "5x5" -> 5;
            case "8x8" -> 8;
            default -> 10;
        };
    }

    public int getNumberOfWords(int numberOfWords) {
        return numberOfWords;
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
                dictionaryAlerts.showAlertInformation("Chúc mừng!", "Bạn đã thấy từ: " + selectedWord);

                if (model.getWords().isEmpty()) {
                    winningStage = new Stage();
                    FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getResource("winning.fxml"));
                    Scene scene = new Scene(fxmlLoaderGame.load());
                    winningStage.setScene(scene);
                    winningStage.show();
                    GameWinningController controller = fxmlLoaderGame.getController();
                    controller.setPrimaryStage(winningStage);
                    controller.setGameController(this);
                }
            } else if (model.checkIfContainWord(selectedWord)) {
                dictionaryAlerts.showAlertInformation("Chưa chính xác!", "Bạn đến gần rồi đấy!");
            } else {
                clearSelection();
                dictionaryAlerts.showAlertInformation("Chưa chính xác!", "Hãy thử lại!");
            }
        } else {
            dictionaryAlerts.showAlertInformation("No Selection", "Please select letters before submitting.");
        }
    }

    @FXML
    private void restartGame() throws FileNotFoundException {
        int size = getGridSize(gridSizeChoiceBox.getValue());
        int numOfWords = getNumberOfWords(numberOfWordsChoiceBox.getValue());
        if (size < numOfWords) {
            dictionaryAlerts.showAlertInformation("Không hợp lệ", "Kích thước bảng cần lớn hơn số từ!");
            return;
        }
        model = new WordSearchGameModel(size, numOfWords);
        initModel(model);
        gridPane.getChildren().clear();
        if (size == 8) {
            gridPane.setLayoutX(90);
            gridPane.setLayoutY(90);
        } else if (size == 5) {
            gridPane.setLayoutX(150);
            gridPane.setLayoutY(150);
        } else {
            gridPane.setLayoutX(50);
            gridPane.setLayoutY(50);
        }
        letterButtons.clear();
        foundWordsLabel.setText("Đã thấy từ: ");
        score.setScore(0);
        updateScoreLabel();
        initTable();
    }

    private void initTable() {
        for (int i = 0; i < model.getBoard().getSize(); i++) {
            for (int j = 0; j < model.getBoard().getSize(); j++) {
                Button button = createLetterButton(i, j);
                letterButtons.add(button);
                gridPane.add(button, j, i);
            }
        }
    }

    public void startNewGame() throws FileNotFoundException {
        model = new WordSearchGameModel(getGridSize(gridSizeChoiceBox.getValue()), getNumberOfWords(numberOfWordsChoiceBox.getValue()));
        initModel(model);
        gridPane.getChildren().clear();
        letterButtons.clear();
        foundWordsLabel.setText("Đã thấy từ: ");
        initTable();
    }

    @FXML
    private void restartWord() {
        clearSelection();
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
        int lastSelectedRow = letterButtons.indexOf(lastSelectedButton) / model.getBoard().getSize();
        int lastSelectedCol = letterButtons.indexOf(lastSelectedButton) % model.getBoard().getSize();

        return Math.abs(row - lastSelectedRow) <= 1 && Math.abs(col - lastSelectedCol) <= 1;
    }

    private void clearSelection() {
        selectedLetters.setLength(0);
        selectedLetterButtons.forEach(this::setStyleForButton);
        selectedLetterButtons.clear();
    }


    private void handleLetterButtonClick(int row, int col) {
        Button clickedButton = letterButtons.get(row * model.getBoard().getSize() + col);
        if (selectedLetterButtons.contains(clickedButton)) {
            return;
        }
        char clickedLetter = (char) model.getBoard().getGrid()[row][col];
        if (!isAdjacent(row, col)) {
            clearSelection();
        }
        clickedButton.setEffect(highlightEffect);
        selectedLetters.append(clickedLetter);
        if (selectedLetterButtons.isEmpty()) {
            firstSelectButton = new Pair<>(row, col);
        }
        selectedLetterButtons.add(clickedButton);
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score.getScore());
    }

    public void initModel(WordSearchGameModel model) {
        this.model = model;
    }

    private Button createLetterButton(int row, int col) {
        char letter = (char) model.getBoard().getGrid()[row][col];
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
                "-fx-background-color: #a7c957; " +
                        "-fx-font-size: 15; " +
                        "-fx-font-family: Arial; " +
                        "-fx-text-fill: #4831d4; " +
                        "-fx-stroke: black; " +
                        "-fx-stroke-width: 2;"
        );
    }
}
