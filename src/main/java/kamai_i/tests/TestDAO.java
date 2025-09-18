/*
package kamai_i.tests;

import org.junit.*;

import kamai_i.model.DAO.LetterDAO;
import kamai_i.model.DAO.WordDAO;
import kamai_i.model.persistance.Letter;
import kamai_i.model.persistance.Word;

import java.util.*;

import static org.junit.Assert.*;

public class TestDAO {

    private WordDAO wordDAO;
    private LetterDAO letterDAO;

    private Letter lA, lB, lC;

    public void setUp() {
        wordDAO = new WordDAO();
        letterDAO = new LetterDAO();

        // Nettoyer la base si nécessaire

        // Création des lettres utilisées
        lA = new Letter("∏", "A");
        lB = new Letter("∑", "B");
        lC = new Letter("±", "C");

        letterDAO.create(lA);
        letterDAO.create(lB);
        letterDAO.create(lC);
    }

    @Test
    public void testCreateAndFindById() {
        Word word = new Word(new Letter[]{lA,lB}, 0.5, 0.5, 0.5, 0.0);
        wordDAO.create(word);
        int id = wordDAO.findId(word);
        Word fetched = wordDAO.findById(id);
        assertNotNull(fetched);
        assertEquals(word.toString(), fetched.toString());
    }

    @Test
    public void testFindAll() {
        Word word1 = new Word(new Letter[]{lA,lB}, 0.5, 0.5, 0.5, 0.0);
        Word word2 = new Word(new Letter[]{lB,lC}, 0.7, 0.9, 0.0, 0.0);

        if (!wordDAO.isIn(word1)) wordDAO.create(word1);
        if (!wordDAO.isIn(word2)) wordDAO.create(word2);
        HashSet<Word> allWords = wordDAO.findAll();
        System.out.println(allWords);
    }

    @Test
    public void testFindLetters() {
        Word word = new Word(new Letter[]{lA,lB}, 0.5, 0.5, 0.5, 0.0);
        wordDAO.create(word);

        Letter[] letters = wordDAO.findLetters(word);
        assertEquals(2, letters.length);
        assertEquals("A", letters[0].toString().substring(0, 1));
    }

    @Test
    public void testUpdateParameters() {
        Word word = new Word(new Letter[]{lA,lB}, 0.5, 0.5, 0.5, 0.0);
        wordDAO.create(word);

        word.setComplexity(0.9);
        word.setEmotional(0.8);
        wordDAO.updateParameters(word);

        Word updated = wordDAO.findById(wordDAO.findId(word));
        assertEquals(0.9, updated.getComplexity());
        assertEquals(0.8, updated.getEmotional());
    }

    @Test
    public void testUpdateLetters() {
        Word word = new Word(new Letter[]{lA,lB}, 0.5, 0.5, 0.5, 0.0);
        wordDAO.create(word);

        Letter[] newLetters = {lB, lC};
        word.setWord(newLetters);

        Letter[] fetchedLetters = wordDAO.findLetters(word);
        assertEquals("B", fetchedLetters[0].toString().substring(0, 1));
    }

    @Test
    public void testDeleteWord() {
        Word word = new Word(new Letter[]{lA,lB}, 0.5, 0.5, 0.5, 0.0);
        wordDAO.create(word);

        wordDAO.delete(word);
        Word deleted = wordDAO.findById(wordDAO.findId(word));
        assertNull(deleted);
    }

    @Test
    public void testUpdateLinksSymmetric() {
        Word wordA = new Word(new Letter[]{lA}, 0.5, 0.5, 0.5, 0.0);
        Word wordB = new Word(new Letter[]{lB}, 0.6, 0.6, 0.3, 0.0);
        Word wordC = new Word(new Letter[]{lC}, 0.7, 0.7, 0.0, 0.0);

        wordDAO.create(wordA);
        wordDAO.create(wordB);
        wordDAO.create(wordC);

        wordA.setLinks(new ArrayList<>(Arrays.asList(wordB, wordC)));

        List<Word> linksOfB = wordDAO.findLinks(wordB);
        List<Word> linksOfC = wordDAO.findLinks(wordC);

        assertTrue(linksOfB.stream().anyMatch(w -> w.getId() == wordA.getId()));
        assertTrue(linksOfC.stream().anyMatch(w -> w.getId() == wordA.getId()));

        // Mise à jour : suppression de C
        wordA.setLinks(new ArrayList<>(Collections.singletonList(wordB)));

        linksOfC = wordDAO.findLinks(wordC);
        assertTrue(linksOfC.stream().noneMatch(w -> w.getId() == wordA.getId()));
    }

    public static void main(String[] args) {
        TestDAO test = new TestDAO();
        test.setUp();
        test.testCreateAndFindById();
        test.testFindAll();
        test.testFindLetters();
        test.testUpdateParameters();
        test.testUpdateLetters();
        test.testUpdateLinksSymmetric();
        test.testDeleteWord();
    }
}*/