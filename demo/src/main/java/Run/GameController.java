package Run;

import java.util.*;

import DictionaryApplication.Question;
import DictionaryApplication.Word;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
    TextField textField1;
    @FXML
    TextField textField2;
    @FXML
    TextField textField3;
    @FXML
    TextField textField4;
    @FXML
    TextField textField5;
    @FXML
    TextField textField6;
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
    Map<String, Integer> wordExplain;
    Map<String , Integer> wordTarget;
    Map<Integer, Boolean> wordIsSelect;
    List<String> wordList = new ArrayList<>(dictionaryCommandline.getWordlist().keySet());

    public void insertNewWord() {
        wordIsSelect.put(0, true);
        for(int i=0; i<6; i++) {
            Random random = new Random();
            int randomNumber = 0;
            while (wordIsSelect.get(randomNumber)) {
                randomNumber = random.nextInt(103931);
            }
            wordTarget.put(wordList.get(randomNumber), randomNumber);
            wordExplain.put(dictionaryCommandline.getWordlist().get(wordList.get(randomNumber)).getWord_explain(), randomNumber);
        }
    }
    public void setButton() {
        int i1 = 6;
        int i2 = 6;
        for(String key : wordTarget.keySet())
        {
            if(i1 == 6)
            {
            }
        }
    }
    void gameStart () {

    }
}
