package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import model.persistance.Type;
import model.persistance.Word;
import model.util.Colors;

public class TypeDAO extends DAO<Type>{
    
    @Override
    public Set<Type> findAll(int limit) {

        if(limit < -1){
            throw new IllegalArgumentException("Limit must be greater than -1.");
        }

        String query;

        if (limit == -1){
            query = "SELECT * FROM Type ORDER BY position";
        }else{
            query = "SELECT * FROM Type ORDER BY position LIMIT " + limit;
        }

        Set<Type> ret = new HashSet<>();

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
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
                double[] color = {rs.getDouble("colorR"), rs.getDouble("colorG"), rs.getDouble("colorB"), rs.getDouble("colorT")};
                Type type = new Type(rs.getString("label"), parent, root, rs.getInt("position"), color);
                type.setId(rs.getInt("typeId"));
                ret.add(type);
            }
        } catch (Exception e) {
            System.err.println(Colors.error("TypeDAO findAll: ", e.getMessage()));
        }

        return ret;
    }

    @Override
    public Set<Type> findAll() {
        return findAll(-1);
    }

    @Override
    public Type findById(int id) {
        Type ret = null;
        String query = "SELECT * FROM Type WHERE typeId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
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
                double[] color = {rs.getDouble("colorR"), rs.getDouble("colorG"), rs.getDouble("colorB"), rs.getDouble("colorT")};
                ret = new Type(rs.getString("label"), parent, root, rs.getInt("position"), color);
                ret.setId(id);
            }
        } catch (Exception e) {
            System.err.println(Colors.error("TypeDAO findById: ", e.getMessage()));
        }

        return ret;
    }
    
    public Type findByLabel(String label){
        Type ret = null;
        String query = "SELECT typeId FROM Type WHERE label = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setString(1, label);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ret = findById(rs.getInt("typeId"));
            }
        } catch (Exception e) {
            System.err.println(Colors.error("TypeDAO findId: ", e.getMessage()));
        }

        return ret;
    }

    /**
     * Finds a Type by its ID without loading its parent or root.
     * @param id The ID of the Type to find.
     * @return A Type object with only the label set, or null if not found.
     */
    private Type findShallow(int id) {
        Type ret = null;
        String query = "SELECT * FROM Type WHERE typeId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                ret = new Type(rs.getString("label"));
            }
        } catch (Exception e) {
            System.err.println(Colors.error("TypeDAO findById: ", e.getMessage()));
        }

        return ret;
    }

    @Override
    public void update(Type type) {
        String query = "UPDATE Type SET label = ?, parentId = ?, rootId = ?, position = ? WHERE typeId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {

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
            
            int lines = ps.executeUpdate();
            System.out.println(lines + " rows updated");
        }
        catch (SQLException e) {
            System.err.println(Colors.error("TypeDAO update: ", e.getMessage()));
        }
    }

    @Override
    public int create(Type type) {
        String query = "INSERT INTO Type (typeId, label, parentId, rootId, position) VALUES (?, ?, ?, ?, ?)";
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
    public void delete(Type type) {
        String query = "DELETE FROM Type WHERE typeId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setInt(1, type.getId());
            
            int rows = ps.executeUpdate();
            System.out.println(rows + " rows deleted");
        } catch (SQLException e) {
            System.err.println(Colors.error("TypeDAO delete: ", e.getMessage()));
        }
    }
}
