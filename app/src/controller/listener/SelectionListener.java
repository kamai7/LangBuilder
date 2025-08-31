package controller.listener;

import controller.fragments.NavItem;
import exceptions.InvalidUserArgument;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;

public abstract class SelectionListener<U,T extends NavItem<U>> implements ChangeListener<T>{

    protected U newObject;
    private String errorMessage;

    public SelectionListener(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        try{
            newObject = newValue.getObject();
            perform();

            //remove the listener
        }catch(InvalidUserArgument e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.setTitle(errorMessage);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public abstract void perform() throws InvalidUserArgument;
    
}
