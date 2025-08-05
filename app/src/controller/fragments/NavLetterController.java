package controller.fragments;

import java.util.Arrays;

import controller.Controller;
import controller.LetterEditorController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import model.persistance.Letter;
import utils.Colors;
import view.FXMLHandler;

public class NavLetterController {

    private Controller mainController;

    private Letter object;
    
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
        FXMLHandler<BorderPane, LetterEditorController> editor = new FXMLHandler<>("/fxml/static/editor_letter.fxml");
        mainController.setContent(editor.get());
        editor.getController().init(mainController, object);
    }

    public void init(Controller mainController, Letter letter) {
        if (mainController == null || letter == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController and object cannot be null"));
        }
        this.mainController = mainController;
        this.object = letter;
        objectLabel.setText(letter.getCharacter());
        descriptionLabel.setText(letter.getCharacterAscii());
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
