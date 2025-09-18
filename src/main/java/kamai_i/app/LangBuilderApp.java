package kamai_i.app;

import javafx.application.Application;
import javafx.stage.Stage;
import kamai_i.view.View;

public class LangBuilderApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        new View();
    }

}