package model.managment;

import java.sql.SQLException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.dao.LetterDAO;
import model.persistance.Letter;

public class LetterManagement {

    public ObjectProperty<Letter> letter;

    public LetterManagement() {
        this.letter = new SimpleObjectProperty<>();
    }

    public void addLetter(String letter, String letterAscii) throws IllegalArgumentException, SQLException{

        if(letter.trim().length() == 0){
            throw new IllegalArgumentException("Letter cannot be empty");
        }

        if (letterAscii.trim().length() == 0){
            throw new IllegalArgumentException("Ascii cannot be empty");
        }

        LetterDAO letterDAO = new LetterDAO();
        letterDAO.create(new Letter(letter, letterAscii));

        this.letter.set(new Letter(letter, letterAscii));
    }

    public ObjectProperty<Letter> getLetter() {
        return letter;
    }
}
