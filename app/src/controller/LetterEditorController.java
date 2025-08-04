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
        System.out.println(Colors.info("Cancel button clicked"));
    }

    @FXML
    private void delete() {
        try{
            management.deleteLetter();
        }catch(IllegalArgumentException e){
            return;
        }catch(SQLIntegrityConstraintViolationException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, Colors.error(e.getMessage()));
            alert.setTitle("In use error");
            alert.setContentText("this letter is used by several words");
            alert.show();
        }
    }

    public void setHeaderObject(String object) {
        this.headerObject.setText(object);
    }

    public void setHeaderObjectStyle(String style) {
        this.headerObject.setStyle(style);
    }
}
