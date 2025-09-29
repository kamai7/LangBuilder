package kamai_i.almostroundcircles.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kamai_i.utils.Colors;

public class Circle {

    final Timeline life;
    private int state;
    private final Game game;
    public static final double TIME_RANGE = 0.5;
    private KeyCode key;
    private final double duration;
    private final Color color;

    private double posX; // -1 is left and 1 si right
    private double posY; // -1 is bottom and 1 is top

    public Circle(double duration, Color color, Game game, KeyCode key, double posX, double posY){

        if (duration <= 0) {
            throw new IllegalArgumentException(Colors.error("Circle.circle()","durration cant be equals or lower than zero"));
        }
        if (game == null) {
            throw new IllegalArgumentException(Colors.error("Circle.circle()","the game model cant be null"));
        }
        if (key == null) {
            throw new IllegalArgumentException(Colors.error("Circle.circle()","the key model cant be null"));
        }
        if (color == null) {
            throw new IllegalArgumentException(Colors.error("Circle.circle()","the color cant be null"));
        }
        if(posX > 1 || posX < -1){
            throw new IllegalArgumentException(Colors.error("Circle.circle()","the X pos candot be out of the range [-1;1]"));
        }
        if(posY > 1 || posY < -1){
            throw new IllegalArgumentException(Colors.error("Circle.circle()","the Y pos candot be out of the range [-1;1]"));
        }

        this.color = color;
        this.key = key;
        this.game = game;
        this.posX = posX;
        this.posY = posY;
        this.duration = duration;

        double timeSlice = TIME_RANGE/10.0;

        KeyFrame ok = new KeyFrame(Duration.seconds(duration - timeSlice*5), _ -> {state = 1;});
        KeyFrame good = new KeyFrame(Duration.seconds(duration - timeSlice*3), _ -> {state = 2;});
        KeyFrame perfect = new KeyFrame(Duration.seconds(duration - timeSlice), _ -> {state = 3;});

        KeyFrame good_ = new KeyFrame(Duration.seconds(duration + timeSlice ), _ -> {state = 2;});
        KeyFrame ok_ = new KeyFrame(Duration.seconds(duration + timeSlice * 3), _ -> {state = 1;});
        KeyFrame fail = new KeyFrame(Duration.seconds(duration + timeSlice * 5), _ -> game.fail());

        life = new Timeline(ok,good,perfect,good_,ok_,fail);
        life.play();
    }

    public double x(){
        return posX;
    }

    public double y(){
        return posY;
    }

    public KeyCode key(){
        return key;
    }

    public double duration(){
        return duration;
    }

    public Color color(){
        return color;
    }

    boolean action(KeyCode pressedKey){
        boolean ret = false;
        if(pressedKey.equals(key)){
            if (state == 0){
                game.fail();
            } else {
                ret = true;
                life.stop();
                switch (state) {
                    case 1 -> {
                        System.out.println("OK");
                    }
                    case 2 -> {
                        System.out.println("GOOD");
                    }
                    case 3 -> {
                        System.out.println("PERFECT");
                    }
                }
            }
            
        }else{
            game.fail();
        }
        return ret;
    }
    
}
