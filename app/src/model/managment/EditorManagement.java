package model.managment;

import controller.EditorController;
import controller.LetterEditorController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.FXMLHandler;

public class EditorManagement {
    
    public static FXMLHandler<BorderPane, EditorController> openLetterEditor(){
        FXMLHandler<BorderPane, EditorController> editor = new FXMLHandler<>("/fxml/static/editor.fxml");
        EditorController controller = editor.getController();
        controller.setHeaderObject("φʹ");
        controller.setHeaderName("Letter:");
        controller.setHeaderObjectStyle("-fx-text-fill: radial-gradient(focus-distance 0%, center 60% 100%, radius 80%, rgb(255, 0, 234), rgb(255, 187, 0))");

        FXMLHandler<VBox, LetterEditorController> letterEditor = new FXMLHandler<>("/fxml/static/letter_editor.fxml");
        LetterEditorController letterController = letterEditor.getController();
        letterController.initValues("φʹ", "phi'");
        controller.setContent(letterEditor.get());

        return editor;
    }

}
