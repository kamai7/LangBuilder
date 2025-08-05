package controller.fragments;

import java.util.Arrays;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import model.managment.LetterManagement;
import model.managment.TypeManagement;
import model.persistance.Letter;
import model.persistance.Type;
import utils.Colors;
import utils.PersistenceUtils;

public class TypeItemController {

    private Controller mainController;

    private TypeManagement management;
    
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

    public void init(Controller mainController, Type type) {
        if (mainController == null || type == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController and object cannot be null"));
        }
        this.mainController = mainController;
        this.management = new TypeManagement(type);
        setObjectText(type.getLabel());
        setDescriptionText(PersistenceUtils.wordToString(type.getRoot()));
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
