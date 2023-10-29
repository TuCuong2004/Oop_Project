//import org.w3c.dom.ls.LSInput;

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors


public class DictionaryManagement extends Dictionary {

    public DictionaryManagement() throws FileNotFoundException {
        insertFromFilePath("src\\dictionaries.txt");
    }

    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            System.out.print("Word: ");
            String word = sc.nextLine();
            System.out.print("Form: ");
            String form = sc.nextLine();
            System.out.print("Mean: ");
            String explain = sc.nextLine();
            if(form.isEmpty()){
                super.getWordlist().put(word, new Word(word, explain));
            }
            else{
                super.getWordlist().put(word, new Word(word, explain, form));
            }
        }

    }

    public void insertFromFilePath(String filePath) throws FileNotFoundException {
        File path = new File(filePath);

        Scanner sc = new Scanner(path);
        String s;
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            //System.out.println(s);
            String[] temp = s.split("\t");

            if (temp.length == 2) {
                super.getWordlist().put(temp[0], new Word(temp[0], temp[1]));
            } // từ và nghĩa

            if (temp.length == 3) {
                Word insert = new Word();
                super.getWordlist().put(temp[0], new Word(temp[0], temp[2], temp[1]));
            } // từ ,từ loại và nghĩa
        }
    }

    public void insertFromFilePath() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name input file: ");
        String filePath = scanner.nextLine();
        insertFromFilePath(filePath);   
    }

    public void dictionaryLookup() {
        Scanner sc = new Scanner(System.in);
        String word_target = sc.nextLine();
        if(super.getWordlist().get(word_target) == null){
            System.out.println("Not found!");
        }
        else{
            Word word = super.getWordlist().get(word_target);
            if(word.getWord_form() != null){
                System.out.println("Form: " + word.getWord_form());
            }
            System.out.println("Mean: " + word.getWord_explain());
        }
    }
}
