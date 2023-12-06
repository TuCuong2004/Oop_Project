package Run;

import DictionaryApplication.CommandlineApp;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static Run.App.*;
import static javafx.fxml.FXMLLoader.load;


public class MenuController implements Initializable {
    private static final int DURATION = 300;
    private static final int DASHBOARD_WIDTH = 173;

    @FXML
    private Button exitButton;
    @FXML
    private Button closeMenu_button;
    @FXML
    private Button translate_button;
    @FXML
    private Button add_button;
    @FXML
    private Button search_button;
    @FXML
    private Button import_button;
    @FXML
    private Button wordSearchGame_button;
    @FXML
    private Button connectGame_button;
    @FXML
    private Button commandLine_button;
    @FXML
    private VBox dashboard;
    @FXML
    private AnchorPane shadowPane;
    @FXML
    private AnchorPane menuPane;
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
            Parent component;
            component = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToAdd(ActionEvent event) throws IOException {
        showComponent("add.fxml");
    }

    public void goToSearch(ActionEvent event) throws IOException {
        showComponent("search.fxml");

    }

    public void openImportAlert(ActionEvent event) throws IOException {
        import_alert = new Stage();
        FXMLLoader fxmlLoader_menu = new FXMLLoader(getClass().getResource("import.fxml"));
        Scene scene = new Scene(fxmlLoader_menu.load());
        import_alert.setScene(scene);
        import_alert.show();
    }

    public void goToWordSearchGame(ActionEvent event) throws IOException {
        showComponent("word_search_game.fxml");
    }
    public void goToConnectWordGame(ActionEvent event) throws IOException {
        showComponent("word_matching_game.fxml");
    }

    public void gotoTranslate(ActionEvent event) throws IOException {
        showComponent("translateAPI.fxml");
    }

    public void gotoCommandLine(ActionEvent event) throws IOException {
        dictionaryCommandline.saveChanges();
        window.close();
        CommandlineApp commandlineApp = new CommandlineApp();
        commandlineApp.start();
    }

    public void exit(ActionEvent event) throws IOException {
        dictionaryCommandline.saveChanges();
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shadowPane.setVisible(false);
        dashboard.setLayoutX(-DASHBOARD_WIDTH);
        makeMouseEvent(search_button);
        makeMouseEvent(add_button);
        makeMouseEvent(translate_button);
        makeMouseEvent(wordSearchGame_button);
        makeMouseEvent(connectGame_button);
        makeMouseEvent(import_button);
        makeMouseEvent(commandLine_button);
        makeMouseEvent(exitButton);
        makeMouseEvent(closeMenu_button);
    }

    public void setDashboardVisible() {
        shadowPane.setVisible(true);
//        dashboard.setLayoutX(0);
        if (dashboard.getLayoutX() >= 0) {
            return;
        }
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(dashboard);
        transition.setByX(DASHBOARD_WIDTH);
        transition.setDuration(Duration.millis(DURATION));
        transition.play();
        shadowPane.toFront();
        dashboard.toFront();
    }

    public void setDashboardHidden() {
        shadowPane.setVisible(false);
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(dashboard);
        transition.setByX(-DASHBOARD_WIDTH);
        transition.setDuration(Duration.millis(DURATION));
        transition.play();
        dashboard.setLayoutX(-DASHBOARD_WIDTH);
    }

    public void clickMenuButton() throws IOException {
        setDashboardVisible();
    }

    public void clickMenuPaneButton() throws IOException {
        setDashboardHidden();
    }

    public void removeOthers(ActionEvent event) {
        for (int i = 0; i < ((AnchorPane) ((Node) (event.getSource())).getScene().getRoot()).getChildren().size(); i++) {
            if (((AnchorPane) ((Node) (event.getSource())).getScene().getRoot()).getChildren().get(i) != this.dashboard
                    && ((AnchorPane) ((Node) (event.getSource())).getScene().getRoot()).getChildren().get(i) != shadowPane) {
                ((AnchorPane) ((Node) (event.getSource())).getScene().getRoot()).getChildren().remove(i);
                i--;
            }
        }
    }

    private void makeMouseEvent(Button button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> button.setStyle("-fx-background-color: rgb(234,235,237);" +
                        "-fx-background-radius: 6;"));
        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> button.setStyle("-fx-background-color: transparent;" +
                        "-fx-background-radius: 6;"));
    }
}
