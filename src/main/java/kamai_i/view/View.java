package kamai_i.view;

import kamai_i.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class View extends Stage {

    private AnchorPane main;
    private FXMLHandler<AnchorPane,Controller> navHeader;
    private Scene scene;
    
    public View() {
        navHeader = new FXMLHandler<>("/fxml/static/header_nav.fxml");
        AnchorPane navHeaderNode = navHeader.get();

        main = new AnchorPane(navHeaderNode);
        AnchorPane.setTopAnchor(navHeaderNode, 0.0);
        AnchorPane.setLeftAnchor(navHeaderNode, 0.0);
        AnchorPane.setRightAnchor(navHeaderNode, 0.0);
        AnchorPane.setBottomAnchor(navHeaderNode, 0.0);
        main.setPrefHeight(720.0);
        main.setPrefWidth(1280.0);

        scene = new Scene(main, 1280, 720);
        navHeader.getController().init(this);
        Font.loadFont(getClass().getResourceAsStream("/fxml/fonts/Inter-Regular.ttf"), 12);
        scene.getStylesheets().add("/fxml/style/redefined.css");
        scene.getStylesheets().add("/fxml/style/style.css");
        this.setScene(scene);
        this.getIcons().add(new Image("/fxml/icons/logo.png"));

        this.show();
    }

}
