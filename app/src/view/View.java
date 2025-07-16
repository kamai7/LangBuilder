package view;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class View extends Stage {

    private AnchorPane main;
    private FXMLHandler<AnchorPane,Controller> content;
    private Controller controller;
    private Scene scene;
    
    public View() {
        content = new FXMLHandler<>("/fxml/static/header_nav.fxml");
        controller = content.getController();
        AnchorPane contentNode = content.get();
        controller.setContent(contentNode);

        main = new AnchorPane(contentNode);
        AnchorPane.setTopAnchor(contentNode, 0.0);
        AnchorPane.setLeftAnchor(contentNode, 0.0);
        AnchorPane.setRightAnchor(contentNode, 0.0);
        AnchorPane.setBottomAnchor(contentNode, 0.0);
        main.setPrefHeight(720.0);
        main.setPrefWidth(1280.0);

        scene = new Scene(main, 1280, 720);
        scene.getStylesheets().add("/fxml/style/style.css");
        this.setScene(scene);
        this.show();
    }

}
