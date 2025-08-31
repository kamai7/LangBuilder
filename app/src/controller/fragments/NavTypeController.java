package controller.fragments;

import java.sql.SQLIntegrityConstraintViolationException;

import controller.Controller;
import controller.TypeEditorController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import model.managment.TypeManagement;
import model.persistance.Type;
import utils.Colors;
import view.FXMLHandler;

public class NavTypeController extends NavItem<Type> {

    @FXML
    private void initialize() {
    }

    @FXML
    private void contentClicked() {
        selectedCheckBox.setSelected(!selectedCheckBox.isSelected());
        mainController.getSelectedType().set(this);
    }

    @FXML
    private void edit() {
        FXMLHandler<BorderPane, TypeEditorController> editor = new FXMLHandler<>("/fxml/static/editor_type.fxml");
        mainController.setContent(editor.get(), editor.getController());
        editor.getController().init(mainController, object);
    }

    public void init(Controller mainController, Type type) {
        if (mainController == null || type == null) {
            if (mainController == null) System.out.println("mainController == null");
            throw new IllegalArgumentException(Colors.error("TypeItemController.init" , "mainController and object cannot be null"));
        }
        this.mainController = mainController;
        this.object = type;
        objectLabel.setText(type.getLabel());
        objectLabel.setStyle("-fx-text-fill:" + Colors.colorToHex(type.getColor()));
        if (type.getParentId() != -1) {
            descriptionLabel.setText(type.getParent().getLabel());
            descriptionLabel.setStyle("-fx-text-fill:" + Colors.colorToHex(type.getParent().getColor()));
        }else{
            descriptionLabel.setText("");
        }
    }

    public void delete(){
        TypeManagement management = new TypeManagement(object);
        try{
            management.deleteType();
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this type has already been deleted");
            alert.setTitle("In use error");
            alert.show();
        }catch(SQLIntegrityConstraintViolationException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this type is used by other types or words");
            alert.setTitle("In use error");
            alert.show();
        }
    }
    
}
