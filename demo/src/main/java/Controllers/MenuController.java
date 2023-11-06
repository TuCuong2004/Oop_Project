package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static Run.App.scene_type;
import static Run.App.window;
import static javafx.fxml.FXMLLoader.load;


public class MenuController {
    @FXML
    private Button add_button;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void goTo(ActionEvent event) throws IOException {
        Parent root = load(getClass().getResource("hello-view1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
