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
        view.set(EditorManagement.openWordEditor().get());
    }

    @FXML
    private void createLetter() {
        view.set(EditorManagement.openLetterEditor().get());
    }

    @FXML
    private void createType() {
        view.set(EditorManagement.openTypeEditor().get());
    }

    public ObjectProperty<? extends Node> getView() {
        return view;
    }
}
