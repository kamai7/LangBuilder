package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import model.persistance.Letter;

public  class LetterDAO extends DAO<Letter> {

    public LetterDAO() {
        super();
    }

    @Override
    public HashSet<Letter> findAll() {
        
        HashSet<Letter> ret = new HashSet<>();
        String query = "SELECT * FROM Letter";

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)) {

            while(rs.next()){
                Letter letter = new Letter(rs.getString("letter"), rs.getString("letterAscii"));
                letter.updateValues();
                ret.add(letter);
            }

        }catch(SQLException e){
            System.err.println("LetterDAO findAll: " + e.getMessage());
        }
    
        return ret;
    }

    @Override
    public int findId(Letter letter){

        int ret = -1;
        String character = letter.getCharacter();
        String characterAscii = letter.getCharacterAscii();

        String query = "SELECT * FROM Letter WHERE letter LIKE ? OR letterAscii LIKE ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            
            ps.setString(1, character);
            ps.setString(2, characterAscii);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                ret = rs.getInt("letterId");
            }

        }catch(SQLException e){
            System.err.println("LetterDAO findId: " + e.getMessage());
        }
    
        return ret;
    }

    public Letter findById(int id){
        Letter ret = null;
        String query = "SELECT * FROM Letter WHERE letterId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                ret = new Letter(rs.getString("letter"), rs.getString("letterAscii"));
                ret.updateValues();
            }

        }catch(SQLException e){
            System.err.println("LetterDAO findById: " + e.getMessage());
        }
    
        return ret;
    }

    public String findCharacter(Letter letter){
        String ret = null;
        String query = "SELECT * FROM Letter WHERE letterId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            
            ps.setInt(1, letter.getId());
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                ret = rs.getString("letter");
            }

        }catch(SQLException e){
            System.err.println("LetterDAO findCharacter: " + e.getMessage());
        }
    
        return ret;
    }

    public String findCharacterAscii(Letter letter){
        String ret = null;
        String query = "SELECT * FROM Letter WHERE letterId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            
            ps.setInt(1, letter.getId());
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                ret = rs.getString("letterAscii");
            }

        }catch(SQLException e){
            System.err.println("LetterDAO findCharacterAscii: " + e.getMessage());
        }
    
        return ret;
    }

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

    public void create(Letter letter){
        String query = "INSERT INTO Letter VALUES (?, ?, ?)";

        try(Connection c = getConnection()){

            try(PreparedStatement ps = c.prepareStatement(query)){
                ps.setInt(1, getRowsCount("Letter") + 1);
                ps.setString(2, letter.getCharacter());
                ps.setString(3, letter.getCharacterAscii());
                ps.executeUpdate();
            }

        }catch(SQLException e){
            System.err.println("LetterDAO create: " + e.getMessage());
        }
    }
}
