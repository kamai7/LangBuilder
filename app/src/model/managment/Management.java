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
    ArrayList<Letter> letterAll;
    ArrayList<Type> typeAll;

    public Management() {
        wordDAO = new WordDAO();
        letterDAO = new LetterDAO();
        typeDAO = new TypeDAO();

        wordAll = wordDAO.findAll();
        letterAll = letterDAO.findAll();
        typeAll = typeDAO.findAll();
    }

    public ArrayList<Word> getFilteredWords(String str) {

        ArrayList<Word> ret;

        if(str.equals("")){
            ret = wordDAO.findAll(100);
        }else{

            ret = new ArrayList<>();
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

    public ArrayList<Letter> getFilteredLetters(String str) {
        if (str.equals("")){
            return letterDAO.findAll(100);
        }
        return letterDAO.findByString(str);
    }

    public ArrayList<Type> getFilteredTypes(String str) {
        if (str.equals("")){
            System.out.println("getFilteredTypes " + typeDAO.findAll());
            return typeDAO.findAll(100);
        }
        ArrayList<Type> ret = typeDAO.findByLabel(str);
        ret.addAll(typeDAO.findByParentLabel(str));
        return ret;
    }

}
