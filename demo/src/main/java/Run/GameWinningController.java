package Run;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class GameWinningController {

    @FXML
    private Label winningMessageLabel;

    @FXML
    private Button newGameButton;

    private Stage primaryStage;
    private WordSearchGameController wordSearchGameController;
    private WordMatchingGameController wordMatchingGameController;

    public void initialize() {
        newGameButton.setOnAction(event -> {
            try {
                handleNewGameButton();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setWinningMessage(String message) {
        winningMessageLabel.setText(message);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void handleNewGameButton() throws FileNotFoundException {
        primaryStage.close();
        if(wordSearchGameController != null) wordSearchGameController.startNewGame();
        else if (wordMatchingGameController != null) {
            wordMatchingGameController.startNewGame();
        }
    }

    public void setGameController(WordSearchGameController wordSearchGameController) {
        this.wordSearchGameController = wordSearchGameController;
    }
    public void setGameController(WordMatchingGameController wordMatchingGameController) {
        this.wordMatchingGameController = wordMatchingGameController;
    }
}
