package utils;

import controller.LetterEditorController;
import controller.TypeEditorController;
import controller.WordEditorController;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import view.FXMLHandler;

public class EditorUtils {

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