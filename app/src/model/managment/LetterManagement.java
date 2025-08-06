package model.managment;

import java.sql.SQLIntegrityConstraintViolationException;
import model.dao.LetterDAO;
import model.persistance.Letter;
import utils.Colors;

public class LetterManagement {

    private Letter letter;
    private LetterDAO letterDAO;

    private String letterCharacter;
    private String letterCharacterAscii;

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

    public void editLetter() throws IllegalArgumentException, SQLIntegrityConstraintViolationException{

        if(letterCharacter.trim().length() == 0){
            throw new IllegalArgumentException("Letter cannot be empty");
        }

        if (letterCharacterAscii.trim().length() == 0){
            throw new IllegalArgumentException("Ascii cannot be empty");
        }
        if(this.letter == null){
            Letter temp = new Letter(letterCharacter, letterCharacterAscii);
            temp.setId(letterDAO.create(temp));
            this.letter = temp;
        }else{
            Letter tmp = new Letter(letterCharacter, letterCharacterAscii);
            if (!tmp.equals(this.letter)){
                this.letter.setCharacter(letterCharacter);
                this.letter.setCharacterAscii(letterCharacterAscii);
                letterDAO.update(this.letter);
            }
        }
    }

    public void deleteLetter() throws IllegalArgumentException, SQLIntegrityConstraintViolationException{
        if(letter == null){
            throw new IllegalArgumentException(Colors.error("LetterManagement.deleteLetter:", "letter cannot be null"));
        }

        letterDAO.delete(letter);
        this.letter = null;
    }

    public void setLetter(String letter, String letterAscii){
        this.letterCharacter = letter;
        this.letterCharacterAscii = letterAscii;
    }
}
