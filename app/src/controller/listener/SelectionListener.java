package controller.listener;

import controller.fragments.NavItem;
import exceptions.InvalidUserArgument;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;

public abstract class SelectionListener<U,T extends NavItem<U>> implements ChangeListener<T>{

    protected U newObject;
    private ObjectProperty<T> property;

    public SelectionListener(ObjectProperty<T> property) {
        this.property = property;
    }

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        try{
            newObject = newValue.getObject();
            perform();

        }catch(InvalidUserArgument e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.setTitle("Arguments error");
            alert.setContentText(e.getMessage());
            alert.show();
        }finally{
            //remove the listener
            property.removeListener(this);
            property.set(null);
        }
    }

    public abstract void perform() throws InvalidUserArgument;
    
}
