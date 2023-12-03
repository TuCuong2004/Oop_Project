package Run;

import java.io.FileNotFoundException;
import java.util.*;

import DictionaryApplication.DictionaryManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.Random;

import static Run.App.dictionaryCommandline;

public class GameController {
    @FXML
    List<Button> buttonList;
    @FXML
    Button button1;
    @FXML
    Button button2;
    @FXML
    Button button3;
    @FXML
    Button button4;
    @FXML
    Button button5;
    @FXML
    Button button6;
    @FXML
    Button button7;
    @FXML
    Button button8;
    @FXML
    Button button9;
    @FXML
    Button button10;
    @FXML
    Button button11;
    @FXML
    Button button12;
    @FXML
    TextArea textField1;
    @FXML
    TextArea textField2;
    @FXML
    TextArea textField3;
    @FXML
    TextArea textField4;
    @FXML
    TextArea textField5;
    @FXML
    TextArea textField6;
    @FXML
    TextArea textField7;
    @FXML
    TextArea textField8;
    @FXML
    TextArea textField9;
    @FXML
    TextArea textField10;
    @FXML
    TextArea textField11;
    @FXML
    TextArea textField12;
    @FXML
    Pane pane1;
    @FXML
    Pane pane2;
    @FXML
    Pane pane3;
    @FXML
    Pane pane4;
    @FXML
    Pane pane5;
    @FXML
    Pane pane6;
    @FXML
    Pane pane7;
    @FXML
    Pane pane8;
    @FXML
    Pane pane9;
    @FXML
    Pane pane10;
    @FXML
    Pane pane11;
    @FXML
    Pane pane12;
    int selectWordId = 0;
    int selectPaneId = 1;
    Map<String, Integer> wordExplain = new HashMap<>();
    Map<String, Integer> wordTarget = new HashMap<String, Integer>();
    Map<Integer, Boolean> wordIsSelect = new HashMap<>();
    DictionaryManagement dictionaryManagement = new DictionaryManagement();
//    Map<Integer,Boolean> paneIsVisible = new HashMap<>();

    public void insertNewWord() throws FileNotFoundException {
        dictionaryManagement.insertFromFilePath();
        List<String> wordList = new ArrayList<>(dictionaryManagement.getWordlist().keySet());
        wordIsSelect.put(0, true);
        for (int i = 0; i < 6; i++) {
            Random random = new Random();
            int randomNumber = 0;
            while (wordIsSelect.containsKey(randomNumber)) {
                randomNumber = random.nextInt(2914);
            }
            wordTarget.put(wordList.get(randomNumber), randomNumber);
            wordExplain.put(dictionaryCommandline.getWordlist().get(wordList.get(randomNumber)).getWord_explain(), randomNumber);
        }
    }

    public void setButton() {
        int i1 = 6;
        int i2 = 6;
        for (String key : wordTarget.keySet()) {
            if (i1 == 6) {
                textField6.setText(key);
            } else if (i1 == 5) {
                textField5.setText(key);
            } else if (i1 == 4) {
                textField4.setText(key);
            } else if (i1 == 3) {
                textField3.setText(key);
            } else if (i1 == 2) {
                textField2.setText(key);
            } else if (i1 == 1) {
                textField1.setText(key);
            }
            i1--;
        }

        for (String key : wordExplain.keySet()) {
            if (i2 == 6) {
                textField12.setText(key);
            }
            if (i2 == 5) {
                textField11.setText(key);
            }
            if (i2 == 4) {
                textField10.setText(key);
            }
            if (i2 == 3) {
                textField9.setText(key);
            }
            if (i2 == 2) {
                textField8.setText(key);
            }
            if (i2 == 1) {
                textField7.setText(key);
            }
            i2--;
        }
    }

    public void game() throws FileNotFoundException {

        insertNewWord();
        setButton();
    }

    public void gameStart() throws FileNotFoundException {
        insertNewWord();
        setButton();

//        for(int i=1; i<=12; i++){
//            paneIsVisible.put(i,true);
//        }
        pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(false);
        pane4.setVisible(false);
        pane5.setVisible(false);
        pane6.setVisible(false);
        pane7.setVisible(false);
        pane8.setVisible(false);
        pane9.setVisible(false);
        pane10.setVisible(false);
        pane11.setVisible(false);
        pane12.setVisible(false);
        textField7.setWrapText(true);
        textField8.setWrapText(true);
        textField9.setWrapText(true);
        textField10.setWrapText(true);
        textField11.setWrapText(true);
        textField12.setWrapText(true);

    }

    public Pane getSelectedPane() {
        switch (selectPaneId) {
            case 1:
                return pane1;
            case 2:
                return pane2;
            case 3:
                return pane3;
            case 4:
                return pane4;
            case 5:
                return pane5;
            case 6:
                return pane6;
            case 7:
                return pane7;
            case 8:
                return pane8;
            case 9:
                return pane9;
            case 10:
                return pane10;
            case 11:
                return pane11;
            case 12:
                return pane12;
            default:
                return null;
        }
    }

