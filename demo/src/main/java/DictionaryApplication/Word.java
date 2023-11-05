package DictionaryApplication;

public class Word {
    private String word_target;
    private String word_explain;
    private String word_form;

    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public Word(String word_target, String word_explain, String word_form) {
        this.word_target = word_target;
        this.word_explain = word_explain;
        this.word_form = word_form;
    }

    public Word() {}

    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String getWord_form() {
        return word_form;
    }

    public void setWord_form(String word_form) {
        this.word_form = word_form;
    }
}
