package controller.fragments;

import java.util.Arrays;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import model.managment.LetterManagement;
import model.persistance.Letter;
import utils.Colors;
import view.FXMLHandler;

public class LetterItemController {

    private Controller mainController;

    private LetterManagement management;
    
    @FXML
    private Label objectLabel,
                  descriptionLabel;

    @FXML
    private CheckBox selectedCheckBox;


    @FXML
    private void initialize() {
    }

    @FXML
    private void contentClicked() {
        selectedCheckBox.setSelected(!selectedCheckBox.isSelected());
    }

    @FXML
    private void edit() {
        //mainController.edit();
    }

    public void init(Controller mainController, Letter letter) {
        if (mainController == null || letter == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController and object cannot be null"));
        }
        this.mainController = mainController;
        this.management = new LetterManagement(letter);
        setObjectText(letter.getCharacter());
        setDescriptionText(letter.getCharacterAscii());
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

    public boolean isSelected() {
        return selectedCheckBox.isSelected();
    }

    public CheckBox getSelectCheckbox() {
        return selectedCheckBox;
    }

    @Override
    public int hashCode() {
        Object[] obj = {objectLabel.getText(), descriptionLabel.getText()};
        return Arrays.deepHashCode(obj);
    }
    
}
