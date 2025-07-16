package view;

import controller.Controller;
import controller.HomeController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class View extends Stage {

    private AnchorPane main;
    private FXMLHandler<AnchorPane,Controller> navHeader;
    private FXMLHandler<GridPane,HomeController> content;
    private Scene scene;
    
    public View() {
        navHeader = new FXMLHandler<>("/fxml/static/header_nav.fxml");
        AnchorPane navHeaderNode = navHeader.get();

        content = new FXMLHandler<>("/fxml/static/home_page.fxml");
        GridPane contentNode = content.get();
        navHeader.getController().setContent(contentNode);

        content.getController().getView().addListener((observable, oldValue, newValue) -> navHeader.getController().setContent(newValue));

        main = new AnchorPane(navHeaderNode);
        AnchorPane.setTopAnchor(navHeaderNode, 0.0);
        AnchorPane.setLeftAnchor(navHeaderNode, 0.0);
        AnchorPane.setRightAnchor(navHeaderNode, 0.0);
        AnchorPane.setBottomAnchor(navHeaderNode, 0.0);
        main.setPrefHeight(720.0);
        main.setPrefWidth(1280.0);

        scene = new Scene(main, 1280, 720);
        scene.getStylesheets().addAll("/fxml/style/style.css", "/fxml/style/redefined.css");
        this.setScene(scene);
        this.getIcons().add(new Image("/fxml/icons/logo.png"));
        this.show();
    }

}
