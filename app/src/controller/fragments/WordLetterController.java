package controller.fragments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import utils.Colors;

public class WordLetterController {

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

    public void init (String objectText) {
        if (objectText == null) {
            throw new IllegalArgumentException(Colors.error("EditorItemCheckboxController.init" , "editorController cannot be null"));
        }
        this.objectLabel.setText(objectText);
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
}
