package kamai_i.controller.fragments;

import kamai_i.controller.Controller;
import kamai_i.controller.LetterEditorController;
import kamai_i.exceptions.InvalidUserArgument;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import kamai_i.model.managment.LetterManagement;
import kamai_i.model.persistance.Letter;
import kamai_i.utils.Colors;
import kamai_i.view.FXMLHandler;

public class NavLetterController extends NavItem<Letter>{

    @FXML
    private void initialize() {}

    @FXML
    protected void contentClicked() {
        selectedCheckBox.setSelected(!selectedCheckBox.isSelected());
        mainController.getSelectedLetter().set(this);
    }

    @FXML
    private void edit() {
        FXMLHandler<BorderPane, LetterEditorController> editor = new FXMLHandler<>("/fxml/static/editor_letter.fxml");
        mainController.setContent(editor.get(), editor.getController());
        editor.getController().init(mainController, object);
    }

    public void init(Controller mainController, Letter letter) {
        if (mainController == null || letter == null) {
            throw new IllegalArgumentException(Colors.error("NavLetterController.init" , "mainController and object cannot be null"));
        }
        this.mainController = mainController;
        this.object = letter;
        objectLabel.setText(letter.getCharacter());
        descriptionLabel.setText(letter.getCharacterAscii());
    }

    public void delete(){
        LetterManagement management = new LetterManagement(object);
        try{
            management.delete();
            if (!mainController.getContent().equals(this)) {
                mainController.initHome();
            }
        }catch(InvalidUserArgument e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this letter has already been deleted");
            alert.setTitle("In use error");
            alert.show();
        }
    }
    
}
