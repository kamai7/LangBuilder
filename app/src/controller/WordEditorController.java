package controller;


import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import model.util.Colors;

public class WordEditorController {

    @FXML
    private HBox chooseTypeButtonContainer;

    @FXML
    private FlowPane lettersPane,
                     rootsPane,
                     linksPane;

    @FXML
    private CheckBox usableCheckBox,
                     lengthCheckBox,
                     rootsCheckBox,
                     linksCheckBox,
                     emotionalityCheckBox,
                     vulgarityCheckBox,
                     formalityCheckBox;

    @FXML
    private TextArea translationsArea,
                     definitionsArea;

    @FXML
    public void initialize() {
        System.out.println(Colors.success("WordEditorController initialized"));
    }

    @FXML
    private void addLetter() {
        System.out.println(Colors.info("Add letter button clicked"));
    }

    @FXML
    private void addRoot() {
        System.out.println(Colors.info("Add root button clicked"));
    }

    @FXML
    private void addLink() {
        System.out.println(Colors.info("Add link button clicked"));
    }

    @FXML
    private void chooseType() {
        System.out.println(Colors.info("Choose type button clicked"));
    }

    @FXML
    private void deleteType() {
        System.out.println(Colors.info("Delete type button clicked"));
    }

    @FXML
    private void generate() {
        System.out.println(Colors.info("generate button clicked"));
    }
}
