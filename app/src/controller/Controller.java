package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import controller.fragments.NavItem;
import controller.fragments.NavLetterController;
import controller.fragments.NavTypeController;
import controller.fragments.NavWordController;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.layout.Pane;
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

    private ArrayList<NavWordController> wordNavItems;
    private ArrayList<NavLetterController> letterNavItems;
    private ArrayList<NavTypeController> typeNavItems;

    private ObjectProperty<NavWordController> selectedWord;
    private ObjectProperty<NavLetterController> selectedLetter;
    private ObjectProperty<NavTypeController> selectedType;
    
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
        wordSelectAllCheckBox.selectedProperty().addListener(event -> {
            boolean allSelected = letterSelectAllCheckBox.isSelected();
            for(NavWordController word: wordNavItems){
                word.getCheckbox().setSelected(allSelected);
            }
        });
        letterSelectAllCheckBox.selectedProperty().addListener(event -> {
            boolean allSelected = letterSelectAllCheckBox.isSelected();
            for(NavLetterController letter: letterNavItems){
                letter.getCheckbox().setSelected(allSelected);
            }
        });
        typeSelectAllCheckBox.selectedProperty().addListener(event -> {
            boolean allSelected = typeSelectAllCheckBox.isSelected();
            for(NavTypeController type: typeNavItems){
                type.getCheckbox().setSelected(allSelected);
            }
        });


        wordSearch.textProperty().addListener((observable, oldValue, newValue) -> reloadWordsNav());
        typeSearch.textProperty().addListener((observable, oldValue, newValue) -> reloadTypesNav());
        letterSearch.textProperty().addListener((observable, oldValue, newValue) -> reloadLettersNav());

        //init combo boxes
        ArrayList<String> wordChoices = new ArrayList<>(Arrays.asList("default (types)", "ascending", "descending", "length", "emotionality", "vulgarity", "formality"));
        ArrayList<String> letterChoices = new ArrayList<>(Arrays.asList("default (ascending)", "descending", "most used"));
        ArrayList<String> typeChoices = new ArrayList<>(Arrays.asList("default (ascending)", "descending", "parent"));
        wordSortList = FXCollections.observableArrayList(wordChoices);
        wordSortComboBox.setItems(wordSortList);
        wordSortComboBox.setValue("default (types)");
        wordSortComboBox.setOnAction(event -> reloadWordsNav());

        letterSortList = FXCollections.observableArrayList(letterChoices);
        letterSortComboBox.setItems(letterSortList);
        letterSortComboBox.setValue("default (types)");
        letterSortComboBox.setOnAction(event -> reloadLettersNav());

        typeSortList = FXCollections.observableArrayList(typeChoices);
        typeSortComboBox.setItems(typeSortList);
        typeSortComboBox.setValue("default (types)");
        typeSortComboBox.setOnAction(event -> reloadTypesNav());

        //create management object
        management = new Management();

        wordNavItems = new ArrayList<>();
        typeNavItems = new ArrayList<>();
        letterNavItems = new ArrayList<>();

        selectedWord = new SimpleObjectProperty<>();
        selectedLetter = new SimpleObjectProperty<>();
        selectedType = new SimpleObjectProperty<>();

        //init nav lists
        reloadLettersNav();
        reloadTypesNav();
        reloadWordsNav();
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
        for(NavWordController word: wordNavItems){
            if (word.getCheckbox().isSelected()){
                word.delete();
            }
        }
        reloadWordsNav();
        wordSelectAllCheckBox.setSelected(false);
    }

    @FXML
    private void letterDeleteAll() {
        for(NavLetterController letter: letterNavItems){
            if (letter.getCheckbox().isSelected()){
                letter.delete();
            }
        }
        reloadLettersNav();
        letterSelectAllCheckBox.setSelected(false);
    }

    @FXML
    private void typeDeleteAll() {
        //ArrayList<Integer> index = new ArrayList<>();
        for(NavTypeController type: typeNavItems){
            if (type.getCheckbox().isSelected()){
                type.delete();
            }
        }
        reloadTypesNav();
        typeSelectAllCheckBox.setSelected(false);
    }

    /**
     * Sets the content of this controller's content pane.
     * first, it will remove the node which is the current content of the pane.
     * then, it will add the new content to the pane.
     * @param <T> type of content (must be a node)
     * @param content the node to set as content
     */
    public <T extends Node> void setContent(T content) {
        if (content == null) {
            throw new IllegalArgumentException(Colors.error("Controller.setContent:", "content cannot be null"));
        }
        this.content.getChildren().clear();
        this.content.getChildren().add(content);
    }

    /**
     * Initializes the home page. used by other controllers to go back to the home page
     */
    public void initHome(){
        FXMLHandler<GridPane, HomeController> home = new FXMLHandler<>("/fxml/static/home_page.fxml");
        setContent(home.get());
        home.getController().init(this);
    }

    /**
     * Selects or deselects all items displayed in a list of nav item
     * @param items the list of nav items
     * @param checkbox the checkbox which the state will be read to select or deselect all items
     */
    public void selectAll(ArrayList<NavItem<?>> items, CheckBox checkbox) {
        boolean selectAll = checkbox.isSelected();
        for(NavItem<?> item: items){
            item.getCheckbox().setSelected(selectAll);
        }
    }

    public void reloadWordsNav(){
        Platform.runLater(() -> {
            ArrayList<Word> filteredWords = management.getFilteredWords(wordSearch.getText());
            reloadNav(wordContainer, wordNavItems, filteredWords, "/fxml/fragments/nav/word.fxml");
        });
    }

    public void reloadLettersNav(){
        Platform.runLater(() -> {
            ArrayList<Letter> filteredLetters = management.getFilteredLetters(letterSearch.getText());
            reloadNav(letterContainer, letterNavItems, filteredLetters, "/fxml/fragments/nav/letter.fxml");
        });
    }

    public void reloadTypesNav(){
        Platform.runLater(() -> {
            ArrayList<Type> filteredTypes = management.getFilteredTypes(typeSearch.getText());
            reloadNav(typeContainer, typeNavItems, filteredTypes, "/fxml/fragments/nav/type.fxml");
        });
    }

    private <O, C extends Pane, T extends NavItem<O>> void reloadNav(C itemContainer, ArrayList<T> navItems, ArrayList<O> filteredObjects, String fxmlPath){
        itemContainer.getChildren().clear();
        navItems.clear();

        for(O object: filteredObjects) {
            FXMLHandler<BorderPane, NavItem<T>> itemFragment = new FXMLHandler<>(fxmlPath);
            itemContainer.getChildren().add(itemFragment.get());
            T controller = (T) itemFragment.getController();
            navItems.add(controller);
            controller.init(this, object);
        }
    }

    public void fetchWords(){
        management.fetchWords();
    }

    public ObjectProperty<NavWordController> getSelectedWord() {
        return selectedWord;
    }

    public ObjectProperty<NavTypeController> getSelectedType() {
        return selectedType;
    }

    public ObjectProperty<NavLetterController> getSelectedLetter() {
        return selectedLetter;
    }

}
