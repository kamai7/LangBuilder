package model.managment;

import java.sql.SQLIntegrityConstraintViolationException;

import javafx.scene.paint.Color;
import model.dao.TypeDAO;
import model.persistance.Type;
import model.persistance.Word;
import utils.Colors;

public class TypeManagement {

    private Type type;
    private TypeDAO typeDAO;

    private Type parent;
    private Word root;
    private Color color;
    private int position;
    private String label;

    public TypeManagement() {
        typeDAO = new TypeDAO();
        this.position = -1;
    }

    public TypeManagement(Type type) {
        if (type == null) {
            throw new IllegalArgumentException(Colors.error("TypeManagement.TypeManagement:", "type cannot be null"));
        }
        this.type = type;
        this.parent = type.getParent();
        this.root = type.getRoot();
        this.color = type.getColor();
        this.label = type.getLabel();
        this.position = type.getPosition();

        typeDAO = new TypeDAO();
    }

    public void editType() throws SQLIntegrityConstraintViolationException, IllegalArgumentException {
        if(label == null || label.trim().length() == 0){
            throw new IllegalArgumentException("Type name cannot be empty");
        }
        if (this.root != null && position == -1){
            throw new IllegalArgumentException("radical position is not not valid");
        }
        if(this.type == null){
            Type temp = new Type(label, parent, root, position, color);
            System.out.println(temp);
            temp.setId(typeDAO.create(temp));
            System.out.println(temp);
            this.type = temp;
        }else{
            Type tmp = new Type(label, parent, root, position, color);
            if (!tmp.equals(this.type)){
                this.type.setLabel(label);
                this.type.setParent(parent);
                this.type.setRoot(root);
                this.type.setPosition(position);
                this.type.setColor(color);
                typeDAO.update(this.type);
            }
        }
    }

    public Type getType() {
        return type;
    }

    public void deleteType() throws IllegalArgumentException, SQLIntegrityConstraintViolationException{
        typeDAO.delete(type);
        this.type = null;
    }

    public void setParent(Type parent) throws IllegalArgumentException{

        if (parent.equals(this.type)) {
            throw new IllegalArgumentException("This type cannot be its own parent");
        }
        checkParentCycles(parent.getParentId());

        this.parent = parent;
    }

    private void checkParentCycles(int parentId) throws IllegalArgumentException{

        Type parent = typeDAO.findById(parentId);

        if (parent != null) {
            if (parent.equals(this.type)) {
                throw new IllegalArgumentException("This type cannot be the parent of a type that it is a supertype");
            } else {
                checkParentCycles(parent.getParentId());
            }
        }
    }

    public void setRoot(Word root) {
        this.root = root;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    
}
