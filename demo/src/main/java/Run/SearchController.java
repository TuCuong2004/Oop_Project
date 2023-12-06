package Run;

import DictionaryApplication.Dictionary;
import DictionaryApplication.DictionaryAlerts.DictionaryAlerts;
import DictionaryApplication.DictionaryManagement;
import DictionaryApplication.GgTranslateTextToSpeech;
import DictionaryApplication.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import static Run.App.dictionaryCommandline;

public class SearchController implements Initializable {

    private final String path = "C:\\Users\\Admin\\IdeaProjects\\Oop_Project\\demo\\src\\main\\resources\\Utils\\dictionaries.txt";
    private final Dictionary dictionary = new Dictionary();
    private final DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private final DictionaryAlerts dictionaryAlerts = new DictionaryAlerts();
    @FXML
    private VBox searchVBox ;
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
    private TextArea explanation /*IPA_pronunciation, partOfSpeech, example*/;
    @FXML
    private ListView<String> listResults;
    @FXML
    private Pane headerOfExplanation;
    @FXML
    private TextField pronounced;
    private Word selectedWord;
    private int indexOfSelectedWord;
    private int firstIndexOfListFound;

    public SearchController() throws FileNotFoundException {
    }

    public void setSearchBox(String wordExplain) {
        searchVBox.getChildren().clear();
//        searchVBox = new VBox();
//        File path = new File(filePath);
//
//        Scanner sc = new Scanner(path);
//        String s;
//        while (sc.hasNextLine()) {
//            s = sc.nextLine();
//            //System.out.println(s);
//            String[] temp = s.split("\t");
//
//            if (temp.length == 2) {
//                super.getWordlist().put(temp[0], new Word(temp[0], temp[1]));
//            } // từ và nghĩa
//
//            if (temp.length == 3) {
//                super.getWordlist().put(temp[0], new Word(temp[0], temp[2], temp[1]));
//            } // từ ,từ loại và nghĩa
//        }
        String[] line = wordExplain.split("\n");
        for(int i=0; i<line.length; i++)
        {
                AnchorPane anchorPane = new AnchorPane();
            if(line[i].contains("*"))
            {

                TextField textField = new TextField();
                textField.setText(line[i]);
                textField.getStyleClass().add("wordFormField");
                textField.setPrefColumnCount( calculatePrefColumnCount(textField.getText(),10));
                textField.setEditable(false);
//                textField.setPrefColumnCount(line[i].length());
//                System.out.println("co chay den");
                anchorPane.getChildren().add(textField);
            }
            else if(line[i].startsWith("-"))
            {
                line[i] = line[i].replace("-","");
                TextField textField1 = new TextField("-");
                textField1.setEditable(false);
                TextField textField2 = new TextField(line[i]);
                textField1.getStyleClass().add("explain-text");
                textField2.getStyleClass().add("explain-text");
                textField1.setPrefColumnCount(calculatePrefColumnCount(textField1.getText(),7));
//                textField1.setPrefColumnCount(1);
                textField2.setPrefColumnCount(calculatePrefColumnCount(textField2.getText(),7));
                textField2.setLayoutX(10);
                anchorPane.getChildren().addAll(textField1,textField2);
            }
            else if(line[i].contains("VD:"))
            {
//                System.out.println(line[i]);
                line[i] = line[i].replace("VD:","");
                TextField textField1 = new TextField("VD:");
                textField1.setEditable(false);
                textField1.setLayoutX(10);
                textField1.getStyleClass().add("explain-text");
                textField1.setStyle("-fx-font-weight: bold;");
                TextField textField2 = new TextField(line[i]);
                textField2.getStyleClass().add("explain-text");
                textField2.setLayoutX(30);
                textField1.setPrefColumnCount(calculatePrefColumnCount(textField1.getText(),7));
                textField2.setPrefColumnCount(calculatePrefColumnCount(textField2.getText(),7));
                anchorPane.getChildren().addAll(textField1,textField2);
            }
            else if(line[i].startsWith("/*"))
            {
                TextField textField = new TextField("/* new add */");
                textField.getStyleClass().add("new-add");
                textField.setEditable(false);
//                textField.setPrefColumnCount(1000);
                anchorPane.getChildren().addAll(textField);
            }
            else
            {
//                AnchorPane anchorPane1 = new AnchorPane();
                TextField textField = new TextField();
                textField.setText(line[i]);
                textField.getStyleClass().add("explain-text");
                textField.setPrefColumnCount( calculatePrefColumnCount(textField.getText(),10));
                textField.setEditable(false);
                anchorPane.getChildren().add(textField);
            }
            searchVBox.getChildren().add(anchorPane);
        }

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
        dictionaryCommandline.setTrie();
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

      //  explanation.setVisible(false);
//        IPA_pronunciation.setVisible(false);
//        partOfSpeech.setVisible(false);
//        example.setVisible(false);

//        saveButton.setVisible(false);
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
        if (list.size() >= 15) {
            listHeader.setText("Có hơn " + list.size() + " kết quả liên quan");
        } else {
            listHeader.setText("Có " + list.size() + " kết quả liên quan");
        }
        if (list.isEmpty()) {
            notAvailableAlert.setVisible(true);
//            setListDefault(firstIndexOfListFound);
//            System.out.println("lỗi ở đây");
        } else /* if available */ {
            notAvailableAlert.setVisible(false);
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
//            if (indexOfSelectedWord == -1) return;
            pronounced.setText(selectedWord.getSpelling());
            englishWord.setText(selectedWord.getWord_target());
//            explanation.setText(selectedWord.getWord_explain());
            headerOfExplanation.setVisible(true);
//            explanation.setVisible(true);
//            explanation.setEditable(false);
//            saveButton.setVisible(false);
            setSearchBox(selectedWord.getWord_explain());
        }
    }

