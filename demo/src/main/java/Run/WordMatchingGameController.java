package Run;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import game_model.WordMatchingGameModel;
import game_model.WordSearchGameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;


public class WordMatchingGameController implements Initializable {

    @FXML
    private GridPane gridPane;
    private int point = 0;
    @FXML
    List<Button> buttonList;
    private Pair<Integer, Integer> currentChoice = null; //row, value
    private Button currentChoiceButton = null;
    private WordMatchingGameModel model;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            model = new WordMatchingGameModel();
            buttonList = new ArrayList<>();
            initTable();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void initTable() {
        for (int i = 0; i < model.getBoard().getRow(); i++) {
            for (int j = 0; j < model.getBoard().getColumn(); j++) {
                Button button = initButton(i, j);
                buttonList.add(button);
                gridPane.add(button, j, i);
            }
        }
    }

    private Button initButton(int row, int col) {
        String s = model.getBoard().getCell(row, col);
        Button button = new Button(s);
        button.setWrapText(true);
        button.setMinSize(250, 50);
        button.setMaxSize(250, 50);
        button.setOnAction(e -> {
            try {
                handleLetterButtonClick(row, col);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        setStyleForButton(button);
        return button;
    }

    private void handleLetterButtonClick(int row, int col) throws IOException {
        Button clickedButton = buttonList.get(row * model.getBoard().getColumn() + col);
        if (currentChoiceButton == null || currentChoice.getKey() == col) {
            if (currentChoiceButton != null) setStyleForButton(currentChoiceButton);
            clickedButton.getStyleClass().add("game-panel-filled");
            currentChoiceButton = clickedButton;
            makeCurrentChoice(row, col);
        } else {
            if (col == 1 && currentChoice.getValue().equals(model.getWordExplain().get(model.getBoard().getCell(row, col)))) {
                deleteEndResetChoice(clickedButton);
                point++;
            } else if (col == 0 && currentChoice.getValue().equals(model.getWordTarget().get(model.getBoard().getCell(row, col)))) {
                deleteEndResetChoice(clickedButton);
                point++;
            } else {
                resetChoice(clickedButton);
            }
        }
        if (point == 6) {
            Stage winningStage = new Stage();
            FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getResource("winning.fxml"));
            Scene scene = new Scene(fxmlLoaderGame.load());
            winningStage.setScene(scene);
            winningStage.show();
            GameWinningController controller = fxmlLoaderGame.getController();
            controller.setPrimaryStage(winningStage);
            controller.setGameController(this);
        }
    }

    public void startNewGame() {
        point = 0;
        try {
            model = new WordMatchingGameModel();
            buttonList = new ArrayList<>();
            initTable();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeCurrentChoice(int row, int col) {
        if (col == 0) {
            currentChoice = new Pair<>(col, model.getWordTarget().get(model.getBoard().getCell(row, col)));
        } else {
            currentChoice = new Pair<>(col, model.getWordExplain().get(model.getBoard().getCell(row, col)));
        }
    }

    private void resetChoice(Button button) {
        setStyleForButton(button);
        setStyleForButton(currentChoiceButton);
        currentChoiceButton = null;
        currentChoice = null;
    }

    private void deleteEndResetChoice(Button button) {
        button.setVisible(false);
        button.setDisable(true);
        currentChoiceButton.setVisible(false);
        currentChoiceButton.setDisable(true);
        currentChoiceButton = null;
        currentChoice = null;
    }

    private void setToNotSelect() {
        currentChoice = null;
        currentChoiceButton = null;
    }

    private void setStyleForButton(Button button) {
        button.getStyleClass().clear();
        button.getStyleClass().add("game-panel");
    }
}