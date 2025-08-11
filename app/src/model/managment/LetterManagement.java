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
        this.letter = new Letter();
    }

    public LetterManagement(Letter letter) {
        if (letter == null){
            throw new IllegalArgumentException(Colors.error("LetterManagement.LetterManagement:", "letter cannot be null"));
        }
        this.letter = letter;
        this.letterDAO = new LetterDAO();
    }

    public void editLetter() throws IllegalArgumentException, SQLIntegrityConstraintViolationException{

        if(this.letter.getId() == -1){
            letterDAO.create(letter);
        }else{
            letterDAO.update(this.letter);
        }
    }

    public void deleteLetter() throws IllegalArgumentException, SQLIntegrityConstraintViolationException{
        if(letter == null){
            throw new IllegalArgumentException(Colors.error("LetterManagement.deleteLetter:", "letter cannot be null"));
        }

        letterDAO.delete(letter);
        this.letter = null;
    }

    public Letter getLetter() {
        return letter;
    }
}
