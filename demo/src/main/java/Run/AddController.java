package Run;

import DictionaryApplication.DictionaryAlerts.DictionaryAlerts;
import DictionaryApplication.DictionaryCommandline.Dictionary;
import DictionaryApplication.DictionaryCommandline.DictionaryManagement;
import DictionaryApplication.DictionaryCommandline.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    private final Dictionary dictionary = new Dictionary();
    private final DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private final DictionaryAlerts dictionaryAlerts = new DictionaryAlerts();

    @FXML
    private TextField wordTargetInput;
    @FXML
    private TextArea wordExplainInput;
    @FXML
    private Label successAlert;
    @FXML
    private Button addButton;

    public AddController() throws FileNotFoundException {
    }

    /**
     * Initialize successful alert attributes(visibility, delay time).
     */
    private void showSuccessAlert() {
        successAlert.setVisible(true);
        dictionaryManagement.setTimeout(() -> successAlert.setVisible(false), 1500);
    }

    /**
     * Reset all word inputs.
     */
    private void resetInput() {
        wordTargetInput.setText("");
        wordExplainInput.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dictionaryManagement.insertFromFilePath();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (wordExplainInput.getText().isEmpty() || wordTargetInput.getText().isEmpty()) {
            addButton.setDisable(true);
        }

        wordTargetInput.setOnKeyTyped(keyEvent -> addButton.setDisable(wordExplainInput.getText().isEmpty() ||
                wordTargetInput.getText().isEmpty()));

        wordExplainInput.setOnKeyTyped(keyEvent -> addButton.setDisable(wordExplainInput.getText().isEmpty() ||
                wordTargetInput.getText().isEmpty()));

        successAlert.setVisible(false);
    }

    /**
     * Handle all types of clicking buttons.
     */
    @FXML
    private void handleClickToAddButton() {
        Alert alertConfirmation = dictionaryAlerts.alertConfirmation("Add this word?",
                "Bạn xác nhận thêm từ này?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        String englishWord = wordTargetInput.getText().trim();
        String meaning = wordExplainInput.getText().trim();

        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                Word word = new Word(englishWord, meaning);
                String path = "src/main/resources/Utils/dictionaries.txt";
                if (dictionary.contains(word)) {
                    int indexOfWord = dictionaryManagement.dictionarySearcher(dictionary, englishWord);
                    Alert selectionAlert = dictionaryAlerts.alertConfirmation("This word has already existed",
                            "Từ này đã tồn tại.\nBạn hãy thay thế hoặc bổ sung nghĩa vừa nhập cho từ này.");
                    selectionAlert.getButtonTypes().clear();

                    ButtonType replaceButton = new ButtonType("Replace(Thay thế)");
                    ButtonType insertButton = new ButtonType("Add(Bổ sung)");
                    selectionAlert.getButtonTypes().addAll(replaceButton, insertButton, ButtonType.CANCEL);

                    Optional<ButtonType> selection = selectionAlert.showAndWait();

                    if (selection.isPresent()) {

                        if (selection.get() == replaceButton) {
                            dictionary.get(indexOfWord).setWord_explain(meaning);
                            dictionaryManagement.dictionaryExportToFile(dictionary, path);
                            showSuccessAlert();
                        }

                        if (selection.get() == insertButton) {
                            String oldMeaning = dictionary.get(indexOfWord).getWord_explain();
                            dictionary.get(indexOfWord).setWord_explain(oldMeaning + "\n-> " + meaning);
                            dictionaryManagement.dictionaryExportToFile(dictionary, path);
                            showSuccessAlert();
                        }

                        if (selection.get() == ButtonType.CANCEL) {
                            dictionaryAlerts.showAlertWarning("Warning(Cảnh báo)",
                                    "Failed to add a new word(Thêm từ mới thất bại)!");
                        }
                    }
                } else {
                    dictionary.add(word);
                    dictionaryManagement.addWord(word, path);
                    showSuccessAlert();
                }
                addButton.setDisable(true);
                resetInput();
            } else if (option.get() == ButtonType.CANCEL) {
                dictionaryAlerts.showAlertWarning("Warning(Cảnh báo)",
                        "Changes are not recorded(Thay đổi không được ghi nhận)!!!");
            }
        }
    }
}

