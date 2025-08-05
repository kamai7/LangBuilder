package controller.fragments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import utils.Colors;

public class WordFieldController {

    @FXML
    private Label objectLabel;

    @FXML
    private Button deleteButton;
    
    @FXML
    public void initialize() {
    }
    
    @FXML
    public void deleteObject() {
        System.out.println("Delete object button clicked");
    }

    public void init(String objectText) {
        if (objectText == null) {
            throw new IllegalArgumentException(Colors.error("EditorItemController.init" , "editorController cannot be null"));
        }
        this.objectLabel.setText(objectText);
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
    
}
