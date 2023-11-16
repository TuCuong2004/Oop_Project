package DictionaryApplication.DictionaryCommandline;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class CommandlineApp  {

    private final DictionaryCommandline dictionaryCommandline;
    private final Scanner scanner;

    public CommandlineApp() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        dictionaryCommandline = new DictionaryCommandline();
    }

    public void start() throws FileNotFoundException {
        while (true) {
            System.out.println("""
                    Welcome to My Application!
                    [0] Exit // javaFX tự có rồi
                    [1] Add
                    [2] Remove // Alert của add
                    [3] Update // bỏ
                    [4] Display // chắc là menu :)
                    [5] Lookup
                    [6] Search // làm cùng lookup
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
                    Game game = new Game();
                    game.start();
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

    Stage window ;
    Scene scene1,scene2;
    Button button1  = new Button();
    Button button2 = new Button();
    Button button3 = new Button();
//    @Override
//    public void start(Stage stage) throws IOException {
//    }
//
//
//
//    public static void main(String[] args) {
//        launch();
//    }
}
