package controller;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.managment.EditorManagement;
import model.util.Colors;
import view.FXMLHandler;

public class Controller {

    private FXMLHandler<GridPane,HomeController> contentCode;

    @FXML
    private TextField wordSearch,
                      typeSearch,
                      letterSearch,
                      wordLengthField,
                      wordMinEmotionalityField,
                      wordMaxEmotionalityField,
                      wordMinVulgarityField,
                      wordMaxVulgarityField,
                      wordMinFormalityField,
                      wordMaxFormalityField;

    @FXML
    private HBox content,
                 wordLengthContainer,
                 wordTypeContainer,
                 wordLinkedContainer,
                 typeParentContainer;

    @FXML
    private GridPane wordEmotionalityContainer,
                     wordVulgarityContainer,
                     wordFormalityContainer;

    @FXML
    private Slider wordLengthSlider,
                   wordMinEmotionalitySlider,
                   wordMaxEmotionalitySlider,
                   wordMinVulgaritySlider,
                   wordMaxVulgaritySlider,
                   wordMinFormalitySlider,
                   wordMaxFormalitySlider;

    @FXML
    private CheckBox wordLengthCheckBox,
                     wordTypeCheckBox,
                     wordLinkedCheckBox,
                     wordEmotionalityCheckBox,
                     wordVulgarityCheckBox,
                     wordFormalityCheckBox,
                     typeParentCheckBox;
    
