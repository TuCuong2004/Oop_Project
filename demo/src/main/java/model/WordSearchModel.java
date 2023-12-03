package model;

import DictionaryApplication.DictionaryManagement;
import Run.WordSearchController;
import javafx.scene.control.Button;
import javafx.util.Pair;

import java.util.*;

public class WordSearchModel {

    public static final int GRID_SIZE = 10;

    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private ArrayList<String> words;
    private List<Pair<Integer, Integer>> emptySlots = new ArrayList<>();
    private List<Pair<Integer, Integer>> suggestCells = new ArrayList<>();
    private Random random = new Random();

    Board board = new Board(GRID_SIZE);
    private List<String> foundWords = new ArrayList<>();

    public WordSearchModel() {
        initEmptySlots();
        initializeWords();
        generateGrid();
    }

    private void initializeWords() {
        Random rand = new Random();
        List<String> values = new ArrayList<>(dictionaryManagement.getWordlist().keySet());
        words = new ArrayList<>();

        while (words.size() <= 1) {
            String word = values.get(rand.nextInt(values.size()));
            if (word.length() < GRID_SIZE && !word.contains(" ") && !words.contains(word.toUpperCase())) {
                words.add(word.toUpperCase());
            }
        }
        System.out.println(words);
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

//        fillEmptySpaces();
        board.print();
    }

    boolean isValid(String word, int row, int col, boolean isHorizontal) {
        boolean ok = true;

        if (isHorizontal) {
            if (col + word.length() > GRID_SIZE) {
                ok = false;
            } else {
                for (int i = 0; i < word.length(); i++) {
                    if (board.getCell(row, col + i) != 0 && board.getCell(row, col + i) != word.charAt(i)) {
                        ok = false;
                        break;
                    }
                }
            }
        } else {
            if (row + word.length() > GRID_SIZE) {
                ok = false;
            } else {
                for (int i = 0; i < word.length(); i++) {
                    if (board.getCell(row + i, col) != 0 && board.getCell(row + i, col) != word.charAt(i)) {
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
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                emptySlots.add(new Pair<>(row, col));
            }
        }
    }


    private void fillEmptySpaces() {
        Random random = new Random();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board.getGrid()[i][j] == 0) {
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
        return dictionaryManagement.getWordlist().get(selectedWord) != null;
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
}
