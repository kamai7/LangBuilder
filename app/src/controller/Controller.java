package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.util.Colors;

public class Controller {

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
    private void settings() {
        System.out.println(Colors.info("Settings button clicked"));
    }

    @FXML
    private void about() {
        System.out.println(Colors.info("About button clicked"));
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

}
