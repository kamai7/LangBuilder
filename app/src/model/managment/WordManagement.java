package model.managment;

import java.util.ArrayList;

import model.persistance.Letter;
import model.persistance.Word;

public class WordManagement {

    ArrayList<Word> words;

    ArrayList<Letter> letters;

    public String getWordFromLetters() {
        String ret = "";
        for (Letter letter : letters) {
            ret += letter.getCharacter();
        }
        return ret;
    }

    public String getWordFromLettersAscii() {
        String ret = "";
        for (Letter letter : letters) {
            ret += letter.getCharacterAscii();
        }
        return ret;
    }

    public void addLetter(String letter, String letterAscii) {
        letters.add(new Letter(letter, letterAscii));
    }
    
}
