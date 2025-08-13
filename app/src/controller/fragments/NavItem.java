package controller.fragments;

import controller.Controller;
import javafx.scene.control.CheckBox;

public interface NavItem<T> {

    public void init(Controller mainController, T object);

    public CheckBox getCheckbox();

    public void delete();

    public T getObject();

}
