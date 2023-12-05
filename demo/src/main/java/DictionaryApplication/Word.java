package DictionaryApplication;

public class Word {
    private String word_target;
    private String word_explain;
    private String spelling;

    private String wordForm;

    private String example;


    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public Word(String word_target, String word_explain, String spelling) {
        this.word_target = word_target;
        this.word_explain = word_explain;
        this.spelling = spelling;
    }

    public Word(String word_target, String word_explain, String spelling, String wordForm, String example) {
        this.word_target = word_target;
        this.word_explain = word_explain;
        this.spelling = spelling;
        this.wordForm = wordForm;
        this.example = example;
    }

    public Word() {
    }

    public String getWordForm() {
        return wordForm;
    }

    public void setWordForm(String wordForm) {
        this.wordForm = wordForm;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

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

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }
}
