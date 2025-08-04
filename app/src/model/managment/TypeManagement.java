package model.managment;

import java.sql.SQLIntegrityConstraintViolationException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import model.persistance.Type;
import model.persistance.Word;

public class TypeManagement {

    public ObjectProperty<Type> type;

    public TypeManagement() {
        this.type = new SimpleObjectProperty<Type>();
    }

    public void createType(String label, Type parent, Word root, int position, Color color) throws SQLIntegrityConstraintViolationException, IllegalArgumentException {

    }

    public ObjectProperty<Type> getType() {
        return type;
    }
    
}
