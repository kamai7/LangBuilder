package controller;

import java.sql.SQLIntegrityConstraintViolationException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.managment.LetterManagement;
import model.persistance.Letter;
import utils.Colors;

public class LetterEditorController {

    private Controller mainController;

    private LetterManagement management;

    @FXML
    private Label headerObject;

    @FXML
    private TextField letter,
                      ascii;

    @FXML
    public void initialize() {
        management = new LetterManagement();
        System.out.println(Colors.success("LetterEditorController initialized"));
    }

    public void initValues(Letter letter) {
        this.letter.setText(letter.getCharacter());
        this.ascii.setText(letter.getCharacterAscii());
    }

    public String getLetter() {
        return letter.getText();
    }

    public String getAscii() {
        return ascii.getText();
    }

    public LetterManagement getManagement() {
        return management;
    }

    @FXML
    private void apply() {
        try{
            management.createLetter(letter.getText(), ascii.getText());
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
        }
    }

    @FXML
    private void cancel() {
        mainController.initHome();
    }

    @FXML
    private void delete() {
        try{
            management.deleteLetter();
            mainController.initHome();
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

    public void setHeaderObject(String object) {
        this.headerObject.setText(object);
    }

    public void setHeaderObjectStyle(String style) {
        this.headerObject.setStyle(style);
    }

    public void init(Controller mainController) {
        this.mainController = mainController;
    }
}
