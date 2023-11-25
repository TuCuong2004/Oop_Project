package DictionaryApplication;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryCommandLine extends DictionaryManagement {

    public DictionaryCommandLine() throws FileNotFoundException {
    }

    public void showAllWords() {
        int count = 1;
        System.out.printf("%-5s| %-15s| %-10s| %s\n", "No", "Word", "Form", "Mean");
        for (String word_target : super.getWordlist().keySet()) {
            Word word = super.getWordlist().get(word_target);
            System.out.printf("%-5d| %-15s| %-10s| %s\n",
                    count,
                    word.getWord_target(),
                    word.getWord_form() == null ? "" : word.getWord_form(),
                    word.getWord_explain());
        }
    }

    public void dictionarySearcher() {
        System.out.println("Nhập: ");
        Scanner sc = new Scanner(System.in);
        String s_in = sc.nextLine();
        for (String word : super.getWordlist().keySet()) {
            if (word.startsWith(s_in))
                System.out.println(word);
        }
    }
    
}

