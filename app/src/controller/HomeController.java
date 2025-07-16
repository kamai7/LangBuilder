package controller;

import javafx.fxml.FXML;
import model.util.Colors;

public class HomeController {

    @FXML
    private void initialize() {
        System.out.println(Colors.success("HomeController initialized"));
    }

    @FXML
    private void createWord() {
        System.out.println(Colors.info("Create word button clicked"));
    }

    @FXML
    private void createLetter() {
        System.out.println(Colors.info("Create letter button clicked"));
    }

    @FXML
    private void createType() {
        System.out.println(Colors.info("Create type button clicked"));
    }
}
