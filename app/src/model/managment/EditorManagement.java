package model.managment;


import java.util.ArrayList;

import controller.EditorController;
import controller.LetterEditorController;
import controller.WordEditorController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import view.FXMLHandler;

public class EditorManagement {

    ArrayList<Word> words;
    ArrayList<Letter> letters;
    ArrayList<Type> types;
    
    public static FXMLHandler<BorderPane, EditorController> openLetterEditor(){
        FXMLHandler<BorderPane, EditorController> editor = new FXMLHandler<>("/fxml/static/editor.fxml");
        EditorController controller = editor.getController();
        controller.setHeaderObject("φʹ");
        controller.setHeaderName("Letter: ");
        controller.setHeaderObjectStyle("-fx-text-fill: radial-gradient(focus-distance 0%, center 60% 100%, radius 80%, rgb(255, 0, 234), rgb(255, 187, 0))");

        FXMLHandler<VBox, LetterEditorController> letterEditor = new FXMLHandler<>("/fxml/static/editor_letter.fxml");
        controller.setContent(letterEditor.get());

        return editor;
    }

    public static FXMLHandler<BorderPane, EditorController> openTypeEditor(){
        FXMLHandler<BorderPane, EditorController> editor = new FXMLHandler<>("/fxml/static/editor.fxml");
        EditorController controller = editor.getController();
        controller.setHeaderObject("Verb");
        controller.setHeaderName("Word:");
        controller.setHeaderObjectStyle("-fx-text-fill:  linear-gradient(to bottom right, rgba(182, 182, 182, 1), rgba(255, 255, 255, 1));");

        FXMLHandler<VBox, LetterEditorController> typeEditor = new FXMLHandler<>("/fxml/static/editor_type.fxml");
        controller.setContent(typeEditor.get());

        return editor;
    }

    public static FXMLHandler<BorderPane, EditorController> openWordEditor(){
        FXMLHandler<BorderPane, EditorController> editor = new FXMLHandler<>("/fxml/static/editor.fxml");
        EditorController controller = editor.getController();
        controller.setHeaderObject("∂∫∑");
        controller.setHeaderName("Word:");
        controller.setHeaderObjectStyle("-fx-text-fill:  linear-gradient(to bottom right, rgba(182, 182, 182, 1), rgba(255, 255, 255, 1));");

        FXMLHandler<VBox, WordEditorController> typeEditor = new FXMLHandler<>("/fxml/static/editor_word.fxml");
        controller.setContent(typeEditor.get());

        return editor;
    }

    public void addLetter(String letter, String letterAscii) {
        letters.add(new Letter(letter, letterAscii));
    }
}
