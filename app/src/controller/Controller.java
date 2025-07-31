package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import controller.fragments.NavItemController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.managment.Management;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import model.util.Colors;
import model.util.Controls;
import view.FXMLHandler;

public class Controller {

    private FXMLHandler<GridPane,HomeController> contentCode;
    private Management management;

    @FXML
    private TextField wordSearch,
                      typeSearch,
                      letterSearch,
                      wordMinLengthField,
                      wordMaxLengthField,
                      wordMinEmotionalityField,
                      wordMaxEmotionalityField,
                      wordMinVulgarityField,
                      wordMaxVulgarityField,
                      wordMinFormalityField,
                      wordMaxFormalityField;

    @FXML
    private HBox content,
                 wordTypeContainer,
                 wordLinkedContainer,
                 typeParentContainer;

    @FXML
    private GridPane wordLengthContainer,
                     wordEmotionalityContainer,
                     wordVulgarityContainer,
                     wordFormalityContainer;

    @FXML
    private Slider wordMinLengthSlider,
                   wordMaxLengthSlider,
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
                     typeParentCheckBox,
                     wordSelectAllCheckBox,
                     letterSelectAllCheckBox,
                     typeSelectAllCheckBox;

    @FXML
    private ComboBox<String> wordSortComboBox,
                             letterSortComboBox,
                             typeSortComboBox;

    @FXML
    private VBox wordContainer,
                 letterContainer,
                 typeContainer;

    private ObservableList<String> wordSortList,
                                   letterSortList,
                                   typeSortList;
    
    @FXML
    private void initialize() {
        // Add listeners to sliders
        wordMinLengthSlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            wordMinLengthField.setText(String.valueOf(newValue.intValue()));
        });
        wordMaxLengthSlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            wordMaxLengthField.setText(String.valueOf(newValue.intValue()));
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
        wordMinLengthField.textProperty().addListener(event -> {
            try{
                int value = Integer.parseInt(wordMinLengthField.getText());
                wordMinLengthSlider.setValue(value);
            } catch (NumberFormatException e){}
        });
        wordMaxLengthField.textProperty().addListener(event -> {
            try {
                int value =  Integer.parseInt(wordMaxLengthField.getText());
                wordMaxLengthSlider.setValue(value);
            } catch (NumberFormatException e) {}
        });
        wordMinEmotionalityField.textProperty().addListener(event -> {
            try {
                int value = Integer.parseInt(wordMinEmotionalityField.getText());
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
        wordSelectAllCheckBox.selectedProperty().addListener(event -> wordSelectAll());
        letterSelectAllCheckBox.selectedProperty().addListener(event -> letterSelectAll());
        typeSelectAllCheckBox.selectedProperty().addListener(event -> typeSelectAll());


        wordSearch.textProperty().addListener((observable, oldValue, newValue) -> updatewordSearch());
        typeSearch.textProperty().addListener((observable, oldValue, newValue) -> updatetypeSearch());
        letterSearch.textProperty().addListener((observable, oldValue, newValue) -> updateletterSearch());

        //init combo boxes
        ArrayList<String> wordChoices = new ArrayList<>(Arrays.asList("default (types)", "ascending", "descending", "length", "emotionality", "vulgarity", "formality"));
        ArrayList<String> letterChoices = new ArrayList<>(Arrays.asList("default (ascending)", "descending", "most used"));
        ArrayList<String> typeChoices = new ArrayList<>(Arrays.asList("default (ascending)", "descending", "parent"));
        wordSortList = FXCollections.observableArrayList(wordChoices);
        wordSortComboBox.setItems(wordSortList);
        wordSortComboBox.setValue("default (types)");

        letterSortList = FXCollections.observableArrayList(letterChoices);
        letterSortComboBox.setItems(letterSortList);
        letterSortComboBox.setValue("default (types)");

        typeSortList = FXCollections.observableArrayList(typeChoices);
        typeSortComboBox.setItems(typeSortList);
        typeSortComboBox.setValue("default (types)");

        //create management object
        management = new Management();

        //init nav lists
        Set<Word> words = management.getWords100();
        for(Word word: words){
            FXMLHandler<BorderPane, NavItemController> item = Controls.convertWordToFXMLHandler(word);
            wordContainer.getChildren().add(item.get());
        }

        Set<Type> types = management.getTypes100();
        for(Type type: types) {
            FXMLHandler<BorderPane, NavItemController> item = Controls.convertTypeToFXMLHandler(type);
            typeContainer.getChildren().add(item.get());
        }

        Set<Letter> letters = management.getLetters100();
        for(Letter letter: letters) {
            FXMLHandler<BorderPane, NavItemController> item = Controls.convertLetterToFXMLHandler(letter);
            letterContainer.getChildren().add(item.get());
        }
        
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
        letterContainer.getChildren().clear();
        Set<Word> filteredWords = management.getFilteredWords(wordSearch.getText());
        for(Word word: filteredWords) {
            FXMLHandler<BorderPane, NavItemController> wordEditor = Controls.convertWordToFXMLHandler(word);
            wordContainer.getChildren().add(wordEditor.get());
        }
    }

    private void updatetypeSearch(){
        letterContainer.getChildren().clear();
        Set<Type> filteredTypes = management.getFilteredTypes(typeSearch.getText());
        for(Type type: filteredTypes) {
            FXMLHandler<BorderPane, NavItemController> typeEditor = Controls.convertTypeToFXMLHandler(type);
            typeContainer.getChildren().add(typeEditor.get());
        }
    }

    private void updateletterSearch(){
        letterContainer.getChildren().clear();
        Set<Letter> filteredLetters = management.getFilteredLetters(letterSearch.getText());
        for(Letter letter: filteredLetters) {
            FXMLHandler<BorderPane, NavItemController> letterEditor = Controls.convertLetterToFXMLHandler(letter);
            letterContainer.getChildren().add(letterEditor.get());
        }
    }

    private void initHome(){
        FXMLHandler<GridPane, HomeController> home = new FXMLHandler<>("/fxml/static/home_page.fxml");
        this.contentCode = home;
        setContent(contentCode.get());

        HomeController controller = home.getController();
        controller.getCreateLetterButton().setOnAction(e -> {
            FXMLHandler<BorderPane, LetterEditorController> editor = Controls.getLetterEditor();
            setContent(editor.get());
            LetterEditorController editorController = editor.getController();
            editorController.getManagement().getLetter().addListener((observable, oldValue, newValue) -> {
                management.addLetter(newValue);
                updateletterSearch();
                setContent(home.get());
            });
        });

        controller.getCreateTypeButton().setOnAction(e -> {
            FXMLHandler<BorderPane, TypeEditorController> editor = Controls.getTypeEditor();
            setContent(editor.get());
        });

        controller.getCreateWordButton().setOnAction(e -> {
            FXMLHandler<BorderPane, WordEditorController> editor = Controls.getWordEditor();
            setContent(editor.get());
        });
    }

    private void wordSelectAll(){
        System.out.println("Select all words");
    }

    private void letterSelectAll(){
        System.out.println("Select all letters");
    }

    private void typeSelectAll(){
        System.out.println("Select all types");
    }

}
