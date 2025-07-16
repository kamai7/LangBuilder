package controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import model.managment.EditorManagement;
import model.util.Colors;

public class HomeController {

    private ObjectProperty<BorderPane> view;

    @FXML
    private void initialize() {
        System.out.println(Colors.success("HomeController initialized"));
        view = new SimpleObjectProperty<>(null);
    }

    @FXML
    private void createWord() {
        System.out.println(Colors.info("Create word button clicked"));
    }

    @FXML
    private void createLetter() {
        view.set(EditorManagement.openLetterEditor().get());
    }

    @FXML
    private void createType() {
        System.out.println(Colors.info("Create type button clicked"));
    }

    public ObjectProperty<? extends Node> getView() {
        return view;
    }
}
