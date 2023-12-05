package Run;

import DictionaryApplication.DictionaryAlerts.DictionaryAlerts;
import DictionaryApplication.Dictionary;
import DictionaryApplication.DictionaryCommandLine;
import DictionaryApplication.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Run.App.dictionaryCommandline;

public class AddController implements Initializable {
    private final Dictionary dictionary = new Dictionary();
//    private final DictionaryCommandLine dictionaryCommandline = new DictionaryCommandLine();
    private final DictionaryAlerts dictionaryAlerts = new DictionaryAlerts();

    @FXML
    private TextField wordTargetInput;
    @FXML
    private TextArea wordExplainInput;
    @FXML
    private TextArea wordSpellingInput;
    @FXML
    private TextArea wordFormInput;
    @FXML
    private TextArea exampleInput;
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
        dictionaryCommandline.setTimeout(() -> successAlert.setVisible(false), 1500);
    }

    /**
     * Reset all word inputs.
     */
    private void resetInput() {
        wordTargetInput.setText("");
        wordExplainInput.setText("");
        wordFormInput.setText("");
        wordSpellingInput.setText("");
        exampleInput.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        dictionaryCommandline.insertFromFile();
        if (wordExplainInput.getText().isEmpty() || wordTargetInput.getText().isEmpty()) {
            addButton.setDisable(true);
        }

        wordTargetInput.setOnKeyTyped(keyEvent -> addButton.setDisable(wordExplainInput.getText().isEmpty() ||
                wordTargetInput.getText().isEmpty()));

        wordExplainInput.setOnKeyTyped(keyEvent -> addButton.setDisable(wordExplainInput.getText().isEmpty() ||
                wordTargetInput.getText().isEmpty()));

        wordFormInput.setOnKeyTyped(keyEvent -> addButton.setDisable(wordExplainInput.getText().isEmpty() ||
                wordTargetInput.getText().isEmpty()));

        wordSpellingInput.setOnKeyTyped(keyEvent -> addButton.setDisable(wordExplainInput.getText().isEmpty() ||
                wordTargetInput.getText().isEmpty()));

        exampleInput.setOnKeyTyped(keyEvent -> addButton.setDisable(wordExplainInput.getText().isEmpty() ||
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
        String englishWord = wordTargetInput.getText();
        String meaning = "+ " + wordExplainInput.getText().trim();
        String form = "* " + wordFormInput.getText().trim();
        String spelling = "/" + wordSpellingInput.getText().trim() + "/";
        String example = "= " + exampleInput.getText().trim();
        Word word = new Word(englishWord, meaning, form, spelling, example);

        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
//                String path = "C:\\Users\\Admin\\IdeaProjects\\Oop_Project\\demo\\src\\main\\resources\\Utils\\dictionaries.txt";
                if (dictionaryCommandline.getWord(word.getWord_target()) != null) {
                    System.out.println("Đã có");
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
                            dictionaryCommandline.getWord(word.getWord_target()).setWordForm(word.getWordForm());
                            dictionaryCommandline.getWord(word.getWord_target()).setSpelling(word.getSpelling());
                            dictionaryCommandline.getWord(word.getWord_target()).setExample(word.getExample());
//                            dictionaryCommandline.dictionaryExportToFile(dictionary, path);
                            showSuccessAlert();
                        }

                        if (selection.get() == insertButton) {
                            String oldMeaning = dictionaryCommandline.getWord(word.getWord_target()).getWord_explain();
                            String oldForm = dictionaryCommandline.getWord(word.getWord_target()).getWordForm();
                            String oldSpelling = dictionaryCommandline.getWord(word.getWord_target()).getSpelling();
                            String oldExample = dictionaryCommandline.getWord(word.getWord_target()).getExample();
//                            dictionary.get(indexOfWord).setWord_explain(oldMeaning + "\n-> " + meaning);
                            dictionaryCommandline.getWord(word.getWord_target()).setWord_explain(oldMeaning + "\n-> " + meaning);
                            dictionaryCommandline.getWord(word.getWord_target()).setWordForm(oldForm + "\n-> " + form);
                            dictionaryCommandline.getWord(word.getWord_target()).setSpelling(oldSpelling + "\n-> " + spelling);
                            dictionaryCommandline.getWord(word.getWord_target()).setExample(oldExample + "\n-> " + example);
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
                addButton.setDisable(true);
                resetInput();
            } else if (option.get() == ButtonType.CANCEL) {
                dictionaryAlerts.showAlertWarning("Warning(Cảnh báo)",
                        "Changes are not recorded(Thay đổi không được ghi nhận)!!!");
            }
        }
    }
}

