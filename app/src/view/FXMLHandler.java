package view;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import model.util.Colors;

public class FXMLHandler<T extends Node, U> {

    private FXMLLoader loader;
    private T element;
    private U controller;
    
    public FXMLHandler(String path) {

        if (path == null) {
            throw new IllegalArgumentException(Colors.error("FXMLHandler FXMLHandler: path cannot be null"));
        }

        if(path.trim().length() == 0) {
            throw new IllegalArgumentException(Colors.error("FXMLHandler FXMLHandler: path cannot be empty"));
        }

        URL url = getClass().getResource(path);

        if (url == null) {
            throw new IllegalArgumentException(Colors.error("FXMLHandler FXMLHandler: path not found: " + path));
        }

        try{
            this.loader = new FXMLLoader(url);
            element = this.loader.load();
            controller = this.loader.getController();
        } catch (Exception e) {
            System.err.println(Colors.error("FXMLHandler FXMLHandler: Could not load FXML file: " + url, e.getMessage() + "\n" + e.getStackTrace()));
        }
    }

    public T get() {
        return element;
    }

    public U getController() {
        return controller;
    }

}
