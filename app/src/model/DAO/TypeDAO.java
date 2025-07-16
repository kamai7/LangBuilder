package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.persistance.Type;
import model.persistance.Word;
import model.util.Colors;

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
            System.err.println(Colors.error("TypeDAO findId: ", e.getMessage()));
        }

        return ret;
    }

    @Override
    public Type findById(int id){
        Type ret = null;
        String query = "SELECT * FROM Type WHERE typeId = ?";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                Type parent = null;
                int parentId = rs.getInt("parentId");
                if (!rs.wasNull()) {
                    parent = findShallow(parentId);
                }

                Word root = null;
                int rootId = rs.getInt("rootId");
                if (!rs.wasNull()) {
                    root = new WordDAO().findById(rootId);
                }

                ret = new Type(rs.getString("label"), parent, root, rs.getInt("position"));
                ret.setId(id);
            }

        }catch (Exception e){
            System.err.println(Colors.error("TypeDAO findById: ", e.getMessage()));
        }

        return ret;
    }

    private Type findShallow(int id){
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
            System.err.println(Colors.error("TypeDAO findById: ", e.getMessage()));
        }

        return ret;
    }

    @Override
    public void update(Type type){
        int lines = 0;
        String query = "UPDATE Type SET label = ?, parentId = ?, rootId = ?, position = ? WHERE typeId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)){

            ps.setString(1, type.getLabel());

            if (type.getParent() == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, type.getParent().getId());
            }

            if (type.getRoot() == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, type.getRoot().getId());
            }

            ps.setInt(4, type.getPosition());
            ps.setInt(5, type.getId());
            lines = ps.executeUpdate();
            System.out.println(lines + " rows updated");
        }catch (SQLException e){
            System.err.println(Colors.error("TypeDAO update: ", e.getMessage()));
        }
    }

    @Override
    public int create(Type type){
        String query = "INSERT INTO Type VALUES (?, ?, ?, ?, ?)";
        int retId = -1;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {

            int id = nextId("Type", "typeId");
            ps.setInt(1, id);
            ps.setString(2, type.getLabel());

            if (type.getParent() == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, type.getParent().getId());
            }

            if (type.getRoot() == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, type.getRoot().getId());
            }

            ps.setInt(5, type.getPosition());
            ps.executeUpdate();

            // Only if the insert was successful
            type.setId(id);
            retId = id;
        }
        catch (SQLException e) {
            System.err.println(Colors.error("TypeDAO create: ", e.getMessage()));
        }

        return retId;
    }

    @Override
    public void delete(Type type){
        String query = "DELETE FROM Type WHERE typeId = ?";
        int rows = 0;

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(query)){
            ps.setInt(1, type.getId());
            rows = ps.executeUpdate();
            System.out.println(rows + " rows deleted");
        }catch (SQLException e){
            System.err.println(Colors.error("TypeDAO delete: ", e.getMessage()));
        }
    }
}