    @FXML
    private void initialize() {
        // Add listeners to sliders
        wordLengthSlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            wordLengthField.setText(String.valueOf(newValue.intValue()));
        });
        wordMinEmotionalitySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            float rounded = Math.round(newValue.doubleValue() * 20.0) / 20.0f;
            wordMinEmotionalityField.setText(rounded + "");
        });
        wordMaxEmotionalitySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            float rounded = Math.round(newValue.doubleValue() * 20.0) / 20.0f;
            wordMaxEmotionalityField.setText(rounded + "");
        });
        wordMinVulgaritySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            float rounded = Math.round(newValue.doubleValue() * 20.0) / 20.0f;
            wordMinVulgarityField.setText(rounded + "");
        });
        wordMaxVulgaritySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            float rounded = Math.round(newValue.doubleValue() * 20.0) / 20.0f;
            wordMaxVulgarityField.setText(rounded + "");
        });
        wordMinFormalitySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            float rounded = Math.round(newValue.doubleValue() * 20.0) / 20.0f;
            wordMinFormalityField.setText(rounded + "");
        });

        // Add listeners to text fields
        wordLengthField.textProperty().addListener(event -> {
            try{
                int value = Integer.parseInt(wordLengthField.getText());
                wordLengthSlider.setValue(value);
            } catch (NumberFormatException e){}
        });
        wordMinEmotionalityField.textProperty().addListener(event -> {
            try {
                float value = Float.parseFloat(wordMinEmotionalityField.getText());
                wordMinEmotionalitySlider.setValue(value);
            } catch (NumberFormatException e) {}
        });
        wordMaxEmotionalityField.textProperty().addListener(event -> {
            try {
                float value = Float.parseFloat(wordMaxEmotionalityField.getText());
                wordMaxEmotionalitySlider.setValue(value);
            } catch (NumberFormatException e) {}
        });
        wordMinVulgarityField.textProperty().addListener(event -> {
            try {
                float value = Float.parseFloat(wordMinVulgarityField.getText());
                wordMinVulgaritySlider.setValue(value);
            } catch (NumberFormatException e) {}
        });
        wordMaxVulgarityField.textProperty().addListener(event -> {
            try {
                float value = Float.parseFloat(wordMaxVulgarityField.getText());
                wordMaxVulgaritySlider.setValue(value);
            } catch (NumberFormatException e) {}
        });
        wordMinFormalityField.textProperty().addListener(event -> {
            try {
                float value = Float.parseFloat(wordMinFormalityField.getText());
                wordMinFormalitySlider.setValue(value);
            } catch (NumberFormatException e) {}
        });
        wordMaxFormalityField.textProperty().addListener(event -> {
            try {
                float value = Float.parseFloat(wordMaxFormalityField.getText());
                wordMaxFormalitySlider.setValue(value);
            } catch (NumberFormatException e) {}
        });

        //init selector checkboxes
        wordLengthContainer.setDisable(!wordLengthCheckBox.isSelected());
        wordTypeContainer.setDisable(!wordTypeCheckBox.isSelected());
        wordLinkedContainer.setDisable(!wordLinkedCheckBox.isSelected());
        wordEmotionalityContainer.setDisable(!wordEmotionalityCheckBox.isSelected());
        wordVulgarityContainer.setDisable(!wordVulgarityCheckBox.isSelected());
        wordFormalityContainer.setDisable(!wordFormalityCheckBox.isSelected());
        typeParentContainer.setDisable(!typeParentCheckBox.isSelected());

        wordLengthCheckBox.selectedProperty().addListener(event -> wordLengthContainer.setDisable(!wordLengthCheckBox.isSelected()));
        wordTypeCheckBox.selectedProperty().addListener(event -> wordTypeContainer.setDisable(!wordTypeCheckBox.isSelected()));
        wordLinkedCheckBox.selectedProperty().addListener(event -> wordLinkedContainer.setDisable(!wordLinkedCheckBox.isSelected()));
        wordEmotionalityCheckBox.selectedProperty().addListener(event -> wordEmotionalityContainer.setDisable(!wordEmotionalityCheckBox.isSelected()));
        wordVulgarityCheckBox.selectedProperty().addListener(event -> wordVulgarityContainer.setDisable(!wordVulgarityCheckBox.isSelected()));
        wordFormalityCheckBox.selectedProperty().addListener(event -> wordFormalityContainer.setDisable(!wordFormalityCheckBox.isSelected()));
        typeParentCheckBox.selectedProperty().addListener(event -> typeParentContainer.setDisable(!typeParentCheckBox.isSelected()));


        wordSearch.textProperty().addListener((observable, oldValue, newValue) -> updatewordSearch());
        typeSearch.textProperty().addListener((observable, oldValue, newValue) -> updatetypeSearch());
        letterSearch.textProperty().addListener((observable, oldValue, newValue) -> updateletterSearch());

        initHome();

        System.out.println(Colors.success("Controller initialized"));
    }

    @FXML
    private void load() {
        System.out.println(Colors.info("Load button clicked"));
    }

    @FXML
    private void save() {
        System.out.println(Colors.info("Save button clicked"));
    }

    @FXML
    private void export() {
        System.out.println(Colors.info("Export button clicked"));
    }

    @FXML
    private void home() {
        initHome();
    }

    @FXML
    private void settings() {
        System.out.println(Colors.info("Settings button clicked"));
    }

    @FXML
    private void wordChooseType() {
        System.out.println(Colors.info("Choose type button clicked"));
    }

    @FXML
    private void wordDeleteType() {
        System.out.println(Colors.info("Delete type button clicked"));
    }

    @FXML
    private void wordChooseLinked() {
        System.out.println(Colors.info("Choose linked button clicked"));
    }

    @FXML
    private void wordDeleteLinked() {
        System.out.println(Colors.info("Delete linked button clicked"));
    }

    @FXML
    private void typeChooseParent() {
        System.out.println(Colors.info("Choose parent button clicked"));
    }

    @FXML
    private void typeDeleteParent() {
        System.out.println(Colors.info("Delete parent button clicked"));
    }

    public <T extends Node> void setContent(T content) {
        this.content.getChildren().clear();
        this.content.getChildren().add(content);
    }

    private void updatewordSearch(){
        System.out.println(Colors.info("Search word: ", wordSearch.getText()));
    }

    private void updatetypeSearch(){
        System.out.println(Colors.info("Search type: ", typeSearch.getText()));
    }

    private void updateletterSearch(){
        System.out.println(Colors.info("Search letter: ", letterSearch.getText()));
    }

    private void initHome(){
        FXMLHandler<GridPane, HomeController> home = new FXMLHandler<>("/fxml/static/home_page.fxml");
        this.contentCode = home;
        setContent(contentCode.get());

        HomeController controller = home.getController();
        controller.getCreateLetterButton().setOnAction(e -> {
            FXMLHandler<BorderPane, EditorController> editor = EditorManagement.openLetterEditor();
            setContent(editor.get());
        });

        controller.getCreateTypeButton().setOnAction(e -> {
            FXMLHandler<BorderPane, EditorController> editor = EditorManagement.openTypeEditor();
            setContent(editor.get());
        });

        controller.getCreateWordButton().setOnAction(e -> {
            FXMLHandler<BorderPane, EditorController> editor = EditorManagement.openWordEditor();
            setContent(editor.get());
        });
    }

}
