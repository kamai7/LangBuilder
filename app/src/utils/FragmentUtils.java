package utils;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

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
    
}
