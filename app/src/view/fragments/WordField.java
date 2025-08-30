package view.fragments;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class WordField extends HBox{

   protected Label objectLabel;
   protected Button deleteButton;

   public WordField(String objectText) {
      super(5);
      this.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
      this.layoutXProperty().set(10);
      this.layoutYProperty().set(10);
      this.getStyleClass().add("editor-item");

      objectLabel = new Label();
      objectLabel.getStyleClass().add("text-color1");
      objectLabel.setText(objectText);

      deleteButton = new Button();
      ImageView deleteImageView = new ImageView();
      deleteImageView.setFitHeight(18.0);
      deleteImageView.setFitWidth(18.0);
      deleteImageView.setPickOnBounds(true);
      deleteImageView.setPreserveRatio(true);
      deleteImageView.setImage(new Image("/fxml/icons/cross.png"));
      deleteButton.setGraphic(deleteImageView);
      this.getChildren().addAll(objectLabel, deleteButton);
   }

   public Button getDeleteButton() {
      return deleteButton;
   }

   public Label getObjectLabel() {
      return objectLabel;
   }
    
}

/*
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.Separator?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.HBox?>

<HBox alignment="CENTER_LEFT" layoutX="10.0" layoutY="10.0" spacing="5.0" styleClass="editor-item" xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="controller.fragments.WordFieldController">
   <children>
      <Label fx:id="objectLabel" styleClass="text-color1" text="text..."/>
      <Button fx:id="deleteButton" mnemonicParsing="false">
         <graphic>
            <ImageView fitHeight="18.0" fitWidth="18.0" pickOnBounds="true" preserveRatio="true">
               <image>
                  <Image url="@../../icons/cross.png" />
               </image>
            </ImageView>
         </graphic>
      </Button>
      <Separator orientation="VERTICAL" />
   </children>
</HBox>
 */