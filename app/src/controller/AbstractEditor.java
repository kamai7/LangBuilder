package controller;


public abstract class AbstractEditor<T> {

    protected Controller mainController;

    public abstract void init(Controller mainController, T letter);

    public abstract void init(Controller mainController);

    protected void applyAnimation() {
    }
    
}
