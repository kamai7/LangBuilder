package kamai_i.controller;

import java.util.ArrayList;

import kamai_i.controller.fragments.NavTypeController;
import kamai_i.controller.fragments.NavWordController;
import kamai_i.controller.listener.SelectionListener;
import kamai_i.exceptions.InvalidUserArgument;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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
import kamai_i.model.managment.WordManagement;
import kamai_i.model.persistance.Letter;
import kamai_i.model.persistance.Type;
import kamai_i.model.persistance.Word;
import kamai_i.utils.AnimationUtils;
import kamai_i.utils.Colors;
import kamai_i.utils.FragmentUtils;
import kamai_i.utils.PersistenceUtils;
import kamai_i.view.fragments.WordField;
import kamai_i.view.fragments.WordLetter;

public class WordEditorController extends AbstractEditor<Word> {

    private boolean delete = true;

    private WordManagement management;

    private SelectionListener<Type, NavTypeController> chooseTypeListener;
    private SelectionListener<Word, NavWordController> chooseRootListener;
    private SelectionListener<Word, NavWordController> chooseLinkListener;

    @FXML
    private HBox chooseTypeButtonContainer;

    @FXML
    private FlowPane lettersPane,
                     typesPane,
                     rootsPane,
                     linksPane;

    @FXML
    private Label wordPreviewLabel,
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

        emotionalitySlider.valueProperty().addListener((ChangeListener<Number>) (_, _, newValue) -> management.getWord().setEmotional(newValue.doubleValue()));
        vulgaritySlider.valueProperty().addListener((ChangeListener<Number>) (_, _, newValue) -> management.getWord().setVulgarity(newValue.doubleValue()));
        formalitySlider.valueProperty().addListener((ChangeListener<Number>) (_, _, newValue) -> management.getWord().setFormality(newValue.doubleValue()));

        lengthContainer.setDisable(!lengthCheckBox.isSelected());
        emotionalityContainer.setDisable(!emotionalityCheckBox.isSelected());
        vulgarityContainer.setDisable(!vulgarityCheckBox.isSelected());
        formalityContainer.setDisable(!formalityCheckBox.isSelected());
        rootsContainer.setDisable(!rootsCheckBox.isSelected());
        linksContainer.setDisable(!linksCheckBox.isSelected());

        lengthCheckBox.selectedProperty().addListener(_ -> lengthContainer.setDisable(!lengthCheckBox.isSelected()));
        emotionalityCheckBox.selectedProperty().addListener(_ -> emotionalityContainer.setDisable(!emotionalityCheckBox.isSelected()));
        vulgarityCheckBox.selectedProperty().addListener(_ -> vulgarityContainer.setDisable(!vulgarityCheckBox.isSelected()));
        formalityCheckBox.selectedProperty().addListener(_ -> formalityContainer.setDisable(!formalityCheckBox.isSelected()));
        rootsCheckBox.selectedProperty().addListener(_ -> rootsContainer.setDisable(!rootsCheckBox.isSelected()));
        linksCheckBox.selectedProperty().addListener(_ -> linksContainer.setDisable(!linksCheckBox.isSelected()));
        usableCheckBox.selectedProperty().addListener(_ -> management.getWord().setUsable(usableCheckBox.isSelected()));

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
        mainController.selectWordTab();
        mainController.getSelectedWord().addListener(chooseRootListener);
    }

    @FXML
    private void addLink() {
        removeAllListeners();
        mainController.selectWordTab();
        mainController.getSelectedWord().addListener(chooseLinkListener);
    }

    @FXML
    private void addType() {
        removeAllListeners();
        mainController.selectTypeTab();
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
        }catch(InvalidUserArgument e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Arguments error");
            alert.setContentText(e.getMessage());
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
            management.delete();
            removeAllListeners();
            mainController.fetchWords();
            mainController.reloadWordsNav();
            mainController.initHome();
        }catch(InvalidUserArgument e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "this word has already been deleted");
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
            addType(t);
        }
        for (Word w : word.getRoots()) {
            addRoot(w);
        }
        for (Word w : word.getLinks()) {
            addLink(w);
        }

    }

    public void init(Controller mainController) {
        if (mainController == null) {
            throw new IllegalArgumentException(Colors.error("LetterItemController.init" , "mainController cannot be null"));
        }
        management = new WordManagement();
        this.mainController = mainController;

        chooseTypeListener = new SelectionListener<>(mainController.getSelectedType()) {
            public void perform() throws InvalidUserArgument {
                management.getWord().getTypes().add(newObject);
                addType(newObject);
            }
        };

        chooseRootListener = new SelectionListener<>(mainController.getSelectedWord()) {
            public void perform() throws InvalidUserArgument{
                management.addRoot(newObject);
                addRoot(newObject);
            }
        };

        chooseLinkListener = new SelectionListener<>(mainController.getSelectedWord()) {
            public void perform() throws InvalidUserArgument{
                management.addLink(newObject);
                addLink(newObject);
            }
        };

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
        WordLetter wordLetter = new WordLetter(l.getCharacter());
        lettersPane.getChildren().add(lettersPane.getChildren().size() - 1, wordLetter);
        wordLetter.getDeleteButton().setOnAction(_ -> {
            management.getWord().getLetters().remove(l);
            AnimationUtils.smooth(wordLetter.opacityProperty(), 0.0);
            lettersPane.getChildren().remove(wordLetter);
            updateWordPreview();
        });
        Platform.runLater(() -> {
            addLetterField.setText("");
        });
        delete = true;
    }

    private void addLink(Word object) {
        WordField field = new WordField(PersistenceUtils.wordToString(object));
        linksPane.getChildren().add(field);

        fieldApear(field);

        field.getDeleteButton().setOnAction(_ -> {
            management.getWord().getLinks().remove(object);
            AnimationUtils.smooth(field.opacityProperty(), 0.0);
            linksPane.getChildren().remove(field);
        });
    }

    private void addRoot(Word object) {
        WordField field = new WordField(PersistenceUtils.wordToString(object));
        rootsPane.getChildren().add(field);

        fieldApear(field);

        field.getDeleteButton().setOnAction(_ -> {
            management.getWord().getRoots().remove(object);
            AnimationUtils.smooth(field.opacityProperty(), 0.0);
            rootsPane.getChildren().remove(field);
        });
    }

    private void addType(Type object) {
        WordField field = new WordField(object.getLabel());
        typesPane.getChildren().add(field);

        fieldApear(field);

        field.getDeleteButton().setOnAction(_ -> {
            management.getWord().getTypes().remove(object);
            AnimationUtils.smooth(field.opacityProperty(), 0.0);
            typesPane.getChildren().remove(field);
        });
    }

    private void fieldApear(WordField field) {
        field.setTranslateX(30.0);
        AnimationUtils.smoothApear(field, field.translateXProperty(), 0.0);
    }

    public void removeAllListeners() {
        mainController.getSelectedType().removeListener(chooseTypeListener);
        mainController.getSelectedWord().removeListener(chooseLinkListener);
        mainController.getSelectedWord().removeListener(chooseRootListener);
    }

}