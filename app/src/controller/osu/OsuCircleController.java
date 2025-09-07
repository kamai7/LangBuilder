package controller.osu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.osu.OsuCircle;

public class OsuCircleController {

    private OsuCircle circle;

    private double life;
    private double duration;
    private BooleanProperty alive;

    private Timeline timeline;
    private Timeline lifeTimeline;

    private KeyCode key;

    public OsuCircleController(Color color, KeyCode key, double duration) {
        this.circle = new OsuCircle(color, key.getName(), duration * OsuCircle.CIRCLE_MAX_SIZE_FACTOR);
        this.key = key;
        this.duration = duration;
        this.life = 0;
        this.alive = new SimpleBooleanProperty(true);

        timeline = new Timeline(circle.getKeyFrames());
        lifeTimeline = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> live()));
        lifeTimeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        lifeTimeline.play();
    }

    public OsuCircle get() {
        return circle;
    }

    public KeyCode getKey() {
        return key;
    }

    public BooleanProperty isAlive() {
        return alive;
    }

    public double getLifeTime() {
        return life;
    }

    public double getDuration() {
        return duration;
    }

    private void live(){
        this.life += 0.05;
        if (this.life > this.duration * OsuCircle.CIRCLE_MAX_SIZE_FACTOR) {
            this.alive.set(false);
        }
    }

    public void close() {
        timeline.stop();
        lifeTimeline.stop();
    }
}
