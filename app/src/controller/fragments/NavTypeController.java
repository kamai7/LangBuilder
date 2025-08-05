package controller.fragments;

import java.util.Arrays;

import controller.Controller;
import controller.TypeEditorController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import model.persistance.Type;
import utils.Colors;
import utils.PersistenceUtils;
import view.FXMLHandler;

public class NavTypeController {

    private Controller mainController;

    private Type object;
    
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
        FXMLHandler<BorderPane, TypeEditorController> editor = new FXMLHandler<>("/fxml/static/editor_type.fxml");
        mainController.setContent(editor.get());
        editor.getController().init(mainController, object);
    }

    public void init(Controller mainController, Type type) {
        if (mainController == null || type == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController and object cannot be null"));
        }
        this.mainController = mainController;
        this.object = type;
        objectLabel.setText(type.getLabel());
        descriptionLabel.setText(PersistenceUtils.wordToString(type.getRoot()));
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
