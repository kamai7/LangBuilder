package controller;


import java.text.ParseException;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import model.util.Colors;
import model.util.Utils;

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
            float rounded = Math.round(newValue.doubleValue() * 100.0) / 100.0f;
            emotionalityValue.setText(String.format("%.2f", rounded));
            emotionalitySlider.setValue(rounded);
        });
        vulgaritySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            float rounded = Math.round(newValue.doubleValue() * 100.0) / 100.0f;
            vulgarityValue.setText(String.format("%.2f", rounded));
            vulgaritySlider.setValue(rounded);
        });
        formalitySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            float rounded = Math.round(newValue.doubleValue() * 100.0) / 100.0f;
            formalityValue.setText(String.format("%.2f", rounded));
            formalitySlider.setValue(rounded);
        });

        // Add listeners to text fields
        lengthValue.setOnAction(event -> {
            try {
                int value = Integer.parseInt(lengthValue.getText());
                lengthSlider.setValue(value);
            } catch (NumberFormatException e) {
                lengthValue.setText(String.valueOf((int) lengthSlider.getValue())); // revert to old value if parsing fails
            }
        });
        emotionalityValue.setOnAction(event -> {
            try {
                float value = Utils.parseFloat(emotionalityValue.getText());
                emotionalitySlider.setValue(value);
            } catch (ParseException e) {
                String value = Utils.formatNumber(emotionalitySlider.getValue());
                emotionalityValue.setText(value);
            }
        });
        vulgarityValue.setOnAction(event -> {
            try {
                float value = Utils.parseFloat(vulgarityValue.getText());
                vulgaritySlider.setValue(value);
            } catch (ParseException e) {
                String value = Utils.formatNumber(vulgaritySlider.getValue());
                vulgarityValue.setText(value);
            }
        });
        formalityValue.setOnAction(event -> {
            try {
                float value = Utils.parseFloat(formalityValue.getText());
                formalitySlider.setValue(value);
            } catch (ParseException e) {
                String value = Utils.formatNumber(formalitySlider.getValue());
                System.out.println(value);
                formalityValue.setText(value);
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
