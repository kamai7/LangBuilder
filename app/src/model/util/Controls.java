package model.util;

import controller.EditorController;
import controller.LetterEditorController;
import controller.TypeEditorController;
import controller.WordEditorController;
import controller.fragments.NavItemController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import view.FXMLHandler;

public class Controls {

    public static String wordToString(Word word){

        if (word == null) {
            throw new IllegalArgumentException(Colors.error("word cannot be null"));
        }

        String ret = "";
        for (Letter letter : word.getLetters()) {
            ret += letter.getCharacter();
        }
        return ret;
    }

    public static String wordAsciiToString(Word word){

        if (word == null) {
            throw new IllegalArgumentException(Colors.error("word cannot be null"));
        }

        String ret = "";
        for (Letter letter : word.getLetters()) {
            ret += letter.getCharacterAscii();
        }
        return ret;
    }

    public static FXMLHandler<BorderPane, NavItemController> convertWordToFXMLHandler(Word word) {

        if (word == null) {
            throw new IllegalArgumentException(Colors.error("word cannot be null"));
        }

        FXMLHandler<BorderPane, NavItemController> ret = new FXMLHandler<>("/fxml/fragments/nav/nav_item.fxml");
        NavItemController controller = ret.getController();
        controller.setObjectText(wordToString(word));
        controller.setDescriptionText(wordAsciiToString(word));
        for (Type type : word.getTypes()) {
            double[] colors = type.getColor();
            controller.addType(type.getLabel(), new Color(colors[0], colors[1], colors[2], colors[3]));
            
        }
        return ret;
    }

     public static FXMLHandler<BorderPane, NavItemController> convertLetterToFXMLHandler(Letter letter) {

        if (letter == null) {
            throw new IllegalArgumentException(Colors.error("letter cannot be null"));
        }

        FXMLHandler<BorderPane, NavItemController> letterEditor = new FXMLHandler<>("/fxml/fragments/nav/nav_item.fxml");
        NavItemController controller = letterEditor.getController();
        controller.setObjectText(letter.getCharacter());
        controller.setDescriptionText(letter.getCharacterAscii());
        return letterEditor;
    }

    public static FXMLHandler<BorderPane, NavItemController> convertTypeToFXMLHandler(Type type) {

        if (type == null) {
            throw new IllegalArgumentException(Colors.error("type cannot be null"));
        }

        FXMLHandler<BorderPane, NavItemController> typeEditor = new FXMLHandler<>("/fxml/fragments/nav/nav_item.fxml");
        NavItemController controller = typeEditor.getController();
        controller.setObjectText(type.getLabel());
        controller.setDescriptionText(wordToString(type.getRoot()));
        return typeEditor;
    }

    public static FXMLHandler<BorderPane, EditorController> getLetterEditor(Letter letter){
        FXMLHandler<BorderPane, EditorController> editor = new FXMLHandler<>("/fxml/static/editor.fxml");
        EditorController controller = editor.getController();
        controller.setHeaderObject("φʹ");
        controller.setHeaderName("Letter: ");
        Color color1 = Colors.convertRGBAToColor(new int[]{255, 0, 234, 255});
        Color color2 = Colors.convertRGBAToColor(new int[]{255, 187, 0, 255});
        controller.setHeaderObjectStyle("-fx-text-fill:" + Colors.radialGradient(color1, color2));

        FXMLHandler<VBox, LetterEditorController> letterEditor = new FXMLHandler<>("/fxml/static/editor_letter.fxml");
        controller.setContent(letterEditor.get());

        return editor;
    }

    public static FXMLHandler<BorderPane, EditorController> getTypeEditor(Type type){
        FXMLHandler<BorderPane, EditorController> editor = new FXMLHandler<>("/fxml/static/editor.fxml");
        EditorController controller = editor.getController();
        controller.setHeaderObject("Verb");
        controller.setHeaderName("Word:");
        Color[] colors = Colors.calcGradient(new Color(type.getColor()[0], type.getColor()[1], type.getColor()[2], type.getColor()[3]));
        controller.setHeaderObjectStyle("-fx-text-fill:"  + Colors.linearGradient(colors[0], colors[1]));

        FXMLHandler<VBox, TypeEditorController> typeEditor = new FXMLHandler<>("/fxml/static/editor_type.fxml");
        controller.setContent(typeEditor.get());

        return editor;
    }

    public static FXMLHandler<BorderPane, EditorController> getWordEditor(Word word){
        FXMLHandler<BorderPane, EditorController> editor = new FXMLHandler<>("/fxml/static/editor.fxml");
        EditorController controller = editor.getController();
        controller.setHeaderObject("∂∫∑");
        controller.setHeaderName("Word:");
        Color color2 = Colors.convertRGBAToColor(new int[]{153, 0, 255, 255});
        Color color1 = Colors.convertRGBAToColor(new int[]{0, 174, 255, 255});
        controller.setHeaderObjectStyle("-fx-text-fill:" + Colors.radialGradient(color1, color2));

        FXMLHandler<VBox, WordEditorController> wordEditor = new FXMLHandler<>("/fxml/static/editor_word.fxml");
        controller.setContent(wordEditor.get());

        return editor;
    }

    public static FXMLHandler<BorderPane, EditorController> getLetterEditor(){
        FXMLHandler<BorderPane, EditorController> editor = new FXMLHandler<>("/fxml/static/editor.fxml");
        EditorController controller = editor.getController();
        controller.setHeaderObject("φʹ");
        controller.setHeaderName("Letter: ");
        Color color1 = Colors.convertRGBAToColor(new int[]{255, 0, 234, 255});
        Color color2 = Colors.convertRGBAToColor(new int[]{255, 187, 0, 255});
        controller.setHeaderObjectStyle("-fx-text-fill:" + Colors.radialGradient(color1, color2));

        FXMLHandler<VBox, LetterEditorController> letterEditor = new FXMLHandler<>("/fxml/static/editor_letter.fxml");
        controller.setContent(letterEditor.get());

        return editor;
    }

    public static FXMLHandler<BorderPane, EditorController> getTypeEditor(){
        FXMLHandler<BorderPane, EditorController> editor = new FXMLHandler<>("/fxml/static/editor.fxml");
        EditorController controller = editor.getController();
        controller.setHeaderObject("Verb");
        controller.setHeaderName("Word:");
        Color color = Colors.convertRGBAToColor(new int[]{0, 174, 255, 255});
        Color[] colors = Colors.calcGradient(color);
        controller.setHeaderObjectStyle("-fx-text-fill:"  + Colors.linearGradient(colors[0], colors[1]));

        FXMLHandler<VBox, TypeEditorController> typeEditor = new FXMLHandler<>("/fxml/static/editor_type.fxml");
        controller.setContent(typeEditor.get());

        return editor;
    }

    public static FXMLHandler<BorderPane, EditorController> getWordEditor(){
        FXMLHandler<BorderPane, EditorController> editor = new FXMLHandler<>("/fxml/static/editor.fxml");
        EditorController controller = editor.getController();
        controller.setHeaderObject("∂∫∑");
        controller.setHeaderName("Word:");
        Color color2 = Colors.convertRGBAToColor(new int[]{153, 0, 255, 255});
        Color color1 = Colors.convertRGBAToColor(new int[]{0, 174, 255, 255});
        controller.setHeaderObjectStyle("-fx-text-fill:" + Colors.radialGradient(color1, color2));

        FXMLHandler<VBox, WordEditorController> wordEditor = new FXMLHandler<>("/fxml/static/editor_word.fxml");
        controller.setContent(wordEditor.get());

        return editor;
    }
    
}
