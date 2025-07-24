package controller;


import java.util.ArrayList;

import controller.fragments.EditorItemCheckboxController;
import controller.fragments.EditorItemController;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import model.managment.EditorManagement;
import model.util.Colors;
import view.FXMLHandler;

public class WordEditorController {

    private ArrayList<FXMLHandler<HBox, EditorItemCheckboxController>> letters;

    private EditorManagement wordManagement;

    @FXML
    private HBox chooseTypeButtonContainer;

    @FXML
    private FlowPane lettersPane,
                     typesPane,
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
    private HBox lengthContainer,
                 emotionalityContainer,
                 vulgarityContainer,
                 formalityContainer,
                 rootsContainer,
                 linksContainer;

    @FXML
    public void initialize() {

        // Add listeners to sliders
        lengthSlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            lengthValue.setText(String.valueOf(newValue.intValue()));
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
                float value = Float.parseFloat(vulgarityValue.getText());
                vulgaritySlider.setValue(value);
            } catch (NumberFormatException e) {}
        });
        formalityValue.textProperty().addListener(event -> {
            try {
                float value = Float.parseFloat(formalityValue.getText());
                formalitySlider.setValue(value);
            } catch (NumberFormatException e) {
            }
        });

        lengthContainer.setDisable(!lengthCheckBox.isSelected());
        emotionalityContainer.setDisable(!emotionalityCheckBox.isSelected());
        vulgarityContainer.setDisable(!vulgarityCheckBox.isSelected());
        formalityContainer.setDisable(!formalityCheckBox.isSelected());
        rootsContainer.setDisable(!rootsCheckBox.isSelected());
        linksContainer.setDisable(!linksCheckBox.isSelected());

        lengthCheckBox.selectedProperty().addListener(event -> lengthContainer.setDisable(!lengthCheckBox.isSelected()));
        emotionalityCheckBox.selectedProperty().addListener(event -> emotionalityContainer.setDisable(!emotionalityCheckBox.isSelected()));
        vulgarityCheckBox.selectedProperty().addListener(event -> vulgarityContainer.setDisable(!vulgarityCheckBox.isSelected()));
        formalityCheckBox.selectedProperty().addListener(event -> formalityContainer.setDisable(!formalityCheckBox.isSelected()));
        rootsCheckBox.selectedProperty().addListener(event -> rootsContainer.setDisable(!rootsCheckBox.isSelected()));
        linksCheckBox.selectedProperty().addListener(event -> linksContainer.setDisable(!linksCheckBox.isSelected()));

        letters = new ArrayList<>();

        wordManagement = new EditorManagement();

        System.out.println(Colors.success("WordEditorController initialized"));
    }

    @FXML
    private void addLetter() {
        FXMLHandler<HBox, EditorItemCheckboxController> letter = new FXMLHandler<>("../fxml/fragments/editor/item_checkbox.fxml");
        EditorItemCheckboxController controller = letter.getController();
        controller.setText("Σ");
        lettersPane.getChildren().add(letter.get());
        letters.add(letter);

        controller.getDeleteObjectButton().setOnAction(e -> lettersPane.getChildren().remove(letter.get()));
    }

    @FXML
    private void addRoot() {
        FXMLHandler<HBox, EditorItemController> root = new FXMLHandler<>("../fxml/fragments/editor/item.fxml");
        EditorItemController controller = root.getController();
        controller.setText("Σ");
        rootsPane.getChildren().add(root.get());
        controller.getDeleteObjectButton().setOnAction(e -> rootsPane.getChildren().remove(root.get()));
    }

    @FXML
    private void addLink() {
        FXMLHandler<HBox, EditorItemController> link = new FXMLHandler<>("../fxml/fragments/editor/item.fxml");
        EditorItemController controller = link.getController();
        controller.setText("Σ");
        linksPane.getChildren().add(link.get());
        controller.getDeleteObjectButton().setOnAction(e -> linksPane.getChildren().remove(link.get()));
    }

    @FXML
    private void addType() {
        System.out.println(Colors.info("add type button clicked"));
    }

    @FXML
    private void deleteType() {
        System.out.println(Colors.info("Delete type button clicked"));
    }

    @FXML
    private void generate() {
        System.out.println(Colors.info("generate button clicked"));
    }

    @FXML
    private void nextGenerateButton() {
        System.out.println(Colors.info("next generate button clicked"));
    }

    @FXML
    private void previousGenerateButton() {
        System.out.println(Colors.info("previous generate button clicked"));
    }

    public float getLength() {
        return lengthSlider.getValue();
    }

    public float getEmotionality() {
        return emotionalitySlider.getValue();
    }

    public float getVulgarity() {
        return vulgaritySlider.getValue();
    }

    public float getFormality() {
        return formalitySlider.getValue();
    }

    public void setLength(float length) {
        lengthSlider.setValue(length);
    }

    public void setEmotionality(float emotionality) {
        emotionalitySlider.setValue(emotionality);
    }

    public void setVulgarity(float vulgarity) {
        vulgaritySlider.setValue(vulgarity);
    }

    public void setFormality(float formality) {
        formalitySlider.setValue(formality);
    }

}
