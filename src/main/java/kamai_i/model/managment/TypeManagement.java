package kamai_i.model.managment;

import java.sql.SQLException;

import kamai_i.exceptions.InvalidUserArgument;
import kamai_i.model.dao.TypeDAO;
import kamai_i.model.persistance.Type;
import kamai_i.utils.Colors;

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

    public void edit() throws InvalidUserArgument {

        if (type.getRoot() != null && type.getPosition() == -1){
            throw new IllegalArgumentException("radical position is not not valid");
        }
        try{
            if(type.getId() == -1){
                typeDAO.create(type);
            }else{
                typeDAO.update(type);
            }
        }catch (SQLException e){
            throw new InvalidUserArgument("TypeManagement.editType: " + e.getMessage());
        }
    }

    public Type getType() {
        return type;
    }

    public void delete() throws InvalidUserArgument{
        try{
            typeDAO.delete(type);
            this.type = null;
        }catch (SQLException e){
            throw new InvalidUserArgument("TypeManagement.deleteType: " + e.getMessage());
        }
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
