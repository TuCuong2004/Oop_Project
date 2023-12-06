package Run;


import DictionaryApplication.GgTranslateTextToSpeech;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TranslateController implements Initializable {
    private String sourceLanguage = "en";
    private String toLanguage = "vi";
    @FXML
    private ChoiceBox<String> sourceLanguageChoiceBox;
    @FXML
    private ChoiceBox<String> toLanguageChoiceBox;
    @FXML
    private TextArea sourceLangField, toLangField;
    @FXML
    private Button translateBtn;
    private Stage bookMarkStage;
    private final String[] language = {"Tiếng Việt", "Tiếng Anh", "Tiếng Pháp", "Tiếng Đức"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sourceLanguageChoiceBox.getItems().addAll(language);
        sourceLanguageChoiceBox.setValue("Tiếng Việt");
        sourceLanguageChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> sourceLanguage = setLanguageToTranslate(language[t1.intValue()]));

        toLanguageChoiceBox.getItems().addAll(language);
        toLanguageChoiceBox.setValue("Tiếng Anh");
        toLanguageChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> toLanguage = setLanguageToTranslate(language[t1.intValue()]));

        translateBtn.setOnAction(event -> {
            try {
                handleOnClickTranslateBtn();
            } catch (IOException e) {
                e.fillInStackTrace();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });

        sourceLangField.setOnKeyTyped(keyEvent -> translateBtn.setDisable(sourceLangField.getText().trim().isEmpty()));

        translateBtn.setDisable(true);
        toLangField.setEditable(false);
    }

    private String setLanguageToTranslate(String language) {
        return switch (language) {
            case "Tiếng Anh" -> "en";
            case "Tiếng Việt" -> "vi";
            case "Tiếng Pháp" -> "fr";
            case "Tiếng Đức" -> "de";
            default -> "";
        };
    }

    @FXML
    private void handleOnClickTranslateBtn() throws IOException, URISyntaxException {
        String srcText = sourceLangField.getText();
        String trans = translate(sourceLanguage, toLanguage, srcText);
        toLangField.setText(trans);
    }

    @FXML
    private void handleSourceSpeaker() {
        GgTranslateTextToSpeech.play(sourceLangField.getText(), sourceLanguage);
    }

    @FXML
    private void handleToSpeaker() {
        GgTranslateTextToSpeech.play(toLangField.getText(), toLanguage);
    }

    @FXML
    private void handleOnClickSwitchToggle() {
        String tmpText = sourceLangField.getText();
        sourceLangField.setText(toLangField.getText());
        toLangField.setText(tmpText);

        String tmpLang = sourceLanguageChoiceBox.getValue();
        sourceLanguageChoiceBox.setValue(toLanguageChoiceBox.getValue());
        toLanguageChoiceBox.setValue(tmpLang);
    }

    @FXML
    private void bookMarkWord() {
        String word_target = sourceLangField.getText();
        String word_explain = toLangField.getText();
        if (bookMarkStage == null) {
            bookMarkStage = createBookMarkStage(word_target, word_explain);
        }
        bookMarkStage.show();
    }

    private Stage createBookMarkStage(String word_target, String word_explain) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookmark.fxml"));
        Stage stage = new Stage();

        try {
            Parent root = loader.load();
            BookmarkController bookmarkController = loader.getController();
            bookmarkController.setBookMarkText(word_target, word_explain, stage);
            Scene scene = new Scene(root, 432, 300);
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stage;
    }

    private static String translate(String langFrom, String langTo, String text) throws IOException, URISyntaxException {
        if (langTo.equals(langFrom)) {
            return text;
        }
        String urlStr = "https://script.google.com/macros/s/AKfycbzrWtn-y_EEAuij3SwdNDZc5_sHAvUxVorOSUh_Fa2eV5KNalz1Ag8JaKPhdHsYdt0r/exec" +
                "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&target=" + langTo +
                "&source=" + langFrom;
        URI uri = new URI(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
