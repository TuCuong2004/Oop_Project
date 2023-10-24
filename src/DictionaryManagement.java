import org.w3c.dom.ls.LSInput;

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors


public class DictionaryManagement extends Dictionary {
    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] temp = new String[3];
       for(int i=0; i<=n; i++) {
           String s = sc.nextLine();
           temp = s.split("\t\t");
           if (temp.length == 2) {
               Word insert = new Word();
               insert.setWord_target(temp[0]);
               insert.setWord_explain(temp[1]);
               super.getWordlist().put(temp[0],insert);
           }

           if (temp.length == 3) {
               Word insert = new Word();
               insert.setWord_target(temp[0]);
               insert.setWord_form(temp[1]);
               insert.setWord_explain(temp[2]);
               super.getWordlist().put(temp[0],insert);
           }
       }

    }
    public void insertFromFilePath() throws FileNotFoundException {


        File path = new File("src\\dictionaries.txt");

        Scanner sc = new Scanner(path);
        String s;
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            //System.out.println(s);
            String[] temp = s.split("\t\t");



            if (temp.length == 2) {
                Word insert = new Word();
                insert.setWord_target(temp[0]);
                insert.setWord_explain(temp[1]);
                super.getWordlist().put(temp[0],insert);
            } // từ và nghĩa

            if (temp.length == 3) {
                Word insert = new Word();
                insert.setWord_target(temp[0]);
                insert.setWord_form(temp[1]);
                insert.setWord_explain(temp[2]);
                super.getWordlist().put(temp[0],insert);
            } // từ ,từ loại và nghĩa

        }

        return;
    }
    public void dictionaryLookup() {
        Scanner sc = new Scanner(System.in);
        String in = sc.nextLine();
        String s;
        if(super.getWordlist().get(in).getWord_form() != null)
        {
            s =  super.getWordlist().get(in).getWord_form()
                    + " " + super.getWordlist().get(in).getWord_explain();
        }
        else  s = super.getWordlist().get(in).getWord_explain();

        System.out.println(s);
    }
}
