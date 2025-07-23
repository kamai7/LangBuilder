package model.managment;

import java.util.HashSet;
import java.util.Set;

import model.dao.LetterDAO;
import model.dao.TypeDAO;
import model.dao.WordDAO;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import model.util.Controls;

public class NavManagement {
    
    Set<Word> words;
    Set<Letter> letters;
    Set<Type> types;

    Set<Word> wordAll;
    Set<Letter> letterAll;
    Set<Type> typeAll;

    public NavManagement() {
        WordDAO wordDAO = new WordDAO();
        LetterDAO letterDAO = new LetterDAO();
        TypeDAO typeDAO = new TypeDAO();
        words = wordDAO.findAll(100);
        letters = letterDAO.findAll(100);
        types = typeDAO.findAll(100);

        wordAll = wordDAO.findAll();
        letterAll = letterDAO.findAll();
        typeAll = typeDAO.findAll();
    }

    public Set<Word> getFilteredWords(String str) {

        Set<Word> ret;

        if(str.equals("")){
            ret = words;
        }else{

            ret = new HashSet<>();
            for (Word word : wordAll) {
                boolean found = false;
                String wordInString = Controls.wordToString(word);

                if(wordInString.contains(str.toLowerCase())) {
                    ret.add(word);
                    found = true;
                }
                if(!found){
                    String wordInStringAscii = Controls.wordAsciiToString(word);

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
        }
        return ret;
    }

    public Set<Letter> getFilteredLetters(String str) {

        Set<Letter> ret;

        if(str.equals("")){
            ret = letters;
        }else{

            ret = new HashSet<>();

            for (Letter letter : letterAll) {
                if(letter.getCharacter().contains(str)) {
                    ret.add(letter);
                }else{
                    if(letter.getCharacterAscii().toLowerCase().contains(str.toLowerCase())) {
                        ret.add(letter);
                    }
                }
            }
        }
        return ret;
    }

    public Set<Type> getFilteredTypes(String str) {

        Set<Type> ret;

        if(str.equals("")){
            ret = types;
        }else{

            ret = new HashSet<>();

            for (Type type : typeAll) {
                if(type.getLabel().contains(str)) {
                    ret.add(type);
                }
            }
        }
        return ret;
    }

    public Set<Word> getWords(){
        return words;
    }

    public Set<Letter> getLetters(){
        return letters;
    }

    public Set<Type> getTypes(){
        return types;
    }

}
