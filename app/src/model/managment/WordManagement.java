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

    public void addLetter(Letter letter) {
        if (letter == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.addLetter:", "letter cannot be null"));
        }
        letters.add(letter);
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
    public void addType(Type type) {
        if (type == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.addType:", "type cannot be null"));
        }
        if (types.contains(type)) {
            throw new IllegalArgumentException("type already selected");
        }
        types.add(type);
    }

    public void removeType(Type type) {
        if (type == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.removeType:", "type cannot be null"));
        }
        types.remove(type);
    }

    public void setUsable(boolean isUsable) {
        this.isUsable = isUsable;
    }

    public void setEmotionality(double emotional) {
        this.emotional = emotional;
    }

    public void setFormality(double formality) {
        this.formality = formality;
    }

    public void setVulgarity(double vulgarity) {
        this.vulgarity = vulgarity;
    }

    public void addTranslations(String translations) {
        String[] translationSeparated = translations.split(";");
        for (String translation: translationSeparated) {
            if (translation != null && translation.trim().length() > 0) {
                this.translations.add(translation);
            }
        }
    }

    public void addDefinitions(String definitions) {
        String[] definitionSeparated = definitions.split(";");
        for (String definition: definitionSeparated) {
            if (definition != null && definition.trim().length() > 0) {
                this.definitions.add(definition);
            }
        }
    }

    public Letter findLetterUnique(String l){
        Letter ret = null;

        int counter = 0;
        for (String letter: allLetters) {
            if (letter.contains(l)){
                counter++;;
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
        return ret;
    }

    public void addRoot(Word word) {
        if (word == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.addRoot:", "word cannot be null"));
        }
        if (roots.contains(word)) {
            throw new IllegalArgumentException("root already selected");
        }
        roots.add(word);
    }

    public void removeRoot(Word word) {
        if (word == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.removeRoot:", "word cannot be null"));
        }
        roots.remove(word);
    }

    public void addLink(Word word) {
        if (word == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.addLink:", "word cannot be null"));
        }
        if (links.contains(word)) {
            throw new IllegalArgumentException("link already selected");
        }
        links.add(word);
    }

    public void removeLink(Word word) {
        if (word == null) {
            throw new IllegalArgumentException(Colors.error("WordManagement.removeLink:", "word cannot be null"));
        }
        links.remove(word);
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

    public ArrayList<Letter> getLetters() {
        return letters;
    }

}
  