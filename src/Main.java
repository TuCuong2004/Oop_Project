import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
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
            if(action == 5) {
                dictionaryCommandline.dictionaryLookup();
            }
            if(action==6) {
                dictionaryCommandline.dictionarySearcher();
            }
            if(action == 7) {
            }
            if(action == 8) {
                dictionaryCommandline.insertFromFilePath();
            }
        }
    }
}