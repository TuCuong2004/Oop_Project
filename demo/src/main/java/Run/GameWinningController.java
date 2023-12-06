package Run;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameWinningController {

    @FXML
    private Label winningMessageLabel;

    @FXML
    private Button newGameButton;

    private Stage primaryStage;
    private WordSearchController wordSearchController;

    public void initialize() {
        newGameButton.setOnAction(event -> handleNewGameButton());
    }

    public void setWinningMessage(String message) {
        winningMessageLabel.setText(message);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void handleNewGameButton() {
        primaryStage.close();

//        wordSearchController.startNewGame();
    }

    public void setWordSearchController(WordSearchController wordSearchController) {
        this.wordSearchController = wordSearchController;
    }
}
