package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import controller.fragments.NavLetterController;
import controller.fragments.NavTypeController;
import controller.fragments.NavWordController;
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
import utils.Colors;
import utils.FragmentUtils;
import view.FXMLHandler;

public class Controller {
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

    private Set<FXMLHandler<BorderPane, NavWordController>> wordNavItems;
    private Set<FXMLHandler<BorderPane, NavLetterController>> letterNavItems;
    private Set<FXMLHandler<BorderPane, NavTypeController>> typeNavItems;
    
    @FXML
    private void initialize() {

        //init sliders
        FragmentUtils.initSlider(wordMinLengthSlider, wordMinLengthField, 1);
        FragmentUtils.initSlider(wordMaxLengthSlider, wordMaxLengthField, 1);
        FragmentUtils.initSlider(wordMinEmotionalitySlider, wordMinEmotionalityField, 0.05);
        FragmentUtils.initSlider(wordMaxEmotionalitySlider, wordMaxEmotionalityField, 0.05);
        FragmentUtils.initSlider(wordMinVulgaritySlider, wordMinVulgarityField, 0.05);
        FragmentUtils.initSlider(wordMaxVulgaritySlider, wordMaxVulgarityField, 0.05);
        FragmentUtils.initSlider(wordMinFormalitySlider, wordMinFormalityField, 0.05);
        FragmentUtils.initSlider(wordMaxFormalitySlider, wordMaxFormalityField, 0.05);

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


        wordSearch.textProperty().addListener((observable, oldValue, newValue) -> loadWordsNav());
        typeSearch.textProperty().addListener((observable, oldValue, newValue) -> loadTypesNav());
        letterSearch.textProperty().addListener((observable, oldValue, newValue) -> loadLettersNav());

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

        wordNavItems = new HashSet<>();
        typeNavItems = new HashSet<>();
        letterNavItems = new HashSet<>();

        //init nav lists
        loadLettersNav();
        loadTypesNav();
        loadWordsNav();
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

    @FXML
    private void wordEditAll() {
        System.out.println(Colors.info("Edit all button clicked"));
    }

    @FXML
    private void letterEditAll() {
        System.out.println(Colors.info("Edit all button clicked"));
    }

    @FXML
    private void typeEditAll() {
        System.out.println(Colors.info("Edit all button clicked"));
    }

    @FXML
    private void wordDeleteAll() {
        for(FXMLHandler<BorderPane, NavWordController> item: wordNavItems){
            if (item.getController().getSelectCheckbox().isSelected()){
                item.getController().delete();
            }
        }
        loadWordsNav();
        wordSelectAllCheckBox.setSelected(false);
    }

    @FXML
    private void letterDeleteAll() {
        for(FXMLHandler<BorderPane, NavLetterController> item: letterNavItems){
            if (item.getController().getSelectCheckbox().isSelected()){
                item.getController().delete();
            }
        }
        loadLettersNav();
        letterSelectAllCheckBox.setSelected(false);
    }

    @FXML
    private void typeDeleteAll() {
        for(FXMLHandler<BorderPane, NavTypeController> item: typeNavItems){
            if (item.getController().getSelectCheckbox().isSelected()){
                item.getController().delete();
            }
        }
        loadTypesNav();
        typeSelectAllCheckBox.setSelected(false);
    }

    public <T extends Node> void setContent(T content) {
        if (content == null) {
            throw new IllegalArgumentException(Colors.error("Controller.setContent:", "content cannot be null"));
        }
        this.content.getChildren().clear();
        this.content.getChildren().add(content);
    }

    private void wordSelectAll(){
        boolean allSelected = wordSelectAllCheckBox.isSelected();
        for(FXMLHandler<BorderPane, NavWordController> item: wordNavItems){
            item.getController().getSelectCheckbox().setSelected(allSelected);
        }
    }

    private void letterSelectAll(){
        boolean allSelected = letterSelectAllCheckBox.isSelected();
        for(FXMLHandler<BorderPane, NavLetterController> item: letterNavItems){
            item.getController().getSelectCheckbox().setSelected(allSelected);
        }
    }

    private void typeSelectAll(){
        boolean allSelected = typeSelectAllCheckBox.isSelected();
        for(FXMLHandler<BorderPane, NavTypeController> item: typeNavItems){
            item.getController().getSelectCheckbox().setSelected(allSelected);
        }
    }

    public void initHome(){
        FXMLHandler<GridPane, HomeController> home = new FXMLHandler<>("/fxml/static/home_page.fxml");
        setContent(home.get());
        home.getController().init(this);
    }

    public void loadWordsNav(){
        wordContainer.getChildren().clear();
        wordNavItems.clear();
        Set<Word> filteredWords = management.getFilteredWords(wordSearch.getText());
        for(Word word: filteredWords) {
            FXMLHandler<BorderPane, NavWordController> wordEditor = new FXMLHandler<>("/fxml/fragments/nav/word.fxml");
            wordContainer.getChildren().add(wordEditor.get());
            wordNavItems.add(wordEditor);
            wordEditor.getController().init(this, word);
        }
    }

    public void loadLettersNav(){
        letterContainer.getChildren().clear();
        letterNavItems.clear();
        Set<Letter> filteredLetters = management.getFilteredLetters(letterSearch.getText());
        for(Letter letter: filteredLetters) {
            FXMLHandler<BorderPane, NavLetterController> letterEditor = new FXMLHandler<>("/fxml/fragments/nav/letter.fxml");
            letterContainer.getChildren().add(letterEditor.get());
            letterNavItems.add(letterEditor);
            letterEditor.getController().init(this, letter);
        }
    }

    public void loadTypesNav(){
        typeContainer.getChildren().clear();
        typeNavItems.clear();
        Set<Type> filteredTypes = management.getFilteredTypes(typeSearch.getText());
        for(Type type: filteredTypes) {
            FXMLHandler<BorderPane, NavTypeController> typeEditor = new FXMLHandler<>("/fxml/fragments/nav/type.fxml");
            typeContainer.getChildren().add(typeEditor.get());
            typeNavItems.add(typeEditor);
            typeEditor.getController().init(this, type);
        }
    }

}
