package controller;

import java.sql.SQLIntegrityConstraintViolationException;

import controller.fragments.NavTypeController;
import controller.fragments.NavWordController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.managment.TypeManagement;
import model.persistance.Type;
import utils.Colors;
import utils.PersistenceUtils;

public class TypeEditorController {

    private Controller mainController;
    private TypeManagement management;
    private ObservableList<String> positionWords;
    private ChangeListener<NavTypeController> parentListener;
    private ChangeListener<NavWordController> rootListener;
    
    @FXML
    private TextField nameTextField;

    @FXML
    private ColorPicker colorColorPicker;

    @FXML
    private Label headerObject;

    @FXML
    private Button chooseWordButton,
                   chooseParentButton;

    @FXML
    private ComboBox<String> positionWordComboBox;

    @FXML
    private void initialize() {

        positionWords = FXCollections.observableArrayList("start", "middle", "end");
        positionWordComboBox.setItems(positionWords);

        rootListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends NavWordController> observable, NavWordController oldValue, NavWordController newValue) {
                chooseParentButton.setStyle("-fx-font-weight: bold;");
                management.setRoot(newValue.getWord());
                //remove the listener
                mainController.getSelectedWord().removeListener(this);
            }
        };

        parentListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends NavTypeController> observable, NavTypeController oldValue, NavTypeController newValue) {
                chooseParentButton.setText(newValue.getType().getLabel());
                chooseParentButton.setStyle("-fx-text-fill: " + Colors.colorToHex(newValue.getType().getColor()) + ";");
                chooseParentButton.setStyle("-fx-font-weight: bold;");
                management.setParent(newValue.getType());
                //remove the listener
                mainController.getSelectedType().removeListener(this);
                System.out.println("listener removed");
            }
        };

        System.out.println(Colors.success("TypeEditorController initialized"));
    }

    @FXML
    private void chooseWord() {
        mainController.getSelectedWord().addListener(rootListener);
        chooseWordButton.setText("click on a Word");
        chooseWordButton.setStyle("-fx-text-fill: #ffffffff;");
    }

    @FXML
    private void deleteWord() {
        management.setRoot(null);
        mainController.getSelectedWord().removeListener(rootListener);
        chooseWordButton.setText("Choose a Word");
        chooseWordButton.setStyle("-fx-font-weight: normal;");
        chooseWordButton.setStyle("-fx-text-fill: #c0c0c0;");

    }

    @FXML
    private void chooseParent() {
        mainController.getSelectedType().addListener(parentListener);
        System.out.println("listener set");
        chooseParentButton.setText("click on a Type");
        chooseParentButton.setStyle("-fx-text-fill: #ffffffff;");
    }

    @FXML
    private void deleteParent() {
        management.setParent(null);
        mainController.getSelectedType().removeListener(parentListener);
        System.out.println("listener removed");
        chooseParentButton.setText("Choose a Type");
        chooseParentButton.setStyle("-fx-font-weight: normal;");
        chooseParentButton.setStyle("-fx-text-fill: #c0c0c0;");
    }

    @FXML
    private void apply() {
        management.setLabel(nameTextField.getText());
        management.setColor(colorColorPicker.getValue());
        management.setPosition(positionWordComboBox.getSelectionModel().getSelectedIndex());
        try{
            management.editType();
            mainController.initHome();
            mainController.loadTypesNav();
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.setTitle("Arguments error");
            alert.setContentText(e.getMessage());
            alert.show();

        }catch(SQLIntegrityConstraintViolationException e){

            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.setTitle("Clone error");
            alert.setContentText("this letter already exists");
            alert.show();
        }
    } 

    @FXML
    private void cancel() {
        mainController.initHome();
    }

    @FXML
    private void delete() {
        try{
            management.deleteType();
            mainController.initHome();
            mainController.loadTypesNav();
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this type have already been deleted");
            alert.setTitle("In use error");
            alert.show();
        }catch(SQLIntegrityConstraintViolationException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this type is used by several words or other types");
            alert.setTitle("In use error");
            alert.show();
        }
    }

    public void init(Controller mainController, Type type) {
        if (mainController == null || type == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController cannot be null"));
        }
        init(mainController);
        management = new TypeManagement(type);

        this.headerObject.setText(type.getLabel());
        Color[] colors = Colors.calcGradient(type.getColor());
        System.out.println("colors: " + colors[0] + " " + colors[1]+ " " + type.getColor());
        this.headerObject.setStyle("-fx-text-fill:" + Colors.radialGradient(colors[0], colors[1]));
        this.nameTextField.setText(type.getLabel());
        this.colorColorPicker.setValue(type.getColor());
        if (type.getParentId() != 0) {
            this.chooseParentButton.setText(type.getParent().getLabel());
            this.chooseParentButton.setStyle("-fx-font-weight: bold;");
        }
        if (type.getRootId() != 0) {
            this.chooseWordButton.setText(PersistenceUtils.wordToString(type.getRoot()));
            this.chooseWordButton.setStyle("-fx-font-weight: bold;");
        }
        if(type.getPosition() != -1) {
            this.positionWordComboBox.getSelectionModel().select(type.getPosition());
        }
    }

    public void init(Controller mainController) {
        if (mainController == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController cannot be null"));
        }
        management = new TypeManagement();

        this.mainController = mainController;
        this.headerObject.setText("???");
        Color color1 = Colors.convertRGBAToColor(new int[]{255, 0, 234, 255});
        Color color2 = Colors.convertRGBAToColor(new int[]{255, 187, 0, 255});
        this.headerObject.setStyle("-fx-text-fill:" + Colors.radialGradient(color1, color2));
    }
}
