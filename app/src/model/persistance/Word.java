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

    private ArrayList<Integer> letterIds;

    private double emotional;
    private double formality;
    private double vulgarity;
    private boolean isUsable;

    private ArrayList<String> translations;
    private ArrayList<String> definitions;
    
    /** non-directed graph!!! */
    private Set<Integer> linkIds;
    
    private Set<Integer> rootIds;

    private Set<Integer> typeIds;

    private Set<Word> links;
    private Set<Word> roots;
    private Set<Type> types;
    private ArrayList<Letter> letters;
    
    
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
    }

    public Word(ArrayList<Integer> letterIds) {
        this(letterIds, 0, 0, 0, new ArrayList<>(), new ArrayList<>(), true, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    public Word () {
        this.letterIds = new ArrayList<>();
        this.linkIds = new HashSet<>();
        this.rootIds = new HashSet<>();
        this.typeIds = new HashSet<>();

        this.emotional = 0;
        this.formality = 0;
        this.vulgarity = 0;
        this.translations = new ArrayList<>();
        this.definitions = new ArrayList<>();
        this.isUsable = true;
    }

    @Override
    public String toString() {
        return "Word: " + id + " : " + letters +
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
        if (letterIds == null) {
            letterIds = new ArrayList<>();
            for (Letter letter : letters) {
                letterIds.add(letter.getId());
            }
        }

        letters = null;
        return letterIds;
    }

    public ArrayList<Letter> getLetters() {
        if (letters == null) {
            letters = new ArrayList<>();
            LetterDAO letterDAO = new LetterDAO();
            for (Letter letter : letters) {
                letters.add(letterDAO.findById(letter.getId()));
            }
        }

        letterIds = null;
        return letters;
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

        rootIds = null;
        return roots;
    }

    public Set<Integer> getRootIds() {
        if (rootIds == null) {
            rootIds = new HashSet<>(); 
            for (Word word : roots) {
                rootIds.add(word.getId());
            }
        }

        roots = null;
        return rootIds;
    }

    public Set<Word> getLinks() {
        
        links = new HashSet<>(); 
        WordDAO wordDAO = new WordDAO();
        for (Integer id : linkIds) {
            links.add(wordDAO.findById(id));
        }

        linkIds = null;
        return links;
    }

    public Set<Integer> getLinkIds() {
        if (linkIds == null) {
            linkIds = new HashSet<>(); 
            for (Word word : links) {
                linkIds.add(word.getId());
            }
        }

        return linkIds;
    }

    public Set<Type> getTypes() {
        if (types == null) {
            types = new HashSet<>(); 
            TypeDAO typeDAO = new TypeDAO();
            for (Integer id : typeIds) {
                types.add(typeDAO.findById(id));
            }
        }
        
        typeIds = null;
        return types;
    }

    public Set<Integer> getTypeIds() {
        if (typeIds == null) {
            typeIds = new HashSet<>(); 
            for (Type type : types) {
                typeIds.add(type.getId());
            }
        }

        types = null;
        return typeIds;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException(Colors.error("Word.setId","id cannot be negative"));
        }
        this.id = id;
    }

    public void setLetters(ArrayList<Letter> letters) {
        if (letters == null || letters.isEmpty()) {
            throw new IllegalArgumentException(Colors.error("Word.setLetters","letterIds cannot be null or empty"));
        }

        this.letters = letters;
        this.letterIds = null;
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

    public void setRoots(Set<Word> roots) {
        if (roots == null) {
            throw new IllegalArgumentException(Colors.error("Word.setRoots","roots cannot be null"));
        }

        this.roots = roots;
        this.rootIds = null;
    }

    public void setRootsId(Set<Integer> rootIds) {
        if (rootIds == null) {
            throw new IllegalArgumentException(Colors.error("Word.setRoots","rootIds cannot be null"));
        }

        this.rootIds = rootIds;
        this.roots = null;
    }

    public void setLinks(Set<Word> links) {
        if (links == null) {
            throw new IllegalArgumentException(Colors.error("Word.setLinks","links cannot be null"));
        }

        this.links = links;
        this.linkIds = null;
    }

    public void setLinksId(Set<Integer> linkIds) {
        if (linkIds == null) {
            throw new IllegalArgumentException(Colors.error("Word.setLinks","linkIds cannot be null"));
        }

        this.linkIds = linkIds;
        this.links = null;
    }

    public void setTypes(Set<Type> types) {
        if (types == null) {
            throw new IllegalArgumentException(Colors.error("Word.setTypes","types cannot be null"));
        }

        this.types = types;
        this.typeIds = null;
    }

    public void setTypesId(Set<Integer> typeIds) {
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
