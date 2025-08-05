package controller;


import java.util.ArrayList;

import controller.fragments.EditorItemCheckboxController;
import controller.fragments.EditorItemController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import utils.Colors;
import utils.FragmentUtils;
import view.FXMLHandler;

public class WordEditorController {

    private Controller mainController;

    private ArrayList<FXMLHandler<HBox, EditorItemCheckboxController>> letters;

    @FXML
    private HBox chooseTypeButtonContainer;

    @FXML
    private FlowPane lettersPane,
                     typesPane,
                     rootsPane,
                     linksPane;

    @FXML
    private Label headerObject,
                  generatedWordLabel,
                  generatedWordAsciiLabel;

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
                      formalityValue,
                      addLetterField;

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
        FragmentUtils.initSlider(lengthSlider, lengthValue, 1);
        FragmentUtils.initSlider(emotionalitySlider, emotionalityValue, 0.05);
        FragmentUtils.initSlider(vulgaritySlider, vulgarityValue, 0.05);
        FragmentUtils.initSlider(formalitySlider, formalityValue, 0.05);

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

        System.out.println(Colors.success("WordEditorController initialized"));
    }

    @FXML
    private void addLetter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if(addLetterField.getText().length() > 0) {
                FXMLHandler<HBox, EditorItemCheckboxController> letter = new FXMLHandler<>("../fxml/fragments/editor/item_checkbox.fxml");
                EditorItemCheckboxController controller = letter.getController();
                controller.setText(addLetterField.getText());
                lettersPane.getChildren().add(lettersPane.getChildren().size() - 1,letter.get());
                controller.getDeleteObjectButton().setOnAction(e -> lettersPane.getChildren().remove(letter.get()));
                addLetterField.setText("");
            }else{
                System.out.println(Colors.error("Letter field is empty"));
            }
        }
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

    @FXML
    private void apply() {
        mainController.initHome();
    }

    @FXML
    private void cancel() {
        mainController.initHome();
    }

    @FXML
    private void delete() {
        mainController.initHome();
    }

    public void setHeaderObject(String object) {
        this.headerObject.setText(object);
    }

    public void setHeaderObjectStyle(String style) {
        this.headerObject.setStyle(style);
    }

    public double getLength() {
        return lengthSlider.getValue();
    }

    public double getEmotionality() {
        return emotionalitySlider.getValue();
    }

    public double getVulgarity() {
        return vulgaritySlider.getValue();
    }

    public double getFormality() {
        return formalitySlider.getValue();
    }

    public void setLength(double length) {
        lengthSlider.setValue(length);
    }

    public void setEmotionality(double emotionality) {
        emotionalitySlider.setValue(emotionality);
    }

    public void setVulgarity(double vulgarity) {
        vulgaritySlider.setValue(vulgarity);
    }

    public void setFormality(double formality) {
        formalitySlider.setValue(formality);
    }

    public void init(Controller mainController) {
        this.mainController = mainController;
    }

}