    /**
     * Handle clicking-to-edit-explanation button.
     */
    @FXML
    private void handleClickingEditButton() {
//        explanation.setEditable(true);
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
        if (selectedWord != null) {
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
                dictionaryCommandline.updateWord(selectedWord.getWord_target(),getStringFromVbox());
                dictionaryAlerts.showAlertInformation("Successfully updated!",
                        "Cập nhật nghĩa thành công!");
            } else {
                dictionaryAlerts.showAlertWarning("Update failed or cancelled!",
                        "Cập nhật thất bại hoặc đã bị huỷ!");
            }
            saveButton.setVisible(false);
//            explanation.setEditable(false);
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
//        explanation.setVisible(false);
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
                dictionaryCommandline.deleteWord(selectedWord.getWord_target());
//                renewListOfResultsAfterDeleting();
                dictionaryAlerts.showAlertInformation("Announcement(Thông báo)",
                        "Successfully deleted(Xoá từ thành công)!");
                dictionaryCommandline.setTrie();
            } else {
                dictionaryAlerts.showAlertWarning("Announcement(Thông báo)",
                        "Deletion failed or cancelled(Xoá từ thất bại hoặc đã bị huỷ)!");
            }
        }
    }

    private int calculatePrefColumnCount(String text,int t) {
        // Tính độ rộng của văn bản
        double textWidth = new javafx.scene.text.Text(text).getBoundsInLocal().getWidth();

        // Tính số cột ước lượng dựa trên độ rộng của văn bản
        return (int) Math.ceil(textWidth / t); // 7 là một giá trị thử nghiệm, có thể điều chỉnh
    }

    public String getStringFromVbox () {
        String relust = "";
        for(Node a : searchVBox.getChildren())
        {
            AnchorPane anchorPane = (AnchorPane) a;
            for (Node b : anchorPane.getChildren())
            {
                TextField textField = (TextField) b;
                relust += textField.getText();
            }
            relust += "\n";
        }

        return relust;
    }
}
