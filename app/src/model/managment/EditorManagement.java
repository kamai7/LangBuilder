package model.managment;


import java.util.ArrayList;

import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;

public class EditorManagement {

    ArrayList<Word> words;
    ArrayList<Letter> letters;
    ArrayList<Type> types;
    

    public void addLetter(String letter, String letterAscii) {
        letters.add(new Letter(letter, letterAscii));
    }
}
