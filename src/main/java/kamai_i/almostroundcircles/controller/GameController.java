package kamai_i.almostroundcircles.controller;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import kamai_i.almostroundcircles.model.Game;
import kamai_i.almostroundcircles.view.CircleView;
import kamai_i.almostroundcircles.view.GameView;
import kamai_i.utils.Colors;
import kamai_i.view.View;

public class GameController {

    private  final GameView view;
    private final View scene;
    private final Game model;
    private EventHandler<KeyEvent> keyEvent;
    private final ArrayList<CircleController> circles;
    private boolean direction;

    public GameController(View scene, Game model, GameView gameVIew) {
        if (scene == null) {
            throw new IllegalArgumentException(Colors.error("OsuController:", "mainView cannot be null"));
        }

        this.scene = scene;
        this.model = model;
        this.view = gameVIew;
        this.circles = new ArrayList<>();

        initListeners();

        scene.getScene().setOnKeyPressed(keyEvent);
    }

    private void initListeners(){
        this.keyEvent = (KeyEvent event) -> {
            if (Game.keys.contains(event.getCode())) {
                model.hit(event.getCode());
            }
        };

        model.aliveProperty().addListener((_, _, newValue) -> {
            if (!newValue) {
                scene.getScene().setOnKeyPressed(null);
                for(CircleController controller: circles){
                    controller.stop();
                }
            }
        });

        model.newCircleProperty().addListener((_, _, newModel) -> {

            CircleView newCircleView = new CircleView(newModel.color(), newModel.key().getName(), newModel.duration());
            CircleController newController = new CircleController(newCircleView, direction);
            direction = !direction;
            view.getChildren().add(newCircleView);
            circles.add(newController);

            double x = (((newModel.x() + 1) / 2) * (view.getWidth() - 2 * GameView.MARGIN)) + GameView.MARGIN;
            double y = (((newModel.y() + 1) / 2) * (view.getHeight() - 2 * GameView.MARGIN)) + GameView.MARGIN;
            newCircleView.setLayoutX(x);
            newCircleView.setLayoutX(y);
        });

        model.activeCircleProperty().addListener((_, old, _) -> {
            if (old != null) {
                KeyFrame[] keys = circles.get(0).disapear(0.7);
                KeyFrame remove = new KeyFrame(Duration.seconds(0.75), _ -> view.getChildren().remove(0));
                circles.remove(0);

                Timeline animation = new Timeline(keys[0], keys[1], keys[2], remove);
                animation.play();   
            }
        });
    }
    
}
