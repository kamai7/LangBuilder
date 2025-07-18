package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
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
    private TextField searchWord, searchType, searchLetter;

    @FXML
    private HBox content;
    
    @FXML
    private void initialize() {
        System.out.println(Colors.success("Controller initialized"));

        searchWord.textProperty().addListener((observable, oldValue, newValue) -> updateSearchWord());
        searchType.textProperty().addListener((observable, oldValue, newValue) -> updateSearchType());
        searchLetter.textProperty().addListener((observable, oldValue, newValue) -> updateSearchLetter());

        initHome();
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

    public <T extends Node> void setContent(T content) {
        this.content.getChildren().clear();
        this.content.getChildren().add(content);
    }

    private void updateSearchWord(){
        System.out.println(Colors.info("Search word: ", searchWord.getText()));
    }

    private void updateSearchType(){
        System.out.println(Colors.info("Search type: ", searchType.getText()));
    }

    private void updateSearchLetter(){
        System.out.println(Colors.info("Search letter: ", searchLetter.getText()));
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
