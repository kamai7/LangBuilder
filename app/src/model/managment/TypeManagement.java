package model.managment;

import java.sql.SQLIntegrityConstraintViolationException;

import model.dao.TypeDAO;
import model.persistance.Type;
import utils.Colors;

public class TypeManagement {

    private Type type;
    private TypeDAO typeDAO;

    public TypeManagement() {
        typeDAO = new TypeDAO();
        type = new Type();
    }

    public TypeManagement(Type type) {
        if (type == null) {
            throw new IllegalArgumentException(Colors.error("TypeManagement.TypeManagement:", "type cannot be null"));
        }
        this.type = type;

        typeDAO = new TypeDAO();
    }

    public void editType() throws SQLIntegrityConstraintViolationException, IllegalArgumentException {

        if (type.getRoot() != null && type.getPosition() == -1){
            throw new IllegalArgumentException("radical position is not not valid");
        }
        if(type.getId() == -1){
            typeDAO.create(type);
        }else{
            typeDAO.update(type);
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

        this.type.setParent(parent.getId());
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
}
