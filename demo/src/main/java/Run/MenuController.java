package Run;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static Run.App.scene_type;
import static Run.App.window;
import static javafx.fxml.FXMLLoader.load;


public class MenuController {

    @FXML
    Button translate_button;
    @FXML
    private Button add_button;
    @FXML
    private Button search_button;
    @FXML
    private Button import_button;
    @FXML
    private Button game_button;
    private Stage stage;
    public static Stage import_alert;
    private Scene scene;
    private Parent root;

    public MenuController() {
    }

    @FXML
    private AnchorPane container;
    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    @FXML
    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            setNode(component);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public void goToAdd(ActionEvent event) throws IOException {
//       stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        FXMLLoader fxmlLoader_menu = new FXMLLoader(getClass().getResource("add.fxml"));
//        Scene scene = new Scene(fxmlLoader_menu.load());
//        stage.setScene(scene);
//        stage.show();
        showComponent("add.fxml");
    }

    public void goToSearch(ActionEvent event) throws IOException {
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        FXMLLoader fxmlLoader_menu = new FXMLLoader(getClass().getResource("search.fxml"));
//        Scene scene = new Scene(fxmlLoader_menu.load());
//        stage.setScene(scene);
//        stage.show();
        showComponent("search.fxml");

    }

    public  void openImportAlert(ActionEvent event) throws IOException {
        import_alert = new Stage();
        FXMLLoader fxmlLoader_menu = new FXMLLoader(getClass().getResource("import.fxml"));
        Scene scene = new Scene(fxmlLoader_menu.load());
        import_alert.setScene(scene);
        import_alert.show();
    }

    public void goToGame(ActionEvent event) throws IOException {
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        FXMLLoader fxmlLoader_menu = new FXMLLoader(getClass().getResource("game.fxml"));
//        Scene scene = new Scene(fxmlLoader_menu.load());
//        stage.setScene(scene);
//        stage.show();
        showComponent("game.fxml");
    }

    public void gotoTranslate(ActionEvent event) throws IOException {
        showComponent("translateAPI.fxml");
    }

}
