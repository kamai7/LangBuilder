package controller;

import java.awt.Button;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import model.util.Colors;

public class WordEditorController {

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private FlowPane lettersPane,
                     rootsPane,
                     linksPane;
    
    @FXML
    private Button addLetterButton,
                   addRootButton,
                   addLinkButton;

    @FXML
    private CheckBox usableCheckBox,
                     lengthCheckBox,
                     rootsCheckBox,
                     linksCheckBox,
                     emotionalityCheckBox,
                     vulgarityCheckBox,
                     formalityCheckBox;

    @FXML
    public void initialize() {
        System.out.println(Colors.success("WordEditorController initialized"));
    }
}
