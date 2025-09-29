package kamai_i.almostroundcircles.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kamai_i.utils.Colors;
import kamai_i.utils.ListsUtils;

public class Game {

    public static final int COLOR_PHASE_DURATION = 20;
    private final long birthDate;
    private final Timeline game;

    private final ObjectProperty<Circle> newCircle;
    private final ObjectProperty<Circle> activeCircle;
    private final BooleanProperty alive;
    private final List<Circle> circles;
    public static final Set<KeyCode> keys = Set.of(KeyCode.Q, KeyCode.S);
    
    public Game(){
        circles = new ArrayList<>();
        newCircle = new SimpleObjectProperty<>();
        alive = new SimpleBooleanProperty(true);
        activeCircle = new SimpleObjectProperty<>();

        KeyFrame addCircle = new KeyFrame(Duration.seconds(0.5), _ -> randomCircle());
        game = new Timeline(addCircle);
        game.setCycleCount(Timeline.INDEFINITE);

        birthDate = System.currentTimeMillis();
        game.play();
    }

    public Color getColor(double saturation) {
        int colorPhase = (int) ((System.currentTimeMillis() - birthDate)/COLOR_PHASE_DURATION);
        return getColor(saturation, colorPhase);
    }

    public Color getColor(double saturation, Color color) {
        throw new RuntimeException(Colors.info("pas implémenté"));
    }

    public Color getColor(double saturation, int colorPhase){

        if(saturation < 0 || saturation > 1) {
            throw new IllegalArgumentException(Colors.error("Game.getColor()","la saturation entrée n'est pas entre 0 et 1"));
        }

        colorPhase = colorPhase%768;

        int r = 0;
        int g = 0;
        int b = 0;
        if (colorPhase < 256) {
            r = (int) (saturation * (255 - colorPhase));
            g = (int) (saturation * colorPhase);
        } else if (colorPhase < 512) {
            colorPhase = colorPhase - 256;
            g = (int) (saturation * (255 - colorPhase));
            b = (int) (saturation * colorPhase);
        } else if (colorPhase < 768) {
            colorPhase = colorPhase - 512;
            b = (int) (saturation * (255 - colorPhase));
            r = (int) (saturation * colorPhase);
        }
        return new Color(r/255.0, g/255.0, b/255.0, 1);
    }

    public void randomCircle() {
        double x =  (Math.random() * 2) -1;
        double y =  (Math.random() * 2) -1;
        KeyCode k = ListsUtils.draw(keys);
        

        Circle circle = new Circle(1.3,getColor(0.8),this,k,x,y);

        circles.add(circle);
        newCircle.set(circle);

        if (circles.size() == 1){
            activeCircle.set(circle);
        }
    }

    public void fail(){
        alive.set(false);
        game.stop();
        for(Circle circle: circles){
            circle.life.stop();
        }
        System.out.println(Colors.error("! FAIL !"));
    }

    public ObjectProperty<Circle> newCircleProperty(){
        return newCircle;
    }

    public ObjectProperty<Circle> activeCircleProperty(){
        return activeCircle;
    }

    public BooleanProperty aliveProperty(){
        return alive;
    }

    public void next() {
        if (!circles.isEmpty()) {
            circles.remove(0);
            if (!circles.isEmpty()) {
                activeCircle.set(circles.get(0));
            }else{
                activeCircle.set(null);
            }
        }
    }

}
