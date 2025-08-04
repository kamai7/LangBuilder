package model.managment;

import java.util.HashSet;
import java.util.Set;

import model.dao.LetterDAO;
import model.dao.TypeDAO;
import model.dao.WordDAO;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import utils.PersistenceUtils;

public class Management {
    
    Set<Word> words100;
    Set<Letter> letters100;
    Set<Type> types100;

    Set<Word> wordAll;
    Set<Letter> letterAll;
    Set<Type> typeAll;

    Object selected;

    public Management() {
        WordDAO wordDAO = new WordDAO();
        LetterDAO letterDAO = new LetterDAO();
        TypeDAO typeDAO = new TypeDAO();
        words100 = wordDAO.findAll(100);
        letters100 = letterDAO.findAll(100);
        types100 = typeDAO.findAll(100);

        wordAll = wordDAO.findAll();
        letterAll = letterDAO.findAll();
        typeAll = typeDAO.findAll();
    }

    public void setSelected(Object selected) {
        this.selected = selected;
    }

    public Set<Word> getFilteredWords(String str) {

        Set<Word> ret;

        if(str.equals("")){
            ret = words100;
        }else{

            ret = new HashSet<>();
            for (Word word : wordAll) {
                boolean found = false;
                String wordInString = PersistenceUtils.wordToString(word);

                if(wordInString.contains(str.toLowerCase())) {
                    ret.add(word);
                    found = true;
                }
                if(!found){
                    String wordInStringAscii = PersistenceUtils.wordAsciiToString(word);

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
        if (str.equals("")){
            return letters100;
        }

        LetterDAO letterDAO= new LetterDAO();
        return letterDAO.findByString(str);
    }

    public Set<Type> getFilteredTypes(String str) {

        Set<Type> ret;

        if(str.equals("")){
            ret = types100;
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

    public Set<Word> getWords100(){
        return words100;
    }

    public Set<Letter> getLetters100(){
        return letters100;
    }

    public Set<Type> getTypes100(){
        return types100;
    }

    public void addLetter(Letter letter){
        letterAll.add(letter);
        if(letters100.size() < 100){
            letters100.add(letter);
        }
    }

    public void addWord(Word word){
        wordAll.add(word);
        if(words100.size() < 100){
            words100.add(word);
        }
    }

    public void addType(Type type){
        typeAll.add(type);
        if(types100.size() < 100){
            types100.add(type);
        }
    }

}
