package app;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import view.View;

public class LangBuilderApp extends Application {

     public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        View vue = new View();
        Controller adminNavigationControl = new Controller(vue);
    }

}