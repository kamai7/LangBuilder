package view;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class FXMLHandler<T extends Node, U> {

    private FXMLLoader loader;
    private T element;
    private U controller;
    
    public FXMLHandler(String path) {

        if (path == null) {
            throw new IllegalArgumentException("path cannot be null");
        }

        if(path.trim().length() == 0) {
            throw new IllegalArgumentException("path cannot be empty");
        }

        URL url = getClass().getResource(path);

        try{
            this.loader = new FXMLLoader(url);
            element = this.loader.load();
            controller = this.loader.getController();
        } catch (Exception e) {
            throw new RuntimeException("Could not load FXML file: " + url + " -> Error: " + e.getMessage());
        }
    }

    public T get() {
        return element;
    }

    public U getController() {
        return controller;
    }

}
