package DictionaryApplication;//import org.w3c.dom.ls.LSInput
import DictionaryApplication.Trie.Trie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {
    private final Trie trie = new Trie();
    public final String path = "demo\\src\\main\\resources\\Utils\\dictionaries.txt";

    public DictionaryManagement() throws FileNotFoundException {
        insertFromFilePath(path);
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
            if(form.isEmpty()){
                super.getWordlist().put(word, new Word(word, explain));
            }
            else{
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
        try {
            //String filePath = scanner.nextLine();
            insertFromFilePath(path);
        } catch (NoSuchElementException e) {
            System.out.println("No file input!");
        } catch (IllegalStateException e) {
            System.out.println("Input closed!");
        }
    }

    /**
     * Import data from dictionary.
     */
    public void insertFromFile(Dictionary dictionary, String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String englishWord = bufferedReader.readLine();
            englishWord = englishWord.replace("|", "");
            String line;
            while (bufferedReader.readLine() != null) {
                Word word = new Word();
                word.setWord_target(englishWord.trim());
                line = bufferedReader.readLine();
                while (line != null) {
                    if (!line.startsWith("|")) {
                        line = line.concat("\n");
                    } else {
                        englishWord = line.replace("|", "");
                        break;
                    }
                }
                word.setWord_explain(englishWord.trim());
                dictionary.add(word);
            }
            bufferedReader.close();
        } catch (IOException ioException) {
            System.out.println("An I/O exception of some sort has occurred: " + ioException);
        } catch (Exception exception) {
            System.out.println("Something went wrong with inserting: " + exception);
        }
    }

    public void dictionaryLookup() {
        Scanner sc = new Scanner(System.in);
        String word_target = sc.nextLine();
        if(super.getWordlist().get(word_target) == null){
            System.out.println("Not found!");
        }
        else{
            Word word = super.getWordlist().get(word_target);
            if(word.getWord_form() != null){
                System.out.println("Form: " + word.getWord_form());
            }
            System.out.println("Mean: " + word.getWord_explain());
        }
    }

    /**
     * Look up words by commands.
     */
    public ObservableList<String> dictionaryLookUp(String key) {
        ObservableList<String> list = FXCollections.observableArrayList();
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
    public void updateWord(Dictionary dictionary, int index, String meaning, String path) {
        try {
            dictionary.get(index).setWord_explain(meaning);
            dictionaryExportToFile(dictionary, path);
        } catch (NullPointerException nullPointerException) {
            System.out.println("Updating Null Exception: " + nullPointerException);
        }
    }

    /**
     * Delete a word by its index.
     */
    public void deleteWord(Dictionary dictionary, int index, String path) {
        try {
            dictionary.remove(index);
            setTrie(dictionary);
            dictionaryExportToFile(dictionary, path);
        } catch (NullPointerException nullPointerException) {
            System.out.println("Deleting Null Exception: " + nullPointerException);
        }
    }

    /**
     * Add a word to file.
     */
    public void addWord(Word word, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("|" + word.getWord_target() + "\n" + word.getWord_explain());
            bufferedWriter.newLine();
        } catch (IOException ioException) {
            System.out.println("Adding I/O Exception: " + ioException);
        } catch (NullPointerException nullPointerException) {
            System.out.println("Adding Null Exception: " + nullPointerException);
        }
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
    public void setTrie(Dictionary dictionary) {
        try {
            for (Word word : dictionary) {
                trie.insert(word.getWord_target());
            }
        } catch (NullPointerException nullPointerException) {
            System.out.println("Set Trie Null Exception: " + nullPointerException);
        }
    }
}
