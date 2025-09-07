package controller;

import java.util.ArrayList;
import java.util.Arrays;

import controller.fragments.NavItem;
import controller.fragments.NavLetterController;
import controller.fragments.NavTypeController;
import controller.fragments.NavWordController;
import controller.osu.OsuController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.managment.Management;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import utils.AnimationUtils;
import utils.Colors;
import utils.FragmentUtils;
import view.FXMLHandler;
import view.View;

public class Controller {
    private Management management;
    private View view;

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

    @FXML
    private TabPane tabPane;

    private ObservableList<String> wordSortList,
                                   letterSortList,
                                   typeSortList;

    private ArrayList<NavWordController> wordNavItems;
    private ArrayList<NavLetterController> letterNavItems;
    private ArrayList<NavTypeController> typeNavItems;

    private ObjectProperty<NavWordController> selectedWord;
    private ObjectProperty<NavLetterController> selectedLetter;
    private ObjectProperty<NavTypeController> selectedType;

    private Object controllerContent;
    
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
            boolean allSelected = wordSelectAllCheckBox.isSelected();
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

    public void init(View view) {
        this.view = view;
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
        fetchWords();
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
        fetchLetters();
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
        fetchTypes();
        reloadTypesNav();
        typeSelectAllCheckBox.setSelected(false);
    }

    @FXML
    private void osu(){
        OsuController osuController = new OsuController(view);
        setContent(osuController.get(), osuController);
    }

    /**
     * Sets the content of this controller's content pane.
     * first, it will remove the node which is the current content of the pane.
     * then, it will add the new content to the pane.
     * @param <T> type of content (must be a node)
     * @param content the node to set as content
     */
    public <T extends Node, U> void setContent(T content, U controller) {
        if (content == null) {
            throw new IllegalArgumentException(Colors.error("Controller.setContent:", "content cannot be null"));
        }

        this.content.getChildren().clear();
        this.content.getChildren().add(content);
        this.controllerContent = controller;
        content.setScaleX(0.5);
        content.setScaleY(0.5);
        content.setOpacity(0);
        KeyValue scaleX1 = new KeyValue(content.scaleXProperty(), 1.05, AnimationUtils.QUAD_EASE_OUT);
        KeyValue scaleY1 = new KeyValue(content.scaleYProperty(), 1.05, AnimationUtils.QUAD_EASE_OUT);
        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.15), scaleX1, scaleY1);

        KeyValue scaleX2 = new KeyValue(content.scaleXProperty(), 1, AnimationUtils.QUAD_EASE_IN);
        KeyValue scaleY2 = new KeyValue(content.scaleYProperty(), 1, AnimationUtils.QUAD_EASE_IN);
        KeyValue opacity1 = new KeyValue(content.opacityProperty(), 1, AnimationUtils.QUAD_EASE_OUT);
        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.3), scaleX2, scaleY2, opacity1);
        
        Timeline timeline = new Timeline(keyFrame1, keyFrame2);
        timeline.play();
    }

    /**
     * Initializes the home page. used by other controllers to go back to the home page
     */
    public void initHome(){
        FXMLHandler<GridPane, HomeController> home = new FXMLHandler<>("/fxml/static/home_page.fxml");
        setContent(home.get(), home.getController());
        home.getController().init(this);
    }

    /**
     * Returns the controller of the current content pane
     * @return the controller of the current content pane
     */
    public Object getContent() {
        return controllerContent;
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

            wordContainer.getChildren().clear();
            wordNavItems.clear();
            for(Word word: filteredWords) {
                FXMLHandler<BorderPane, NavWordController> wordFragment = new FXMLHandler<>("/fxml/fragments/nav/word.fxml");
                wordContainer.getChildren().add(wordFragment.get());
                NavWordController controller = wordFragment.getController();
                wordNavItems.add(controller);
                controller.init(this, word);
            }
        });
    }

    public void reloadLettersNav(){
        Platform.runLater(() -> {
            ArrayList<Letter> filteredLetters = management.getFilteredLetters(letterSearch.getText());

            letterContainer.getChildren().clear();
            wordNavItems.clear();
            for (Letter letter: filteredLetters) {
                FXMLHandler<BorderPane, NavLetterController> letterFragment = new FXMLHandler<>("/fxml/fragments/nav/letter.fxml");
                letterContainer.getChildren().add(letterFragment.get());
                NavLetterController controller = letterFragment.getController();
                letterNavItems.add(controller);
                controller.init(this, letter);
            }
        });
    }

    public void reloadTypesNav(){
        Platform.runLater(() -> {
            ArrayList<Type> filteredTypes = management.getFilteredTypes(typeSearch.getText());

            typeContainer.getChildren().clear();
            typeNavItems.clear();
            for (Type type: filteredTypes) {
                FXMLHandler<BorderPane, NavTypeController> typeFragment = new FXMLHandler<>("/fxml/fragments/nav/type.fxml");
                typeContainer.getChildren().add(typeFragment.get());
                NavTypeController controller = typeFragment.getController();
                typeNavItems.add(controller);
                controller.init(this, type);
            }
        });
    }

    /**
     * Fetches all words from the database
     * filtering words are more complex than others, so for each research. the managment class need all words, to filter them by itself
     */
    public void fetchWords(){
        management.fetchWords();
    }

    public void fetchLetters(){
        management.fetchLetters();
    }

    public void fetchTypes(){
        management.fetchTypes();
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

    public void selectTypeTab(){
        tabPane.getSelectionModel().select(2);
    }

    public void selectLetterTab(){
        tabPane.getSelectionModel().select(1);
    }

    public void selectWordTab(){
        tabPane.getSelectionModel().select(0);
    }

}
