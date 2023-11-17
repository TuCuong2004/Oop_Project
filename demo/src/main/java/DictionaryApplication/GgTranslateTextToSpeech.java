package DictionaryApplication;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GgTranslateTextToSpeech {
    public static String errorString = "|o!d^x<@";
    public static InputStream getAudio(String text, String languageOutput) throws IOException {
        String urlStr = "https://translate.google.com/translate_tts?ie=UTF-8&tl="
                + languageOutput
                + "&client=tw-ob&q="
                + URLEncoder.encode(text, StandardCharsets.UTF_8);

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        InputStream audioSrc = con.getInputStream();
        return new BufferedInputStream(audioSrc);
    }

    public static void play(InputStream sound) throws JavaLayerException {
        new Player(sound).play();
    }

    public static String play(String text, String languageOutput) {
        try {
            play(getAudio(text, languageOutput));
            return "";
        } catch (JavaLayerException | IOException e) {
            return errorString;
        }
    }

    public static void main(String[] args) {
        String text = "Hello";
        play(text,"EN");
    }
}