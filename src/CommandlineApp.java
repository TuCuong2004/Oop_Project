import java.io.FileNotFoundException;
import java.util.Scanner;

public class CommandlineApp {

    private DictionaryCommandline dictionaryCommandline;
    private Scanner scanner;

    public CommandlineApp() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        dictionaryCommandline = new DictionaryCommandline();
    }

    public void start() throws FileNotFoundException {
        while (true) {
            System.out.println("""
                    Welcome to My Application!
                    [0] Exit
                    [1] Add
                    [2] Remove
                    [3] Update
                    [4] Display
                    [5] Lookup
                    [6] Search
                    [7] Game
                    [8] Import from file""");
            System.out.print("Chọn: ");
            int action = scanner.nextInt();
            switch (action) {
                case 0 -> {
                    System.out.println("End!");
                    return;
                }
                case 1 -> dictionaryCommandline.insertFromCommandline();
//                case 2:
//                case 3:
                case 4 -> dictionaryCommandline.showAllWords();
                case 5 -> dictionaryCommandline.dictionaryLookup();
                case 6 -> dictionaryCommandline.dictionarySearcher();
//                case 7:
                case 8 -> dictionaryCommandline.insertFromFilePath();
                default -> System.out.println("Action not supported");
            }
        }
    }
}
