package Run;


import DictionaryApplication.GgTranslateTextToSpeech;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class TranslateController implements Initializable {
    private String sourceLanguage = "en";
    private String toLanguage = "vi";
    private boolean isToVietnameseLang = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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



    @FXML
    private void handleOnClickTranslateBtn() throws IOException, URISyntaxException {
        String srcText = sourceLangField.getText();
        String trans;
        trans = translate(sourceLanguage,toLanguage,srcText);
        toLangField.setText(trans);
        GgTranslateTextToSpeech.play(trans,sourceLanguage);
    }

    @FXML
    private void handleOnClickSwitchToggle() {
        sourceLangField.clear();
        toLangField.clear();
        if (isToVietnameseLang) {
            englishLabel.setLayoutX(426);
            vietnameseLabel.setLayoutX(104);
            sourceLanguage = "vi";
            toLanguage = "en";
        } else {
            englishLabel.setLayoutX(100);
            vietnameseLabel.setLayoutX(426);
            sourceLanguage = "en";
            toLanguage = "vi";
        }
        isToVietnameseLang = !isToVietnameseLang;
    }

    @FXML
    private TextArea sourceLangField, toLangField;

    @FXML
    private Button translateBtn;

    @FXML
    private Label englishLabel , vietnameseLabel;

    private static String translate(String langFrom, String langTo, String text) throws IOException, URISyntaxException {
        // INSERT YOU URL HERE
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