    public TextArea getSelectedTextField() {
        switch (selectPaneId) {
            case 1:
                return textField1;
            case 2:
                return textField2;
            case 3:
                return textField3;
            case 4:
                return textField4;
            case 5:
                return textField5;
            case 6:
                return textField6;
            case 7:
                return textField7;
            case 8:
                return textField8;
            case 9:
                return textField9;
            case 10:
                return textField10;
            case 11:
                return textField11;
            case 12:
                return textField12;
            default:
                return null;
        }
    }

    public Button getSelectedButton() {
        switch (selectPaneId) {
            case 1:
                return button1;
            case 2:
                return button2;
            case 3:
                return button3;
            case 4:
                return button4;
            case 5:
                return button5;
            case 6:
                return button6;
            case 7:
                return button7;
            case 8:
                return button8;
            case 9:
                return button9;
            case 10:
                return button10;
            case 11:
                return button11;
            case 12:
                return button12;
            default:
                return null;
        }
    }

    public void clickOnButton1() {
        if (wordTarget.get(textField1.getText()) == selectWordId && selectPaneId > 6) {
            textField1.setVisible(false);
            button1.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane1.setVisible(true);
            selectPaneId = 1;
            selectWordId = wordTarget.get(textField1.getText());
        }
    }

    public void clickOnButton2() {
        if (wordTarget.get(textField2.getText()) == selectWordId && selectPaneId > 6) {
            textField2.setVisible(false);
            button2.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane2.setVisible(true);
            selectPaneId = 2;
            selectWordId = wordTarget.get(textField2.getText());
        }
    }

    public void clickOnButton3() {
        if (wordTarget.get(textField3.getText()) == selectWordId && selectPaneId > 6) {
            textField3.setVisible(false);
            button3.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane3.setVisible(true);
            selectPaneId = 3;
            selectWordId = wordTarget.get(textField3.getText());
        }
    }

    public void clickOnButton4() {
        if (wordTarget.get(textField4.getText()) == selectWordId && selectPaneId > 6) {
            textField4.setVisible(false);
            button4.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane4.setVisible(true);
            selectPaneId = 4;
            selectWordId = wordTarget.get(textField4.getText());
        }
    }

    public void clickOnButton5() {
        if (wordTarget.get(textField5.getText()) == selectWordId && selectPaneId > 6) {
            textField5.setVisible(false);
            button5.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane5.setVisible(true);
            selectPaneId = 5;
            selectWordId = wordTarget.get(textField5.getText());
        }
    }

    public void clickOnButton6() {
        if (wordTarget.get(textField6.getText()) == selectWordId && selectPaneId > 6) {
            textField6.setVisible(false);
            button6.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane6.setVisible(true);
            selectPaneId = 6;
            selectWordId = wordTarget.get(textField6.getText());
        }
    }


    public void clickOnButton7() {
        if (wordExplain.get(textField7.getText()) == selectWordId && selectPaneId <= 6) {
            textField7.setVisible(false);
            button7.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane7.setVisible(true);
            selectPaneId = 7;
            selectWordId = wordExplain.get(textField7.getText());
        }
    }

    public void clickOnButton8() {
        if (wordExplain.get(textField8.getText()) == selectWordId && selectPaneId <= 6) {
            textField8.setVisible(false);
            button8.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane8.setVisible(true);
            selectPaneId = 8;
            selectWordId = wordExplain.get(textField8.getText());
        }
    }

    public void clickOnButton9() {
        if (wordExplain.get(textField9.getText()) == selectWordId && selectPaneId <= 6) {
            textField9.setVisible(false);
            button9.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane9.setVisible(true);
            selectPaneId = 9;
            selectWordId = wordExplain.get(textField9.getText());
        }
    }

    public void clickOnButton10() {
        if (wordExplain.get(textField10.getText()) == selectWordId && selectPaneId <= 6) {
            textField10.setVisible(false);
            button10.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane10.setVisible(true);
            selectPaneId = 10;
            selectWordId = wordExplain.get(textField10.getText());
        }
    }

    public void clickOnButton11() {
        if (wordExplain.get(textField11.getText()) == selectWordId && selectPaneId <= 6) {
            textField11.setVisible(false);
            button11.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane11.setVisible(true);
            selectPaneId = 11;
            selectWordId = wordExplain.get(textField11.getText());
        }
    }

    public void clickOnButton12() {
        if (wordExplain.get(textField12.getText()) == selectWordId && selectPaneId <= 6) {
            textField12.setVisible(false);
            button12.setVisible(false);
            getSelectedPane().setVisible(false);
            getSelectedTextField().setVisible(false);
            getSelectedButton().setVisible(false);
        } else {
            getSelectedPane().setVisible(false);
            pane12.setVisible(true);
            selectPaneId = 12;
            selectWordId = wordExplain.get(textField12.getText());
        }
    }


}
