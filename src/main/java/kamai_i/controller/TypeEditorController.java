package kamai_i.controller;

import kamai_i.controller.fragments.NavTypeController;
import kamai_i.controller.fragments.NavWordController;
import kamai_i.controller.listener.SelectionListener;
import kamai_i.exceptions.InvalidUserArgument;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import kamai_i.model.managment.TypeManagement;
import kamai_i.model.persistance.Type;
import kamai_i.model.persistance.Word;
import kamai_i.utils.Colors;
import kamai_i.utils.PersistenceUtils;

public class TypeEditorController extends AbstractEditor<Type> {

    private TypeManagement management;
    private ObservableList<String> positionWords;
    private ChangeListener<NavTypeController> parentListener;
    private ChangeListener<NavWordController> rootListener;
    
    @FXML
    private TextField nameTextField;

    @FXML
    private ColorPicker colorColorPicker;

    @FXML
    private Button chooseWordButton,
                   chooseParentButton;

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
        removeAllListeners();
        mainController.selectWordTab();
        mainController.getSelectedWord().addListener(rootListener);
        chooseWordButton.setText("click on a Word");
        chooseWordButton.setStyle("-fx-text-fill: #ffffffff;");
    }

    @FXML
    private void deleteWord() {
        mainController.getSelectedWord().removeListener(rootListener);
        mainController.getSelectedWord().set(null);
        chooseWordButton.setText("Choose a Word");
        chooseWordButton.setStyle("-fx-font-weight: normal;");
        chooseWordButton.setStyle("-fx-text-fill: #c0c0c0;");

    }

    @FXML
    private void chooseParent() {
        removeAllListeners();
        mainController.selectTypeTab();
        mainController.getSelectedType().addListener(parentListener);
        chooseParentButton.setText("click on a Type");
        chooseParentButton.setStyle("-fx-text-fill: #ffffffff;");
    }

    @FXML
    private void deleteParent() {
        mainController.getSelectedType().removeListener(parentListener);
        mainController.getSelectedType().set(null);
        management.getType().setParent(null);
        chooseParentButton.setText("Choose a Type");
        chooseParentButton.setStyle("-fx-font-weight: normal;");
        chooseParentButton.setStyle("-fx-text-fill: #c0c0c0;");
    }

    @FXML
    private void apply() {
        try{
            Type type = management.getType();
            type.setLabel(nameTextField.getText());
            type.setColor(colorColorPicker.getValue());
            type.setPosition(positionWordComboBox.getSelectionModel().getSelectedIndex());
            management.edit();
            removeAllListeners();
            mainController.fetchTypes();
            mainController.reloadTypesNav();
            mainController.initHome();
        }catch(InvalidUserArgument e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Arguments error");
            alert.setContentText(e.getMessage());
            alert.show();

        }
    } 

    @FXML
    private void cancel() {
        removeAllListeners();
        mainController.initHome();
    }

    @FXML
    private void delete() {
        try{
            management.delete();
            removeAllListeners();
            mainController.fetchTypes();
            mainController.reloadTypesNav();
            mainController.initHome();
        }catch(InvalidUserArgument e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this type have already been deleted");
            alert.setTitle("In use error");
            alert.show();
        }
    }

    private void removeAllListeners() {
        mainController.getSelectedType().removeListener(parentListener);
        mainController.getSelectedWord().removeListener(rootListener);
    }

    public void init(Controller mainController, Type type) {
        if (mainController == null || type == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController cannot be null"));
        }
        init(mainController);
        management = new TypeManagement(type);

        this.headerObject.setText(type.getLabel());
        Color[] colors = Colors.calcGradient(type.getColor());
        this.headerObject.setStyle("-fx-text-fill:" + Colors.radialGradient(colors[0], colors[1]));
        this.nameTextField.setText(type.getLabel());
        this.colorColorPicker.setValue(type.getColor());
        if (type.getParentId() != -1) {
            this.chooseParentButton.setText(type.getParent().getLabel());
            this.chooseParentButton.setStyle("-fx-text-fill: " + Colors.colorToHex(type.getParent().getColor()) + "; -fx-font-weight: bold;");
            System.out.println(Colors.colorToHex(type.getColor()));
        }
        if (type.getRootId() != -1) {
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

        rootListener = new SelectionListener<Word, NavWordController>(mainController.getSelectedWord()) {
            public void perform() throws InvalidUserArgument{
                chooseWordButton.setStyle("-fx-font-weight: bold;");
                chooseWordButton.setText(PersistenceUtils.wordToString(newObject));
                management.getType().setRoot(newObject);
            }
        };

        parentListener = new SelectionListener<Type,NavTypeController>(mainController.getSelectedType()) {
            public void perform() throws InvalidUserArgument{
                management.setParent(newObject);
                chooseParentButton.setText(newObject.getLabel());
                chooseParentButton.setStyle("-fx-text-fill: " + Colors.colorToHex(newObject.getColor()) + "; -fx-font-weight: bold;");
            }
        };

        this.headerObject.setText("???");
        Color color1 = Colors.convertRGBAToColor(new int[]{255, 0, 234, 255});
        Color color2 = Colors.convertRGBAToColor(new int[]{255, 187, 0, 255});
        this.headerObject.setStyle("-fx-text-fill:" + Colors.radialGradient(color1, color2));
    }
}
