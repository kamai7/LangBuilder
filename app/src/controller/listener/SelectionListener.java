package controller.listener;

import controller.fragments.NavItem;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;

public abstract class SelectionListener<U,T extends NavItem<U>> implements ChangeListener<T>{

    private ObjectProperty<T> property;
    protected U newObject;
    private String errorMessage;

    public SelectionListener(ObjectProperty<T> property, String errorMessage) {
        this.property = property;
        this.errorMessage = errorMessage;
    }

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        try{
            newObject = newValue.getObject();
            perform();

            //remove the listener
            property.removeListener(this);
            property.set(null);
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.setTitle(errorMessage);
            alert.setContentText(errorMessage + "\n" + e.getMessage());
            alert.show();
        }
    }

    public abstract void perform();
    
}
