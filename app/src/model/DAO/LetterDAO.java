package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.persistance.Letter;
import utils.Colors;

public  class LetterDAO extends DAO<Letter> {

    @Override
    public Set<Letter> findAll(int limit) {

        if(limit < -1){
            throw new IllegalArgumentException("Limit must be greater than -1.");
        }

        Set<Letter> ret = new HashSet<>();
        String query;
        if (limit == -1){
            query = "SELECT * FROM Letter ORDER BY letterAscii ASC";
        }else{
            query = "SELECT * FROM Letter ORDER BY letterAscii ASC LIMIT " + limit;
        }

        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while(rs.next()){
                Letter letter = new Letter(rs.getString("letter"), rs.getString("letterAscii"));
                letter.setId(rs.getInt("letterId"));
                ret.add(letter);
            }
        } catch(SQLException e) {
            System.err.println(Colors.error("LetterDAO findAll: ", e.getMessage()));
        }
    
        return ret;
    }

    @Override
    public Set<Letter> findAll() {
        return findAll(-1);
    }

    @Override
    public Letter findById(int id) {
        Letter ret = null;
        String query = "SELECT * FROM Letter WHERE letterId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ret = new Letter(rs.getString("letter"), rs.getString("letterAscii"));
                ret.setId(id);
            }
        } catch(SQLException e) {
            System.err.println(Colors.error("LetterDAO findById: ", e.getMessage()));
        }
    
        return ret;
    }

    @Override
    public Set<Letter> findByString(String str){
        
        String query = "SELECT * FROM Letter WHERE letter LIKE ? OR letterAscii LIKE ? LIMIT 200";
        Set<Letter> ret = new HashSet<>();

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){

            ps.setString(1, "%" + str + "%");
            ps.setString(2, "%" + str + "%");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Letter letter = new Letter(rs.getString("letter"), rs.getString("letterAscii"));
                letter.setId(rs.getInt("letterId"));
                ret.add(letter);
            }
        }
        catch(SQLException e) {
            System.err.println(Colors.error("LetterDAO findByString: ", e.getMessage()));
        }

        return ret;
    }

    @Override
    public void delete(Letter letter) throws SQLIntegrityConstraintViolationException{
        String query = "DELETE FROM Letter WHERE letterId = ?";

         try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setInt(1, letter.getId());
            ps.executeUpdate();
        }
        catch(SQLException e) {
            System.err.println(Colors.error("LetterDAO delete: ", e.getMessage()));
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw (SQLIntegrityConstraintViolationException) e;
            }
        }
    }

    @Override
    public void update(Letter letter) {
        String query = "UPDATE Letter SET letter = ?, letterAscii = ? WHERE letterId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setString(1, letter.getCharacter());
            ps.setString(2, letter.getCharacterAscii());
            ps.setInt(3, letter.getId());
            ps.executeUpdate();
        }
        catch(SQLException e) {
            System.err.println(Colors.error("LetterDAO update: ", e.getMessage()));
        }
    }

    @Override
    public int create(Letter letter) throws SQLIntegrityConstraintViolationException{
        int retId = -1; 
        String query = "INSERT INTO Letter (letterId, letter, letterAscii) VALUES (?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {

            int id = nextId("Letter", "letterId");

            ps.setInt(1, getRowsCount("Letter") + 1);
            ps.setString(2, letter.getCharacter());
            ps.setString(3, letter.getCharacterAscii());
            ps.executeUpdate();

             // Only if the insert was successful
            letter.setId(id);
            retId = id;
        }
        catch (SQLException e) {
            System.err.println(Colors.error("LetterDAO create: " , e.getMessage()));
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw (SQLIntegrityConstraintViolationException) e;
            }
        }

        return retId;
    }
}
