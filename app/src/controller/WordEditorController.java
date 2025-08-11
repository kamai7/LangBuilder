package controller;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

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
                Type type = newValue.getType();
                try {
                    management.getWord().getTypes().add(newValue.getType());

                    FXMLHandler<HBox, WordFieldController> typeFragment = new FXMLHandler<>("/fxml/fragments/editor/word_field.fxml");
                    WordFieldController typeControl = typeFragment.getController();
                    typesPane.getChildren().add(typeFragment.get());

                    typeControl.getDeleteButton().setOnAction(event -> {
                        typesPane.getChildren().remove(typeFragment.get());
                        management.getWord().getTypes().remove(type);
                    });

                    typeControl.init(type.getLabel());
                    typeControl.setStyle("-fx-text-fill: " + Colors.colorToHex(type.getColor()) + "; -fx-font-weight: bold;");

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
                Word word = newValue.getWord();
                try {
                    management.getWord().getRoots().add(word);

                    FXMLHandler<HBox, WordFieldController> rootFragment = new FXMLHandler<>("/fxml/fragments/editor/word_field.fxml");
                    WordFieldController rootControl = rootFragment.getController();
                    rootsPane.getChildren().add(rootFragment.get());

                    rootControl.getDeleteButton().setOnAction(event -> {
                        rootsPane.getChildren().remove(rootFragment.get());
                        management.getWord().getRoots().remove(word);
                    });

                    rootControl.init(PersistenceUtils.wordToString(word));
                    rootControl.setStyle("-fx-font-weight: bold;");

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
                Word word = newValue.getWord();
                try {
                    management.getWord().getLinks().add(word);

                    FXMLHandler<HBox, WordFieldController> linkFragment = new FXMLHandler<>("/fxml/fragments/editor/word_field.fxml");
                    WordFieldController linkControl = linkFragment.getController();
                    linksPane.getChildren().add(linkFragment.get());

                    linkControl.getDeleteButton().setOnAction(event -> {
                        linksPane.getChildren().remove(linkFragment.get());
                        management.getWord().getLinks().remove(word);
                    });

                    linkControl.init(PersistenceUtils.wordToString(word));
                    linkControl.setStyle("-fx-font-weight: bold;");

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
        ArrayList<Letter> letters = management.getLetters();
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
        mainController.getSelectedWord().removeListener(chooseRootListener);
        mainController.getSelectedWord().addListener(chooseRootListener);
    }

    @FXML
    private void addLink() {
        mainController.getSelectedWord().removeListener(chooseLinkListener);
        mainController.getSelectedWord().addListener(chooseLinkListener);
    }

    @FXML
    private void addType() {
        mainController.getSelectedType().removeListener(chooseTypeListener);
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
        management.addTranslations(translationsArea.getText());
        management.addDefinitions(definitionsArea.getText());
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

    private void updateWordPreview() {
        ArrayList<Letter> letters = management.getLetters();
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
            management.getLetters().remove(l);
            updateWordPreview();
        });
        Platform.runLater(() -> {
            addLetterField.setText("");
        });
        delete = true;
    }

}