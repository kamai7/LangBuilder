package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.persistance.Type;
import model.persistance.Word;

public class TypeDAO extends DAO<Type>{
    
    @Override
    public ArrayList<Type> findAll(){
        ArrayList<Type> ret = new ArrayList<>();

        return ret;
    }

    public Type findByLabel(String label){
        Type ret = null;
        String query = "SELECT typeId FROM Type WHERE label = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            ps.setString(1, label);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                ret = findById(rs.getInt("typeId"));
            }

        }catch (Exception e){
            System.err.println("TypeDAO findId: " + e.getMessage());
        }

        return ret;
    }

    public Type findById(int id){
        Type ret = null;
        String query = "SELECT * FROM Type WHERE typeId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                Type parent = shallowFind(rs.getInt("parentId"));
                WordDAO wordDAO = new WordDAO();
                Word word = wordDAO.findById(rs.getInt("rWordId"));
                ret = new Type(rs.getString("label"), parent, word ,rs.getInt("position"));
            }

        }catch (Exception e){
            System.err.println("TypeDAO findById: " + e.getMessage());
        }

        return ret;
    }

    private Type shallowFind(int id){
        Type ret = null;
        String query = "SELECT * FROM Type WHERE typeId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                ret = new Type(rs.getString("label"));
            }

        }catch (Exception e){
            System.err.println("TypeDAO findById: " + e.getMessage());
        }

        return ret;
    }

    public void update(Type type){
        int lines = 0;
        String query = "UPDATE Type SET label = ?, parentId = ?, rootId = ?, position = ? WHERE typeId = ?";

        try (Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){

            ps.setString(1, type.getLabel());
            if (type.getParent() == null) {
                ps.setInt(2, 0);
            }else{
                ps.setInt(2, type.getParent().getId());
            }
            if (type.getRoot() == null) {
                ps.setInt(3, 0);
            }else{
                ps.setInt(3, type.getRoot().getId());
            }
            ps.setInt(4, type.getPosition());
            ps.setInt(5, type.getId());
            lines = ps.executeUpdate();
            System.out.println(lines + " rows updated");
        }catch (SQLException e){
            System.err.println("TypeDAO update: " + e.getMessage());
        }
    }

    public int create(Type type){
        String query = "INSERT INTO Type VALUES (?, ?, ?, ?, ?)";
        int ret = -1;

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            ret = getRowsCount("Type") + 1;
            ps.setInt(1, ret);
            ps.setString(2, type.getLabel());
            if (type.getParent() == null) {
                ps.setInt(3, 0);
            }else{
                ps.setInt(3, type.getParent().getId());
            }
            if (type.getRoot() == null) {
                ps.setInt(4, 0);
            }else{
                ps.setInt(4, type.getRoot().getId());
            }
            ps.setInt(5, type.getPosition());
            ps.executeUpdate();

        }catch (SQLException e){
            System.err.println("TypeDAO create: " + e.getMessage());
        }

        return ret;
    }

    public void delete(Type type){
        String query = "DELETE FROM Type WHERE typeId = ?";
        int rows = 0;

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            ps.setInt(1, type.getId());
            rows = ps.executeUpdate();
            System.out.println(rows + " rows deleted");
        }catch (SQLException e){
            System.err.println("TypeDAO delete: " + e.getMessage());
        }
    }
}
