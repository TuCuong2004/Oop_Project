package DictionaryApplication;//import org.w3c.dom.ls.LSInput

import DictionaryApplication.Trie.Trie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static Run.App.dictionaryCommandline;

public class DictionaryManagement extends Dictionary {

    private Trie trie = new Trie();
    public final String path = "demo/src/main/resources/Utils/test.txt";

    public DictionaryManagement() {
        insertFromFile();
    }

    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            System.out.print("Word: ");
            String word = sc.nextLine();
            System.out.print("Form: ");
            String form = sc.nextLine();
            System.out.print("Mean: ");
            String explain = sc.nextLine();
            if (form.isEmpty()) {
                super.getWordlist().put(word, new Word(word, explain));
            } else {
                super.getWordlist().put(word, new Word(word, explain, form));
            }
        }

    }

    public void insertFromFilePath(String filePath) throws FileNotFoundException {
        File path = new File(filePath);

        Scanner sc = new Scanner(path);
        String s;
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            //System.out.println(s);
            String[] temp = s.split("\t");

            if (temp.length == 2) {
                super.getWordlist().put(temp[0], new Word(temp[0], temp[1]));
            } // từ và nghĩa

            if (temp.length == 3) {
                super.getWordlist().put(temp[0], new Word(temp[0], temp[2], temp[1]));
            } // từ ,từ loại và nghĩa
        }
    }

    public void insertFromFilePath() throws FileNotFoundException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Name input file: ");
        super.setWordlist(new HashMap<>());
        try {
            //String filePath = scanner.nextLine();
            insertFromFilePath("demo/src/main/java/DictionaryApplication/dictionaries.txt");
        } catch (NoSuchElementException e) {
            System.out.println("No file input!");
        } catch (IllegalStateException e) {
            System.out.println("Input closed!");
        }
    }

    /**
     * Import data from dictionary.
     */
    public void insertFromFile(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String englishWord = bufferedReader.readLine();
            englishWord = englishWord.replace("|", "");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Word word = new Word();
                word.setWord_target(englishWord.trim());
                if (line.startsWith("/")) {
                    word.setSpelling(line);
                    line = bufferedReader.readLine();
                }
                if (line.startsWith("*")) {
                    word.setWordForm(line);
                    line = bufferedReader.readLine();
                }
                if (line.startsWith("=")) {
//                    line = line.replace("=", "!");
//                    if (line.contains("+")) line = line.replace("+", "\n-");
                    //System.out.println(line);
                    word.setExample(line);
                    System.out.println(word.getExample());
                    line = bufferedReader.readLine();
                }
                String meaning = line + "\n";
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("-")) meaning += line + "\n";
                    else if (line.startsWith("|")) {
                        englishWord = line.replace("|", "");
                        break;
                    }
                }
                word.setWord_explain(meaning.trim());
                super.getWordlist().put(word.getWord_target(),word);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("An error occur with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public void insertFromFile() {
        insertFromFile(path);
    }
    public Word getWord(String word_target) {
        if (dictionaryCommandline.getWordlist().containsKey(word_target)) {
            return dictionaryCommandline.getWordlist().get(word_target);
        } else {
            System.out.println("Not found!");
        }
        return null;
    }

    public void dictionaryLookup() {
        Scanner sc = new Scanner(System.in);
        String word_target = sc.nextLine();
        if(getWord(word_target) != null) {
            if (getWord(word_target).getSpelling() != null) {
                System.out.println("Form: " + getWord(word_target).getSpelling() );
            }
            System.out.println("Mean: " + getWord(word_target).getWord_explain());
        }
    }

    /**
     * Look up words by commands.
     */
    public ObservableList<String> dictionaryLookUp(String key) {
        ObservableList<String> list = FXCollections.observableArrayList();
        int count = 20;
//        try {
            List<String> results = trie.autoComplete(key);
//            if (results != null) {
//                int length = Math.min(results.size(), 15);
//                for (int i = 0; i < length; ++i) {
//                    list.add(results.get(i));
//                }
//            }
//        } catch (Exception exception) {
//            System.out.println("Something went wrong with dictionary looking up: " + exception);
        for (String word : super.getWordlist().keySet()) {
            if (count <= 0) {
                break;
            }
            if (word.startsWith(key)) {
                list.add(word);
                count--;
            }
        }
        return list;
    }

    /**
     * Export data from dictionary to file.
     */
    public void dictionaryExportToFile(Dictionary dictionary, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Word word : dictionary) {
                bufferedWriter.write("|" + word.getWord_target() + "\n" + word.getWord_explain());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception exception) {
            System.out.println("Something went wrong with exporting from dictionary: " + exception);
        }
    }

    /**
     * Algorithm of searching tool in the dictionary.
     */
    public int dictionarySearcher(Dictionary dictionary, String keyWordToShowResults) {
        try {
            dictionary.sort(new ArrangeDictionaryByWords());
            int left = 0;
            int right = dictionary.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int res = dictionary.get(mid).getWord_target().compareTo(keyWordToShowResults);
                if (res == 0) {
                    return mid;
                }
                if (res <= 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        } catch (NullPointerException nullPointerException) {
            System.out.println("Searching Null Exception: " + nullPointerException);
        }
        return -1;
    }

    /**
     * Update word meaning by its index.
     */
    public void updateWord(String wordTarget, String wordExplain) {
        Word word = new Word(wordTarget,wordExplain);
        dictionaryCommandline.getWordlist().replace(wordTarget,word);
    }

    /**
     * Delete a word by its index.
     */
    public void deleteWord(String wordTarget) {
        try {
            super.getWordlist().remove(wordTarget);
        } catch (NullPointerException nullPointerException) {
            System.out.println("Deleting Null Exception: " + nullPointerException);
        }
    }

    /**
     * Add a word to file.
     */
    public void addWord(Word word) {
//        if(super.getWordlist().get(word.getWord_target()) != null) {
//            super.getWordlist().put(word.getWord_target(),word);
//        }
//        else System.out.println("từ này đã có trong từ điển");
        super.getWordlist().put(word.getWord_target(),word);
    }

    /**
     * Set timeout for app.
     */
    public void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception exception) {
                System.err.println("Time out exception!");
            }
        }).start();
    }

    /**
     * Set a trie from dictionary.
     */
    public void setTrie() {
        trie = new Trie();
        try {
            for (String word : dictionaryCommandline.getWordlist().keySet()) {
                trie.insert(word);
//                System.out.println(word);
            }
        } catch (NullPointerException nullPointerException) {
            System.out.println("Set Trie Null Exception: " + nullPointerException);
        }
    }

    public void test() {
        for(char s : trie.getChildren().keySet()) System.out.println(s);
    }

    public ObservableList<String> dictionarySearch(String key) {
        ObservableList<String> list = FXCollections.observableArrayList();
        int count = 20;
        try {
            List<String> results = trie.autoComplete(key);
            if (results != null) {
                int length = Math.min(results.size(), 15);
                for (int i = 0; i < length; ++i) {
                    list.add(results.get(i));
                }
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong with dictionary looking up: " + exception);
//        for (String word : super.getWordlist().keySet()) {
//            if (count <= 0) {
//                break;
//            }
//            if (word.startsWith(key)) {
//                list.add(word);
//                count--;
//            }
        }
        return list;
    }

    public void saveChanges() throws IOException {
        StringBuilder dictionaryTxt = new StringBuilder();
        for(Word word : dictionaryCommandline.getWordlist().values())
        {
                dictionaryTxt.append("|" + word.getWord_target() + "\n"
                        + (word.getSpelling() != null ? word.getSpelling() + "\n" : "")
                        + (word.getWordForm() != null ? word.getWordForm() + "\n" : "")
                        + word.getWord_explain() + "\n"
                        + (word.getExample() != null ? word.getExample() + "\n" : "") + '\n');
        }

        Files.write(Paths.get("demo/src/main/resources/Utils/dictionaries.txt"), dictionaryTxt.toString().getBytes());
    }
}
