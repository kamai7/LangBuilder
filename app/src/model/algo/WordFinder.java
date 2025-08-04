package model.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.dao.WordDAO;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import utils.Colors;

class WordGenerator {

    private HashSet<Word> find;
    private HashSet<Word> links;
    private HashSet<Word> roots;

    private Type type;
    private HashMap<Letter, Integer> letters;
    private int wordLength;

    private double emotional;
    private double formality;
    private double vulgarity;

    private ArrayList<Double> vectorWordLength;
    private ArrayList<HashMap<Double, Letter>> vectorLetters;

    private HashMap<String, Double> coeficients;
    private double totalCoef;

    private HashSet<Word> words;

    public WordGenerator(Type type) {
        if (type == null) {
            throw new IllegalArgumentException(Colors.error("type cannot be null"));
        }

        this.find = new HashSet<>();
        this.vectorWordLength = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            this.vectorWordLength.add(0.0);
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