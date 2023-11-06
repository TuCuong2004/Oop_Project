package Run;

import Controllers.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static Stage window;
    public static String scene_type;

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        FXMLLoader fxmlLoader_menu = new FXMLLoader(App.class.getResource("hello-view1.fxml"));
        FXMLLoader fxmlLoader_add = new FXMLLoader(getClass().getResource("hello-view2.fxml"));
        Scene scene_menu = new Scene(fxmlLoader_menu.load(), 650, 450);
        Scene scene_add = new Scene(fxmlLoader_add.load(), 650, 450);
//        if(scene_type.equals("add"))
//        {
//            window.setScene(scene_add);
//        }
        window.setScene(scene_menu);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}