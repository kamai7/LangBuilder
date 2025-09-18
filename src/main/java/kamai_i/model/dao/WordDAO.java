package kamai_i.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import kamai_i.model.persistance.Letter;
import kamai_i.model.persistance.Word;
import kamai_i.utils.Colors;

public class WordDAO extends DAO<Word> {
    @Override
    public ArrayList<Word> findAll(int limit) {
        ArrayList<Word> ret = new ArrayList<>(getRowsCount("Word"));

        if(limit < -1){
            throw new IllegalArgumentException("Limit must be greater than -1.");
        }

        String query;

        if (limit == -1){
            query = "SELECT w.wordId FROM WordsLetters wl JOIN Word w ON wl.wordLId = w.wordId JOIN Letter l ON l.letterId = wl.letterWId GROUP BY w.wordId ORDER BY COUNT(*) ASC, MIN(l.letterAscii) ASC";
        }else{
            query = "SELECT w.wordId FROM WordsLetters wl JOIN Word w ON wl.wordLId = w.wordId JOIN Letter l ON l.letterId = wl.letterWId GROUP BY w.wordId ORDER BY COUNT(*) ASC, MIN(l.letterAscii) ASC LIMIT " + limit;
        }

        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                ret.add(findById(rs.getInt("wordId")));
            }

        } catch(SQLException e) {
            System.err.println(Colors.error("WordDAO findAll: ", e.getMessage()));
        }

        return ret;
    }

    /*
    also uopdate the word
     */
    @Override
    public Word findById(int id){
        Word ret = null;
        String query = "SELECT * FROM Word WHERE wordId = ?;" +
                       "SELECT * FROM WordsLetters WHERE wordLId = ? ORDER BY position ASC;" +
                       "SELECT * FROM Definition WHERE dWordId = ?;" +
                       "SELECT * FROM Translation WHERE tWordId = ?;" +
                       "SELECT * FROM UsedRoots WHERE roWordId = ?;" +
                       "SELECT * FROM Link WHERE lWordId = ? OR linkedWordId = ?;" +
                       "SELECT * FROM Link WHERE lWordId = ? OR linkedWordId = ?;" +
                       "SELECT * FROM WordsTypes WHERE tyWordId = ?;";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            // Set the parameters for the prepared statement
            for (int i = 1; i <= 10; i++) {
                ps.setInt(i, id);
            }

            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            
            if (rs.next()) {
                double emotional = rs.getDouble("emotional");
                double formality = rs.getDouble("formality");
                double vulgarity = rs.getDouble("vulgarity");
                boolean isUsable = rs.getBoolean("isUsable");
                
                ArrayList<Integer> letterIds = new ArrayList<>();
                ArrayList<String> definitions = new ArrayList<>();
                ArrayList<String> translations = new ArrayList<>();
                Set<Integer> rootIds = new HashSet<>();
                Set<Integer> linkIds = new HashSet<>();
                Set<Integer> typeIds = new HashSet<>();

                // Get the letters
                ps.getMoreResults();
                rs = ps.getResultSet();
                while (rs.next()) {
                    letterIds.add(rs.getInt("letterWId"));
                }

                // Get the definitions
                ps.getMoreResults();
                rs = ps.getResultSet();
                while (rs.next()) {
                    definitions.add(rs.getString("definition"));
                }

                // Get the translations
                ps.getMoreResults();
                rs = ps.getResultSet();
                while (rs.next()) {
                    translations.add(rs.getString("translation"));
                }

                // Get the roots
                ps.getMoreResults();
                rs = ps.getResultSet();
                while (rs.next()) {
                    rootIds.add(rs.getInt("usedRootId"));
                }

                // Get the links
                ps.getMoreResults();
                rs = ps.getResultSet();
                while (rs.next()) {
                    int linkedWordId = rs.getInt("linkedWordId");
                    if (linkedWordId != id){
                        linkIds.add(linkedWordId);
                    }
                }
                // try to get links in the other way
                ps.getMoreResults();
                rs = ps.getResultSet();
                while (rs.next()) {
                    int lWordId = rs.getInt("lWordId");
                    if (lWordId != id){
                        linkIds.add(lWordId);
                    }
                }

                // Get the types
                ps.getMoreResults();
                rs = ps.getResultSet();
                while (rs.next()) {
                    typeIds.add(rs.getInt("wordTypeId"));
                }

                ret = new Word(letterIds, emotional, formality, vulgarity, translations, definitions, isUsable, linkIds, rootIds, typeIds);
                ret.setId(id);
            }
        } catch(SQLException e) {
            System.err.println(Colors.error("WordDAO.findById: ", e.getMessage()));
        }
    
        return ret;
    }

    public ArrayList<Word> findByTranslation(String translation){
        Set<Word> ret = new HashSet<>();

        String query = "SELECT * FROM Word JOIN Translation ON wordId = tWordId WHERE translation LIKE ? ORDER BY char_length(letterAscii), letterAscii ASC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setString(1, "%" + translation + "%");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ret.add(findById(rs.getInt("wordId")));
            }
        } catch(SQLException e) {
            System.err.println(Colors.error("WordDAO findByTranslation: ", e.getMessage()));
        }

        return new ArrayList<>(ret);
    }

    public Word findByLetters(ArrayList<Letter> letters){
        ArrayList<Integer> find = null;
        String query = "SELECT wordLId FROM WordsLetters WHERE position = ? AND letterWId = ?";

        try (Connection c = getConnection()) {
            
            for (int i = 0; i < letters.size(); i++) {
                PreparedStatement ps = c.prepareStatement(query);
                ps.setInt(1, i+1);
                ps.setInt(2, letters.get(i).getId());
                ResultSet rs = ps.executeQuery();

                ArrayList<Integer> result = new ArrayList<>();

                while(rs.next()){
                    result.add(rs.getInt("wordLId"));
                }

                if (find == null) {
                    find = result;
                } else {
                    find.retainAll(result);
                }
            }
        } catch(SQLException e) {
            System.err.println(Colors.error("WordDAO.findByLetters: ", e.getMessage()));
        }

        if (find == null || find.isEmpty()) {
            return null;
        } else {
            return findById(find.get(0));
        }
    }

    public void updateParameters(Word word, Connection c) throws SQLException {
        String query = "UPDATE Word SET emotional = ?, formality = ?, vulgarity = ? WHERE wordId = ?";
        
        PreparedStatement ps = c.prepareStatement(query);

        ps.setDouble(1, word.getEmotional());
        ps.setDouble(2, word.getFormality());
        ps.setDouble(3, word.getVulgarity());
        ps.setInt(4, word.getId());
        ps.executeUpdate();
    }

    public void updateLetters(Word word, Connection c) throws SQLException {
        String queryAdd = "INSERT INTO WordsLetters VALUES (?, ?, ?)";
        String queryDelete = "DELETE FROM WordsLetters WHERE wordLId = ?";
        int rows = 0;

        PreparedStatement ps = c.prepareStatement(queryDelete);
        ps.setInt(1, word.getId());
        rows += ps.executeUpdate();

        PreparedStatement ps2 = c.prepareStatement(queryAdd);
        for(int i = 0; i < word.getLetters().size(); i++){
            ps2.setInt(1, word.getId());
            ps2.setInt(2, i + 1);
            ps2.setInt(3, word.getLetterIds().get(i));
            ps2.addBatch();
        }
        rows += ps2.executeBatch().length;

        System.out.println(rows + " rows updated");
    }

    public void updateDefinitions(Word word, Connection c) throws SQLException {
        String queryDelete = "DELETE FROM Definition WHERE dWordId = ?";
        String queryAdd = "INSERT INTO Definition VALUES (?, ?)";
        int rows = 0;

        PreparedStatement ps = c.prepareStatement(queryDelete);
        ps.setInt(1, word.getId());
        rows += ps.executeUpdate();

        PreparedStatement ps2 = c.prepareStatement(queryAdd);
        for(String s : word.getDefinitions()){
            ps2.setInt(1, word.getId());
            ps2.setString(2, s);
            ps2.addBatch();
        }
        rows += ps2.executeBatch().length;

        System.out.println(rows + " rows updated");
    }

    public void updateTranslations(Word word, Connection c) throws SQLException {
        String queryDelete = "DELETE FROM Translation WHERE tWordId = ?";
        String queryAdd = "INSERT INTO Translation (tWordId, translation) VALUES (?, ?)";
        int rows = 0;

        PreparedStatement ps = c.prepareStatement(queryDelete);
        ps.setInt(1, word.getId());
        rows += ps.executeUpdate();

        PreparedStatement ps2 = c.prepareStatement(queryAdd);
        for(String s : word.getTranslations()){
            ps2.setInt(1, word.getId());
            ps2.setString(2, s);
            ps2.addBatch();
        }
        rows += ps2.executeBatch().length;
        System.out.println(rows + " rows updated");
    }

    public void updateRoots(Word word, Connection c) throws SQLException {
        String queryDelete = "DELETE FROM UsedRoots WHERE roWordId = ?";
        String queryAdd = "INSERT INTO UsedRoots VALUES (?, ?)";
        int rows = 0;

        PreparedStatement ps = c.prepareStatement(queryDelete);
        ps.setInt(1, word.getId());
        rows += ps.executeUpdate();

        PreparedStatement ps2 = c.prepareStatement(queryAdd);
        for(Integer r : word.getRootIds()){
            ps2.setInt(1, word.getId());
            ps2.setInt(2, r);
            ps2.addBatch();
        }
        rows += ps2.executeBatch().length;
        System.out.println(rows + " rows updated");
    }

    public void updateLinks(Word word, Connection c) throws SQLException {
        String deleteLinkQuery = "DELETE FROM Link WHERE (lWordId = ? AND linkedWordId = ?) OR (linkedWordId = ? AND lWordId = ?)";
        String insertLinkQuery = "INSERT INTO Link (lWordId, linkedWordId) VALUES (?, ?)";
        int rows = 0;

        // Récupérer les anciens liens
        Set<Integer> oldLinkIds = findById(word.getId()).getLinkIds();

        Set<Integer> newLinkIds = word.getLinkIds();

        // Déterminer les liens à ajouter et à supprimer
        Set<Integer> linksToAdd = new HashSet<>(newLinkIds);
        linksToAdd.removeAll(oldLinkIds); // Nouveaux liens

        Set<Integer> linksToRemove = new HashSet<>(oldLinkIds);
        linksToRemove.removeAll(newLinkIds); // Liens obsolètes

        // Suppression des liens (dans les deux sens)
        try (PreparedStatement psDelete = c.prepareStatement(deleteLinkQuery)) {
            for (int linkId : linksToRemove) {
                // essai dans le premier sens
                psDelete.setInt(1, word.getId());
                psDelete.setInt(2, linkId);

                // essai dans l'autre sens
                psDelete.setInt(3, word.getId());
                psDelete.setInt(4, linkId);
                
                psDelete.addBatch();
            }
            rows += psDelete.executeBatch().length;
        }

        // Ajout des nouveaux liens (dans les deux sens)
        try (PreparedStatement psInsert = c.prepareStatement(insertLinkQuery)) {
            for (int linkId : linksToAdd) {
                // Insertion lien direct
                psInsert.setInt(1, word.getId());
                psInsert.setInt(2, linkId);
                psInsert.addBatch();
            }
            rows += psInsert.executeBatch().length;
        }
        System.out.println(rows + " rows updated");
    }

    public void updateTypes(Word word, Connection c) throws SQLException {
        String queryDelete = "DELETE FROM WordsTypes WHERE tyWordId = ?";
        String queryAdd = "INSERT INTO WordsTypes VALUES (?, ?)";
        int rows = 0;
        PreparedStatement ps = c.prepareStatement(queryDelete);
        ps.setInt(1, word.getId());
        rows += ps.executeUpdate();

        PreparedStatement ps2 = c.prepareStatement(queryAdd);
        for(Integer t : word.getTypeIds()){
            ps2.setInt(1, word.getId());
            ps2.setInt(2, t);
            ps2.addBatch();
        }
        rows += ps2.executeBatch().length;
        System.out.println(rows + " rows updated");
    }

    @Override
    public void delete(Word word) throws SQLException{
        String query = "DELETE FROM Word WHERE wordId = ?";
        String queryLetters = "DELETE FROM WordsLetters WHERE wordLId = ?";
        String queryDef = "DELETE FROM Definition WHERE dWordId = ?";
        String queryTrans = "DELETE FROM Translation WHERE tWordId = ?";
        String queryRoot = "DELETE FROM UsedRoots WHERE roWordId = ?";
        String queryLink = "DELETE FROM Link WHERE lWordId = ? OR linkedWordId = ?";
        String queryType = "DELETE FROM WordsTypes WHERE tyWordId = ?";

        int rows = 0;

        try(Connection c = getConnection()){
            c.setAutoCommit(false);
            try{
                PreparedStatement ps = c.prepareStatement(queryType);
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();

                PreparedStatement ps2 = c.prepareStatement(queryLink);
                ps2.setInt(1, word.getId());
                ps2.setInt(2, word.getId());
                rows += ps2.executeUpdate();

                
                PreparedStatement ps3 = c.prepareStatement(queryDef);
                ps3.setInt(1, word.getId());
                rows += ps3.executeUpdate();
                
                PreparedStatement ps4 = c.prepareStatement(queryTrans);
                ps4.setInt(1, word.getId());
                rows += ps4.executeUpdate();

                PreparedStatement ps5 = c.prepareStatement(queryRoot);
                ps5.setInt(1, word.getId());
                rows += ps5.executeUpdate();
                
                PreparedStatement ps6 = c.prepareStatement(queryLetters);
                ps6.setInt(1, word.getId());
                rows += ps6.executeUpdate();
                
                PreparedStatement ps7 = c.prepareStatement(query);
                ps7.setInt(1, word.getId());
                rows += ps7.executeUpdate();

                c.commit();
            }catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
        

        System.out.println(rows + " rows deleted");
    }

    @Override
    public void update(Word word) throws SQLException{
        try (Connection c = getConnection()) {
            c.setAutoCommit(false);
            try{
                updateParameters(word,c);
                updateLetters(word, c);
                updateDefinitions(word, c);
                updateTranslations(word, c);
                updateRoots(word, c);
                updateLinks(word, c);
                updateTypes(word, c);
                c.commit();
            }catch (SQLException e){
                c.rollback();
                throw e;
            }
        }
    }

    @Override
    public int create(Word word) throws SQLException{
        String query = "INSERT INTO Word (wordId, emotional, formality, vulgarity, isUsable) VALUES (?, ?, ?, ?, ?)";
        String queryLetters = "INSERT INTO WordsLetters (wordLid, position, letterWId) VALUES (?, ?, ?)";
        String queryDef = "INSERT INTO Definition (dWordId, def) VALUES (?, ?)";
        String queryTrans = "INSERT INTO Translation (tWordId, translation) VALUES (?, ?)";
        String queryLink = "INSERT INTO Link (lWordId, linkedWordId) VALUES (?, ?)";
        String queryRoot = "INSERT INTO UsedRoots (roWordId, usedRootId) VALUES (?, ?)";
        String queryType = "INSERT INTO WordsTypes (tyWordId, wordTypeId) VALUES (?, ?)";

        int rows = 0;
        int retId = -1;

        try(Connection c = getConnection()) {
            c.setAutoCommit(false);
            try{
                int wordId = nextId("Word", "wordId");

                PreparedStatement ps = c.prepareStatement(query);
                ps.setInt(1, wordId);
                ps.setDouble(2, word.getEmotional());
                ps.setDouble(3, word.getFormality());
                ps.setDouble(4, word.getVulgarity());
                ps.setBoolean(5, word.isUsable());
                rows += ps.executeUpdate();

                PreparedStatement ps2 = c.prepareStatement(queryLetters);
                for (int i = 0; i < word.getLetterIds().size(); i++) {
                    ps2.setInt(1, wordId);
                    ps2.setInt(2, i+1);
                    ps2.setInt(3, word.getLetterIds().get(i));
                    ps2.addBatch();
                }
                rows += ps2.executeBatch().length;

                PreparedStatement ps3 = c.prepareStatement(queryDef);
                for (String d : word.getDefinitions()) {
                    ps3.setInt(1, wordId);
                    ps3.setString(2, d);
                    ps3.addBatch();
                }
                rows += ps3.executeBatch().length;

                PreparedStatement ps4 = c.prepareStatement(queryTrans);
                for (String t : word.getTranslations()) {
                    ps4.setInt(1, wordId);
                    ps4.setString(2, t);
                    ps4.addBatch();
                }
                rows += ps4.executeBatch().length;

                PreparedStatement ps5 = c.prepareStatement(queryLink);
                for(Integer l : word.getLinkIds()){
                    ps5.setInt(1, wordId);
                    ps5.setInt(2, l);
                    ps5.addBatch();
                }
                rows += ps5.executeBatch().length;

                PreparedStatement ps6 = c.prepareStatement(queryRoot);
                for(Integer r : word.getRootIds()){
                    ps6.setInt(1, wordId);
                    ps6.setInt(2, r);
                    ps6.addBatch();
                }
                rows += ps6.executeBatch().length;

                PreparedStatement ps7 = c.prepareStatement(queryType);
                for(Integer t : word.getTypeIds()){
                    ps7.setInt(1, wordId);
                    ps7.setInt(2, t);
                    ps7.addBatch();
                }
                rows += ps7.executeBatch().length;

                c.commit();
                // Only if the commit was successful
                word.setId(wordId);
                retId = wordId;
                System.out.println(rows + " rows inserted");
            }catch (SQLException e){
                c.rollback();
                throw e;
            }
        }
        return retId;
    }
}
