package DictionaryApplication;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class CommandlineApp  {

    private final DictionaryCommandLine dictionaryCommandline;
    private Scanner scanner;

    public CommandlineApp() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        dictionaryCommandline = new DictionaryCommandLine();
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
                case 7 -> {
                    CmdGame cmdGame = new CmdGame();
                    cmdGame.start();
                }
                case 8 -> {
                    System.out.println("nhập đường dẫn :");
                    Scanner sc = new Scanner(System.in);
                    String path = sc.nextLine();
                    dictionaryCommandline.insertFromFilePath(path);
                }
                default -> System.out.println("Action not supported");
            }
        }
    }
}
