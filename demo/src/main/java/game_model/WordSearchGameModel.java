package game_model;

import DictionaryApplication.DictionaryManagement;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.util.*;

public class WordSearchGameModel extends Game<Character> {

    private int numberOfWords;

    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private ArrayList<String> words;
    private List<Pair<Integer, Integer>> emptySlots = new ArrayList<>();
    private List<Pair<Integer, Integer>> suggestCells = new ArrayList<>();
    private Random random = new Random();

    private List<String> foundWords = new ArrayList<>();

    public WordSearchGameModel(int gridSize, int numberOfWords) throws FileNotFoundException {
        super(new Board<>(gridSize));
        this.numberOfWords = numberOfWords;
        initEmptySlots();
        initializeWords();
        generateGrid();
    }

    private void initializeWords() throws FileNotFoundException {
        dictionaryManagement.insertFromFilePath();
        Random rand = new Random();
        List<String> values = new ArrayList<>(dictionaryManagement.getWordlist().keySet());
        words = new ArrayList<>();

        while (words.size() < numberOfWords) {
            String word = values.get(rand.nextInt(values.size()));
            if (word.length() >= 2 && word.length() < board.getSize() &&
                    !word.contains(" ") && !words.contains(word.toUpperCase())) {
                words.add(word.toUpperCase());
            }
        }
    }

    public void generateGrid() {
        List<Pair<Integer, Integer>> shuffledEmptySlots = new ArrayList<>(emptySlots);
        Collections.shuffle(shuffledEmptySlots);
        Iterator<Pair<Integer, Integer>> iterator = shuffledEmptySlots.iterator();

        for (String word : words) {
            while (iterator.hasNext()) {
                Pair<Integer, Integer> emptySlot = iterator.next();
                boolean isHorizontal = random.nextBoolean();

                if (isValid(word, emptySlot.getKey(), emptySlot.getValue(), isHorizontal)) {
                    fillWord(word, emptySlot.getKey(), emptySlot.getValue(), isHorizontal);
                    iterator.remove();
                    break;
                } else if (isValid(word, emptySlot.getKey(), emptySlot.getValue(), !isHorizontal)) {
                    fillWord(word, emptySlot.getKey(), emptySlot.getValue(), !isHorizontal);
                    iterator.remove();
                    break;
                }
            }
        }

        fillEmptySpaces();
    }

    boolean isValid(String word, int row, int col, boolean isHorizontal) {
        boolean ok = true;

        if (isHorizontal) {
            if (col + word.length() > board.getSize()) {
                ok = false;
            } else {
                for (int i = 0; i < word.length(); i++) {
                    if (board.getCell(row, col + i) != null && board.getCell(row, col + i) != word.charAt(i)) {
                        ok = false;
                        break;
                    }
                }
            }
        } else {
            if (row + word.length() > board.getSize()) {
                ok = false;
            } else {
                for (int i = 0; i < word.length(); i++) {
                    if (board.getCell(row + i, col) != null && board.getCell(row + i, col) != word.charAt(i)) {
                        ok = false;
                        break;
                    }
                }
            }
        }
        return ok;
    }

    private void fillWord(String word, int row, int col, boolean isHorizontal) {
        suggestCells.add(new Pair<>(row, col));
        if (isHorizontal) {
            for (int i = 0; i < word.length(); i++) {
                board.setCell(row, col, word.charAt(i));
                col++;
            }
        } else {
            for (int i = 0; i < word.length(); i++) {
                board.setCell(row, col, word.charAt(i));
                row++;
            }
        }
    }

    private void initEmptySlots() {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                emptySlots.add(new Pair<>(row, col));
            }
        }
    }


    private void fillEmptySpaces() {
        Random random = new Random();

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getCell(i, j) == null) {
                    char randomLetter = (char) ('a' + random.nextInt(26));
                    board.setCell(i, j, randomLetter);
                }
            }
        }
    }

    public boolean checkIfCorrectWord(String selectedWord) {
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            if (word.equals(selectedWord)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean checkIfContainWord(String selectedWord) {
        if (selectedWord.length() < 3) {
            return false;
        }
        for (String word : words) {
            if (word.contains(selectedWord)) {
                return true;
            }
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Pair<Integer, Integer>> getSuggestCells() {
        return suggestCells;
    }

    public void setSuggestCells(List<Pair<Integer, Integer>> suggestCells) {
        this.suggestCells = suggestCells;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }
}
