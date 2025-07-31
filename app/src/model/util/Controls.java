package model.util;

import controller.LetterEditorController;
import controller.TypeEditorController;
import controller.WordEditorController;
import controller.fragments.NavItemController;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import view.FXMLHandler;

public class Controls {

    public static String wordToString(Word word){

        if (word == null) {
            throw new IllegalArgumentException(Colors.error("Controls.wordToString:","word cannot be null"));
        }

        String ret = "";
        for (Letter letter : word.getLetters()) {
            ret += letter.getCharacter();
        }
        return ret;
    }

    public static String wordAsciiToString(Word word){

        if (word == null) {
            throw new IllegalArgumentException(Colors.error("Controls.wordAsciiToString:","word cannot be null"));
        }

        String ret = "";
        for (Letter letter : word.getLetters()) {
            ret += letter.getCharacterAscii();
        }
        return ret;
    }

    public static FXMLHandler<BorderPane, NavItemController> convertWordToFXMLHandler(Word word) {

        if (word == null) {
            throw new IllegalArgumentException(Colors.error("Controls.convertWordToFXMLHandler:","word cannot be null"));
        }

        FXMLHandler<BorderPane, NavItemController> ret = new FXMLHandler<>("/fxml/fragments/nav/nav_item.fxml");
        NavItemController controller = ret.getController();
        controller.setObjectText(wordToString(word));
        controller.setDescriptionText(wordAsciiToString(word));
        for (Type type : word.getTypes()) {
            controller.addType(type.getLabel(), type.getColor());
            
        }
        return ret;
    }

     public static FXMLHandler<BorderPane, NavItemController> convertLetterToFXMLHandler(Letter letter) {

        if (letter == null) {
            throw new IllegalArgumentException(Colors.error("Controls.convertLetterToFXMLHandler:","letter cannot be null"));
        }

        FXMLHandler<BorderPane, NavItemController> letterEditor = new FXMLHandler<>("/fxml/fragments/nav/nav_item.fxml");
        NavItemController controller = letterEditor.getController();
        controller.setObjectText(letter.getCharacter());
        controller.setDescriptionText(letter.getCharacterAscii());
        return letterEditor;
    }

    public static FXMLHandler<BorderPane, NavItemController> convertTypeToFXMLHandler(Type type) {

        if (type == null) {
            throw new IllegalArgumentException(Colors.error("Controls.convertTypeToFXMLHandler:","type cannot be null"));
        }

        FXMLHandler<BorderPane, NavItemController> typeEditor = new FXMLHandler<>("/fxml/fragments/nav/nav_item.fxml");
        NavItemController controller = typeEditor.getController();
        controller.setObjectText(type.getLabel());
        controller.setDescriptionText(wordToString(type.getRoot()));
        return typeEditor;
    }

    public static FXMLHandler<BorderPane, LetterEditorController> getLetterEditor(){

        FXMLHandler<BorderPane, LetterEditorController> editor = new FXMLHandler<>("/fxml/static/editor_letter.fxml");
        LetterEditorController controller = editor.getController();
        controller.setHeaderObject("∱'");
        Color color1 = Colors.convertRGBAToColor(new int[]{255, 0, 234, 255});
        Color color2 = Colors.convertRGBAToColor(new int[]{255, 187, 0, 255});
        controller.setHeaderObjectStyle("-fx-text-fill:" + Colors.radialGradient(color1, color2));

        return editor;
    }

    public static FXMLHandler<BorderPane, TypeEditorController> getTypeEditor(){
        FXMLHandler<BorderPane, TypeEditorController> editor = new FXMLHandler<>("/fxml/static/editor_type.fxml");
        TypeEditorController controller = editor.getController();
        controller.setHeaderObject("Verb");
        Color color = Colors.convertRGBAToColor(new int[]{0, 174, 255, 255});
        Color[] colors = Colors.calcGradient(color);
        controller.setHeaderObjectStyle("-fx-text-fill:"  + Colors.linearGradient(colors[0], colors[1]));

        return editor;
    }

    public static FXMLHandler<BorderPane, WordEditorController> getWordEditor(){

        FXMLHandler<BorderPane, WordEditorController> editor = new FXMLHandler<>("/fxml/static/editor_word.fxml");
        WordEditorController controller = editor.getController();
        controller.setHeaderObject("∱'⇔∩");
        Color color2 = Colors.convertRGBAToColor(new int[]{153, 0, 255, 255});
        Color color1 = Colors.convertRGBAToColor(new int[]{0, 174, 255, 255});
        controller.setHeaderObjectStyle("-fx-text-fill:" + Colors.radialGradient(color1, color2));

        return editor;
    }
    
}
