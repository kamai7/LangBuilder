package model.persistance;

import java.util.Objects;

import model.util.Colors;

public class Type {
    
    private int id = -1;
    private String label;
    private Type parent;
    private Word root;
    private int position;
    private double[] color;
    
    public Type(String label, Type parent, Word root, int position, double[] color) {

        if (label == null || color == null) {
            throw new IllegalArgumentException(Colors.error("parameters cannot be null"));
        }
        if (label.trim().length() == 0 || color.length != 4) {
            throw new IllegalArgumentException(Colors.error("invalid parameters length"));
        }
        for(double d: color) {
            if (d < 0 || d > 1) {
                throw new IllegalArgumentException(Colors.error("color must be between 0 and 1"));
            }
        }
        if (root != null && (position < 0 || position > 2)) {
            throw new IllegalArgumentException(Colors.error("position must be between 0 and 2"));
        }

        this.id = -1;
        this.label = label;
        this.parent = parent;
        this.root = root;
        this.position = position;
        this.color = color;
    }

    public Type(String label) {
        if (label == null) {
            throw new IllegalArgumentException(Colors.error("parameters cannot be null"));
        }
        if (label.trim().length() == 0) {
            throw new IllegalArgumentException(Colors.error("label cannot be empty"));
        }
        this.color = new double[4];
        this.id = -1;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (label == null) {
            throw new IllegalArgumentException(Colors.error("label cannot be null"));
        }
        if (label.trim().length() == 0) {
            throw new IllegalArgumentException(Colors.error("label cannot be empty"));
        }
        this.label = label;
    }

    public Type getParent() {
        return parent;
    }

    public void setParent(Type parent) {
        this.parent = parent;
    }

    public Word getRoot() {
        return root;
    }

    public void setRoot(Word root) {
        this.root = root;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (position < 0 || position > 2) {
            throw new IllegalArgumentException(Colors.error("position must be between 0 and 2"));
        }
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double[] getColor() {
        return color;
    }

    public void setColor(double[] color) {
        if (color == null) {
            throw new IllegalArgumentException(Colors.error("color cannot be null"));
        }
        if (color.length != 4) {
            throw new IllegalArgumentException(Colors.error("invalid color length"));
        }
        for(double d: color) {
            if (d < 0 || d > 1) {
                throw new IllegalArgumentException(Colors.error("color must be between 0 and 1"));
            }
        }
        this.color = color;
    }

    @Override
    public int hashCode(){
        return Objects.hash(label);
    }

}
