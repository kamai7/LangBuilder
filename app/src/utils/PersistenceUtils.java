package utils;

import model.persistance.Letter;
import model.persistance.Word;

public class PersistenceUtils {

    public static String wordToString(Word word){

        if (word == null) {
            throw new IllegalArgumentException(Colors.error("Controls.wordToString:","word cannot be null"));
        }

        String ret = "";
        for (Letter letter : word.getLetters()) {
            ret += letter.getCharacter();
        }
        return ret;
    }

    public static String wordAsciiToString(Word word){

        if (word == null) {
            throw new IllegalArgumentException(Colors.error("Controls.wordAsciiToString:","word cannot be null"));
        }

        String ret = "";
        for (Letter letter : word.getLetters()) {
            ret += letter.getCharacterAscii();
        }
        return ret;
    }
}
