import java.io.IOException;

public class DictionaryCommandline extends DictionaryManagement {

    public void showAllWords() {
       // System.out.println(getSize());
        for (int i = 0; i < getSize(); i++) {
            System.out.print(super.getWordlist()[i].getWord_target() +  " ");
            if (super.getWordlist()[i].getWord_form() != null) {
                System.out.print(super.getWordlist()[i].getWord_form() + "  : " );
            }
            System.out.println(super.getWordlist()[i].getWord_explain());
        }
    }

    public static void main(String[] args) throws IOException {
//        DictionaryManagement dictionary = new DictionaryManagement();
//        dictionary.insertFromFilePath();
//        dictionary.insertFromCommandline();

        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
       // dictionaryCommandline.insertFromFilePath();
        dictionaryCommandline.insertFromCommandline();
        dictionaryCommandline.showAllWords();
    }
}
