package utils;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationUtils {

    public static final Interpolator QUAD_EASE_IN = new Interpolator() {
        @Override
        protected double curve(double t) {
            return t * t;
        }
    };
    
    public static final Interpolator QUAD_EASE_OUT = new Interpolator() {
        @Override
        protected double curve(double t) {
            return t * (2 - t);
        }
    };

    public static final Interpolator QUAD_EASE_IN_OUT = new Interpolator() {
        @Override
        protected double curve(double t) {
            if (t < 0.5) {
                return 2 * t * t;
            } else {
                return -1 + (4 - 2 * t) * t;
            }
        }
    };

    public static <T> void smooth(WritableValue<T> property, T value) {
        smooth(property, value, 0.3, QUAD_EASE_OUT);
    }

    public static <T> void smooth(WritableValue<T> property, T value, Double duration) {
        smooth(property, value, duration, QUAD_EASE_OUT);
    }

    public static <T> void smooth(WritableValue<T> property, T value, Double duration, Interpolator interpolator) {
        KeyValue keyValue = new KeyValue(property, value, interpolator);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(duration), keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    } 

    public static <T> void smoothApear(Node node, WritableValue<T> property, T value) {
        smoothApear(node, property, value, 0.3);
    }

    public static <T> void smoothApear(Node node,WritableValue<T> property, T value, Double duration) {
        KeyValue keyValue = new KeyValue(property, value, QUAD_EASE_OUT);
        KeyValue opacity = new KeyValue(node.opacityProperty(), 1, Interpolator.EASE_OUT);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(duration), keyValue, opacity);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }
}
