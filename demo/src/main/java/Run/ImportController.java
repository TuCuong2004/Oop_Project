package Run;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;

import static Run.App.dictionaryCommandline;
import static Run.MenuController.import_alert;

public class ImportController {
    @FXML
    TextField text_field_path;
    @FXML
    Button button_ok;

    @FXML
    void press_button_ok() throws FileNotFoundException {
        try{
            dictionaryCommandline.insertFromFilePath(text_field_path.getText());
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

        import_alert.close();
    }
}
