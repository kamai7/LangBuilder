package kamai_i.utils;

import java.util.HashSet;

import kamai_i.model.persistance.Letter;
import kamai_i.model.persistance.Word;

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

    public static boolean shallowEquals(HashSet<Word> set1, HashSet<Word> set2){

        if (set1 == null || set2 == null) {
            throw new IllegalArgumentException(Colors.error("Controls.shallowEquals:","sets cannot be null"));
        }

        if (set1.size() != set2.size()) return false;
        HashSet<String> temp = new HashSet<>();
        for (Word word : set1) {
            temp.add(wordToString(word));
        }
        for (Word word : set2) {
            if (!temp.contains(wordToString(word))) return false;
        }
        return true;
    }
}
