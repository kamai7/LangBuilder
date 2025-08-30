package model.managment;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import model.dao.LetterDAO;
import model.dao.WordDAO;
import model.persistance.Letter;
import model.persistance.Word;
import utils.Colors;

public class WordManagement {

    private Word word;
    private WordDAO wordDAO;

    private ArrayList<String> allLetters;
    private ArrayList<String> allLettersAscii;
    
    public WordManagement() {
        wordDAO = new WordDAO();
        
        word = new Word();

        findLetters();
    }

    public WordManagement(Word word) {
        if (word == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.WordManagement:", "word cannot be null"));
        }
        this.word = word;
        wordDAO = new WordDAO();

        findLetters();
    }

    public void deleteWord() throws IllegalArgumentException, SQLIntegrityConstraintViolationException{
        if (word == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.deleteWord:", "word cannot be null"));
        }
        wordDAO.delete(word);
        this.word = null;
    }

    public void edit() throws SQLIntegrityConstraintViolationException, IllegalArgumentException {
        if (word.getLetterIds().size() == 0) {
            throw new IllegalArgumentException("word must contains letters");
        }
        if(word.getId() == -1){
            wordDAO.create(word);
        }else{
            wordDAO.update(word);
        }
    }

    private void findLetters(){
        LetterDAO letterDAO = new LetterDAO();
        ArrayList<Letter> letters = letterDAO.findAll();

        allLetters = new ArrayList<>(letters.size());
        allLettersAscii = new ArrayList<>(letters.size());

        for (Letter letter : letters) {
            allLetters.add(letter.getCharacter());
            allLettersAscii.add(letter.getCharacterAscii());
        }
    }

    public void addTranslations(String translations) {
        word.getTranslations().clear();
        String[] translationSeparated = translations.split(";");
        for (String translation: translationSeparated) {
            if (translation != null && translation.trim().length() > 0) {
                word.getTranslations().add(translation.replace("\n", ""));
            }
        }
    }

    public void addDefinitions(String definitions) {
        word.getDefinitions().clear();
        String[] definitionSeparated = definitions.split(";");
        for (String definition: definitionSeparated) {
            if (definition != null && definition.trim().length() > 0) {
                word.getDefinitions().add(definition.replace("\n", ""));
            }
        }
    }

    public Letter findLetterUnique(String l){
        Letter ret = null;

        int counter = 0;
        if (allLetters.contains(l) || allLettersAscii.contains(l)){
            for (String letter: allLetters) {
                if (letter.contains(l)){
                    counter++;
                }
            }
            for (String letterAscii: allLettersAscii) {
                if (letterAscii.contains(l)){
                    counter++;
                }
            }
            if (counter == 1){
                LetterDAO letterDAO = new LetterDAO();
                ret = letterDAO.findByString(l).get(0);
            }
        }
        return ret;
    }

    public Letter findLetter(String l){
        if (l == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.addLetter:", "letter cannot be null"));
        }
        Letter ret = null;
        LetterDAO letterDAO = new LetterDAO();
        int size = letterDAO.findByString(l).size();

        if (size > 0) {
            ret = letterDAO.findByString(l).get(0);
        }

        return ret;
    }

    public Word getWord() {
        return word;
    }

}
  