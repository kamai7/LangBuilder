package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            query = "SELECT * FROM Type ORDER BY label ASC";
        }else{
            query = "SELECT * FROM Type ORDER BY label ASC LIMIT " + limit;
        }

        ArrayList<Type> ret = new ArrayList<>(getRowsCount("Type"));

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int parentId = rs.getInt("parentId");
                if (rs.wasNull()){
                    parentId = -1;
                }
                int rootId = rs.getInt("rootId");
                if (rs.wasNull()){
                    rootId = -1;
                }
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
    public Type findById(int id) {
        Type ret = null;
        String query = "SELECT * FROM Type WHERE typeId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                int parentId = rs.getInt("parentId");
                if (rs.wasNull()){
                    parentId = -1;
                }
                int rootId = rs.getInt("rootId");
                if (rs.wasNull()){
                    rootId = -1;
                }
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
        String query = "SELECT typeId, parentId FROM Type WHERE label LIKE ? ORDER BY label asc";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            
            ps.setString(1, "%" + label + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ret.add(findById(rs.getInt("typeId")));
                int parentId = rs.getInt("parentId");
                if (!rs.wasNull()){
                    ret.add(findById(parentId));
                }
            }
        } catch (Exception e) {
            System.err.println(Colors.error("TypeDAO.findByLabel: ", e.getMessage()));
        }

        return ret;
    }

    @Override
    public void update(Type type) throws SQLException {
        String query = "UPDATE Type SET label = ?, colorR = ?, colorG = ?, colorB = ?, colorT = ?, parentId = ?, rootId = ?, position = ? WHERE typeId = ?";

        System.out.println(type);

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            c.setAutoCommit(false);
            try{

                ps.setString(1, type.getLabel());
                int[] color = Colors.convertColorToRGBA(type.getColor());
                ps.setInt(2,color[0]);
                ps.setInt(3,color[1]);
                ps.setInt(4,color[2]);
                ps.setInt(5,color[3]);

                if (type.getParentId() == -1) {
                    ps.setNull(6, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(6, type.getParentId());
                }

                if (type.getRootId() == -1) {
                    ps.setNull(7, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(7, type.getRootId());
                }
                if(type.getPosition() == -1) {
                    ps.setNull(8, java.sql.Types.INTEGER);
                }else{
                    ps.setInt(8, type.getPosition());
                }
                ps.setInt(9, type.getId());
                
                int lines = ps.executeUpdate();
                System.out.println(lines + " rows updated");

                c.commit();
            }catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    @Override
    public int create(Type type) throws SQLException{
        String query = "INSERT INTO Type (typeId, label, colorR, colorG, colorB, colorT, parentId, rootId, position) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int retId = -1;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)){
            c.setAutoCommit(false);
            try{

                int id = nextId("Type", "typeId");
                ps.setInt(1, id);
                ps.setString(2, type.getLabel());
                int[] color = Colors.convertColorToRGBA(type.getColor());
                ps.setInt(3,color[0]);
                ps.setInt(4,color[1]);
                ps.setInt(5,color[2]);
                ps.setInt(6,color[3]);

                if (type.getParentId() == -1) {
                    ps.setNull(7, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(7, type.getParentId());
                }

                if (type.getRootId() == -1) {
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
                c.commit();
            }catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }

        return retId;
    }

    @Override
    public void delete(Type type) throws SQLException{
        String query = "DELETE FROM Type WHERE typeId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            c.setAutoCommit(false);
            try{

                ps.setInt(1, type.getId());
                
                int rows = ps.executeUpdate();
                System.out.println(rows + " rows deleted");

                c.commit();
            }catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } 
    }
}
