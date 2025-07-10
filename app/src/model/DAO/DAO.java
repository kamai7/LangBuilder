package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


abstract class DAO<T> {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/LangEngine?allowMultiQueries=true";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123456";

    DAO() {}

    /**
     * Finds all objects of type T in the database.
     * @return an ArrayList containing all objects of type T, or an empty list if none are found.
     */
    public abstract ArrayList<T> findAll();

    /**
     * Finds an object of type T by its ID.
     * @param id the ID of the object to find.
     * @return the object of type T with the specified ID, or null if not found.
     */
    public abstract T findById(int id);
    
    /**
     * Creates a new object of type T in the database.
     * @param obj the object to create.
     * @return the ID of the newly created object, or -1 if creation failed.
     */
    public abstract int create(T obj);
    
    /**
     * Updates an existing object of type T in the database.
     * @param obj the object to update.
     */
    public abstract void update(T obj);
    
    /**
     * Deletes an object of type T from the database.
     * @param obj the object to delete.
     */
    public abstract void delete(T obj);

    /**
     * Returns a connection to the database.
     * @return a Connection object or null if the connection could not be established.
     * @throws SQLException if a database access error occurs.
     */
    protected Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("getConnection(): " + DRIVER_CLASS_NAME + " not found");
            return null;
        }

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Returns the number of rows in the specified table.
     * @param tableName the name of the table to count rows from.
     * @return the number of rows in the table, or 0 if an error occurs.
     */
    public int getRowsCount(String tableName){
        int ret = 0;

        String query = "SELECT COUNT(*) FROM " + tableName;

        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            if (rs.next()) {
                ret = rs.getInt(1);
            }
        } catch(SQLException e) {
            System.err.println("DAO.getRowsCount( + '" + tableName + "'): " + e.getMessage());
        }

        return ret;
    }
}
