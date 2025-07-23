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
                  descriptionLabel;

    @FXML
    private Button editButton;

    @FXML
    private HBox container,
                 typesContainer;

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

    public Button getEditButton() {
        return editButton;
    }

    public boolean isSelected() {
        return selectedCheckBox.isSelected();
    }

    public void addType(String text, Color color) {
        if (text == null) {
            throw new IllegalArgumentException(Colors.error("text cannot be null"));
        }
        if (color == null) {
            throw new IllegalArgumentException(Colors.error("color cannot be null"));
        }

        Label typeLabel = new Label(text);
        typeLabel.setStyle("-fx-text-fill: #" + color.toString().substring(2) + ";");
        typesContainer.getChildren().add(typeLabel);
    }

}
