package model.persistance;

import java.util.Arrays;

import javafx.scene.paint.Color;
import model.dao.TypeDAO;
import model.dao.WordDAO;
import utils.Colors;

/**
 * Represents a grammatical type for a word (e.g., verb, adverb, first-group verb).
 * This class models the linguistic categorization of words, allowing hierarchical
 * relationships (parentId types), morphological rootIds (prefixes or suffixes),
 * and their positions within the type label.
 */
public class Type {

    /**
     * The unique identifier of the word type in the database.
     * This serves as the primary key for the {@code Type} entity.
     * It is initialized with a default value of {@code -1}, which typically indicates
     * that the object has not yet been persisted or retrieved from the database.
     */
    private int id = -1;

    /**
     * The textual label representing the name of the type.
     * This can be any grammatical or linguistic category, such as "Verb",
     * "Adverb", or "First-Group Verb". It provides a human-readable identifier
     * for the type.
     */
    private String label;

    /**
     * The identifier of the parentId type in the type hierarchy.
     * This is a foreign key pointing to another {@code Type} entity in the database,
     * establishing a hierarchical relationship. For example, a "First-Group Verb"
     * could have "Verb" as its parentId.
     * If no parentId exists, this could be set to a sentinel value (e.g., {@code -1}).
     */
    private int parentId;

    /**
     * The identifier of the morphological rootId (prefix or suffix) associated with this type.
     * This refers to a rootId entity in the database, which contributes to the construction
     * or meaning of the type label. For instance, a prefix like "pre-" or a suffix like "-ing".
     * This is useful for morphological analysis and generation.
     */
    private int rootId;

    /**
     * The position of the rootId (prefix or suffix) within the label of the type.
     * <ul>
     *   <li>{@code 0} – The rootId appears at the beginning (prefix).</li>
     *   <li>{@code 1} – The rootId appears in the middle.</li>
     *   <li>{@code 2} – The rootId appears at the end (suffix).</li>
     * </ul>
     * This field determines how the rootId is combined with the label.
     */
    private int position;

    private Type parent;

    private Word root;

    /**
     * The color associated with this type, used for visualization purposes.
     * This can be useful in graphical interfaces or linguistic visualizations
     * where different types are represented using distinct colors for easy identification.
     */
    private Color color;
    
    public Type(String label, Type parent, Word root, int position, Color color) {

        if (label == null || color == null) {
            throw new IllegalArgumentException(Colors.error("Type.Type:","parameters cannot be null"));
        }
        if (label.trim().length() == 0) {
            throw new IllegalArgumentException(Colors.error("Type.Type:","invalid parameters length"));
        }
        if (root != null && (position < 0 || position > 2)) {
            throw new IllegalArgumentException(Colors.error("Type.Type:","position must be between 0 and 2"));
        }

        this.id = -1;
        this.label = label;
        if (parent != null) {
            this.parentId = parent.getId();
        } else {
            this.parentId = -1;
        }
        if (root != null) {
            this.rootId = root.getId();
        } else {
            this.rootId = -1;
        }
        this.position = position;
        this.color = color;
        this.root = root;
        this.parent = parent;
        this.position = position;
        this.color = color;
    }

    public Type(String label, int parentId, int rootId, int position, Color color) {

        if (label == null || color == null) {
            throw new IllegalArgumentException(Colors.error("Type.Type:","parameters cannot be null"));
        }
        if (label.trim().length() == 0) {
            throw new IllegalArgumentException(Colors.error("Type.Type:","invalid parameters length"));
        }
        if (rootId != -1 && (position < 0 || position > 2)) {
            throw new IllegalArgumentException(Colors.error("Type.Type:","position must be between 0 and 2"));
        }

        this.label = label;
        this.parentId = parentId;
        this.rootId = rootId;
        this.position = position;
        this.color = color;
    }

    public Type(String label) {
        this(label,-1,-1,-1,Colors.convertRGBAToColor(new int[]{0, 174, 255, 255}));
    }

    public Type() {
        this.label = "";
        this.parentId = -1;
        this.rootId = -1;
        this.position = -1;
        this.color = Colors.convertRGBAToColor(new int[]{0, 174, 255, 255});
    }

    public String getLabel() {
        return label;
    }

    public int getParentId() {
        return parentId;
    }

    public int getRootId() {
        return rootId;
    }

    public int getId() {
        return id;
    }

    public Word getRoot() {
        if (root == null && rootId != -1) {
            WordDAO wordDAO = new WordDAO();
            root = wordDAO.findById(rootId);
        }
        return root;
    }

    public Type getParent() {
        if (this.parent == null && parentId != -1) {
            TypeDAO typeDAO = new TypeDAO();
            parent = typeDAO.findById(parentId);
        }
        return parent;
    }

    public Color getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public void setLabel(String label) {
        if (label == null) {
            throw new IllegalArgumentException(Colors.error("Type.setLabel:","label cannot be null"));
        }
        if (label.trim().length() == 0) {
            throw new IllegalArgumentException(Colors.error("Type.setLabel:","label cannot be empty"));
        }
        this.label = label;
    }

    public void setParent(int parentId) {
        if (parentId < -1) {
            throw new IllegalArgumentException(Colors.error("Type.setParent:","parentId must be greater than -1"));
        }
        if (this.parentId != parentId) {
            this.parentId = parentId;
            this.parent = null; // Invalide l'objet
        }
    }

    public void setParent(Type parent) {
        this.parent = parent;
            if (parent == null){
                this.parentId = -1;
            }else{
                this.parentId = parent.getId();
            }
    }

    public void setRoot(int rootId) {
        if (rootId < -1) {
            throw new IllegalArgumentException(Colors.error("Type.setRoot:","rootId must be greater than -1"));
        }

        if (this.rootId != rootId) {
            this.rootId = rootId;
            this.root = null;
        }
    }

    public void setRoot(Word root) {
        this.root = root;
        if (root == null){
            this.rootId = -1;
        }else{
            this.rootId = root.getId();
        }
    }

    public void setPosition(int position) {
        if (rootId != -1 && position < 0 || position > 2) {
            throw new IllegalArgumentException(Colors.error("position must be between 0 and 2"));
        }
        this.position = position;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException(Colors.error("Type.setColor:","color cannot be null"));
        }
        this.color = color;
    }

    @Override
    public int hashCode(){
        Object[] obj = {label, parentId, rootId, position, color};
        return Arrays.deepHashCode(obj);
    }

    @Override
    public String toString() {
        return "Type [id=" + id + ", label=" + label + ", parentId=" + parentId + ", rootId=" + rootId + ", position=" + position + ", color=" + color + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Type)) return false;
        Type other = (Type) obj;
        return hashCode() == other.hashCode();
    }

    @Override
    public Type clone() {
        return new Type(label, parentId, rootId, position, color);
    }

}
