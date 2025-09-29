package kamai_i.almostroundcircles.view;

import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class GameView extends Pane {

    public static final double MARGIN = 50;

    public GameView() {
        super();
        maxWidth(Double.MAX_VALUE);
        maxHeight(Double.MAX_VALUE);
        HBox.setHgrow(this, Priority.ALWAYS);
        setEffect(new Glow(0.8));
    }

}
