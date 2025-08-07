package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import model.persistance.Type;
import utils.Colors;

public class TypeDAO extends DAO<Type>{
    
    @Override
    public ArrayList<Type> findAll(int limit) {

        if(limit < -1){
            throw new IllegalArgumentException("Limit must be greater than -1.");
        }

        String query;

        if (limit == -1){
            query = "SELECT * FROM Type ORDER BY LENGTH(label), label ASC";
        }else{
            query = "SELECT * FROM Type ORDER BY LENGTH(label), label ASC LIMIT " + limit;
        }

        ArrayList<Type> ret = new ArrayList<>(getRowsCount("Type"));

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int parentId = rs.getInt("parentId");
                int rootId = rs.getInt("rootId");
                Color color = Colors.convertRGBAToColor(new int[]{rs.getInt("colorR"), rs.getInt("colorG"), rs.getInt("colorB"), rs.getInt("colorT")});
                Type type = new Type(rs.getString("label"), parentId, rootId, rs.getInt("position"), color);
                type.setId(rs.getInt("typeId"));
                ret.add(type);
            }
        } catch (Exception e) {
            System.err.println(Colors.error("TypeDAO.findAll: ", e.getMessage()));
        }

        return ret;
    }

    @Override
    public ArrayList<Type> findAll() {
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
                int parentId = rs.getInt("parentId");
                int rootId = rs.getInt("rootId");
                Color color = Colors.convertRGBAToColor(new int[]{rs.getInt("colorR"), rs.getInt("colorG"), rs.getInt("colorB"), rs.getInt("colorT")});
                ret = new Type(rs.getString("label"), parentId, rootId, rs.getInt("position"), color);
                ret.setId(id);
            }
        } catch (Exception e) {
            System.err.println(Colors.error("TypeDAO.findById: ", e.getMessage()));
        }

        return ret;
    }
    
    public ArrayList<Type> findByLabel(String label){
        ArrayList<Type> ret = new ArrayList<>();
        String query = "SELECT typeId FROM Type WHERE label LIKE ? ORDER BY LENGTH(label), label asc";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setString(1, "%" + label + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ret.add(findById(rs.getInt("typeId")));
            }
        } catch (Exception e) {
            System.err.println(Colors.error("TypeDAO.findByLabel: ", e.getMessage()));
        }

        return ret;
    }

    public ArrayList<Type> findByParentLabel(String label) {
        ArrayList<Type> ret = new ArrayList<>();
        String query = "SELECT typeId FROM Type WHERE parentId LIKE ? ORDER BY LENGTH(label), label asc";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setString(1, "%" + label + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ret.add(findById(rs.getInt("typeId")));
            }
        } catch (Exception e) {
            System.err.println(Colors.error("TypeDAO.findByParentLabel: ", e.getMessage()));
        }

        return ret;
    }

    @Override
    public ArrayList<Type> findByString(String str) {
        return null;
    }

    @Override
    public void update(Type type) {
        String query = "UPDATE Type SET label = ?, colorR = ?, colorG = ?, colorB = ?, colorT = ?, parentId = ?, rootId = ?, position = ? WHERE typeId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {

            ps.setString(1, type.getLabel());
            int[] color = Colors.convertColorToRGBA(type.getColor());
            ps.setInt(2,color[0]);
            ps.setInt(3,color[1]);
            ps.setInt(4,color[2]);
            ps.setInt(5,color[3]);

            if (type.getParentId() == 0) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, type.getParentId());
            }

            if (type.getRootId() == 0) {
                ps.setNull(7, java.sql.Types.INTEGER);
            } else {
                ps.setInt(7, type.getRootId());
            }

            ps.setInt(8, type.getPosition());
            ps.setInt(9, type.getId());
            
            int lines = ps.executeUpdate();
            System.out.println(lines + " rows updated");
        }
        catch (SQLException e) {
            System.err.println(Colors.error("TypeDAO.update: ", e.getMessage()));
        }
    }

    @Override
    public int create(Type type) throws SQLIntegrityConstraintViolationException{
        String query = "INSERT INTO Type (typeId, label, colorR, colorG, colorB, colorT, parentId, rootId, position) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int retId = -1;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)){

            int id = nextId("Type", "typeId");
            ps.setInt(1, id);
            ps.setString(2, type.getLabel());
            int[] color = Colors.convertColorToRGBA(type.getColor());
            ps.setInt(3,color[0]);
            ps.setInt(4,color[1]);
            ps.setInt(5,color[2]);
            ps.setInt(6,color[3]);

            if (type.getParentId() == 0) {
                ps.setNull(7, java.sql.Types.INTEGER);
            } else {
                ps.setInt(7, type.getParentId());
            }

            if (type.getRootId() == 0) {
                ps.setNull(8, java.sql.Types.INTEGER);
            } else {
                ps.setInt(8, type.getRootId());
            }
            if (type.getPosition() == -1) {
                ps.setNull(9, java.sql.Types.INTEGER);
            } else {
                ps.setInt(9, type.getPosition());
            }
            ps.executeUpdate();

            // Only if the insert was successful
            type.setId(id);
            retId = id;
        }
        catch (SQLException e) {
            System.err.println(Colors.error("TypeDAO.create: ", e.getMessage()));
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw (SQLIntegrityConstraintViolationException) e;
            }
        }

        return retId;
    }

    @Override
    public void delete(Type type) throws SQLIntegrityConstraintViolationException{
        String query = "DELETE FROM Type WHERE typeId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setInt(1, type.getId());
            
            int rows = ps.executeUpdate();
            System.out.println(rows + " rows deleted");
        } catch (SQLException e) {
            System.err.println(Colors.error("TypeDAO delete: ", e.getMessage()));
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw (SQLIntegrityConstraintViolationException) e;
            }
        }
    }
}
