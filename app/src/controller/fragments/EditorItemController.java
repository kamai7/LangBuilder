package controller.fragments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.util.Colors;

public class EditorItemController {

    @FXML
    private Button deleteObjectButton;

    @FXML
    private Label ObjectLabel;
    
    @FXML
    public void initialize() {
        System.out.println(Colors.success("EditorItemController initialized"));
    }
    
    @FXML
    public void deleteObject() {
        System.out.println("Delete object button clicked");
    }

    public void setText(String object) {
        this.ObjectLabel.setText(object);
    }

    public void setText(int object) {
        this.ObjectLabel.setText(object + "");
    }

    public void setColor(String color) {
        this.ObjectLabel.setStyle("-fx-text-fill: " + color + ";");
    }

    public Button getDeleteObjectButton() {
        return deleteObjectButton;
    }
    
}
