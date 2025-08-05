package model.managment;

import java.sql.SQLIntegrityConstraintViolationException;
import model.dao.LetterDAO;
import model.persistance.Letter;
import utils.Colors;

public class LetterManagement {

    private Letter letter;
    private LetterDAO letterDAO;

    public LetterManagement() {
        this.letterDAO = new LetterDAO();
    }

    public LetterManagement(Letter letter) {
        if (letter == null){
            throw new IllegalArgumentException(Colors.error("LetterManagement.LetterManagement:", "letter cannot be null"));
        }
        this.letter = letter;
        this.letterDAO = new LetterDAO();
    }

    public void createLetter(String letter, String letterAscii) throws IllegalArgumentException, SQLIntegrityConstraintViolationException{

        if(letter.trim().length() == 0){
            throw new IllegalArgumentException("Letter cannot be empty");
        }

        if (letterAscii.trim().length() == 0){
            throw new IllegalArgumentException("Ascii cannot be empty");
        }

        letterDAO.create(new Letter(letter, letterAscii));

        this.letter = new Letter(letter, letterAscii);
    }

    public void deleteLetter() throws IllegalArgumentException, SQLIntegrityConstraintViolationException{
        if(letter == null){
            throw new IllegalArgumentException(Colors.error("LetterManagement.deleteLetter:", "letter cannot be null"));
        }

        letterDAO.delete(letter);
        this.letter = null;
    }
}
