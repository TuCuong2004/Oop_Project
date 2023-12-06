package game_model;

import DictionaryApplication.DictionaryManagement;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class WordMatchingGameModel extends Game<String> {
    public static final int COLUMNS = 2;
    public static final int NUMBERS_OF_WORD = 6;

    Map<String, Integer> wordExplain = new HashMap<>();
    Map<String, Integer> wordTarget = new HashMap<>();
    Map<Integer, Boolean> wordIsSelect = new HashMap<>();
    DictionaryManagement dictionaryManagement = new DictionaryManagement();

    public WordMatchingGameModel() throws FileNotFoundException {
        super(new Board<>(NUMBERS_OF_WORD, COLUMNS));
        insertNewWord();
        generateGrid();
    }

    public void insertNewWord() throws FileNotFoundException {
        dictionaryManagement.insertFromFilePath();
        List<String> wordList = new ArrayList<>(dictionaryManagement.getWordlist().keySet());
        wordIsSelect.put(0, true);
        for (int i = 0; i < NUMBERS_OF_WORD; i++) {
            Random random = new Random();
            int randomNumber = 0;
            while (wordIsSelect.containsKey(randomNumber)) {
                randomNumber = random.nextInt(2914);
            }
            wordTarget.put(wordList.get(randomNumber), randomNumber);
            wordExplain.put(dictionaryManagement.getWordlist().get(wordList.get(randomNumber)).getWord_explain(), randomNumber);
        }
    }

    private void generateGrid() {
        AtomicInteger i = new AtomicInteger(0);
        wordTarget.forEach((key, value) ->
                board.setCell(i.getAndIncrement(), 0, key));
        i.set(0);
        wordExplain.forEach((key, value) ->
                board.setCell(i.getAndIncrement(), 1, key));
    }

    public Map<String, Integer> getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(Map<String, Integer> wordExplain) {
        this.wordExplain = wordExplain;
    }

    public Map<String, Integer> getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(Map<String, Integer> wordTarget) {
        this.wordTarget = wordTarget;
    }
}
