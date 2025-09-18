package kamai_i.view.osu;

import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class Osu extends Pane {

    public static final double MARGIN = 50;

    public Osu() {
        super();
        maxWidth(Double.MAX_VALUE);
        maxHeight(Double.MAX_VALUE);
        HBox.setHgrow(this, Priority.ALWAYS);
        setEffect(new Glow(0.8));
    }

}
