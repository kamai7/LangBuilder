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

    public Management() {
        wordDAO = new WordDAO();
        letterDAO = new LetterDAO();
        typeDAO = new TypeDAO();

        wordAll = wordDAO.findAll();
    }

    public void fetchWords() {
        wordAll = wordDAO.findAll();
    }

    public ArrayList<Word> getFilteredWords(String str) {

        if(str.equals("")){
            return wordDAO.findAll(100);
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
            return letterDAO.findAll(100);
        }
        return letterDAO.findByString(str);
    }

    public ArrayList<Type> getFilteredTypes(String str) {
        if (str.equals("")){
            return typeDAO.findAll(100);
        }
        return typeDAO.findByLabel(str);
    }

}
