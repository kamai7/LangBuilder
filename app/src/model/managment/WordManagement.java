package model.managment;

import java.sql.SQLIntegrityConstraintViolationException;

import model.dao.WordDAO;
import model.persistance.Word;
import utils.Colors;

public class WordManagement {

    private Word word;

    private WordDAO wordDAO;
    
    public WordManagement() {
        wordDAO = new WordDAO();
    }

    public WordManagement(Word word) {
        if (word == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.WordManagement:", "word cannot be null"));
        }
        this.word = word;
        wordDAO = new WordDAO();
    }

    public void deleteWord() throws IllegalArgumentException, SQLIntegrityConstraintViolationException{
        if (word == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.deleteWord:", "word cannot be null"));
        }
        wordDAO.delete(word);
        this.word = null;
    }

}
  