package kamai_i.controller.osu;

import javafx.event.EventHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kamai_i.utils.Colors;
import kamai_i.utils.ListsUtils;
import kamai_i.view.View;
import kamai_i.view.osu.Osu;
import kamai_i.view.osu.OsuCircle;

public class OsuController {

    public final double TICK_DURATION = 1.0/60.0;
    public final double COLOR_CYCLE_DURATION = 6;
    private int nbTick;

    private Osu osu;
    private View mainView;
    private Color colorCycle;
    private int colorPhase;
    private EventHandler<KeyEvent> gameKeys;

    private Timeline game;
    private List<OsuCircleController> circles;
    public static final Set<KeyCode> possibleKeys = Set.of(KeyCode.Q, KeyCode.S, KeyCode.L, KeyCode.M);

    public OsuController(View mainView) {
        if (mainView == null) {
            throw new IllegalArgumentException(Colors.error("OsuController:", "mainView cannot be null"));
        }

        this.mainView = mainView;

        circles = new ArrayList<>();
        this.osu = new Osu();
        this.gameKeys = new EventHandler<>(){

            @Override
            public void handle(KeyEvent event) {
                if (possibleKeys.contains(event.getCode())) {
                    OsuCircleController circle = circles.get(0);
                    if (event.getCode().equals(circle.getKey())) {
                        circle.action();
                    }else{
                        kill();
                    }
                }
            }
        };

        mainView.getScene().setOnKeyPressed(gameKeys);
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
        mainView.getScene().setOnKeyPressed(null);
        for(OsuCircleController circle: circles){
            circle.getTimeline().stop();
        }
    }
    
}
