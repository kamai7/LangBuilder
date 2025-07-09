package model.persistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import model.DAO.WordDAO;

public class Word{

    private int id;

    private Letter[] word;
    private String[] wordAscii;

    private double emotional;
    private double formality;
    private double vulgarity;

    private ArrayList<String> translations;
    private ArrayList<String> definitions;
    
    /** non-directed graph!!!*/
    private ArrayList<Word> links;
    
    private ArrayList<Word> roots;

    private ArrayList<Type> types = new ArrayList<Type>();

    private WordDAO dao;
    
    
    public Word(Letter[] word, double emotional, double formality, double vulgarity, ArrayList<String> translations, ArrayList<String> definitions, ArrayList<Word> links, ArrayList<Word> roots, ArrayList<Type> types) {
        if (word == null || translations == null || definitions == null || links == null || roots == null || types == null) {
            throw new IllegalArgumentException("parameters are null");
        }

        if (word.length == 0 || emotional < 0 || emotional > 1 || formality < 0 || formality > 1 || vulgarity < 0 || vulgarity > 1) {
            throw new IllegalArgumentException("invalid parameters, no word or out of range");
        }

        this.id = -1;

        this.word = word;
        this.wordAscii = new String[word.length];

        for (int i = 0; i < wordAscii.length; i++) {
            this.wordAscii[i] = word[i].getCharacterAscii();
        }

        this.emotional = emotional;
        this.formality = formality;
        this.vulgarity = vulgarity;

        this.translations = translations;
        this.definitions = definitions;

        this.links = links;
        this.roots = roots;

        this.types = types;
        
        this.dao = new WordDAO();
    }

    public Word(Letter[] word) {
        this(word, 0, 0, 0, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<Word>(), new ArrayList<Word>(), new ArrayList<Type>());
    }

    public void updateValues(){

        this.id = dao.findId(this);

        Word reference = dao.findById(this.id);

        this.emotional = reference.getEmotional();
        this.formality = reference.getFormality();
        this.vulgarity = reference.getVulgarity();
        
        this.definitions = reference.getDefinitions();
        this.translations = reference.getTranslations();
        this.roots = reference.getRoots();
        this.links = reference.getLinks();
        this.types = reference.getTypes();
    }

    public String toString() {
        return "Word: " + id + " : " +
            ", " + Arrays.toString(word) +
            ", -> " + Arrays.toString(wordAscii) +
            "\n\t translations=" + translations +
            "\n\t definitions=" + definitions +
            "\n\t emotional=" + emotional +
            ", formality=" + formality +
            ", vulgarity=" + vulgarity +
            "\n\t roots=" + roots +
            "\n\t links=" + links +
            "\n\t types=" + types;
    }

    public Letter[] getWord() {
        return word;
    }

    public String[] getWordAscii() {
        return wordAscii;
    }

    public ArrayList<String> getTranslations() {
        return translations;
    }

    public ArrayList<String> getDefinitions() {
        return definitions;
    }

    public double getEmotional() {
        return emotional;
    }

    public double getFormality() {
        return formality;
    }

    public double getVulgarity() {
        return vulgarity;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Word> getRoots() {
        return roots;
    }

    public ArrayList<Word> getLinks() {
        return links;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public void setWord(Letter[] word) {
        if (word == null || word.length == 0) {
            throw new IllegalArgumentException("word cannot be null or empty");
        }
        this.word = word;
        this.wordAscii = new String[word.length];
        for (int i = 0; i < wordAscii.length; i++) {
            this.wordAscii[i] = word[i].getCharacterAscii();
        }
    }

    public void setTranslations(ArrayList<String> translations) {
        if (translations == null) {
            throw new IllegalArgumentException("translations cannot be null");
        }
        this.translations = translations;
    }

    public void setDefinitions(ArrayList<String> definitions) {
        if (definitions == null) {
            throw new IllegalArgumentException("definitions cannot be null");
        }
        this.definitions = definitions;
    }

    public void setEmotional(double emotional) {
        if (emotional < 0 || emotional > 1) {
            throw new IllegalArgumentException("emotional must be between 0 and 1");
        }
        this.emotional = emotional;
    }

    public void setFormality(double formality) {
        if (formality < 0 || formality > 1) {
            throw new IllegalArgumentException("formality must be between 0 and 1");
        }
        this.formality = formality;
    }

    public void setVulgarity(double vulgarity) {
        if (vulgarity < 0 || vulgarity > 1) {
            throw new IllegalArgumentException("vulgarity must be between 0 and 1");
        }
        this.vulgarity = vulgarity;
    }

    public void setRoots(ArrayList<Word> roots) {
        if (roots == null) {
            throw new IllegalArgumentException("roots cannot be null");
        }
        this.roots = roots;
    }

    public void setLinks(ArrayList<Word> links) {
        if (links == null) {
            throw new IllegalArgumentException("links cannot be null");
        }
        this.links = links;
    }

    public void setTypes(ArrayList<Type> types) {
        if (types == null) {
            throw new IllegalArgumentException("types cannot be null");
        }
        this.types = types;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}
