package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import utils.Colors;
import view.FXMLHandler;

public class HomeController {

    private Controller mainController;

    @FXML
    private void initialize() {
        System.out.println(Colors.success("HomeController initialized"));
    }

    public void init(Controller mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void createLetter() {
        FXMLHandler<BorderPane, LetterEditorController> editor = new FXMLHandler<>("/fxml/static/editor_letter.fxml");
        mainController.setContent(editor.get());
        editor.getController().init(mainController, null);
    }

    @FXML
    private void createType() {
        FXMLHandler<BorderPane, TypeEditorController> editor = new FXMLHandler<>("/fxml/static/editor_type.fxml");
        mainController.setContent(editor.get());
        editor.getController().init(mainController, null);
    }

    @FXML
    private void createWord() {
        FXMLHandler<BorderPane, WordEditorController> editor = new FXMLHandler<>( "/fxml/static/editor_word.fxml");
        mainController.setContent(editor.get());
        editor.getController().init(mainController, null);
    }
}
