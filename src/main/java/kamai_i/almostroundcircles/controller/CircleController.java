package kamai_i.almostroundcircles.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import kamai_i.almostroundcircles.view.CircleView;
import kamai_i.utils.AnimationUtils;
import kamai_i.utils.Colors;

public class CircleController {

    private final CircleView view;
    private final Timeline animation;
    private final boolean direction;

    public CircleController(CircleView view, boolean direction) {

        if (view == null){
            throw new IllegalArgumentException(Colors.error("CircleController.CircleController","arguments cant be null"));
        }

        this.view = view;
        this.direction = direction;

        KeyFrame[] keyFrames = view.keyFrames();
        animation = new Timeline(keyFrames);
        animation.play();
    }

    void stop(){
        animation.stop();
    }

    private void action(){
        
    }

    KeyFrame[] disapear(double duration){
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

        return new KeyFrame[]{key0, key1, key2};
    }
}
