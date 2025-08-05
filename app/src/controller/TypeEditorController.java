package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.managment.TypeManagement;
import model.persistance.Type;
import utils.Colors;

public class TypeEditorController {

    private Controller mainController;

    private TypeManagement management;

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

        management = new TypeManagement();

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

    @FXML
    private void apply() {/*
        try{
            management.createType(nameTextField.getText(), colorColorPicker.getValue());
            mainController.initHome();
        }catch(IllegalArgumentException e){

            Alert alert = new Alert(Alert.AlertType.ERROR, Colors.error(e.getMessage()));
            alert.setTitle("Arguments error");
            alert.setContentText(e.getMessage());
            alert.show();

        }catch(SQLIntegrityConstraintViolationException e){

            Alert alert = new Alert(Alert.AlertType.ERROR, Colors.error(e.getMessage()));
            alert.setTitle("Clone error");
            alert.setContentText("this letter already exists");
            alert.show();
        }*/
    } 

    @FXML
    private void cancel() {
        mainController.initHome();
    }

    @FXML
    private void delete() {
        mainController.initHome();
    }

    public void init(Controller mainController, Type type) {
        if (mainController == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController cannot be null"));
        }
        this.mainController = mainController;

        if (type == null){
            this.headerObject.setText("Verb");
            Color color = Colors.convertRGBAToColor(new int[]{0, 174, 255, 255});
            Color[] colors = Colors.calcGradient(color);
            this.headerObject.setStyle("-fx-text-fill:"  + Colors.linearGradient(colors[0], colors[1]));
        }
    }
}
