package controller.fragments;

import java.sql.SQLIntegrityConstraintViolationException;

import controller.Controller;
import controller.WordEditorController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.managment.WordManagement;
import model.persistance.Type;
import model.persistance.Word;
import utils.Colors;
import utils.PersistenceUtils;
import view.FXMLHandler;

public class NavWordController implements NavItem<Word> {

    private Controller mainController;

    private Word object;
    
    @FXML
    private Label objectLabel,
                  descriptionLabel;

    @FXML
    private HBox typesContainer;

    @FXML
    private ImageView warningImage;

    @FXML
    private CheckBox selectedCheckBox;


    @FXML
    private void initialize() {
    }

    @FXML
    private void contentClicked() {
        selectedCheckBox.setSelected(!selectedCheckBox.isSelected());
        mainController.getSelectedWord().set(this);
    }

    @FXML
    private void edit() {
        FXMLHandler<BorderPane, WordEditorController> editor = new FXMLHandler<>("/fxml/static/editor_word.fxml");
        mainController.setContent(editor.get());
        editor.getController().init(mainController, object);
    }

    public void init(Controller mainController, Word word) {
        if (mainController == null || word == null) {
            throw new IllegalArgumentException(Colors.error("NavWordController.init" , "mainController and object cannot be null"));
        }
        this.mainController = mainController;
        this.object = word;

        objectLabel.setText(PersistenceUtils.wordToString(word));
        descriptionLabel.setText(PersistenceUtils.wordAsciiToString(word));
        for (Type type : word.getTypes()) {
            addType(type.getLabel(), type.getColor());
        }
        if (word.getTranslations().size() == 0) {
            warningImage.setOpacity(1);
        }
            
    }

    public CheckBox getCheckbox() {
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

    public void delete(){
        WordManagement management = new WordManagement(object);
        try{
            management.deleteWord();
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this letter have already been deleted");
            alert.setTitle("In use error");
            alert.show();
        }catch(SQLIntegrityConstraintViolationException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this letter is used by several words");
            alert.setTitle("In use error");
            alert.show();
        }
    }

    public Word getObject() {
        return object;
    }

}
