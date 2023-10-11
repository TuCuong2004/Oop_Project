import java.util.Scanner;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors


public class DictionaryManagement extends Dictionary {
    public void insertFromFilePath() throws FileNotFoundException {


        File path = new File("src\\dictionaries.txt");

        Scanner sc = new Scanner(path);
        String s;
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            //System.out.println(s);
            String[] temp = s.split("\t\t");

            super.getWordlist()[getSize()] = new Word();
            if (temp.length == 2) {
                super.getWordlist()[getSize()].setWord_target(temp[0]);
                super.getWordlist()[getSize()].setWord_explain(temp[1]);
                setSize(getSize() + 1);
            } // từ và nghĩa

            if (temp.length == 3) {
                super.getWordlist()[getSize()].setWord_target(temp[0]);
                super.getWordlist()[getSize()].setWord_form(temp[1]);
                super.getWordlist()[getSize()].setWord_explain(temp[2]);
                setSize(getSize() + 1);
            } // từ ,từ loại và nghĩa

        }

        return;
    }

    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] temp = new String[3];
       for(int i=0; i<=n; i++) {
           super.getWordlist()[getSize()] = new Word();
           String s = sc.nextLine();
           temp = s.split("\t\t");
           if (temp.length == 2) {
               super.getWordlist()[getSize()].setWord_target(temp[0]);
               super.getWordlist()[getSize()].setWord_explain(temp[1]);
               setSize(getSize() + 1);
           }

           if (temp.length == 3) {
               super.getWordlist()[getSize()].setWord_target(temp[0]);
               super.getWordlist()[getSize()].setWord_form(temp[1]);
               super.getWordlist()[getSize()].setWord_explain(temp[2]);
               setSize(getSize() + 1);
           }
       }

    }

}
