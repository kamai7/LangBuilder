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

    private final double COLOR_CYCLE_DURATION = 6;

    private Osu osu;

    private Color colorCycle;
    private int colorPhase;

    private Timeline colorLoop;
    private Timeline circlesGeneration;

    private List<OsuCircleController> circles;

    public static final Set<KeyCode> possibleKeys = Set.of(KeyCode.Q, KeyCode.D, KeyCode.S, KeyCode.F);

    public OsuController(View mainView) {
        if (mainView == null) {
            throw new IllegalArgumentException(Colors.error("OsuController:", "mainView cannot be null"));
        }

        circles = new ArrayList<>();

        this.osu = new Osu();

        mainView.getScene().setOnKeyPressed(e -> {
            System.out.println(circles.get(0).getLifeTime() + ", duration: " + circles.get(0).getDuration() + "must be between " + circles.get(0).getDuration() * OsuCircle.CIRCLE_MAX_SIZE_FACTOR + " and " + (circles.get(0).getDuration() - ((circles.get(0).getDuration() * OsuCircle.CIRCLE_MAX_SIZE_FACTOR) - circles.get(0).getDuration())));
            if (e.getCode() == circles.get(0).getKey()) {
                double min = circles.get(0).getDuration() - ((circles.get(0).getDuration() * OsuCircle.CIRCLE_MAX_SIZE_FACTOR) - circles.get(0).getDuration());
                if (circles.get(0).getLifeTime() > min) {
                    circles.get(0).close();
                    circles.remove(0);
                    osu.getChildren().remove(0);
                }else {
                    System.out.println(Colors.error("perdu"));
                    circlesGeneration.stop();
                }
            }
        });

        KeyFrame color = new KeyFrame(Duration.seconds(COLOR_CYCLE_DURATION / 765), e -> changeColor(0.8));
        colorLoop = new Timeline(color);
        colorLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame circles = new KeyFrame(Duration.seconds(1), e -> randomCircle());
        circlesGeneration = new Timeline(circles);
        circlesGeneration.setCycleCount(Timeline.INDEFINITE);

        circlesGeneration.play();
        colorLoop.play();
    }

    public Osu get() {
        return osu;
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

        OsuCircleController circle = new OsuCircleController(colorCycle, kCode, 1.2);
        circle.isAlive().addListener(e -> {
            System.out.println(Colors.error("perdu"));
            circlesGeneration.stop();
        });
        circles.add(circle);
        osu.getChildren().add(circle.get());
        circle.get().setLayoutX(x - ((OsuCircle.CIRCLE_RADIUS * OsuCircle.CIRCLE_MAX_SIZE_FACTOR) + 3));
        circle.get().setLayoutY(y - ((OsuCircle.CIRCLE_RADIUS * OsuCircle.CIRCLE_MAX_SIZE_FACTOR) + 3));
    }
    
}
