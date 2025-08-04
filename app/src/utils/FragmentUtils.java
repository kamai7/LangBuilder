package utils;

import controller.fragments.NavItemController;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import view.FXMLHandler;

public class FragmentUtils {

    public static void initSlider(Slider slider, TextField field, double precision) {

        double slice = 1 / precision;

        slider.valueProperty().addListener((ChangeListener<Number>) (ovn, oldValue, newValue) -> {
            double rounded = Math.round(newValue.doubleValue() * slice) / slice;
            field.setText(rounded + "");
        });

        field.textProperty().addListener(event -> {
            try {
                double value = Double.parseDouble(field.getText());
                slider.setValue(value);
            } catch (NumberFormatException e) {}
        });

    }

    public static FXMLHandler<BorderPane, NavItemController> convertNavFragment(Word word) {

        if (word == null) {
            throw new IllegalArgumentException(Colors.error("Controls.convertWordToFXMLHandler:","word cannot be null"));
        }

        FXMLHandler<BorderPane, NavItemController> ret = new FXMLHandler<>("/fxml/fragments/nav/nav_item.fxml");
        NavItemController controller = ret.getController();
        controller.setObjectText(PersistenceUtils.wordToString(word));
        controller.setDescriptionText(PersistenceUtils.wordAsciiToString(word));
        for (model.persistance.Type type : word.getTypes()) {
            controller.addType(type.getLabel(), type.getColor());
            
        }
        return ret;
    }

     public static FXMLHandler<BorderPane, NavItemController> convertNavFragment(Letter letter) {

        if (letter == null) {
            throw new IllegalArgumentException(Colors.error("Controls.convertLetterToFXMLHandler:","letter cannot be null"));
        }

        FXMLHandler<BorderPane, NavItemController> letterEditor = new FXMLHandler<>("/fxml/fragments/nav/nav_item.fxml");
        NavItemController controller = letterEditor.getController();
        controller.setObjectText(letter.getCharacter());
        controller.setDescriptionText(letter.getCharacterAscii());
        return letterEditor;
    }

    public static FXMLHandler<BorderPane, NavItemController> convertNavFragment(Type type) {

        if (type == null) {
            throw new IllegalArgumentException(Colors.error("Controls.convertTypeToFXMLHandler:","type cannot be null"));
        }

        FXMLHandler<BorderPane, NavItemController> typeEditor = new FXMLHandler<>("/fxml/fragments/nav/nav_item.fxml");
        NavItemController controller = typeEditor.getController();
        controller.setObjectText(type.getLabel());
        controller.setDescriptionText(PersistenceUtils.wordToString(type.getRoot()));
        return typeEditor;
    }
    
}
