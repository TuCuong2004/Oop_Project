package Run;

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


    private static int[] shuffleArray(int[] array) {
        Random random = new Random();

        // Thực hiện hoán vị từ cuối lên đầu mảng
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            // Hoán vị
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        return array;
    }
    public Word[] getWordList() {
        Word[] wordLits = new Word[6];
        int rand = ThreadLocalRandom.current().nextInt(1,59);
        String[] word = new String[6];
        String[] explain = new String[6];
        for(int i=0; i<6; i++) {
            for (String selectquestion : dictionaryCommandline.getWordlist().keySet()) {
                rand--;
                if (rand == 0) {
                    wordLits[i].setWord_explain(dictionaryCommandline.getWordlist().get(selectquestion).getWord_explain());
                   wordLits[i].setWord_target( dictionaryCommandline.getWordlist().get(selectquestion).getWord_target());
                    break;
                }
            }
        }
        return wordLits;
    }
    public void setButton() {

    }
    void gameStart () {

    }
}
