import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static   void startApp() throws FileNotFoundException {

        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
//        dictionaryCommandline.insertFromFilePath();
//        dictionaryCommandline.insertFromCommandline();
//        dictionaryCommandline.showAllWords();
//        dictionaryCommandline.dictionaryLookup();
//        dictionaryCommandline.dictionarySearcher();
        int action = 10;
        System.out.println("Welcome to My Application!\n" +
                "[0] Exit\n" +
                "[1] Add\n" +
                "[2] Remove\n" +
                "[3] Update\n" +
                "[4] Display\n" +
                "[5] Lookup\n" +
                "[6] Search\n" +
                "[7] Game\n" +
                "[8] Import from file");
        Scanner sc = new Scanner(System.in);
        while (action > 0) {
            action = sc.nextInt();
            if (action == 1) {
                dictionaryCommandline.insertFromCommandline();
            }
            if (action == 2) {
            }
            if (action == 3) {
                dictionaryCommandline.insertFromFilePath();
            }
            if(action == 4) {
                dictionaryCommandline.showAllWords();
            }
        }
    }
}
