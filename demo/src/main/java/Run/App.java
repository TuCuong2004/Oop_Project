package Run;

import DictionaryApplication.DictionaryCommandLine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileNotFoundException;
import java.io.IOException;

public class App extends Application {
    public static Stage window;
    public static String scene_type;
    public static DictionaryCommandLine dictionaryCommandline;

    static {
        try {
            dictionaryCommandline = new DictionaryCommandLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        FXMLLoader fxmlLoader_menu = new FXMLLoader(getClass().getResource("menu.fxml"));
        Scene scene_menu = new Scene(fxmlLoader_menu.load(), 830, 530);
        window.setScene(scene_menu);
        window.show();
        window.setOnCloseRequest(t -> {
            try {
                dictionaryCommandline.saveChange();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}