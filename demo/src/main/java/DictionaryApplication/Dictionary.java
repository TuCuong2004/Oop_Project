package DictionaryApplication;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary extends ArrayList<Word> {
    private HashMap<String,Word> wordlist = new HashMap<String,Word>();

    public HashMap<String, Word> getWordlist() {
        return wordlist;
    }

    public void setWordlist(HashMap<String, Word> wordlist) {
        this.wordlist = wordlist;
    }
}
