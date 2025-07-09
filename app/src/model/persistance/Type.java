package model.persistance;

public class Type {
    
    private int id;
    private String label;
    private Type parent;
    private int position;

    private TypeDAO dao;
    
    public Type(String label, Type parent, int position) {

        if (label == null || parent == null) {
            throw new IllegalArgumentException("parameters cannot be null");
        }
        if (position < 0 || position > 2) {
            throw new IllegalArgumentException("position must be between 0 and 2");
        }
        if (label.trim().length() == 0) {
            throw new IllegalArgumentException("label cannot be empty");
        }

        this.id = -1;
        this.label = label;
        this.parent = parent;
        this.position = position;
    }

    public updateValues(){

        this.id = dao.findId(this);

        Type reference = dao.findById(this.id);

        this.label = reference.getLabel();
        this.parent = reference.getParent();
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
        if (parent == null) {
            throw new IllegalArgumentException("parent cannot be null");
        }
        this.parent = parent;
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

}
