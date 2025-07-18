package controller.fragments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import model.util.Colors;

public class EditorItemCheckboxController {

    @FXML
    private CheckBox checkbox;

    @FXML
    private Label ObjectLabel;

    @FXML
    private Button deleteObjectButton;

    @FXML
    private void initialize() {
        System.out.println(Colors.success("EditorItemCheckboxController initialized"));
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

    public boolean isChecked() {
        return checkbox.isSelected();
    }

    public Button getDeleteObjectButton() {
        return deleteObjectButton;
    }
    
}
