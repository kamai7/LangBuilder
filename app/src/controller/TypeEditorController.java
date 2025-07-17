package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.util.Colors;

public class TypeEditorController {

    private ObservableList<String> positionWords;
    
    @FXML
    private TextField nameTextField;

    @FXML
    private ColorPicker colorColorPicker;

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
}
