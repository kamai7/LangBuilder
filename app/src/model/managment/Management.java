package model.managment;

import java.util.ArrayList;

import controller.fragments.NavItem2FieldsController;
import controller.fragments.NavItem3FieldsController;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.dao.LetterDAO;
import model.dao.TypeDAO;
import model.dao.WordDAO;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import view.FXMLHandler;

public class Management {
    
    ArrayList<Word> words;
    ArrayList<Letter> letters;
    ArrayList<Type> types;

    public Management() {
        WordDAO wordDAO = new WordDAO();
        LetterDAO letterDAO = new LetterDAO();
        TypeDAO typeDAO = new TypeDAO();
        words = wordDAO.findAll();
        letters = letterDAO.findAll();
        types = typeDAO.findAll();
    }

    public static String wordToString(Word word){
        String ret = "";
        for (Letter letter : word.getLetters()) {
            ret += letter.getCharacter();
        }
        return ret;
    }

    public static String wordAsciiToString(Word word){
        String ret = "";
        for (Letter letter : word.getLetters()) {
            ret += letter.getCharacterAscii();
        }
        return ret;
    }

    public ArrayList<Word> getFilteredWords(String str) {
        if(str.equals("")){
            return words;
        };

        ArrayList<Word> ret = new ArrayList<>();
        
        for (Word word : words) {
            boolean found = false;
            String wordInString = wordToString(word);

            if(wordInString.contains(str.toLowerCase())) {
                ret.add(word);
                found = true;
            }
            if(!found){
                String wordInStringAscii = wordAsciiToString(word);

                if(wordInStringAscii.toLowerCase().contains(str.toLowerCase())) {
                    ret.add(word);
                    found = true;
                }
            }
            if(!found) {
                if(word.getTranslations().contains(str.toLowerCase())) {
                    ret.add(word);
                }
            } 
        }
        return ret;
    }

    public ArrayList<Letter> getFilteredLetters(String str) {

        if(str.equals("")){
            return letters;
        }

        ArrayList<Letter> ret = new ArrayList<>();
        for (Letter letter : letters) {
            if(letter.getCharacter().contains(str)) {
                ret.add(letter);
            }else{
                if(letter.getCharacterAscii().toLowerCase().contains(str.toLowerCase())) {
                    ret.add(letter);
                }
            }
        }
        return ret;
    }

    public ArrayList<Type> getFilteredTypes(String str) {

        if(str.equals("")){
            return types;
        };

        ArrayList<Type> ret = new ArrayList<>();
        for (Type type : types) {
            if(type.getLabel().contains(str)) {
                ret.add(type);
            }
        }
        return ret;
    }

    public FXMLHandler<BorderPane, NavItem3FieldsController> convertWordToFXMLHandler(Word word) {
        FXMLHandler<BorderPane, NavItem3FieldsController> wordEditor = new FXMLHandler<>("/fxml/fragments/nav/nav_item_3fields.fxml");
        NavItem3FieldsController controller = wordEditor.getController();
        controller.setObjectText(wordToString(word));
        controller.setDescriptionText(wordAsciiToString(word));
        for (Type type : word.getTypes()) {
            double[] colors = type.getColor();
            controller.addType(type.getLabel(), new Color(colors[0], colors[1], colors[2], colors[3]));
            
        }
        return wordEditor;
    }

    public FXMLHandler<BorderPane, NavItem2FieldsController> convertLetterToFXMLHandler(Letter letter) {
        FXMLHandler<BorderPane, NavItem2FieldsController> letterEditor = new FXMLHandler<>("/fxml/fragments/nav/nav_item_2fields.fxml");
        NavItem2FieldsController controller = letterEditor.getController();
        controller.setObjectText(letter.getCharacter());
        controller.setDescriptionText(letter.getCharacterAscii());
        return letterEditor;
    }

    public FXMLHandler<BorderPane, NavItem2FieldsController> convertTypeToFXMLHandler(Type type) {
        FXMLHandler<BorderPane, NavItem2FieldsController> typeEditor = new FXMLHandler<>("/fxml/fragments/nav/nav_item_2fields.fxml");
        NavItem2FieldsController controller = typeEditor.getController();
        controller.setObjectText(type.getLabel());
        controller.setDescriptionText(wordToString(type.getRoot()));
        return typeEditor;
    }

    public ArrayList<Word> getWords(){
        return words;
    }

    public ArrayList<Letter> getLetters(){
        return letters;
    }

    public ArrayList<Type> getTypes(){
        return types;
    }

}
