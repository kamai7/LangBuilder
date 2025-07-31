package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.persistance.Type;
import model.util.Colors;

public class TypeEditorController {

    private ObservableList<String> positionWords;
    
    @FXML
    private TextField nameTextField;

    @FXML
    private ColorPicker colorColorPicker;

    @FXML
    private Label headerObject;

    @FXML
    private Button chooseWordButton;

    @FXML
    private ComboBox<String> positionWordComboBox;

    @FXML
    private void initialize() {

        positionWords = FXCollections.observableArrayList("start", "middle", "end");
        positionWordComboBox.setItems(positionWords);

        System.out.println(Colors.success("TypeEditorController initialized"));
    }

    public void initValues(Type type){
        this.nameTextField.setText(type.getLabel());
        this.colorColorPicker.setValue(type.getColor());
    }

    @FXML
    private void chooseWord() {
        System.out.println(Colors.info("Choose word button clicked"));
    }

    @FXML
    private void deleteWord() {
        System.out.println(Colors.info("Delete word button clicked"));
    }

    @FXML
    private void chooseParent() {
        System.out.println(Colors.info("Choose parent button clicked"));
    }

    @FXML
    private void apply() {
        System.out.println(Colors.info("Apply button clicked"));
    }

    @FXML
    private void cancel() {
        System.out.println(Colors.info("Cancel button clicked"));
    }

    @FXML
    private void delete() {
        System.out.println(Colors.info("Delete button clicked"));
    }

    public void setHeaderObject(String object) {
        this.headerObject.setText(object);
    }

    public void setHeaderObjectStyle(String style) {
        this.headerObject.setStyle(style);
    }
}
