package model.util;

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
    
}
