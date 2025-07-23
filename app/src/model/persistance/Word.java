package model.persistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import model.util.Colors;

public class Word {

    private int id = -1;

    private ArrayList<Letter> letters;
    private ArrayList<String> lettersAscii;

    private double emotional;
    private double formality;
    private double vulgarity;
    private boolean isUsable = true;

    private ArrayList<String> translations;
    private ArrayList<String> definitions;
    
    /** non-directed graph!!! */
    private HashSet<Word> links;
    
    private HashSet<Word> roots;

    private HashSet<Type> types;
    
    
    public Word(ArrayList<Letter> letters, double emotional, double formality, double vulgarity, ArrayList<String> translations, ArrayList<String> definitions, HashSet<Word> links, HashSet<Word> roots, HashSet<Type> types) {
        if (letters == null || translations == null || definitions == null || links == null || roots == null || types == null) {
            throw new IllegalArgumentException(Colors.error("parameters are null"));
        }

        if (letters.isEmpty() || emotional < 0 || emotional > 1 || formality < 0 || formality > 1 || vulgarity < 0 || vulgarity > 1) {
            throw new IllegalArgumentException(Colors.error("invalid parameters, no letters or out of range"));
        }

        this.letters = letters;
        this.lettersAscii = new ArrayList<>();
        for (Letter letter : letters) {
            this.lettersAscii.add(letter.getCharacterAscii());
        }

        this.emotional = emotional;
        this.formality = formality;
        this.vulgarity = vulgarity;

        this.translations = translations;
        this.definitions = definitions;

        this.links = links;
        this.roots = roots;

        this.types = types;
    }

    public Word(ArrayList<Letter> letters, double emotional, double formality, double vulgarity) {
        this(letters, emotional, formality, vulgarity, new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    public Word(ArrayList<Letter> letters) {
        this(letters, 0, 0, 0, new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    @Override
    public String toString() {
        return "Word: " + id + " : " +
            letters + " (" + lettersAscii + ") " +
            "\n\t translations=" + translations +
            "\n\t definitions=" + definitions +
            "\n\t emotional=" + emotional +
            ", formality=" + formality +
            ", vulgarity=" + vulgarity +
            "\n\t roots=" + roots +
            "\n\t links=" + links +
            "\n\t types=" + types;
    }

    public ArrayList<Letter> getLetters() {
        return letters;
    }

    public ArrayList<String> getLettersAscii() {
        return lettersAscii;
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

    public boolean isUsable() {
        return isUsable;
    }

    public int getId() {
        return id;
    }

    public HashSet<Word> getRoots() {
        return roots;
    }

    public HashSet<Word> getLinks() {
        return links;
    }

    public HashSet<Type> getTypes() {
        return types;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLetters(ArrayList<Letter> letters) {
        if (letters == null || letters.isEmpty()) {
            throw new IllegalArgumentException(Colors.error("letters cannot be null or empty"));
        }
        this.letters = new ArrayList<>(letters);
        this.lettersAscii = new ArrayList<>();
        for (Letter letter : letters) {
            this.lettersAscii.add(letter.getCharacterAscii());
        }
    }

    public void setTranslations(ArrayList<String> translations) {
        if (translations == null) {
            throw new IllegalArgumentException(Colors.error("translations cannot be null"));
        }
        this.translations = translations;
    }

    public void setDefinitions(ArrayList<String> definitions) {
        if (definitions == null) {
            throw new IllegalArgumentException(Colors.error("definitions cannot be null"));
        }
        this.definitions = definitions;
    }

    public void setEmotional(double emotional) {
        if (emotional < 0 || emotional > 1) {
            throw new IllegalArgumentException(Colors.error("emotional must be between 0 and 1"));
        }
        this.emotional = emotional;
    }

    public void setFormality(double formality) {
        if (formality < 0 || formality > 1) {
            throw new IllegalArgumentException(Colors.error("formality must be between 0 and 1"));
        }
        this.formality = formality;
    }

    public void setVulgarity(double vulgarity) {
        if (vulgarity < 0 || vulgarity > 1) {
            throw new IllegalArgumentException(Colors.error("vulgarity must be between 0 and 1"));
        }
        this.vulgarity = vulgarity;
    }

    public void setUsable(boolean isUsable) {
        this.isUsable = isUsable;
    }

    public void setRoots(HashSet<Word> roots) {
        if (roots == null) {
            throw new IllegalArgumentException(Colors.error("roots cannot be null"));
        }
        this.roots = roots;
    }

    public void setLinks(HashSet<Word> links) {
        if (links == null) {
            throw new IllegalArgumentException(Colors.error("links cannot be null"));
        }
        this.links = links;
    }

    public void setTypes(HashSet<Type> types) {
        if (types == null) {
            throw new IllegalArgumentException(Colors.error("types cannot be null"));
        }
        this.types = types;
    }

    @Override
    public int hashCode() {
        Object[] letters = this.letters.toArray();
        return Arrays.deepHashCode(letters);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Word other = (Word) obj;
        return this.id == other.id &&
               Double.compare(other.emotional, this.emotional) == 0 &&
               Double.compare(other.formality, this.formality) == 0 &&
               Double.compare(other.vulgarity, this.vulgarity) == 0 &&
               this.letters.equals(other.letters) &&
               this.lettersAscii.equals(other.lettersAscii) &&
               Objects.equals(this.translations, other.translations) &&
               Objects.equals(this.definitions, other.definitions) &&
               Objects.equals(this.links, other.links) &&
               Objects.equals(this.roots, other.roots) &&
               Objects.equals(this.types, other.types);
    }
}
