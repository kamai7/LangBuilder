package kamai_i.controller.fragments;

import kamai_i.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public abstract class NavItem<T> {

    protected Controller mainController;

    protected T object;

    @FXML
    protected Label objectLabel,
                  descriptionLabel;

    @FXML
    protected CheckBox selectedCheckBox;

    public CheckBox getCheckbox() {
        return selectedCheckBox;
    }

    public abstract void init(Controller mainController, T object);

    public abstract void delete();

    public T getObject() {
        return object;
    }

    public boolean equals(Object other){
        if (this == other) return true;
        if (!(other instanceof NavItem)) return false;
        NavItem<?> otherController = (NavItem<?>) other;
        return object.equals(otherController.getObject());
    }

}
