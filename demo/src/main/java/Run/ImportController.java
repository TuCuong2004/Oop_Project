package Run;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    Alert alert;

    @FXML
    void press_button_ok() {
        try{
            System.out.println(text_field_path.getText());
            dictionaryCommandline.insertFromFilePath(text_field_path.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Thêm thành công!");
            alert.showAndWait();
            import_alert.close();
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Không tìm thấy file!");
            alert.showAndWait();
        }
    }
}
