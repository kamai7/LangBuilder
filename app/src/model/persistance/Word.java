package model.persistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.dao.LetterDAO;
import model.dao.TypeDAO;
import model.dao.WordDAO;
import utils.Colors;

public class Word {

    private int id = -1;

    private ArrayList<Integer> letterIds = new ArrayList<>();

    private double emotional = 0.5;
    private double formality = 0.5;
    private double vulgarity = 0.5;
    private boolean isUsable = true;

    private ArrayList<String> translations = new ArrayList<>();
    private ArrayList<String> definitions = new ArrayList<>();
    
    /** non-directed graph!!! */
    private Set<Integer> linkIds = new HashSet<>();
    
    private Set<Integer> rootIds = new HashSet<>();

    private Set<Integer> typeIds = new HashSet<>();

    private Set<Word> links = new HashSet<>();
    private Set<Word> roots = new HashSet<>();
    private Set<Type> types = new HashSet<>();
    private ArrayList<Letter> letters = new ArrayList<>();
    private ArrayList<String> lettersAscii = new ArrayList<>();
    
    
    public Word(ArrayList<Letter> letters, double emotional, double formality, double vulgarity, ArrayList<String> translations, ArrayList<String> definitions,
                Set<Word> links, Set<Word> roots, Set<Type> types, boolean isUsable) {
        if (letters == null || translations == null || definitions == null || links == null || roots == null || types == null) {
            throw new IllegalArgumentException(Colors.error("Word.Word","parameters are null"));
        }

        if (letters.isEmpty() || emotional < 0 || emotional > 1 || formality < 0 || formality > 1 || vulgarity < 0 || vulgarity > 1) {
            throw new IllegalArgumentException(Colors.error("Word.Word","invalid parameters, no letterIds or out of range"));
        }

        this.letters = letters;
        this.emotional = emotional;
        this.formality = formality;
        this.vulgarity = vulgarity;
        this.translations = translations;
        this.definitions = definitions;
        this.links = links;
        this.roots = roots;
        this.types = types;
        this.isUsable = isUsable;

        for (Letter letter : letters) {
            this.lettersAscii.add(letter.getCharacterAscii());
            this.letterIds.add(letter.getId());
        }

        for (Word link : links) {
            this.linkIds.add(link.getId());
        }

        for (Word root : roots) {
            this.rootIds.add(root.getId());
        }

        for (Type type : types) {
            this.typeIds.add(type.getId());
        }
    }

    public Word(ArrayList<Integer> letterIds, double emotional, double formality, double vulgarity, ArrayList<String> translations, ArrayList<String> definitions, boolean isUsable,
                Set<Integer> linkIds, Set<Integer> rootIds, Set<Integer> typeIds) {
        if (letterIds == null || translations == null || definitions == null || linkIds == null || rootIds == null || typeIds == null) {
            throw new IllegalArgumentException(Colors.error("Word.Word","parameters are null"));
        }

        if (letterIds.isEmpty() || emotional < 0 || emotional > 1 || formality < 0 || formality > 1 || vulgarity < 0 || vulgarity > 1) {
            throw new IllegalArgumentException(Colors.error("Word.Word","invalid parameters, no letterIds or out of range"));
        }

        this.letterIds = letterIds;
        this.linkIds = linkIds;
        this.rootIds = rootIds;
        this.typeIds = typeIds;

        this.emotional = emotional;
        this.formality = formality;
        this.vulgarity = vulgarity;
        this.translations = translations;
        this.definitions = definitions;
        this.isUsable = isUsable;

        for (Letter letter : letters) {
            this.lettersAscii.add(letter.getCharacterAscii());
        }
    }

