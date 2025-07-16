package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import model.persistance.Letter;
import model.persistance.Type;
import model.persistance.Word;
import model.util.Colors;

public class WordDAO extends DAO<Word> {
    @Override
    public ArrayList<Word> findAll() {
        ArrayList<Word> ret = new ArrayList<>();
        String query = "SELECT wordId FROM Word";

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

    /**
     * Finds a Word by its ID without loading all its details.
     * This method retrieves only the emotional, formality, and vulgarity attributes.
     * @param id The ID of the Word to find.
     * @return A Word object with the specified ID and its shallow attributes, or null if not found.
     */
    private Word findShallow(int id) {
        Word ret = null;
        String query = "SELECT * FROM Word WHERE wordId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double emotional = rs.getDouble("emotional");
                double formality = rs.getDouble("formality");
                double vulgarity = rs.getDouble("vulgarity");
                ret = new Word(new ArrayList<>(), emotional, formality, vulgarity);
                ret.setId(id);
            }
        } catch(SQLException e) {
            System.err.println(Colors.error("WordDAO findShallow: ", e.getMessage()));
        }

        return ret;
    }

    @Override
    public Word findById(int id){
        Word ret = null;
        String query = "SELECT * FROM Word WHERE wordId = ?;" +
                       "SELECT * FROM WordsLetters WHERE wordLId = ?;" +
                       "SELECT * FROM Definition WHERE dWordId = ?;" +
                       "SELECT * FROM Translation WHERE tWordId = ?;" +
                       "SELECT * FROM UsedRoots WHERE roWordId = ?;" +
                       "SELECT * FROM Link WHERE lWordId = ? OR linkedWordId = ?;" +
                       "SELECT * FROM WordsTypes WHERE tyWordId = ?;";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            // Set the parameters for the prepared statement
            for (int i = 1; i <= 8; i++) {
                ps.setInt(i, id);
            }

            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            
            if (rs.next()) {
                double emotional = rs.getDouble("emotional");
                double formality = rs.getDouble("formality");
                double vulgarity = rs.getDouble("vulgarity");
                
                ArrayList<Letter> letters = new ArrayList<>();
                ArrayList<String> definitions = new ArrayList<>();
                ArrayList<String> translations = new ArrayList<>();
                HashSet<Word> roots = new HashSet<>();
                HashSet<Word> links = new HashSet<>();
                HashSet<Type> types = new HashSet<>();

                // Get the letters
                ps.getMoreResults();
                rs = ps.getResultSet();
                LetterDAO letterDAO = new LetterDAO();
                while (rs.next()) {
                    Letter letter = letterDAO.findById(rs.getInt("letterWId"));
                    letters.add(letter);
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
                    roots.add(findShallow(rs.getInt("roWordId")));
                }

                // Get the links
                ps.getMoreResults();
                rs = ps.getResultSet();
                while (rs.next()) {
                    links.add(findShallow(rs.getInt("linkedWordId")));
                }

                // Get the types
                ps.getMoreResults();
                rs = ps.getResultSet();
                while (rs.next()) {
                    types.add(new Type(rs.getString("type")));
                }

                ret = new Word(letters, emotional, formality, vulgarity, translations, definitions, links, roots, types);
                ret.setId(id);
            }
        } catch(SQLException e) {
            System.err.println(Colors.error("WordDAO findById: ", e.getMessage()));
        }
    
        return ret;
    }

    public Word findByLetters(Letter[] letters){
        ArrayList<Integer> find = null;
        String query = "SELECT wordLId FROM WordsLetters WHERE position = ? AND letterWId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            for (int i = 0; i < letters.length; i++) {
                ps.setInt(1, i+1);
                ps.setInt(2, letters[i].getId());
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
            System.err.println(Colors.error("WordDAO findByLetters: ", e.getMessage()));
        }

        if (find == null || find.isEmpty()) {
            return null;
        } else {
            return findById(find.get(0));
        }
    }

    public void updateParameters(Word word) {
        String query = "UPDATE Word SET emotional = ?, formality = ?, vulgarity = ? WHERE wordId = ?";
        
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {

            ps.setDouble(1, word.getEmotional());
            ps.setDouble(2, word.getFormality());
            ps.setDouble(3, word.getVulgarity());
            ps.setInt(4, word.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(Colors.error("WordDAO updateParameters: invalid parameters\n", e.getMessage()));
        }
    }

    public void updateLetters(Word word) {
        String queryAdd = "INSERT INTO WordsLetters VALUES (?, ?, ?)";
        String queryDelete = "DELETE FROM WordsLetters WHERE wordLId = ?";
        int rows = 0;

        try(Connection c = getConnection()){
            try (PreparedStatement ps = c.prepareStatement(queryDelete)) {
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            } catch(SQLException e) {
                System.err.println(Colors.error("WordDAO updateLetters: invalid id\n", e.getMessage()));
            }

            try (PreparedStatement ps = c.prepareStatement(queryAdd)) {
                for(int i = 0; i < word.getLetters().size(); i++){
                    ps.setInt(1, word.getId());
                    ps.setInt(2, i + 1);
                    ps.setInt(3, word.getLetters().get(i).getId());
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            } catch(SQLException e) {
                System.err.println(Colors.error("WordDAO updateLetters: invalid letters\n", e.getMessage()));
            }
            System.out.println(rows + " rows updated");
        } catch(SQLException e) {
            System.err.println(Colors.error("WordDAO updateLetters: invalid parameters\n", e.getMessage()));
        }
    }

    public void updateDefinitions(Word word){
        String queryDelete = "DELETE FROM Def WHERE dWordId = ?";
        String queryAdd = "INSERT INTO Def VALUES (?, ?)";
        int rows = 0;
        try(Connection c = getConnection()){

            try(PreparedStatement ps = c.prepareStatement(queryDelete)){
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }catch(SQLException e){
                System.err.println(Colors.error("WordDAO updateDefinitions: invalid id\n", e.getMessage()));
            }

            try(PreparedStatement ps = c.prepareStatement(queryAdd)){
                for(String s : word.getDefinitions()){
                    ps.setInt(1, word.getId());
                    ps.setString(2, s);
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch(SQLException e){
                System.err.println(Colors.error("WordDAO updateDefinitions: invalid definitions\n", e.getMessage()));
            }
            System.out.println(rows + " rows updated");
        }catch (SQLException e){
            System.err.println(Colors.error("WordDAO updateDefinitions: invalid parameters\n", e.getMessage()));
        }
    }

    public void updateTranslations(Word word){
        String queryDelete = "DELETE FROM Translation WHERE tWordId = ?";
        String queryAdd = "INSERT INTO Translation VALUES (?, ?)";
        int rows = 0;
        try(Connection c = getConnection()){

            try(PreparedStatement ps = c.prepareStatement(queryDelete)){
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }catch(SQLException e){
                System.err.println(Colors.error("WordDAO updateTranslations: invalid id\n", e.getMessage()));
            }

            try(PreparedStatement ps = c.prepareStatement(queryAdd)){
                for(String s : word.getTranslations()){
                    ps.setInt(1, word.getId());
                    ps.setString(2, s);
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch(SQLException e){
                System.err.println(Colors.error("WordDAO updateTranslations: invalid translations\n", e.getMessage()));
            }
            System.out.println(rows + " rows updated");
        }catch (SQLException e){
            System.err.println(Colors.error("WordDAO updateTranslations: invalid parameters\n", e.getMessage()));
        }
    }

    public void updateRoots(Word word){
        String queryDelete = "DELETE FROM UsedRoots WHERE roWordId = ?";
        String queryAdd = "INSERT INTO UsedRoots VALUES (?, ?)";
        int rows = 0;
        try(Connection c = getConnection()){

            try(PreparedStatement ps = c.prepareStatement(queryDelete)){
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }catch(SQLException e){
                System.err.println(Colors.error("WordDAO updateRoots: invalid id\n", e.getMessage()));
            }

            try(PreparedStatement ps = c.prepareStatement(queryAdd)){
                for(Word r : word.getRoots()){
                    ps.setInt(1, word.getId());
                    ps.setInt(2, r.getId());
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch(SQLException e){
                System.err.println(Colors.error("WordDAO updateRoots: invalid roots\n", e.getMessage()));
            }
            System.out.println(rows + " rows updated");
        }catch (SQLException e){
            System.err.println(Colors.error("WordDAO updateRoots: invalid parameters\n", e.getMessage()));
        }
    }

    public void updateLinks(Word word) {
        String deleteLinkQuery = "DELETE FROM Link WHERE lWordId = ? AND linkedWordId = ?";
        String insertLinkQuery = "INSERT INTO Link (lWordId, linkedWordId) VALUES (?, ?)";

        try (Connection c = getConnection()) {
            c.setAutoCommit(false); // Démarre une transaction
            int rows = 0;

            // Récupérer les anciens liens
            Set<Word> oldLinks = findById(word.getId()).getLinks();

            // Préparer les sets pour comparaison
            Set<Integer> oldLinkIds = new HashSet<>();
            for (Word w : oldLinks) {
                oldLinkIds.add(w.getId());
            }

            Set<Integer> newLinkIds = new HashSet<>();
            for (Word w : word.getLinks()) {
                newLinkIds.add(w.getId());
            }

            // Déterminer les liens à ajouter et à supprimer
            Set<Integer> linksToAdd = new HashSet<>(newLinkIds);
            linksToAdd.removeAll(oldLinkIds); // Nouveaux liens

            Set<Integer> linksToRemove = new HashSet<>(oldLinkIds);
            linksToRemove.removeAll(newLinkIds); // Liens obsolètes

            // Suppression des liens (dans les deux sens)
            try (PreparedStatement psDelete = c.prepareStatement(deleteLinkQuery)) {
                for (int linkId : linksToRemove) {
                    // Suppression lien direct
                    psDelete.setInt(1, word.getId());
                    psDelete.setInt(2, linkId);
                    psDelete.addBatch();

                    // Suppression lien inverse
                    psDelete.setInt(1, linkId);
                    psDelete.setInt(2, word.getId());
                    psDelete.addBatch();
                }
                rows += psDelete.executeBatch().length;
            } catch (SQLException e) {
                System.err.println(Colors.error("WordDAO updatelinks remove links: " , e.getMessage()));
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
            } catch (SQLException e) {
                System.err.println(Colors.error("WordDAO updatelinks add links: ", e.getMessage()));
            }

            c.commit();
            System.out.println(rows + " rows updated");
        } catch (SQLException e) {
            System.err.println(Colors.error("WordDAO Transaction failed: ", e.getMessage()));
        }
    }

    @Override
    public void delete(Word word){
        String query = "DELETE FROM Word WHERE wordId = ?";
        String queryLetters = "DELETE FROM WordsLetters WHERE wordLId = ?";
        String queryDef = "DELETE FROM Definition WHERE dWordId = ?";
        String queryTrans = "DELETE FROM Translation WHERE tWordId = ?";
        String queryRoot = "DELETE FROM UsedRoots WHERE roWordId = ?";
        String queryLink = "DELETE FROM Link WHERE lWordId = ? OR linkedWordId = ?";

        int rows = 0;

        try(Connection c = getConnection()){
            try (PreparedStatement ps = c.prepareStatement(queryLink)) {
                ps.setInt(1, word.getId());
                ps.setInt(2, word.getId());
                rows += ps.executeUpdate();
            }
            
            try (PreparedStatement ps = c.prepareStatement(queryDef)) {
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }
            
            try (PreparedStatement ps = c.prepareStatement(queryTrans)) {
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement(queryRoot)) {
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }
            
            try (PreparedStatement ps = c.prepareStatement(queryLetters)) {
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }
            
            try (PreparedStatement ps = c.prepareStatement(query)) {
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            System.err.println(Colors.error("WordDAO delete: invalid parameters\n", e.getMessage()));
        }

        System.out.println(rows + " rows deleted");
    }

    @Override
    public void update(Word word) {
        updateParameters(word);
        updateLetters(word);
        updateDefinitions(word);
        updateTranslations(word);
        updateRoots(word);
        updateLinks(word);
    }

    @Override
    public int create(Word word) {
        String query = "INSERT INTO Word (wordId, emotional, formality, vulgarity, isUsable) VALUES (?, ?, ?, ?, ?)";
        String queryLetters = "INSERT INTO WordsLetters (wordLid, position, letterWId) VALUES (?, ?, ?)";
        String queryDef = "INSERT INTO Definition (dWordId, def) VALUES (?, ?)";
        String queryTrans = "INSERT INTO Translation (tWordId, translation) VALUES (?, ?)";
        String queryLink = "INSERT INTO Link (lWordId, linkedWordId) VALUES (?, ?)";

        int rows = 0;
        int retId = -1;

        try(Connection c = getConnection()) {
            c.setAutoCommit(false);
            int wordId = nextId("Word", "wordId");

            try (PreparedStatement ps = c.prepareStatement(query)) {
                ps.setInt(1, wordId);
                ps.setDouble(2, word.getEmotional());
                ps.setDouble(3, word.getFormality());
                ps.setDouble(4, word.getVulgarity());
                ps.setBoolean(5, word.isUsable());
                rows += ps.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement(queryLetters)) {
                for (int i = 0; i < word.getLetters().size(); i++) {
                    ps.setInt(1, wordId);
                    ps.setInt(2, i+1);
                    ps.setInt(3, word.getLetters().get(i).getId());
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }

            try (PreparedStatement ps = c.prepareStatement(queryDef)) {
                for (String d : word.getDefinitions()) {
                    ps.setInt(1, wordId);
                    ps.setString(2, d);
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }

            try (PreparedStatement ps = c.prepareStatement(queryTrans)) {
                for (String t : word.getTranslations()) {
                    ps.setInt(1, wordId);
                    ps.setString(2, t);
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }

            try (PreparedStatement ps = c.prepareStatement(queryLink)) {
                for(Word l : word.getLinks()){
                    ps.setInt(1, wordId);
                    ps.setInt(2, l.getId());
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }

            c.commit();

            // Only if the commit was successful
            word.setId(wordId);
            retId = wordId;
        }
        catch (SQLException e) {
            System.err.println(Colors.error("WordDAO create: invalid parameters", e.getMessage()));
        }

        System.out.println(rows + " rows inserted");
        
        return retId;
    }
}
