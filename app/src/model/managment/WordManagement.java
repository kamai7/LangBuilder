package model.managment;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.dao.LetterDAO;
import model.dao.WordDAO;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import utils.Colors;

public class WordManagement {

    private Word word;
    private WordDAO wordDAO;
    private ArrayList<String> allLetters;
    private ArrayList<String> allLettersAscii;

    private double emotional;
    private double formality;
    private double vulgarity;
    private boolean isUsable;
    private ArrayList<String> translations;
    private ArrayList<String> definitions;
    private Set<Word> links;
    private Set<Word> roots;
    private Set<Type> types;
    private ArrayList<Letter> letters;
    private ArrayList<String> lettersAscii;
    
    public WordManagement() {
        wordDAO = new WordDAO();
        letters = new ArrayList<>();
        emotional = 0;
        formality = 0;
        vulgarity = 0;
        isUsable = false;
        translations = new ArrayList<>();
        definitions = new ArrayList<>();
        links = new HashSet<>();
        roots = new HashSet<>();
        types = new HashSet<>();
        lettersAscii = new ArrayList<>();

        findLetters();
    }

    public WordManagement(Word word) {
        if (word == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.WordManagement:", "word cannot be null"));
        }
        this.word = word;
        wordDAO = new WordDAO();
        letters = word.getLetters();
        emotional = word.getEmotional();
        formality = word.getFormality();
        vulgarity = word.getVulgarity();
        isUsable = word.isUsable();
        translations = word.getTranslations();
        definitions = word.getDefinitions();
        links = word.getLinks();
        roots = word.getRoots();
        types = word.getTypes();
        lettersAscii = word.getLettersAscii();

        findLetters();
    }

    public void deleteWord() throws IllegalArgumentException, SQLIntegrityConstraintViolationException{
        if (word == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.deleteWord:", "word cannot be null"));
        }
        wordDAO.delete(word);
        this.word = null;
    }

    public String addLetter(String letter) {
        if (letter == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.addLetter:", "letter cannot be null"));
        }
        LetterDAO letterDAO = new LetterDAO();
        Letter letterObj = letterDAO.findByString(letter).get(0);
        letters.add(letterObj);
        return letterObj.getCharacter();
    }

    public void removeLetter(Letter letter) {
        if (letter == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.removeLetter:", "letter cannot be null"));
        }
        letters.remove(letter);
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

    public boolean checkLetter(String l){
        boolean ret = false;

        int counter = 0;
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
            ret = true;
        }
        return ret;
    }

}
  