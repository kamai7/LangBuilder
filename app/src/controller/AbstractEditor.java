package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public abstract class AbstractEditor<T> {

    @FXML
    protected Button applyButton;

    @FXML
    protected Label headerObject;

    protected Controller mainController;

    public abstract void init(Controller mainController, T letter);

    public abstract void init(Controller mainController);

    protected void applyAnimation() {
        
    }
    
}
