public class Dictionary {
    private Word [] wordlist = new Word[10000];
    private int size = 0;

    public Word[] getWordlist() {
        return wordlist;
    }

    public void setWordlist(Word[] wordlist) {
        this.wordlist = wordlist;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
