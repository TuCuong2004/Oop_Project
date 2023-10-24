import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
public class DictionaryCommandline extends DictionaryManagement {

    public void showAllWords() {
        // System.out.println(getSize());
        for (String i : super.getWordlist().keySet()) {
            String s;
            if(super.getWordlist().get(i).getWord_form() != null)
                s=super.getWordlist().get(i).getWord_target() + " "
                        + super.getWordlist().get(i).getWord_form() + ": "
                        + super.getWordlist().get(i).getWord_explain();
            else  s = super.getWordlist().get(i).getWord_target() + " "
                    + super.getWordlist().get(i).getWord_explain();
            System.out.println(s);
        }
    }
    public void dictionarySearcher() {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
    }

    public static void main(String[] args) throws IOException {
//        DictionaryManagement dictionary = new DictionaryManagement();
//        dictionary.insertFromFilePath();
//        dictionary.insertFromCommandline();

        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
//        dictionaryCommandline.insertFromFilePath();
//        dictionaryCommandline.insertFromCommandline();
//        dictionaryCommandline.showAllWords();
//        dictionaryCommandline.dictionaryLookup();
//        String s = "aaa";
//        String s1 = "aaa";
//        if(s=s1)





    }
}
