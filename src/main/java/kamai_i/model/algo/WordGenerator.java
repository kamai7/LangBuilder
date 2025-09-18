package kamai_i.model.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import kamai_i.model.dao.WordDAO;
import kamai_i.model.persistance.Letter;
import kamai_i.model.persistance.Type;
import kamai_i.model.persistance.Word;
import kamai_i.utils.Colors;

public class WordGenerator {

    private Set<Word> find;
    private Set<Word> links;
    private Set<Word> roots;

    private Type type;
    private Map<Letter, Integer> letters;
    private int wordLength;

    private double emotional;
    private double formality;
    private double vulgarity;

    private Map<Integer, Double> vectorWordLength;
    private ArrayList<Map<Double, Letter>> vectorLetters;

    private Map<String, Double> coeficients;
    private double totalCoef;

    private Set<Word> words;

    public WordGenerator(Type type) {
        if (type == null) {
            throw new IllegalArgumentException(Colors.error("type cannot be null"));
        }

        this.find = new HashSet<>();
        this.vectorWordLength = new HashMap<>();
        for (int i = 0; i < 60; i++) {
            this.vectorWordLength.put(0,0.0);
        }
        this.vectorLetters = new ArrayList<>();

        this.type = type;
        this.letters = new HashMap<>();
        this.emotional = -1;
        this.formality = -1;
        this.vulgarity = -1;
        this.wordLength = -1;

        //coefficients
        this.coeficients = new HashMap<>();
        coeficients.put("link", 1.2);
        coeficients.put("root", 1.4);
        coeficients.put("emotional", 1.4);
        coeficients.put("formality", 1.0);
        coeficients.put("vulgarity", 1.0);

        this.totalCoef = 0;

        for (String s : coeficients.keySet()) {
            this.totalCoef += coeficients.get(s);
        }
    }

    public void setType(Type type) {
        if (type == null) {
            throw new IllegalArgumentException(Colors.error("type cannot be null"));
        }

        this.type = type;
    }

    public void setLetters(Map<Letter, Integer> letters) {
        if (letters == null) {
            throw new IllegalArgumentException(Colors.error("letters cannot be null"));
        }
        this.letters = new HashMap<>(letters);
    }

    public void setWordLength(int wordLength) {
        this.wordLength = wordLength;
    }

    public void setEmotional(double emotional) {
        this.emotional = emotional;
    }

    public void setFormality(double formality) {
        this.formality = formality;
    }

    public void setVulgarity(double vulgarity) {
        this.vulgarity = vulgarity;
    }

    public void setLinks(Set<Word> links) {
        this.links = new HashSet<>(links);
    }

    public void setRoots(Set<Word> roots) {
        this.roots = new HashSet<>(roots);
    }

    public void unsetAll() {
        this.type = null;
        this.letters = new HashMap<>();
        this.wordLength = -1;
        this.emotional = -1;
        this.formality = -1;
        this.vulgarity = -1;
        this.links = null;
        this.roots = null;
    }

    private void generateWordLength() {
        int min = 0;
        for(int pos : this.letters.values()) {
            if (min < pos) {
                min = pos;
            }
        }

        //if ()
    }

    public Word generate() {
        WordDAO wordDAO = new WordDAO();
        words = new HashSet<>(wordDAO.findAll());

        generateWordLength();

        return null;
    }
}