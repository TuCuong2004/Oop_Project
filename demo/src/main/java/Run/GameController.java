package Run;

import java.util.*;

import DictionaryApplication.Question;
import DictionaryApplication.Word;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
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
    TextField textField7;
    @FXML
    TextField textField8;
    @FXML
    TextField textField9;
    @FXML
    TextField textField10;
    @FXML
    TextField textField11;
    @FXML
    TextField textField12;
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

    Map<String, Integer> wordExplain = new HashMap<>();
    Map<String , Integer> wordTarget = new HashMap<String, Integer>();
    Map<Integer, Boolean> wordIsSelect = new HashMap<>();
    List<String> wordList = new ArrayList<>(dictionaryCommandline.getWordlist().keySet());

    public void insertNewWord() {
        wordIsSelect.put(0, true);
        for(int i=0; i<6; i++) {
            Random random = new Random();
            int randomNumber = 0;
            while (wordIsSelect.containsKey(randomNumber)) {
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
                textField6.setText(key);
            }
            else if(i1 == 5)
            {
                textField5.setText(key);
            }
            else if(i1 ==4)
            {
                textField4.setText(key);
            }
            else if(i1 ==3)
            {
                textField3.setText(key);
            }
            else if(i1 ==2)
            {
                textField2.setText(key);
            }
            else if(i1 ==1)
            {
                textField1.setText(key);
            }
            i1--;
        }

        for(String key : wordExplain.keySet())
        {
            if(i2 == 6)
            {
                textField12.setText(key);
            }
            if (i2 == 5)
            {
                textField11.setText(key);
            }
            if (i2 == 4)
            {
                textField10.setText(key);
            }
            if (i2 == 3)
            {
                textField9.setText(key);
            }
            if (i2 == 2)
            {
                textField8.setText(key);
            }
            if (i2 == 1)
            {
                textField7.setText(key);
            }
            i2--;
        }
    }
    public void gameStart () {
        insertNewWord();
        setButton();
        pane1.setVisible(false);
        textField2.setEditable(false);
    }
}