    public Word(ArrayList<Integer> letterIds, double emotional, double formality, double vulgarity) {
        this(letterIds, emotional, formality, vulgarity, new ArrayList<>(), new ArrayList<>(), false, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    public Word(ArrayList<Integer> letterIds) {
        this(letterIds, 0.5, 0.5, 0.5, new ArrayList<>(), new ArrayList<>(), false, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    public Word () {
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
            "\n\t rootIds=" + rootIds +
            "\n\t linkIds=" + linkIds +
            "\n\t typeIds=" + typeIds;
    }

    public ArrayList<Integer> getLetterIds() {
        return letterIds;
    }

    public ArrayList<Letter> getLetters() {
        if (letters == null) {
            letterIds = new ArrayList<>();
            LetterDAO letterDAO = new LetterDAO();
            for (Letter letter : letters) {
                letters.add(letterDAO.findById(letter.getId()));
            }
        }
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

    public Set<Word> getRoots() {
        roots = new HashSet<>(); 
        WordDAO wordDAO = new WordDAO();
        for (Integer id : rootIds) {
            roots.add(wordDAO.findById(id));
        }
        return roots;
    }

    public Set<Integer> getRootIds() {
        return rootIds;
    }

    public Set<Word> getLinks() {
        
        links = new HashSet<>(); 
        WordDAO wordDAO = new WordDAO();
        for (Integer id : linkIds) {
            links.add(wordDAO.findById(id));
        }

        return links;
    }

    public Set<Integer> getLinkIds() {
        return linkIds;
    }

    public Set<Type> getTypes() {
        types = new HashSet<>(); 
        TypeDAO typeDAO = new TypeDAO();
        for (Integer id : typeIds) {
            types.add(typeDAO.findById(id));
        }
        return types;
    }

    public Set<Integer> getTypeIds() {
        return typeIds;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException(Colors.error("Word.setId","id cannot be negative"));
        }
        this.id = id;
    }

    public void setLetters(ArrayList<Letter> letters) {
        if (letterIds == null || letterIds.isEmpty()) {
            throw new IllegalArgumentException(Colors.error("Word.setLetters","letterIds cannot be null or empty"));
        }

        this.letters = letters;
        this.lettersAscii = new ArrayList<>();
        this.letterIds = new ArrayList<>();

        for (Letter letter : letters) {
            this.lettersAscii.add(letter.getCharacterAscii());
            this.letterIds.add(letter.getId());
        }
    }

    public void setLetterIds(ArrayList<Integer> letterIds) {
        if (letterIds == null || letterIds.isEmpty()) {
            throw new IllegalArgumentException(Colors.error("Word.setLetters","letterIds cannot be null or empty"));
        }
        this.letterIds = letterIds;
        this.letters = null;
    }

    public void setTranslations(ArrayList<String> translations) {
        if (translations == null) {
            throw new IllegalArgumentException(Colors.error("Word.setTranslations","translations cannot be null"));
        }
        this.translations = translations;
    }

    public void setDefinitions(ArrayList<String> definitions) {
        if (definitions == null) {
            throw new IllegalArgumentException(Colors.error("Word.setDefinitions","definitions cannot be null"));
        }
        this.definitions = definitions;
    }

    public void setEmotional(double emotional) {
        if (emotional < 0 || emotional > 1) {
            throw new IllegalArgumentException(Colors.error("Word.setEmotional","emotional must be between 0 and 1"));
        }
        this.emotional = emotional;
    }

    public void setFormality(double formality) {
        if (formality < 0 || formality > 1) {
            throw new IllegalArgumentException(Colors.error("Word.setFormality","formality must be between 0 and 1"));
        }
        this.formality = formality;
    }

    public void setVulgarity(double vulgarity) {
        if (vulgarity < 0 || vulgarity > 1) {
            throw new IllegalArgumentException(Colors.error("Word.setVulgarity","vulgarity must be between 0 and 1"));
        }
        this.vulgarity = vulgarity;
    }

    public void setUsable(boolean isUsable) {
        this.isUsable = isUsable;
    }

    public void setRoots(HashSet<Word> roots) {
        if (roots == null) {
            throw new IllegalArgumentException(Colors.error("Word.setRoots","roots cannot be null"));
        }

        this.roots = roots;
        this.rootIds = new HashSet<>();

        for (Word word : roots) {
            this.rootIds.add(word.getId());
        }
    }

    public void setRoots(Set<Integer> rootIds) {
        if (rootIds == null) {
            throw new IllegalArgumentException(Colors.error("Word.setRoots","rootIds cannot be null"));
        }

        this.rootIds = rootIds;
        this.roots = null;
    }

    public void setLinks(HashSet<Word> links) {
        if (links == null) {
            throw new IllegalArgumentException(Colors.error("Word.setLinks","links cannot be null"));
        }

        this.links = links;
        this.linkIds = new HashSet<>();

        for (Word word : links) {
            this.linkIds.add(word.getId());
        }
    }

    public void setLinks(Set<Integer> linkIds) {
        if (linkIds == null) {
            throw new IllegalArgumentException(Colors.error("Word.setLinks","linkIds cannot be null"));
        }

        this.linkIds = linkIds;
        this.links = null;
    }

    public void setTypes(HashSet<Type> types) {
        if (types == null) {
            throw new IllegalArgumentException(Colors.error("Word.setTypes","types cannot be null"));
        }

        this.types = types;
        this.typeIds = new HashSet<>();

        for (Type type : types) {
            this.typeIds.add(type.getId());
        }
    }

    public void setTypes(Set<Integer> typeIds) {
        if (typeIds == null) {
            throw new IllegalArgumentException(Colors.error("Word.setTypes","typeIds cannot be null"));
        }

        this.typeIds = typeIds;
        this.types = null;
    }

    @Override
    public int hashCode() {
        Object[] letterIds = {this.letterIds, this.translations, this.definitions, this.emotional, this.formality, this.vulgarity, this.rootIds, this.linkIds, this.typeIds, this.isUsable};
        return Arrays.deepHashCode(letterIds);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Word other = (Word) obj;
        return hashCode() == other.hashCode();
    }

    @Override
    public Word clone() {
        return new Word(this.letterIds, this.emotional, this.formality, this.vulgarity, this.translations, this.definitions, this.isUsable, this.linkIds, this.rootIds, this.typeIds);
    }
}
