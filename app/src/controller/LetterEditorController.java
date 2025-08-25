package controller;

import java.sql.SQLIntegrityConstraintViolationException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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
        System.out.println(Colors.success("LetterEditorController initialized"));
    }


    @FXML
    private void apply() {
        try{
            management.getLetter().setCharacter(letter.getText());
            management.getLetter().setCharacterAscii(ascii.getText());
            management.editLetter();
            mainController.fetchLetters();
            mainController.reloadLettersNav();
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
            mainController.fetchLetters();
            mainController.reloadLettersNav();
            mainController.initHome();
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this letter has already been deleted");
            alert.setTitle("In use error");
            alert.show();
        }catch(SQLIntegrityConstraintViolationException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this letter is used by other words");
            alert.setTitle("In use error");
            alert.show();
        }
    }

    public void init(Controller mainController, Letter letter) {
        if (mainController == null || letter == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController and object cannot be null"));
        }
        init(mainController);
        management = new LetterManagement(letter);
        
        this.letter.setText(letter.getCharacter());
        this.ascii.setText(letter.getCharacterAscii());
        this.headerObject.setText(letter.getCharacter());
    }

    public void init(Controller mainController) {
        if (mainController == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController cannot be null"));
        }
        management = new LetterManagement();

        this.mainController = mainController;
        Color color1 = Colors.convertRGBAToColor(new int[]{255, 0, 234, 255});
        Color color2 = Colors.convertRGBAToColor(new int[]{255, 187, 0, 255});
        this.headerObject.setStyle("-fx-text-fill:" + Colors.radialGradient(color1, color2));

        this.headerObject.setText("?");
    }
}
