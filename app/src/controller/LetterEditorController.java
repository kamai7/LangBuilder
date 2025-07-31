package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.managment.LetterManagement;
import model.persistance.Letter;
import model.util.Colors;

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
            management.addLetter(letter.getText(), ascii.getText());
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, Colors.error(e.getMessage()));
            alert.setTitle("Arguments error");
            alert.setContentText(e.getMessage());
            alert.show();
        }catch(SQLException e){
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
        System.out.println(Colors.info("Delete button clicked"));
    }

    public void setHeaderObject(String object) {
        this.headerObject.setText(object);
    }

    public void setHeaderObjectStyle(String style) {
        this.headerObject.setStyle(style);
    }
}
