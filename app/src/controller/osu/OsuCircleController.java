package controller.osu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.osu.OsuCircle;

public class OsuCircleController {

    public static final double OVERFLOW = 0.3;
    private OsuCircle circle;
    private int birthFrame;
    private double duration;
    private Timeline timeline;
    private KeyCode key;
    private OsuController controller;

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

    public int getState() {
        int state;
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
        return state;
    }
}
