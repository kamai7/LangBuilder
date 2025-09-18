package controller.osu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import utils.Colors;
import utils.ListsUtils;
import view.View;
import view.osu.Osu;
import view.osu.OsuCircle;

public class OsuController {

    public final double TICK_DURATION = 1.0/60.0;
    public final double COLOR_CYCLE_DURATION = 6;
    private int nbTick;

    private Osu osu;

    private Color colorCycle;
    private int colorPhase;

    private Timeline game;

    private List<OsuCircleController> circles;

    public static final Set<KeyCode> possibleKeys = Set.of(KeyCode.Q, KeyCode.S, KeyCode.L, KeyCode.M);

    public OsuController(View mainView) {
        if (mainView == null) {
            throw new IllegalArgumentException(Colors.error("OsuController:", "mainView cannot be null"));
        }

        circles = new ArrayList<>();
        this.osu = new Osu();

        mainView.getScene().setOnKeyPressed(e -> {
            if (possibleKeys.contains(e.getCode())) {
                OsuCircleController circle = circles.get(0);
                if (e.getCode().equals(circle.getKey())) {
                    circle.action();
                }else{
                    kill();
                }
            }
        });
        KeyFrame frame = new KeyFrame(Duration.seconds(TICK_DURATION), _ -> tickUpdate());
        game = new Timeline(frame);
        game.setCycleCount(Timeline.INDEFINITE);
        game.play();
    }

    public Osu get() {
        return osu;
    }

    public int getFrameCount(){
        return nbTick;
    }

    private void changeColor(double saturation) {
        double shift = (1 / 255.0) * saturation;

        if (colorPhase == 0){
            colorCycle = new Color(1, 1 - saturation, 1 - saturation, 1);
        }

        double newR = colorCycle.getRed();
        double newG = colorCycle.getGreen();
        double newB = colorCycle.getBlue();

        if (colorPhase < 255) {
            newR -= shift;
            newG += shift;
        } else if (colorPhase < 509) {
            newG -= shift;
            newB += shift;
        } else if (colorPhase < 763) {
            newB -= shift;
            newR += shift;
        } else {
            colorPhase = 0;
        }
        colorPhase += 1;
        colorCycle = new Color(newR, newG, newB, 1);
    }

    public void randomCircle() {
        double margin = Osu.MARGIN;
        int x = (int) ((Math.random() * (osu.getWidth() - 2 * margin)) + margin);
        int y = (int) ((Math.random() * (osu.getHeight() - 2 * margin)) + margin);

        KeyCode kCode = ListsUtils.draw(possibleKeys);

        OsuCircleController circle = new OsuCircleController(this, colorCycle, kCode, 1.3);
        circles.add(circle);
        osu.getChildren().add(osu.getChildren().size(), circle.get());
        circle.get().setLayoutX(x - ((OsuCircle.CIRCLE_RADIUS) + 3));
        circle.get().setLayoutY(y - ((OsuCircle.CIRCLE_RADIUS) + 3));
    }

    public void removeCircleList(){
        circles.remove(0);
    }

    public void removeCircleContainer(){
        osu.getChildren().remove(0);
    }

    private void tickUpdate() {
        nbTick++;

        changeColor(0.8);

        if(nbTick%40 == 0){
            randomCircle();
        }
    }

    public void kill(){
        System.out.println("echec");
        game.stop();
        for(OsuCircleController circle: circles){
            circle.getTimeline().stop();
        }
    }
    
}
