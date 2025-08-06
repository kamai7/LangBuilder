package model.managment;

import java.sql.SQLIntegrityConstraintViolationException;

import javafx.scene.paint.Color;
import model.dao.TypeDAO;
import model.persistance.Type;
import model.persistance.Word;
import utils.Colors;

public class TypeManagement {

    public Type type;
    public TypeDAO typeDAO;

    public TypeManagement() {
        typeDAO = new TypeDAO();
    }

    public TypeManagement(Type type) {
        if (type == null) {
            throw new IllegalArgumentException(Colors.error("TypeManagement.TypeManagement:", "type cannot be null"));
        }
        this.type = type;
        typeDAO = new TypeDAO();
    }

    public void createType(String label, Type parent, Word root, int position, Color color) throws SQLIntegrityConstraintViolationException, IllegalArgumentException {

    }

    public Type getType() {
        return type;
    }

    public void deleteType() throws IllegalArgumentException, SQLIntegrityConstraintViolationException{
        if(type == null) {
            throw new IllegalArgumentException(Colors.error("TypeManagement.deleteType:", "type cannot be null"));
        }
        typeDAO.delete(type);
        this.type = null;
    }
    
}
