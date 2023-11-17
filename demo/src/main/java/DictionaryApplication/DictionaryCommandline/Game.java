package DictionaryApplication.DictionaryCommandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    private final HashMap<Question, String> questionlist = new HashMap<>();

    public HashMap<Question, String> getQuestionlist() {
        return questionlist;
    }

    public void insertFromFilePath() throws FileNotFoundException {
        String filePath = "dsrc\\main\\java\\com\\example\\demo1\\question.txt";
        File path = new File(filePath);

        Scanner sc = new Scanner(path);

        while (sc.hasNextLine()) {
            Question temp = new Question();
            temp.setQuestion(sc.nextLine());
//            System.out.println(temp.getQuestion());
            temp.setSelect(sc.nextLine());
//            System.out.println(temp.getSelect());
            temp.setAnswer(sc.nextLine());
           // System.out.println(temp.getAnswer());
            questionlist.put(temp, temp.getAnswer());
        }


    }

    public void start() throws FileNotFoundException {
        insertFromFilePath();
        int rand = ThreadLocalRandom.current().nextInt(1,59);
        String answer = "";
        for (Question selectquestion : questionlist.keySet()) {
            rand--;
            if (rand == 0) {
                answer = selectquestion.getAnswer();
                System.out.println(selectquestion.getQuestion());
                System.out.println(selectquestion.getSelect());
                break;
            }
        }
        Scanner sc = new Scanner(System.in);
        String select = sc.nextLine();
        if(select.equals(answer)) {
            System.out.println("correct");
        }
        else System.out.println("incorrect");
    }
}
