package kamai_i.almostroundcircles.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kamai_i.almostroundcircles.model.Circle;
import kamai_i.almostroundcircles.view.CircleView;
import kamai_i.utils.AnimationUtils;
import kamai_i.utils.Colors;

public class CircleController {

    private final CircleView view;
    private final Timeline animation;
    private final boolean direction;
    private final Circle model;

    public CircleController(CircleView view, Circle model, boolean direction) {

        if (view == null){
            throw new IllegalArgumentException(Colors.error("CircleController.CircleController","view cant be null"));
        }
        if (model == null){
            throw new IllegalArgumentException(Colors.error("CircleController.CircleController","model cant be null"));
        }

        this.view = view;
        this.direction = direction;
        this.model = model;

        KeyFrame[] keyFrames = view.keyFrames();
        animation = new Timeline(keyFrames);
        animation.play();
    }

    void stop(){
        animation.stop();
    }

    public void action(KeyEvent e){
        int res = model.action(e.getCode());
        view.outline().scaleYProperty().bind(view.outline().scaleXProperty());
        KeyValue value1 = new KeyValue(view.outline().scaleXProperty(), 1.1, AnimationUtils.QUAD_EASE_IN_OUT);
        KeyValue value2 = new KeyValue(view.outline().strokeProperty(), Color.WHITE, AnimationUtils.QUAD_EASE_IN_OUT);
        KeyValue value3 = new KeyValue(view.outline().scaleXProperty(), 1, AnimationUtils.QUAD_EASE_IN_OUT);
        KeyValue value4 = new KeyValue(view.outline().strokeProperty(), Color.BLACK, AnimationUtils.QUAD_EASE_IN_OUT);

        KeyFrame key1 = new KeyFrame(Duration.seconds(0.1), value1, value2);
        KeyFrame key2 = new KeyFrame(Duration.seconds(0.2), value3);
        KeyFrame key3 = new KeyFrame(Duration.seconds(0.3), value4);
        KeyFrame key4;

        if (res == 0) {
            key4 = new KeyFrame(Duration.seconds(0.1), _ -> model.fail());
        }else{
            switch (res) {
                case 1 -> okScoreAnimation();
                case 2 -> goodScoreAnimation();
                case 3 -> perfectScoreAnimation();
            }
            key4 = new KeyFrame(Duration.seconds(0.1), _ -> disapear(0.7));
        }

        Timeline timeline = new Timeline(key1,key2,key3, key4);
        animation.stop();
        timeline.play();
    }

    private void okScoreAnimation(){

    }

    private void goodScoreAnimation(){

    }
     private void perfectScoreAnimation(){
         
     }

    private void disapear(double duration){
        int orientation = 1;
        if (direction){
            orientation = -1;
        }

        KeyValue value0 = new KeyValue(view.translateXProperty(), 70 * orientation, AnimationUtils.QUAD_EASE_IN);
        KeyValue value1 = new KeyValue(view.translateYProperty(), -90, AnimationUtils.QUAD_EASE_OUT);
        KeyValue value2 = new KeyValue(view.translateYProperty(), 1000, AnimationUtils.QUAD_EASE_IN);
        KeyValue value3 = new KeyValue(view.translateXProperty(), 200 * orientation, AnimationUtils.QUAD_EASE_OUT);
        KeyValue value4 = new KeyValue(view.opacityProperty(), 1, AnimationUtils.QUAD_EASE_IN);
        KeyValue value5 = new KeyValue(view.opacityProperty(), 0, AnimationUtils.QUAD_EASE_IN);

        KeyFrame key0 = new KeyFrame(Duration.seconds(duration/2.5), value0, value1);
        KeyFrame key1 = new KeyFrame(Duration.seconds(duration/1.5), value4);
        KeyFrame key2 = new KeyFrame(Duration.seconds(duration), value2, value3, value5);

        Timeline disapearAnimation = new Timeline(key0, key1, key2);
        disapearAnimation.play();
    }
}
