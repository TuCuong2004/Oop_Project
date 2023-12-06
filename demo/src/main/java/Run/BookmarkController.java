package Run;

import DictionaryApplication.DictionaryAlerts.DictionaryAlerts;
import DictionaryApplication.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

import static Run.App.dictionaryCommandline;


public class BookmarkController {
    private final DictionaryAlerts dictionaryAlerts = new DictionaryAlerts();
    @FXML
    private TextArea word_target;
    @FXML
    private TextArea word_explain;
    @FXML
    private Button saveButton;
    private Stage bookmarkStage;

    public void setBookMarkText(String word_target, String word_explain, Stage stage) {
        this.word_target.setText(word_target);
        this.word_explain.setText(word_explain);
        this.bookmarkStage = stage;
    }

    @FXML
    private void addToDictionary(ActionEvent event) {
        Word word = new Word(word_target.getText(), word_explain.getText());
        Alert alertConfirmation = dictionaryAlerts.alertConfirmation("Add this word?",
                "Bạn xác nhận thêm từ này?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();

        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                if (dictionaryCommandline.getWord(word.getWord_target()) != null) {
//                    int indexOfWord = dictionaryCommandline.dictionarySearcher(dictionary, englishWord);
                    Alert selectionAlert = dictionaryAlerts.alertConfirmation("This word has already existed",
                            "Từ này đã tồn tại.\nBạn hãy thay thế hoặc bổ sung nghĩa vừa nhập cho từ này.");
                    selectionAlert.getButtonTypes().clear();

                    ButtonType replaceButton = new ButtonType("Replace(Thay thế)");
                    ButtonType insertButton = new ButtonType("Add(Bổ sung)");
                    selectionAlert.getButtonTypes().addAll(replaceButton, insertButton, ButtonType.CANCEL);

                    Optional<ButtonType> selection = selectionAlert.showAndWait();

                    if (selection.isPresent()) {

                        if (selection.get() == replaceButton) {
//                            dictionary.get(indexOfWord).setWord_explain(meaning);
                            dictionaryCommandline.getWord(word.getWord_target()).setWord_explain(word.getWord_explain());
//                            dictionaryCommandline.dictionaryExportToFile(dictionary, path);
                            showSuccessAlert();
                        }

                        if (selection.get() == insertButton) {
                            String oldMeaning = dictionaryCommandline.getWord(word.getWord_target()).getWord_explain();
//                            dictionary.get(indexOfWord).setWord_explain(oldMeaning + "\n-> " + meaning);
                            dictionaryCommandline.getWord(word.getWord_target()).setWord_explain(oldMeaning + "\n-> " + word.getWord_explain());
//                            dictionaryCommandline.dictionaryExportToFile(dictionary, path);
                            showSuccessAlert();
                        }

                        if (selection.get() == ButtonType.CANCEL) {
                            dictionaryAlerts.showAlertWarning("Warning(Cảnh báo)",
                                    "Failed to add a new word(Thêm từ mới thất bại)!");
                        }
                    }
                } else {
//                    dictionary.add(word);
                    dictionaryCommandline.addWord(word);
                    System.out.println("add mà");
                    showSuccessAlert();
                }
//                dictionaryCommandline.setTrie();
            } else if (option.get() == ButtonType.CANCEL) {
                dictionaryAlerts.showAlertWarning("Warning(Cảnh báo)",
                        "Changes are not recorded(Thay đổi không được ghi nhận)!!!");
            }
        }
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Word added");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.setOnCloseRequest(event -> {
            bookmarkStage.close();
        });

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == okButton) {
            bookmarkStage.close();
        }
    }
}
