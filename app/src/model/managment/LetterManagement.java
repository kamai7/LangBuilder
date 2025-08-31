package model.managment;

import java.sql.SQLException;

import exceptions.InvalidUserArgument;
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

    public void edit() throws InvalidUserArgument{
        try{
            if(this.letter.getId() == -1){
                letterDAO.create(letter);
            }else{
                letterDAO.update(this.letter);
            }
        }catch (SQLException e){
            throw new InvalidUserArgument("LetterManagement.editLetter: " + e.getMessage());
        }
    }

    public void delete() throws InvalidUserArgument{
        if(letter == null){
            throw new IllegalArgumentException(Colors.error("LetterManagement.deleteLetter:", "letter cannot be null"));
        }
        try{
            letterDAO.delete(letter);
            this.letter = null;
        }catch (SQLException e){
            throw new InvalidUserArgument("LetterManagement.deleteLetter: " + e.getMessage());
        }
    }

    public Letter getLetter() {
        return letter;
    }
}
