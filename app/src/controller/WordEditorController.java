package controller;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Set;

import controller.fragments.NavTypeController;
import controller.fragments.NavWordController;
import controller.fragments.WordFieldController;

import controller.fragments.WordLetterController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import utils.Colors;
import utils.FragmentUtils;
import utils.PersistenceUtils;
import view.FXMLHandler;

public class WordEditorController {

    private boolean delete = true;

    private Controller mainController;

    private WordManagement management;

    private ChangeListener<NavTypeController> chooseTypeListener;
    private ChangeListener<NavWordController> chooseRootListener;
    private ChangeListener<NavWordController> chooseLinkListener;

    @FXML
    private HBox chooseTypeButtonContainer;

    @FXML
    private FlowPane lettersPane,
                     typesPane,
                     rootsPane,
                     linksPane;

    @FXML
    private Label headerObject,
                  wordPreviewLabel,
                  wordAsciiPreviewLabel;

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

        emotionalitySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> management.getWord().setEmotional(newValue.doubleValue()));
        vulgaritySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> management.getWord().setVulgarity(newValue.doubleValue()));
        formalitySlider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> management.getWord().setFormality(newValue.doubleValue()));

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
        usableCheckBox.selectedProperty().addListener(event -> management.getWord().setUsable(usableCheckBox.isSelected()));

        chooseTypeListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends NavTypeController> observable, NavTypeController oldValue, NavTypeController newValue) {
                Type type = newValue.getObject();
                try {
                    management.getWord().getTypes().add(newValue.getObject());

                    addField(type, typesPane, management.getWord().getTypes(), type.getLabel(), type.getColor());

                    //remove the listener
                    mainController.getSelectedType().removeListener(this);
                    mainController.getSelectedType().set(null);
                }catch(IllegalArgumentException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.setTitle("Arguments error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        };

        chooseRootListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends NavWordController> observable, NavWordController oldValue, NavWordController newValue) {
                Word word = newValue.getObject();
                try {
                    management.getWord().getRoots().add(word);
                    addField(word, rootsPane, management.getWord().getRoots(), PersistenceUtils.wordToString(word), null);
                    
                    //remove the listener
                    mainController.getSelectedWord().removeListener(this);
                    mainController.getSelectedWord().set(null);
                }catch(IllegalArgumentException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.setTitle("Arguments error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        };

        chooseLinkListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends NavWordController> observable, NavWordController oldValue, NavWordController newValue) {
                Word word = newValue.getObject();
                try {
                    management.getWord().getLinks().add(word);
                    addField(word, linksPane, management.getWord().getLinks(), PersistenceUtils.wordToString(word), null); 

                    //remove the listener
                    mainController.getSelectedWord().removeListener(this);
                    mainController.getSelectedWord().set(null);
                }catch(IllegalArgumentException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.setTitle("Arguments error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        };

        System.out.println(Colors.success("WordEditorController initialized"));
    }

    @FXML
    private void letterKeyEvent(KeyEvent event) {
        ArrayList<Letter> letters = management.getWord().getLetters();
        if (event.getCode() == KeyCode.ENTER) {
            if(addLetterField.getText().length() > 0) {
                Letter letter = management.findLetter(addLetterField.getText());
                if (letter != null) {
                    letters.add(letter);
                    addLetter(letter);
                    
                }
            }
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            if (addLetterField.getText().equals("") && lettersPane.getChildren().size() > 1) {
                if (delete){
                    lettersPane.getChildren().remove(lettersPane.getChildren().size() - 2);
                    letters.remove(letters.size() - 1);
                }else{
                    delete = true;
                }
                
            }
        } else {
            Platform.runLater(() -> {
                Letter letter = management.findLetterUnique(addLetterField.getText());
                delete = false;
                if (letter != null) {
                    letters.add(letter);
                    addLetter(letter);
                }
            });
        }
        Platform.runLater(() -> {
            updateWordPreview();
        });
    }

    @FXML
    private void addRoot() {
        removeAllListeners();
        mainController.getSelectedWord().addListener(chooseRootListener);
    }

    @FXML
    private void addLink() {
        removeAllListeners();
        mainController.getSelectedWord().addListener(chooseLinkListener);
    }

    @FXML
    private void addType() {
        removeAllListeners();
        mainController.getSelectedType().addListener(chooseTypeListener);
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
        try{
            management.addTranslations(translationsArea.getText());
            management.addDefinitions(definitionsArea.getText());
            management.edit();
            removeAllListeners();
            mainController.fetchWords();
            mainController.reloadWordsNav();
            mainController.initHome();
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.setTitle("Arguments error");
            alert.setContentText(e.getMessage());
            alert.show();
            System.out.println(e.getMessage());
        }catch(SQLIntegrityConstraintViolationException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.setTitle("Clone error");
            alert.setContentText("this word already exists");
            alert.show();
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void cancel() {
        removeAllListeners();
        mainController.initHome();
    }

    @FXML
    private void delete() {
        try{
            management.deleteWord();
            removeAllListeners();
            mainController.fetchWords();
            mainController.reloadWordsNav();
            mainController.initHome();
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this word has already been deleted");
            alert.setTitle("In use error");
            alert.show();
        }catch(SQLIntegrityConstraintViolationException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this word is used by other words");
            alert.setTitle("In use error");
            alert.show();
        }
    }

    public void init(Controller mainController, Word word) {
        if (mainController == null || word == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController and object cannot be null"));
        }
        init(mainController);
        management = new WordManagement(word);

        this.headerObject.setText(PersistenceUtils.wordToString(word));

        emotionalitySlider.setValue(word.getEmotional());
        vulgaritySlider.setValue(word.getVulgarity());
        formalitySlider.setValue(word.getFormality());

        String definitionsText = "";
        for (String s : word.getDefinitions()) {
            definitionsText += s + ";\n";
        }
        definitionsArea.setText(definitionsText);

        String translationsText = "";
        for (String s : word.getTranslations()) {
            translationsText += s + ";\n";
        }
        translationsArea.setText(translationsText);

        ArrayList<Letter> letters = management.getWord().getLetters();
        lengthSlider.setValue(letters.size());
        for (Letter l: letters) {
            addLetter(l);
        }
        updateWordPreview();

        for (Type t : word.getTypes()) {
            addField(t, typesPane, management.getWord().getTypes(), t.getLabel(), t.getColor());
        }
        for (Word w : word.getRoots()) {
            addField(w, rootsPane, management.getWord().getRoots(), PersistenceUtils.wordToString(w), null);
        }
        for (Word w : word.getLinks()) {
            addField(w, linksPane, management.getWord().getLinks(), PersistenceUtils.wordToString(w), null);
        }

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

    private void updateWordPreview() {
        ArrayList<Letter> letters = management.getWord().getLetters();
        String word = "";
        String wordAscii = "";

        for (Letter l : letters) {
            word += l.getCharacter();
            wordAscii += l.getCharacterAscii();
        }
        this.wordPreviewLabel.setText(word);
        this.wordAsciiPreviewLabel.setText(wordAscii);
    }

    private void addLetter(Letter l) {
        FXMLHandler<HBox, WordLetterController> letter = new FXMLHandler<>("../fxml/fragments/editor/word_letter.fxml");
        WordLetterController wordLetterController = letter.getController();
        lettersPane.getChildren().add(lettersPane.getChildren().size() - 1,letter.get());
        wordLetterController.init(l);
        wordLetterController.getDeleteButton().setOnAction(event -> {
            lettersPane.getChildren().remove(letter.get());
            management.getWord().getLetters().remove(l);
            updateWordPreview();
        });
        Platform.runLater(() -> {
            addLetterField.setText("");
        });
        delete = true;
    }

    private <U> void addField(U object, FlowPane fieldPane, Set<U> actionOn, String text, Color color) {
        FXMLHandler<HBox, WordFieldController> fieldFragment = new FXMLHandler<>("/fxml/fragments/editor/word_field.fxml");
        WordFieldController fieldControl = fieldFragment.getController();
        fieldPane.getChildren().add(fieldFragment.get());

        fieldControl.getDeleteButton().setOnAction(event -> {
            fieldPane.getChildren().remove(fieldFragment.get());
            actionOn.remove(object);
        });

        fieldControl.init(text);
        if (color == null) {
            fieldControl.setStyle("-fx-font-weight: bold;");
        } else {
            fieldControl.setStyle("-fx-text-fill: " + Colors.colorToHex(color) + "; -fx-font-weight: bold;");
        }
        
    }

    public void removeAllListeners() {
        mainController.getSelectedType().removeListener(chooseTypeListener);
        mainController.getSelectedWord().removeListener(chooseLinkListener);
        mainController.getSelectedWord().removeListener(chooseRootListener);
    }

}