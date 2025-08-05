package model.managment;

import java.sql.SQLIntegrityConstraintViolationException;

import javafx.scene.paint.Color;
import model.persistance.Type;
import model.persistance.Word;

public class TypeManagement {

    public Type type;

    public TypeManagement() {
    }

    public TypeManagement(Type type) {
        this.type = type;
    }

    public void createType(String label, Type parent, Word root, int position, Color color) throws SQLIntegrityConstraintViolationException, IllegalArgumentException {

    }

    public Type getType() {
        return type;
    }
    
}
