package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


abstract class DAO<T> {
    private final String url = "jdbc:mysql://localhost:3306/LangEngine";
    private final String username = "admin";
    private final String password = "123456";

    DAO() {}

    public abstract ArrayList<T> findAll();
    public abstract T findById(int id);
    public abstract int create(T obj);
    public abstract void update(T obj);
    public abstract void delete(T obj);

    protected Connection getConnection() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("getConnection: " + e.getMessage());
        }
        return c;
    }

    protected void close(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            System.err.println("closeConnection: " + e.getMessage());
        }
    }

    public int getRowsCount(String tableName){
        int ret = 0;

        String query = "SELECT COUNT(*) FROM " + tableName;

        try(Connection c = getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query)){

            if(rs.next()){
                ret = rs.getInt(1);
            }

        }catch(SQLException e){
            System.err.println("DAO getRowsCount: " + e.getMessage());
        }

        return ret;
    }
}
