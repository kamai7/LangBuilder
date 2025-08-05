package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import utils.Colors;
import utils.EditorUtils;
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
        FXMLHandler<BorderPane, LetterEditorController> editor = EditorUtils.getLetterEditor();
        mainController.setContent(editor.get());
        editor.getController().init(mainController);
    }

    @FXML
    private void createType() {
        FXMLHandler<BorderPane, TypeEditorController> editor = EditorUtils.getTypeEditor();
        mainController.setContent(editor.get());
        editor.getController().init(mainController);
    }

    @FXML
    private void createWord() {
        FXMLHandler<BorderPane, WordEditorController> editor = EditorUtils.getWordEditor();
        mainController.setContent(editor.get());
        editor.getController().init(mainController);
    }
}
