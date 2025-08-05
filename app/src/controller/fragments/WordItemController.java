package controller.fragments;

import java.util.Arrays;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.managment.WordManagement;
import model.persistance.Type;
import model.persistance.Word;
import utils.Colors;
import utils.PersistenceUtils;

public class WordItemController {

    private Controller mainController;

    private WordManagement management;
    
    @FXML
    private Label objectLabel,
                  descriptionLabel;

    @FXML
    private HBox typesContainer;

    @FXML
    private Image warningImage;

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

    public void init(Controller mainController, Word word) {
        if (mainController == null || word == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController and object cannot be null"));
        }
        this.mainController = mainController;
        this.management = new WordManagement(word);

        setObjectText(PersistenceUtils.wordToString(word));
        setDescriptionText(PersistenceUtils.wordAsciiToString(word));
        for (Type type : word.getTypes()) {
            addType(type.getLabel(), type.getColor());
        }
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

    @Override
    public int hashCode() {
        Object[] obj = {objectLabel.getText(), descriptionLabel.getText()};
        return Arrays.deepHashCode(obj);
    }

}
