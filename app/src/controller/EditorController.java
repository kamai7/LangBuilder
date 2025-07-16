package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import model.util.Colors;

public class EditorController {

    @FXML
    private Label headerName;

    @FXML
    private Label headerObject;

    @FXML
    private ScrollPane content;

    @FXML
    private void initialize() {
        System.out.println(Colors.success("EditorController initialized"));
    }

    @FXML
    private void apply() {
        System.out.println(Colors.info("Apply button clicked"));
    }

    @FXML
    private void cancel() {
        System.out.println(Colors.info("Cancel button clicked"));
    }

    @FXML
    private void delete() {
        System.out.println(Colors.info("Delete button clicked"));
    }

    public void setHeaderObject(String letter) {
        this.headerObject.setText(letter);
    }

    public void setHeaderName(String name) {
        this.headerName.setText(name);
    }

    public void setHeaderObjectStyle(String style) {
        this.headerObject.setStyle(style);
    }

    public <U extends Node> void setContent(U content) {
        this.content.setContent(content);
    }
}
