package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.persistance.Letter;

public  class LetterDAO extends DAO<Letter> {

    public LetterDAO() {
        super();
    }

    @Override
    public ArrayList<Letter> findAll() {
        ArrayList<Letter> ret = new ArrayList<>();
        String query = "SELECT * FROM Letter";

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)) {

            while(rs.next()){
                Letter letter = new Letter(rs.getString("letter"), rs.getString("letterAscii"));
                ret.add(letter);
            }

        }catch(SQLException e){
            System.err.println("LetterDAO findAll: " + e.getMessage());
        }
    
        return ret;
    }

    @Override
    public Letter findById(int id){
        Letter ret = null;
        String query = "SELECT * FROM Letter WHERE letterId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                ret = new Letter(rs.getString("letter"), rs.getString("letterAscii"));
                ret.setId(id);
            }

        }catch(SQLException e){
            System.err.println("LetterDAO findById: " + e.getMessage());
        }
    
        return ret;
    }

    @Override
    public void delete(Letter letter){
        String query = "DELETE FROM Letter WHERE letterId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            
            ps.setInt(1, letter.getId());
            ps.executeUpdate();

        }catch(SQLException e){
            System.err.println("LetterDAO delete: " + e.getMessage());
        }
    }

    @Override
    public void update(Letter letter){
        String query = "UPDATE Letter SET letter = ?, letterAscii = ? WHERE letterId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            
            ps.setString(1, letter.getCharacter());
            ps.setString(2, letter.getCharacterAscii());
            ps.setInt(3, letter.getId());
            ps.executeUpdate();

        }catch(SQLException e){
            System.err.println("LetterDAO update: " + e.getMessage());
        }
    }

    @Override
    public int create(Letter letter){
        /*int id = getNe
        String query = "INSERT INTO Letter VALUES (?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            int id = 
            ps.setInt(1, getRowsCount("Letter") + 1);
            ps.setString(2, letter.getCharacter());
            ps.setString(3, letter.getCharacterAscii());
            ps.executeUpdate();

        } catch(SQLException e){
            System.err.println("LetterDAO create: " + e.getMessage());
        }*/

        return 0;
    }
}
