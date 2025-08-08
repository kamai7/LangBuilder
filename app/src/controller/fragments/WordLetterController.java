package controller.fragments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.persistance.Letter;
import utils.Colors;

public class WordLetterController {

    Letter object;

    @FXML
    private CheckBox checkbox;

    @FXML
    private Label objectLabel;

    @FXML
    private HBox container;

    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
    }

    public void init (Letter letter) {
        if (letter == null) {
            throw new IllegalArgumentException(Colors.error("EditorItemCheckboxController.init" , "editorController cannot be null"));
        }
        this.objectLabel.setText(letter.getCharacter());
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Letter getObject() {
        return object;
    }
}
