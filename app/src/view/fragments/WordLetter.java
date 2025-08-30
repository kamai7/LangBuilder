package view.fragments;

import javafx.scene.control.CheckBox;
import javafx.scene.text.Font;

public class WordLetter extends WordField{

    private CheckBox checkbox;

    public WordLetter(String objectText) {
        super(objectText);
        this.setSpacing(2);
        objectLabel.setFont(new Font(objectLabel.getFont().getFamily(), 16));
        checkbox = new CheckBox();
        this.getChildren().add(0, checkbox);
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }
    
}

/*
 <?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.CheckBox?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.Separator?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.text.Font?>

<HBox fx:id="container" alignment="CENTER_LEFT" prefHeight="0.0" spacing="2.0" styleClass="editor-item" xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="controller.fragments.WordLetterController">
   <children>
      <CheckBox fx:id="checkbox" mnemonicParsing="false" />
      <Label fx:id="objectLabel" minWidth="10.0" styleClass="text-color1" text="a">
         <font>
            <Font size="16.0" />
         </font></Label>
      <Button fx:id="deleteButton" mnemonicParsing="false">
         <graphic>
            <ImageView fitHeight="16.0" fitWidth="16.0" pickOnBounds="true" preserveRatio="true">
               <image>
                  <Image url="@../../icons/cross.png" />
               </image>
            </ImageView>
         </graphic>
      </Button>
      <Separator orientation="VERTICAL" prefHeight="200.0" />
   </children>
</HBox>
 */