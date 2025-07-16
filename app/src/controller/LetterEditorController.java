package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.util.Colors;

public class LetterEditorController {

    private String letterCode, asciiCode;


    @FXML
    private TextField letter, ascii;

    @FXML
    public void initialize() {
        System.out.println(Colors.success("LetterEditorController initialized"));
    }

    public void initValues(String letter, String ascii) {
        this.letterCode = letter;
        this.asciiCode = ascii;
        this.letter.setText(letterCode);
        this.ascii.setText(asciiCode);
    }

    public String getLetter() {
        return letter.getText();
    }

    public String getAscii() {
        return ascii.getText();
    }
}
