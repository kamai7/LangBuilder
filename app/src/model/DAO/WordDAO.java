package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.persistance.Letter;
import model.persistance.Root;
import model.persistance.Word;
import model.util.Lists;

public class WordDAO extends DAO<Word> {
    
    public WordDAO() {
        super();
    }

    @Override
    public HashSet<Word> findAll() {
    
        HashSet<Word> ret = new HashSet<>();
        String query = "SELECT * FROM Word";

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)) {

            while(rs.next()){

                int id = rs.getInt("wordId");
                Word word = findById(id);
                ret.add(word);
            }

        }catch(SQLException e){
            System.err.println("WordDAO findAll: " + e.getMessage());
        }
        return ret;
    }

    @Override
    public int findId(Word word){

        int ret = -1;
        ArrayList<Integer> find = new ArrayList<Integer>();
        String query = "SELECT wordLId FROM WordsLetters WHERE position = ? AND letterWId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            for(int i = 0; i < word.getWord().length; i++){
                try {
                    ps.setInt(1, i + 1);
                    ps.setInt(2, word.getWord()[i].getId());
                    ResultSet rs = ps.executeQuery();

                    ArrayList<Integer> resQuery = new ArrayList<Integer>();

                    while(rs.next()){
                        resQuery.add(rs.getInt("wordLId"));
                    }

                    if(find.size() == 0){
                        find = new ArrayList<>(resQuery);
                    }else{
                        find = Lists.intersect(find, resQuery);
                    }
                } catch (SQLException e) {
                    System.err.println("findAll: " + e.getMessage());
                }
            }
            
            if (find.size() != 0){
                ret = find.get(0);
            }

        }catch(SQLException e){
            System.err.println("WordDAO findId: " + e.getMessage());
        }
    
        return ret;
    }

    public Word findById(int id){
        Word ret = null;
        String query = "SELECT * FROM Word WHERE WordId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Letter[] letters = findLettersByID(id);
                ret = new Word(letters, rs.getDouble("emotional"), rs.getDouble("complexity"), rs.getDouble("formality"), rs.getDouble("vulgarity"));
            }

        }catch(SQLException e){
            System.err.println("WordDAO findById: " + e.getMessage());
        }
    
        return ret;
    }

    public Word findByRoot(Root root){
        Word ret = null;
        String query = "SELECT * FROM Root WHERE rootId = " + root.getId();

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)){

            if(rs.next()){
                int wordId = rs.getInt("rWordId");
                ret = findById(wordId);
                ret.updateValues();
            }

        }catch(SQLException e){
            System.err.println("WordDAO findByRoot: " + e.getMessage());
        }
    
        return ret;
    }


    public Letter[] findLetters(Word word){
        Letter[] ret = null;

        String query = "SELECT * FROM WordsLetters WHERE wordLId = " + word.getId();

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)){

            ret = new Letter[rs.getRow()];
            Map<Integer,Letter> temp = new HashMap<>();

            while (rs.next()){
                LetterDAO letterDAO = new LetterDAO();
                Letter l = letterDAO.findById(rs.getInt("letterWId"));
                temp.put(rs.getInt("position"),l);
            }

            for (int i = 0; i < ret.length; i++) {
                ret[i] = temp.get(i);
            }

        }catch(SQLException e){
            System.err.println("WordDAO findLetters: " + e.getMessage());
        }
    
        return ret;
    }

    public ArrayList<Word> findLinks(Word word){
        ArrayList<Word> ret = new ArrayList<>();
        
        String query = "SELECT * FROM Links WHERE lWordId = " + word.getId();

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)){

            while (rs.next()){
                Word w = findById(rs.getInt("linkedWordId"));
                w.updateValues();
                ret.add(w);
            }

        }catch(SQLException e){
            System.err.println("WordDAO findLinks: " + e.getMessage());
        }
    
        return ret;
    }

    public Letter[] findLettersByID(int id){
        Letter[] ret = null;

        String query = "SELECT * FROM WordsLetters WHERE wordLId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Integer,Letter> temp = new HashMap<>();

            while (rs.next()){
                LetterDAO letterDAO = new LetterDAO();
                Letter l = letterDAO.findById(rs.getInt("letterWId"));
                temp.put(rs.getInt("position"),l);
            }
            ret = new Letter[temp.size()];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = temp.get(i+1);
            }

        }catch(SQLException e){
            System.err.println("WordDAO findLettersByID: " + e.getMessage());
        }
    
        return ret;
    }

    public ArrayList<String> findDefinitions(Word word){
        ArrayList<String> ret = new ArrayList<>();
        
        String query = "SELECT * FROM Def WHERE dWordId = " + word.getId();

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)){

            while (rs.next()){
                ret.add(rs.getString("def"));
            }

        }catch(SQLException e){
            System.err.println("WordDAO findDefinitions: " + e.getMessage());
        }
    
        return ret;
    }

    public ArrayList<String> findTranslations(Word word){
        ArrayList<String> ret = new ArrayList<>();
        
        String query = "SELECT * FROM Translation WHERE tWordId = " + word.getId();

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)){
            

            while (rs.next()){
                ret.add(rs.getString("translation"));
            }

        }catch(SQLException e){
            System.err.println("WordDAO findTranslations: " + e.getMessage());
        }
    
        return ret;
    }

    public ArrayList<Root> findRoots(Word word){
        ArrayList<Root> ret = new ArrayList<>();
        
        String query = "SELECT * FROM UsedRoots WHERE roWordId = " + word.getId();

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)){

            while (rs.next()){
                RootDAO rootDAO = new RootDAO();
                Root r = rootDAO.findById(rs.getInt("usedRootId"));
                r.updateValues();
                ret.add(r);
            }

        }catch(SQLException e){
            System.err.println("WordDAO findRoots: " + e.getMessage());
        }
    
        return ret;
    }

    public ArrayList<Word> findRadicals(Word word){
        ArrayList<Word> ret = new ArrayList<>();
        
        String query = "SELECT * FROM Radicals WHERE raWordId = " + word.getId();

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)){

            while (rs.next()){
                Word w = findById(rs.getInt("radicalId"));
                w.updateValues();
                ret.add(w);
            }

        }catch(SQLException e){
            System.err.println("findRadicals: " + e.getMessage());
        }
    
        return ret;
    }

    public void updateParameters(Word word){
        String query = "UPDATE Word SET emotional = ?, complexity = ? WHERE wordId = ?";
        
        int rows = 0;

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){

            ps.setDouble(1, word.getEmotional());
            ps.setDouble(2, word.getComplexity());
            ps.setInt(3, word.getId());
            rows += ps.executeUpdate();
            System.out.println(rows + " rows updated");
        }catch(SQLException e){
            System.err.println("WordDAO updateParameters: invalid parameters\n" + e.getMessage());
        }
    }

    public void updateLetters(Word word){
        String queryAdd = "INSERT INTO WordsLetters VALUES (?, ?, ?)";
        String queryDelete = "DELETE FROM WordsLetters WHERE wordLId = ?";
        int rows = 0;
        try(Connection c = getConnection()){

            try(PreparedStatement ps = c.prepareStatement(queryDelete)){
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }catch(SQLException e){
                System.err.println("WordDAO updateLetters: invalid id\n" + e.getMessage());
            }

            try(PreparedStatement ps = c.prepareStatement(queryAdd)){
                for(int i = 0; i < word.getWord().length; i++){
                    ps.setInt(1, word.getId());
                    ps.setInt(2, i + 1);
                    ps.setInt(3, word.getWord()[i].getId());
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch(SQLException e){
                System.err.println("WordDAO updateLetters: invalid letters\n" + e.getMessage());
            }
            System.out.println(rows + " rows updated");
        }catch(SQLException e){
            System.err.println("WordDAO updateLetters: invalid parameters\n" + e.getMessage());
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
                System.err.println("WordDAO updateDefinitions: invalid id\n" + e.getMessage());
            }

            try(PreparedStatement ps = c.prepareStatement(queryAdd)){
                for(String s : word.getDefinitions()){
                    ps.setInt(1, word.getId());
                    ps.setString(2, s);
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch(SQLException e){
                System.err.println("WordDAO updateDefinitions: invalid definitions\n" + e.getMessage());
            }
            System.out.println(rows + " rows updated");
        }catch (SQLException e){
            System.err.println("WordDAO updateDefinitions: invalid parameters\n" + e.getMessage());
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
                System.err.println("WordDAO updateTranslations: invalid id\n" + e.getMessage());
            }

            try(PreparedStatement ps = c.prepareStatement(queryAdd)){
                for(String s : word.getTranslations()){
                    ps.setInt(1, word.getId());
                    ps.setString(2, s);
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch(SQLException e){
                System.err.println("WordDAO updateTranslations: invalid translations\n" + e.getMessage());
            }
            System.out.println(rows + " rows updated");
        }catch (SQLException e){
            System.err.println("WordDAO updateTranslations: invalid parameters\n" + e.getMessage());
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
                System.err.println("WordDAO updateRoots: invalid id\n" + e.getMessage());
            }

            try(PreparedStatement ps = c.prepareStatement(queryAdd)){
                for(Word r : word.getRoots()){
                    ps.setInt(1, word.getId());
                    ps.setInt(2, r.getId());
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch(SQLException e){
                System.err.println("WordDAO updateRoots: invalid roots\n" + e.getMessage());
            }
            System.out.println(rows + " rows updated");
        }catch (SQLException e){
            System.err.println("WordDAO updateRoots: invalid parameters\n" + e.getMessage());
        }
    }

    public void updateLinks(Word word) {
        String deleteLinkQuery = "DELETE FROM Links WHERE lWordId = ? AND linkedWordId = ?";
        String insertLinkQuery = "INSERT INTO Links (lWordId, linkedWordId) VALUES (?, ?)";

        try (Connection c = getConnection()) {
            c.setAutoCommit(false); // Démarre une transaction
            int rows = 0;

            // Récupérer les anciens liens
            ArrayList<Word> oldLinks = findLinks(word);

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
            }catch (SQLException e) {
                System.err.println("WordDAO updatelinks remove links: " + e.getMessage());
            }

            // Ajout des nouveaux liens (dans les deux sens)
            try (PreparedStatement psInsert = c.prepareStatement(insertLinkQuery)) {
                for (int linkId : linksToAdd) {
                    // Insertion lien direct
                    psInsert.setInt(1, word.getId());
                    psInsert.setInt(2, linkId);
                    psInsert.addBatch();

                    // Insertion lien inverse
                    psInsert.setInt(1, linkId);
                    psInsert.setInt(2, word.getId());
                    psInsert.addBatch();
                }
                rows += psInsert.executeBatch().length;
            }catch (SQLException e) {
                System.err.println("WordDAO updatelinks add links: " + e.getMessage());
            }

            c.commit();
            System.out.println(rows + " rows updated");
        } catch (SQLException e) {
            System.err.println("WordDAO Transaction failed: " + e.getMessage());
        }
    }

    public void delete(Word word){
        String query = "DELETE FROM Word WHERE wordId = ?";
        String queryWord = "DELETE FROM WordsLetters WHERE wordLId = ?";
        String queryDef = "DELETE FROM Def WHERE dWordId = ?";
        String queryTrans = "DELETE FROM Trans WHERE tWordId = ?";
        String queryRoot = "DELETE FROM Root WHERE rWordId = ?";
        String queryRad = "DELETE FROM Radicals WHERE raWordId = ?";
        String queryLink = "DELETE FROM Links WHERE lWordId = ? AND linkedWordId = ?";

        int rows = 0;

        try(Connection c = getConnection()){
            try(PreparedStatement ps = c.prepareStatement(queryLink)){
                ArrayList<Word> links = findLinks(word);
                for(Word l : links){
                    
                    ps.setInt(1, word.getId());
                    ps.setInt(2, l.getId());
                    ps.addBatch();

                    ps.setInt(1, l.getId());
                    ps.setInt(2, word.getId());
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch (SQLException e){
                System.err.println("WordDAO delete: invalid links\n" + e.getMessage());
            }

            try(PreparedStatement ps = c.prepareStatement(queryRad)){
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }catch(SQLException e){
                System.err.println("WordDAO delete: invalid radicals\n" + e.getMessage());
            }
            
            try(PreparedStatement ps = c.prepareStatement(queryDef)){
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }catch(SQLException e){
                System.err.println("WordDAO delete: invalid definitions\n" + e.getMessage());
            }
            try(PreparedStatement ps = c.prepareStatement(queryTrans)){
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }catch(SQLException e){
                System.err.println("WordDAO delete: invalid translations\n" + e.getMessage());
            }
            try(PreparedStatement ps = c.prepareStatement(queryRoot)){
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }catch(SQLException e){
                System.err.println("WordDAO delete: invalid roots\n" + e.getMessage());
            }
            try(PreparedStatement ps = c.prepareStatement(queryWord)){
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }catch(SQLException e){
                System.err.println("WordDAO delete: invalid word\n" + e.getMessage());
            }
            try(PreparedStatement ps = c.prepareStatement(query)){
                ps.setInt(1, word.getId());
                rows += ps.executeUpdate();
            }catch(SQLException e){
                System.err.println("WordDAO delete: invalid id\n" + e.getMessage());
            }
        }catch (SQLException e){
            System.err.println("WordDAO delete: invalid parameters\n" + e.getMessage());
        }

        System.out.println(rows + " rows deleted");
    }

    public void create(Word word){
        String query = "INSERT INTO Word VALUES (?, ?, ?, ?, ?)";
        String queryWord = "INSERT INTO WordsLetters VALUES (?, ?, ?)";
        String queryDef = "INSERT INTO Def VALUES (?, ?)";
        String queryTrans = "INSERT INTO Translation VALUES (?, ?)";
        String queryRad = "INSERT INTO Radicals VALUES (?, ?)";
        String queryLink = "INSERT INTO Links VALUES (?, ?)";

        int rows = 0;

        int wordId = getRowsCount("Word") + 1;

        try(Connection c = getConnection()){
            try(PreparedStatement ps = c.prepareStatement(query)){
                ps.setInt(1, wordId);
                ps.setDouble(2, word.getEmotional());
                ps.setDouble(3, word.getComplexity());
                ps.setDouble(4, word.getFormality());
                ps.setDouble(5, word.getVulgarity());
                rows += ps.executeUpdate();
            }catch (SQLException e){
                System.err.println("WordDAO create: invalid word\n" + e.getMessage());
            }

            try(PreparedStatement ps = c.prepareStatement(queryWord)){
                for(int i = 0; i < word.getWord().length; i++){
                    ps.setInt(1, wordId);
                    ps.setInt(2, i+1);
                    ps.setInt(3, word.getWord()[i].getId());
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch (SQLException e){
                System.err.println("WordDAO create: invalid letters\n" + e.getMessage());
            }

            try(PreparedStatement ps = c.prepareStatement(queryDef)){
                for(String d : word.getDefinitions()){
                    ps.setInt(1, wordId);
                    ps.setString(2, d);
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch (SQLException e){
                System.err.println("WordDAO create: invalid definitions\n" + e.getMessage());
            }

            try(PreparedStatement ps = c.prepareStatement(queryTrans)){
                for(String t : word.getTranslations()){
                    ps.setInt(1, wordId);
                    ps.setString(2, t);
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch (SQLException e){
                System.err.println("WordDAO create: invalid translations\n" + e.getMessage());
            }

            try(PreparedStatement ps = c.prepareStatement(queryRad)){
                for(Word r : word.getRadicals()){
                    ps.setInt(1, wordId);
                    ps.setInt(2, r.getId());
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch (SQLException e){
                System.err.println("WordDAO create: invalid radicals\n" + e.getMessage());
            }

            try(PreparedStatement ps = c.prepareStatement(queryLink)){
                ArrayList<Word> links = findLinks(word);
                for(Word l : links){
                    
                    ps.setInt(1, wordId);
                    ps.setInt(2, l.getId());
                    ps.addBatch();

                    ps.setInt(1, l.getId());
                    ps.setInt(2, word.getId());
                    ps.addBatch();
                }
                rows += ps.executeBatch().length;
            }catch (SQLException e){
                System.err.println("WordDAO create: invalid links\n" + e.getMessage());
            }
        }catch (SQLException e){
            System.err.println("WordDAO create: invalid parameters\n" + e.getMessage());
        }
        word.updateValues();

        System.out.println(rows + " rows inserted");
    }

    public boolean isIn(Word word){
        String query = "SELECT * FROM Word WHERE wordId = ?";

        boolean ret = false;

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)) {

            if (rs.next()){
                ret = true;
            }

        }catch (SQLException e){
            System.err.println("WordDAO isIn: invalid parameters\n" + e.getMessage());
        }

        return ret;
    }
}
