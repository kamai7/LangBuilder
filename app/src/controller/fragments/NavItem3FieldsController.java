package controller.fragments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.util.Colors;

public class NavItem3FieldsController {
    
    @FXML
    private Label objectLabel,
                  descriptionLabel,
                  typeLabel;

    @FXML
    private Button editButton;

    @FXML
    private HBox container;

    @FXML
    private CheckBox selectedCheckBox;


    @FXML
    private void initialize() {
        System.out.println(Colors.success("NavItem3FieldsController initialized"));
    }

    @FXML
    private void contentClicked() {
        selectedCheckBox.setSelected(!selectedCheckBox.isSelected());
    }

    public void setObjectText(String text) {
        if (text == null) {
            throw new IllegalArgumentException(Colors.error("text cannot be null"));
        }
        objectLabel.setText(text);
    }

    public void setDescriptionText(String description) {
        if (description == null) {
            throw new IllegalArgumentException(Colors.error("description cannot be null"));
        }
        descriptionLabel.setText(description);
    }

    public void setObjectTypeText(String type) {
        if (type == null) {
            throw new IllegalArgumentException(Colors.error("type cannot be null"));
        }
        typeLabel.setText(type);
    }

    public void setTypeColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException(Colors.error("color cannot be null"));
        }
        typeLabel.setStyle("-fx-text-fill: #" + color.toString().substring(2) + ";");
    }

    public Button getEditButton() {
        return editButton;
    }

    public boolean isSelected() {
        return selectedCheckBox.isSelected();
    }

}
