package kamai_i.controller.osu;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kamai_i.utils.AnimationUtils;
import kamai_i.view.osu.OsuCircle;

public class OsuCircleController {

    public static final double OVERFLOW = 0.3;
    private OsuCircle circle;
    private int birthFrame;
    private double duration;
    private Timeline timeline;
    private KeyCode key;
    private OsuController controller;
    private int state;

    public OsuCircleController(OsuController controller, Color color, KeyCode key, double duration) {
        this.circle = new OsuCircle(color, key.getName(), duration + OVERFLOW);
        this.key = key;
        this.duration = duration;
        this.controller = controller;
        birthFrame = controller.getFrameCount();

        KeyFrame[] keyFrames = circle.getKeyFrames();
        keyFrames[5] = new KeyFrame(Duration.seconds(duration + OVERFLOW), _ -> controller.kill());
        timeline = new Timeline(keyFrames);
        timeline.play();
    }

    public OsuCircle get() {
        return circle;
    }

    public KeyCode getKey() {
        return key;
    }

    public double getDuration() {
        return duration;
    }

    public Timeline getTimeline(){
        return timeline;
    }

    public void close() {
        timeline.stop();
    }

    public void getState() {
        double lifeDuration = (controller.getFrameCount() - birthFrame) * controller.TICK_DURATION;
        if (lifeDuration < duration - OVERFLOW) {
            state = 0;
        } else if(lifeDuration < duration - (2.0/3.0) * OVERFLOW) {
            state = 1;
        } else if(lifeDuration < duration - (1.0/3.0) * OVERFLOW) {
            state = 2;
        } else if(lifeDuration < duration + (1.0/3.0) * OVERFLOW) {
            state = 3;
        } else if(lifeDuration < duration + (2.0/3.0) * OVERFLOW) {
            state = 2;
        } else {
            state = 1;
        }
    }

    public void action() {
        getState();
        controller.removeCircleList();

        circle.scaleXProperty().bind(circle.scaleYProperty());
        KeyValue value0 = new KeyValue(circle.scaleYProperty(), 1.2, AnimationUtils.QUAD_EASE_IN_OUT);
        KeyValue value1 = new KeyValue(circle.scaleYProperty(), 1.0, AnimationUtils.QUAD_EASE_IN_OUT);

        KeyFrame key0 = new KeyFrame(Duration.seconds(0.1), value0);
        KeyFrame key1 = new KeyFrame(Duration.seconds(0.1),  _ -> {
            if (state == 0){
                controller.kill();
            } else{
                if (state == 1) {
                    System.out.println("OK");
                } else if (state == 2) {
                    System.out.println("GOOD");
                } else if (state == 3) {
                    System.out.println("PERFECT");
                }
                disapear();
            }
        });
        KeyFrame key2 = new KeyFrame(Duration.seconds(0.2), value1);
        Timeline action = new Timeline(key0, key1, key2);
        timeline.stop();
        action.play();
        
    }

    public void disapear(){
        KeyValue value0 = new KeyValue(circle.translateXProperty(), 70, AnimationUtils.QUAD_EASE_IN);
        KeyValue value1 = new KeyValue(circle.translateYProperty(), -90, AnimationUtils.QUAD_EASE_OUT);
        KeyValue value2 = new KeyValue(circle.translateYProperty(), 1000, AnimationUtils.QUAD_EASE_IN);
        KeyValue value3 = new KeyValue(circle.translateXProperty(), 200, AnimationUtils.QUAD_EASE_OUT);
        KeyValue value4 = new KeyValue(circle.opacityProperty(), 1, AnimationUtils.QUAD_EASE_IN);
        KeyValue value5 = new KeyValue(circle.opacityProperty(), 0, AnimationUtils.QUAD_EASE_IN);

        KeyFrame key0 = new KeyFrame(Duration.seconds(0.3), value0, value1);
        KeyFrame key1 = new KeyFrame(Duration.seconds(0.6), value4);
        KeyFrame key2 = new KeyFrame(Duration.seconds(0.9), value2, value3, value5);
        KeyFrame key3 = new KeyFrame(Duration.seconds(0.95), _ -> controller.removeCircleContainer());

        Timeline disapear = new Timeline(key0, key1, key2, key3);
        disapear.play();
    }
}
