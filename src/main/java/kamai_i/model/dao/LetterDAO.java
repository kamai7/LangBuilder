package kamai_i.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kamai_i.model.persistance.Letter;
import kamai_i.utils.Colors;

public  class LetterDAO extends DAO<Letter> {

    @Override
    public ArrayList<Letter> findAll(int limit) {

        if(limit < -1){
            throw new IllegalArgumentException("Limit must be greater than -1.");
        }

        ArrayList<Letter> ret = new ArrayList<>(getRowsCount("Letter"));
        String query;
        if (limit == -1){
            query = "SELECT * FROM Letter ORDER BY char_length(letterAscii), letterAscii ASC";
        }else{
            query = "SELECT * FROM Letter ORDER BY char_length(letterAscii), letterAscii ASC LIMIT " + limit;
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

    public ArrayList<Letter> findByString(String str){
        
        String query = "SELECT * FROM Letter WHERE letter LIKE ? OR letterAscii LIKE ? ORDER BY char_length(letterAscii), letterAscii ASC LIMIT 200";
        ArrayList<Letter> ret = new ArrayList<>();

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
    public void delete(Letter letter) throws SQLException{
        String query = "DELETE FROM Letter WHERE letterId = ?";

         try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            c.setAutoCommit(false);
            
            ps.setInt(1, letter.getId());
            ps.executeUpdate();

            c.commit();
        }
        catch(SQLException e) {
            throw e;
        }
    }

    @Override
    public void update(Letter letter) throws SQLException{
        String query = "UPDATE Letter SET letter = ?, letterAscii = ? WHERE letterId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            c.setAutoCommit(false);
            
            ps.setString(1, letter.getCharacter());
            ps.setString(2, letter.getCharacterAscii());
            ps.setInt(3, letter.getId());
            ps.executeUpdate();

            c.commit();
        }
        catch(SQLException e) {
            throw e;
        }
    }

    @Override
    public int create(Letter letter) throws SQLException{
        int retId = -1; 
        String query = "INSERT INTO Letter (letterId, letter, letterAscii) VALUES (?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            c.setAutoCommit(false);

            int id = nextId("Letter", "letterId");

            ps.setInt(1, id);
            ps.setString(2, letter.getCharacter());
            ps.setString(3, letter.getCharacterAscii());
            ps.executeUpdate();

             // Only if the insert was successful
            letter.setId(id);
            retId = id;

            c.commit();
        }
        catch (SQLException e) {
            throw e;
        }

        return retId;
    }
}
