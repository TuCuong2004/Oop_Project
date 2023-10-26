import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.HashMap;
import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {

    public void showAllWords() {
        for (String i : super.getWordlist().keySet()) {
            String s;
            if (super.getWordlist().get(i).getWord_form() != null)
                s = super.getWordlist().get(i).getWord_target() + " "
                        + super.getWordlist().get(i).getWord_form() + ": "
                        + super.getWordlist().get(i).getWord_explain();
            else s = super.getWordlist().get(i).getWord_target() + " "
                    + super.getWordlist().get(i).getWord_explain();
            System.out.println(s);
        }
    }

    public void dictionarySearcher() {
        Scanner sc = new Scanner(System.in);
        String s_in = sc.nextLine();
        for (String i : super.getWordlist().keySet()) {
            if (i.startsWith(s_in))
                System.out.println(i);
        }
    }


}

