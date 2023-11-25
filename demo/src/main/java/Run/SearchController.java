package Run;

import DictionaryApplication.Dictionary;
import DictionaryApplication.DictionaryAlerts.DictionaryAlerts;
import DictionaryApplication.DictionaryManagement;
import DictionaryApplication.GgTranslateTextToSpeech;
import DictionaryApplication.Word;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Run.App.dictionaryCommandline;

public class SearchController implements Initializable {

    private final String path = "C:\\Users\\Admin\\IdeaProjects\\Oop_Project\\demo\\src\\main\\resources\\Utils\\dictionaries.txt";
    private final Dictionary dictionary = new Dictionary();
    private final DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private final DictionaryAlerts dictionaryAlerts = new DictionaryAlerts();
    @FXML
    public AnchorPane searchResult;
    @FXML
    public Pane searchBox;
    @FXML
    public Button volumeButton;
    public ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private TextField searchTerm;
    @FXML
    private Button cancelButton, saveButton;
    @FXML
    private Label englishWord, listHeader, notAvailableAlert;
    @FXML
    private TextArea explanation;
    @FXML
    private ListView<String> listResults;
    @FXML
    private Pane headerOfExplanation;
    private  Word selectedWord ;
    private int indexOfSelectedWord;
    private int firstIndexOfListFound;

    public SearchController() throws FileNotFoundException {
    }

    /**
     * Set default format of list.
     */
//    private void setListDefault(int index) {
//        list.clear();
//        if (index == 0) {
//            listHeader.setText("The first 20 words(20 từ đầu tiên)");
//        } else {
//            listHeader.setText("Related results(Kết quả liên quan)");
//        }
//        //Add 20 related results to list.
//        for (int i = index; i < index + 20; ++i) {
//            list.add(dictionary.get(i).getWord_target());
//        }
//        //Bring the searching results from list to result list.
//        listResults.setItems(list);
//        //Set the text of the word target and explanation of the results.
//        englishWord.setText(dictionary.get(index).getWord_target());
//        englishWord.setText(dictionary.get(index).getWord_explain());
//    }

    /**
     * Override initialize method.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //dictionaryManagement.insertFromFile(dictionary, path);
//        try {
//            dictionaryCommandline.insertFromFilePath();
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found error!");
//        }
//        System.out.println(dictionary.size());
        dictionaryManagement.setTrie();
//        setListDefault(0);
//
        searchTerm.setOnKeyTyped(keyEvent -> {
            if (searchTerm.getText().isEmpty()) {
                cancelButton.setVisible(false);
//                setListDefault(0);
            } else {
                cancelButton.setVisible(true);
                handleOnKeyTyped();
            }
        });
//
        cancelButton.setOnAction(actionEvent -> {
            searchTerm.clear();
            notAvailableAlert.setVisible(false);
            cancelButton.setVisible(false);
//            setListDefault(0);
        });

        explanation.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        notAvailableAlert.setVisible(false);
    }

    /**
     * Handle typing keywords for searching.
     */
    @FXML
    private void handleOnKeyTyped() {
        list.clear();
        String searchKey = searchTerm.getText().trim();
        //Search results using search key, then add them to list.
        list = dictionaryCommandline.dictionarySearch(searchKey);
        //If the word searched is not available in dictionary.
        if (list.isEmpty()) {
            notAvailableAlert.setVisible(true);
//            setListDefault(firstIndexOfListFound);
//            System.out.println("lỗi ở đây");
        } else /* if available */ {
            notAvailableAlert.setVisible(false);
            listHeader.setText("Result available(Kết quả tìm kiếm có sẵn)!");
            listResults.setItems(list);
//            firstIndexOfListFound = dictionaryManagement.dictionarySearcher(dictionary, list.get(0));
        }
    }

    /**
     * Handle showing information of a word when it is clicked.
     */
    @FXML
    private void handleMouseClickAWord() {
        String selectedWordTarget = listResults.getSelectionModel().getSelectedItem();
        if (selectedWordTarget != null) {
            selectedWord = dictionaryCommandline.getWord(selectedWordTarget);
            if (indexOfSelectedWord == -1) return;
            englishWord.setText(selectedWord.getWord_target());
            explanation.setText(selectedWord.getWord_explain());
            headerOfExplanation.setVisible(true);
            explanation.setVisible(true);
            explanation.setEditable(false);
            saveButton.setVisible(false);
        }
    }

    /**
     * Handle clicking-to-edit-explanation button.
     */
    @FXML
    private void handleClickingEditButton() {
        explanation.setEditable(true);
        saveButton.setVisible(true);
        dictionaryAlerts.showAlertWarning("Warning(Cảnh báo)",
                "Explanation editing allowed\n" +
                        "Bạn đã cho phép chỉnh sửa nghĩa của từ này!");
    }

    /**
     * Handle sound when clicking to select a word.
     */
    @FXML
    private void handleClickingSoundButton() {
        if(selectedWord != null) {
            GgTranslateTextToSpeech.play(selectedWord.getWord_target(), "en");
        }
    }

    /**
     * Handle word meaning updating function when clicking.
     */
    @FXML
    private void handleClickingUpdateButton() {
        Alert alertConfirmation = dictionaryAlerts.alertConfirmation("Update meaning of this word?",
                "Bạn muốn cập nhật nghĩa của từ này?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                dictionaryManagement.updateWord(selectedWord.getWord_target(),explanation.getText());
                dictionaryAlerts.showAlertInformation("Successfully updated!",
                        "Cập nhật nghĩa thành công!");
            } else {
                dictionaryAlerts.showAlertWarning("Update failed or cancelled!",
                        "Cập nhật thất bại hoặc đã bị huỷ!");
            }
            saveButton.setVisible(false);
            explanation.setEditable(false);
        }
    }

    /**
     * Renew the list of results after deleting a word.
     */
    private void renewListOfResultsAfterDeleting() {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).equals(englishWord.getText())) {
                list.remove(i);
                break;
            }
        }
        listResults.setItems(list);
        listHeader.setVisible(false);
        explanation.setVisible(false);
    }

    /**
     * Handle word deleting function when clicking.
     */
    @FXML
    private void handleClickingDeleteButton() {
        Alert alertWarning = dictionaryAlerts.alertWarning("Deleting alert",
                "Bạn chắc chắn muốn xoá từ này?");
        alertWarning.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> option = alertWarning.showAndWait();
        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                dictionaryManagement.deleteWord(selectedWord.getWord_target());
//                renewListOfResultsAfterDeleting();
                dictionaryAlerts.showAlertInformation("Announcement(Thông báo)",
                        "Successfully deleted(Xoá từ thành công)!");
            } else {
                dictionaryAlerts.showAlertWarning("Announcement(Thông báo)",
                        "Deletion failed or cancelled(Xoá từ thất bại hoặc đã bị huỷ)!");
            }
        }
    }
}
