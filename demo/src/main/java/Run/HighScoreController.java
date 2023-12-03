package Run;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.HighScoreManager;
import model.Score;

import java.net.URL;
import java.util.ResourceBundle;

public class HighScoreController implements Initializable {
    @FXML
    private TableView<Score> tableView;

    @FXML
    private TableColumn<Score, Integer> scoreColumn;

    @FXML
    private TableColumn<Score, String> timestampColumn;

    private HighScoreManager highScoreManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        highScoreManager = new HighScoreManager();

        // Configure columns
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("formattedTimestamp"));

        // Load high scores into the TableView
        tableView.setItems(FXCollections.observableArrayList(highScoreManager.getHighScores()));
    }
}

