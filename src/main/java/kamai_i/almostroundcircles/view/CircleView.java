package kamai_i.almostroundcircles.view;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import kamai_i.utils.AnimationUtils;
import kamai_i.utils.Colors;

public class CircleView extends StackPane{

    public static final int CIRCLE_RADIUS = 40;
    public static final double OUTLINE_ANIMATION_DURATION = 0.2;
    public static final double CIRCLE_ANIMATION_SHIFT = 0.05;

    private Circle outline;
    private Circle circle;
    private Label label;

    private final KeyFrame key0;
    private final KeyFrame key1;
    private final KeyFrame key2;
    private final KeyFrame key3;
    private final KeyFrame key4;

    public CircleView(Color color, String key, double animationDuration) {
        super();
        if (color == null) {
            throw new IllegalArgumentException(Colors.error("CircleView.CircleView","the color cant be null"));
        }
        if (key == null) {
            throw new IllegalArgumentException(Colors.error("CircleView.CircleView","the key cant be null"));
        }
        double maxSize = (1.0 * (animationDuration + (kamai_i.almostroundcircles.model.Circle.TIME_RANGE / 2.0))) / animationDuration;
        setPrefHeight((CIRCLE_RADIUS * maxSize) * 2);
        setPrefWidth((CIRCLE_RADIUS * maxSize) * 2);

        initOutline();
        initCircle(color);
        initLabel(key);

        getChildren().add(circle);
        getChildren().add(outline);
        getChildren().add(label);

        KeyValue outlineRadiusEnd = new KeyValue(outline.radiusProperty(), CIRCLE_RADIUS, AnimationUtils.QUAD_EASE_OUT);
        KeyValue outlineOpacityEnd = new KeyValue(outline.opacityProperty(), 1, AnimationUtils.QUAD_EASE_OUT);

        KeyValue labelOpacityEnd = new KeyValue(label.opacityProperty(), 1, AnimationUtils.QUAD_EASE_OUT);

        KeyValue circleRadiusStart = new KeyValue(circle.radiusProperty(), CIRCLE_RADIUS/6);
        KeyValue circleOpacityStart = new KeyValue(circle.opacityProperty(), 0);
        KeyValue circleOpacityEnd = new KeyValue(circle.opacityProperty(), 1, AnimationUtils.QUAD_EASE_OUT);
        KeyValue circleRadiusEnd = new KeyValue(circle.radiusProperty(), CIRCLE_RADIUS * maxSize);
        KeyValue circleOutlineColor = new KeyValue(outline.strokeProperty(), new Color(1,1,1,0.2), AnimationUtils.QUAD_EASE_IN);

        key0 = new KeyFrame(Duration.seconds(CIRCLE_ANIMATION_SHIFT), circleRadiusStart, circleOpacityStart);
        key1 = new KeyFrame(Duration.seconds(OUTLINE_ANIMATION_DURATION), outlineRadiusEnd, outlineOpacityEnd);
        key2 = new KeyFrame(Duration.seconds(CIRCLE_ANIMATION_SHIFT + 0.3), circleOpacityEnd);
        key3 = new KeyFrame(Duration.seconds(CIRCLE_ANIMATION_SHIFT + animationDuration/2.5), labelOpacityEnd);
        key4 = new KeyFrame(Duration.seconds(animationDuration + kamai_i.almostroundcircles.model.Circle.TIME_RANGE / 2), circleRadiusEnd, circleOutlineColor);
    }

    private void initOutline() {
        outline = new Circle(CIRCLE_RADIUS/2, CIRCLE_RADIUS/2, CIRCLE_RADIUS/3);
        outline.setStroke(Color.BLACK);
        outline.setOpacity(0.2);
        outline.setStrokeWidth(3);
        outline.setFill(Color.TRANSPARENT);
    }

    private void initCircle(Color color){
        circle = new Circle(CIRCLE_RADIUS/2, CIRCLE_RADIUS/2, CIRCLE_RADIUS/3);
        Color[] colors = Colors.calcGradient(color,0.3);
        ArrayList<Stop> stops = new ArrayList<>();
        stops.add(new Stop(1, colors[0]));
        stops.add(new Stop(0, colors[1]));
        RadialGradient gradient = new RadialGradient(0, 0.3, 0, 1, 0.9, true, CycleMethod.NO_CYCLE, stops);
        circle.setFill(gradient);
        circle.setOpacity(0);
        circle.setStroke(color.darker());
        circle.setStrokeWidth(6);
    }

    private void initLabel(String key) {
        this.label = new Label("[" + key + "]");
        label.setFont(new Font(label.getFont().getFamily(), 26));
        label.setTextFill(Color.BLACK);
        label.scaleYProperty().bind(label.opacityProperty());
        label.scaleXProperty().bind(label.opacityProperty());
        label.setOpacity(0);
    }

    public KeyFrame[] keyFrames() {
        KeyFrame[] ret = new KeyFrame[5];
        ret[0] = key0;
        ret[1] = key1;
        ret[2] = key2;
        ret[3] = key3;
        ret[4] = key4;
        return ret;
    }

    public Circle outline(){
        return outline;
    }
    
}
