package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;


public class DAO<T> {


    private String url = "jdbc:mysql://localhost:3306/LangEngine";
    private String username = "admin";
    private String password = "123456";

    DAO() {
    }

    public HashSet<T> findAll() {
        return null;
    }

    Connection getConnection() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("getConnection: " + e.getMessage());
        }
        return c;
    }

    void close(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            System.err.println("closeConnection: " + e.getMessage());
        }
    }

    public int findId(T word){
        return 0;
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
