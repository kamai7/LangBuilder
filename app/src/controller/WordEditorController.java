package controller;


import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private Slider lengthSlider,
                   emotionalitySlider,
                   vulgaritySlider,
                   formalitySlider;
    
    @FXML
    private TextField lengthValue,
                      emotionalityValue,
                      vulgarityValue,
                      formalityValue;

    @FXML
    public void initialize() {
        System.out.println(Colors.success("WordEditorController initialized"));

        // Add listeners to sliders
        lengthSlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            lengthValue.setText(String.valueOf(newValue.intValue()));
            lengthSlider.setValue(newValue.intValue());
        });
        emotionalitySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            float rounded = Math.round(newValue.doubleValue() * 20.0) / 20.0f;
            emotionalityValue.setText(rounded + "");
        });
        vulgaritySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            float rounded = Math.round(newValue.doubleValue() * 20.0) / 20.0f;
            vulgarityValue.setText(rounded + "");
        });
        formalitySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            float rounded = Math.round(newValue.doubleValue() * 20.0) / 20.0f;
            formalityValue.setText(rounded + "");
        });

        // Add listeners to text fields
        lengthValue.textProperty().addListener(event -> {
            try{
                int value = Integer.parseInt(lengthValue.getText());
                lengthSlider.setValue(value);
            } catch (NumberFormatException e){}
        });
        emotionalityValue.textProperty().addListener(event -> {
            try {
                float value = Float.parseFloat(emotionalityValue.getText());
                emotionalitySlider.setValue(value);
            } catch (NumberFormatException e) {}
        });
        vulgarityValue.textProperty().addListener(event -> {
            try {
                float value = Integer.parseInt(vulgarityValue.getText());
                vulgaritySlider.setValue(value);
            } catch (NumberFormatException e) {}
        });
        formalityValue.textProperty().addListener(event -> {
            try {
                float value = Integer.parseInt(formalityValue.getText());
                formalitySlider.setValue(value);
            } catch (NumberFormatException e) {
            }
        });
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
