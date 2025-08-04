package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.Colors;

public class HomeController {

    @FXML
    private Button createTypeButton,
                   createWordButton,
                   createLetterButton;

    @FXML
    private void initialize() {
        System.out.println(Colors.success("HomeController initialized"));
    }

    public Button getCreateTypeButton() {
        return createTypeButton;
    }

    public Button getCreateWordButton() {
        return createWordButton;
    }

    public Button getCreateLetterButton() {
        return createLetterButton;
    }
}
