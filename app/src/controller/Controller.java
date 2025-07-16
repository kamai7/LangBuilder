package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Controller {

    @FXML
    private TextField searchWord, searchType, searchLetter;

    @FXML
    private HBox content;
    
    @FXML
    private void initialize() {
        System.out.println("Controller initialized");

        searchWord.textProperty().addListener((observable, oldValue, newValue) -> System.out.println("Search word: " + newValue));
        searchType.textProperty().addListener((observable, oldValue, newValue) -> System.out.println("Search type: " + newValue));
        searchLetter.textProperty().addListener((observable, oldValue, newValue) -> System.out.println("Search letter: " + newValue));
    }

    @FXML
    private void load() {
        System.out.println("Load button clicked");
    }

    @FXML
    private void save() {
        System.out.println("Save button clicked");
    }

    @FXML
    private void export() {
        System.out.println("Export button clicked");
    }

    @FXML
    private void settings() {
        System.out.println("settings wheel clicked");
    }

    @FXML
    private void about() {
        System.out.println("About button clicked");
    }

    public <T extends Node> void setContent(T content) {
        this.content.getChildren().clear();
        this.content.getChildren().add(content);
    }

}
