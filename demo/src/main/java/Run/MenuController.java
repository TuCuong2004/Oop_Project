package Run;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static Run.App.scene_type;
import static Run.App.window;
import static javafx.fxml.FXMLLoader.load;


public class MenuController {
    @FXML
    private Button add_button;
    @FXML
    private Button search_button;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public MenuController() {
    }

    public void goToAdd(ActionEvent event) throws IOException {
       stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader_menu = new FXMLLoader(getClass().getResource("add.fxml"));
        Scene scene = new Scene(fxmlLoader_menu.load());
        stage.setScene(scene);
        stage.show();
    }

    public void goToSearch(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader_menu = new FXMLLoader(getClass().getResource("search.fxml"));
        Scene scene = new Scene(fxmlLoader_menu.load());
        stage.setScene(scene);
        stage.show();
    }
}
