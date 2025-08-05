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

    LetterDAO letterDAO;
    WordDAO wordDAO;
    TypeDAO typeDAO;

    Set<Word> wordAll;
    Set<Letter> letterAll;
    Set<Type> typeAll;

    public Management() {
        wordDAO = new WordDAO();
        letterDAO = new LetterDAO();
        typeDAO = new TypeDAO();

        wordAll = wordDAO.findAll();
        letterAll = letterDAO.findAll();
        typeAll = typeDAO.findAll();
    }

    public Set<Word> getFilteredWords(String str) {

        Set<Word> ret;

        if(str.equals("")){
            ret = wordDAO.findAll(100);
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
            return letterDAO.findAll(100);
        }
        return letterDAO.findByString(str);
    }

    public Set<Type> getFilteredTypes(String str) {

        Set<Type> ret;

        if(str.equals("")){
            ret = typeDAO.findAll(100);
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

}
