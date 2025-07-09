package model.persistance;

import model.DAO.TypeDAO;

public class Type {
    
    private int id;
    private String label;
    private Type parent;
    private Word root;
    private int position;

    private TypeDAO dao;
    
    public Type(String label, Type parent, Word root, int position) {

        if (label == null) {
            throw new IllegalArgumentException("parameters cannot be null");
        }
        if (label.trim().length() == 0) {
            throw new IllegalArgumentException("label cannot be empty");
        }
        if (root != null && (position < 0 || position > 2)) {
            throw new IllegalArgumentException("position must be between 0 and 2");
        }

        this.id = -1;
        this.label = label;
        this.parent = parent;
        this.root = root;
        this.position = position;
    }

    public Type(String label) {
        if (label == null) {
            throw new IllegalArgumentException("parameters cannot be null");
        }
        if (label.trim().length() == 0) {
            throw new IllegalArgumentException("label cannot be empty");
        }

        this.id = -1;
        this.label = label;
    }

    public void updateValues(){

        this.id = dao.findId(this);

        Type reference = dao.findById(this.id);

        this.label = reference.getLabel();
        this.parent = reference.getParent();
        this.root = reference.getRoot();
        this.position = reference.getPosition();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (label == null) {
            throw new IllegalArgumentException("label cannot be null");
        }
        if (label.trim().length() == 0) {
            throw new IllegalArgumentException("label cannot be empty");
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
            throw new IllegalArgumentException("position must be between 0 and 2");
        }
        this.position = position;
    }

    public int getId() {
        return id;
    }

}
