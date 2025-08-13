package model.managment;

import java.util.ArrayList;

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

    ArrayList<Word> wordAll;

    ArrayList<Word> word100;
    ArrayList<Letter> letter100;
    ArrayList<Type> type100;

    public Management() {
        wordDAO = new WordDAO();
        letterDAO = new LetterDAO();
        typeDAO = new TypeDAO();

        wordAll = wordDAO.findAll();
        word100 = wordDAO.findAll(100);
        letter100 = letterDAO.findAll(100);
        type100 = typeDAO.findAll(100);
    }

    public void fetchWords() {
        wordAll = wordDAO.findAll();
        word100 = wordDAO.findAll(100);
    }

    public void fetchLetters() {
        letter100 = letterDAO.findAll(100);
    }

    public void fetchTypes() {
        type100 = typeDAO.findAll(100);
    }

    public ArrayList<Word> getFilteredWords(String str) {

        if(str.equals("")){
            return word100;
        }else{
            ArrayList<Word> ret = new ArrayList<>();
            for (Word word : wordAll) {
                boolean found = false;
                String wordInString = PersistenceUtils.wordToString(word);

                if(wordInString.contains(str)) {
                    ret.add(word);
                    found = true;
                }
                if(!found){
                    String wordInStringAscii = PersistenceUtils.wordAsciiToString(word).toLowerCase();

                    if(wordInStringAscii.toLowerCase().contains(str.toLowerCase())) {
                        ret.add(word);
                        found = true;
                    }
                }
                if(!found) {
                    for (String t: word.getTranslations()) {
                        if(t.toLowerCase().contains(str.toLowerCase())) {
                            ret.add(word);
                        }
                    }
                } 
            }
            return ret;
        }
    }

    public ArrayList<Letter> getFilteredLetters(String str) {
        if (str.equals("")){
            return letter100;
        }
        return letterDAO.findByString(str);
    }

    public ArrayList<Type> getFilteredTypes(String str) {
        if (str.equals("")){
            return type100;
        }
        return typeDAO.findByLabel(str);
    }

}
