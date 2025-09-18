package kamai_i.view.fragments;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class NavItem extends BorderPane{

    protected Label objectLabel;
    protected Label descriptionLabel;
    protected CheckBox CheckBox;
    protected Button deleteButton;

    public NavItem() {
        super();
        this.getStyleClass().add("word-list-item");
        
        objectLabel = new Label();
        objectLabel.setMinWidth(50);
        objectLabel.setFont(new Font(objectLabel.getFont().getFamily(), 14));

        descriptionLabel = new Label();
        descriptionLabel.setMinWidth(50);
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.setFont(new Font(descriptionLabel.getFont().getFamily(), 14));

        HBox container = new HBox(10,objectLabel, descriptionLabel);
        /*
         <padding>
            <Insets bottom="1.0" top="1.0" />
         </padding>
         <BorderPane.margin>
            <Insets left="10.0" right="10.0" />
         </BorderPane.margin>
         */
        container.prefHeight(20);
        container.setPadding(new Insets(1, 0, 1, 0));
        
        deleteButton = new Button();
    }
    
}

/*
 <?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.CheckBox?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.BorderPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.text.Font?>

<BorderPane styleClass="word-list-item" xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="kamai_i.controller.fragments.NavLetterController">
   <center>
      <HBox alignment="CENTER_LEFT" maxWidth="1.7976931348623157E308" onMouseClicked="#contentClicked" prefHeight="20.0" spacing="10.0">
         <children>
            <Label fx:id="objectLabel" maxWidth="1.7976931348623157E308" minWidth="50.0" text="Mot" textFill="WHITE">
               <font>
                  <Font size="14.0" />
               </font></Label>
            <Label fx:id="descriptionLabel" minWidth="50.0" styleClass="description" text="Mot ASCII" textFill="WHITE">
               <font>
                  <Font size="14.0" />
               </font></Label>
         </children>
         <padding>
            <Insets bottom="1.0" top="1.0" />
         </padding>
         <BorderPane.margin>
            <Insets left="10.0" right="10.0" />
         </BorderPane.margin>
      </HBox>
   </center>
   <left>
      <CheckBox fx:id="selectedCheckBox" mnemonicParsing="false" BorderPane.alignment="CENTER" />
   </left>
   <right>
      <Button alignment="CENTER_RIGHT" contentDisplay="CENTER" minWidth="30.0" mnemonicParsing="false" onAction="#edit">
         <graphic>
            <ImageView fitHeight="150.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true">
               <image>
                  <Image url="@../../icons/edit.png" />
               </image>
            </ImageView>
         </graphic>
      </Button>
   </right>
</BorderPane>
 */