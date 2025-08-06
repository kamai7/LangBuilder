package controller;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import controller.fragments.WordFieldController;

import controller.fragments.WordLetterController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.managment.WordManagement;
import model.persistance.Word;
import utils.Colors;
import utils.FragmentUtils;
import utils.PersistenceUtils;
import view.FXMLHandler;

public class WordEditorController {

    private Controller mainController;

    private WordManagement management;

    private ArrayList<FXMLHandler<HBox, WordLetterController>> letters;
    private ArrayList<FXMLHandler<HBox, WordFieldController>> roots;
    private ArrayList<FXMLHandler<HBox, WordFieldController>> links;
    private ArrayList<FXMLHandler<HBox, WordFieldController>> types;

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
        roots = new ArrayList<>();
        links = new ArrayList<>();
        types = new ArrayList<>();

        System.out.println(Colors.success("WordEditorController initialized"));
    }

    @FXML
    private void addLetter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if(addLetterField.getText().length() > 0) {
                FXMLHandler<HBox, WordLetterController> letter = new FXMLHandler<>("../fxml/fragments/editor/word_letter.fxml");
                WordLetterController wordLetterController = letter.getController();
                lettersPane.getChildren().add(lettersPane.getChildren().size() - 1,letter.get());
                wordLetterController.init(addLetterField.getText());
                wordLetterController.getDeleteButton().setOnAction(event1 -> {lettersPane.getChildren().remove(letter.get()); letters.remove(letter);});
                addLetterField.setText("");
                letters.add(letter);
            }else{
                System.out.println(Colors.error("Letter field is empty"));
            }
        }
    }

    @FXML
    private void addRoot() {
        FXMLHandler<HBox, WordFieldController> root = new FXMLHandler<>("../fxml/fragments/editor/word_field.fxml");
        rootsPane.getChildren().add(root.get());
        WordFieldController wordFieldController = root.getController();
        wordFieldController.init("Σ");
        wordFieldController.getDeleteButton().setOnAction(event -> {rootsPane.getChildren().remove(root.get()); roots.remove(root);});
        roots.add(root);

    }

    @FXML
    private void addLink() {
        FXMLHandler<HBox, WordFieldController> link = new FXMLHandler<>("../fxml/fragments/editor/word_field.fxml");
        linksPane.getChildren().add(link.get());
        link.getController().getDeleteButton().setOnAction(event -> {linksPane.getChildren().remove(link.get()); links.remove(link);});
        link.getController().init("Σ");
        links.add(link);
    }

    @FXML
    private void addType() {
        FXMLHandler<HBox, WordFieldController> type = new FXMLHandler<>("/fxml/fragments/editor/word_field.fxml");
        typesPane.getChildren().add(type.get());
        type.getController().getDeleteButton().setOnAction(event -> {typesPane.getChildren().remove(type.get()); types.remove(type);});
        type.getController().init("verb");
        types.add(type);
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
        try{
            management.deleteWord();
            mainController.initHome();
            mainController.loadLettersNav();
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this letter have already been deleted");
            alert.setTitle("In use error");
            alert.show();
        }catch(SQLIntegrityConstraintViolationException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this letter is used by several words");
            alert.setTitle("In use error");
            alert.show();
        }
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

    public void init(Controller mainController, Word word) {
        if (mainController == null || word == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController and object cannot be null"));
        }
        init(mainController);
        management = new WordManagement(word);

        this.headerObject.setText(PersistenceUtils.wordToString(word));
    }

    public void init(Controller mainController) {
        if (mainController == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController cannot be null"));
        }
        management = new WordManagement();

        this.mainController = mainController;
        this.headerObject.setText("???");
        Color color2 = Colors.convertRGBAToColor(new int[]{153, 0, 255, 255});
        Color color1 = Colors.convertRGBAToColor(new int[]{0, 174, 255, 255});
        this.headerObject.setStyle("-fx-text-fill:" + Colors.radialGradient(color1, color2));
    }

}