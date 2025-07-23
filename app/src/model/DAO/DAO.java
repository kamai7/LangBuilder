package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import model.util.Colors;


abstract class DAO<T> {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/LangBuilder?allowMultiQueries=true";
    private static final String USERNAME = "langbuilder";
    private static final String PASSWORD = "123456";

    DAO() {}

    /**
     * Finds all objects of type T in the database.
     * @param limit the maximum number of objects to return (-1 for no limit).
     * @return an ArrayList containing all objects of type T, or an empty list if none are found.
     */
    public abstract Set<T> findAll(int limit);

    /**
     * Finds all objects of type T in the database.
     * @return an ArrayList containing all objects of type T, or an empty list if none are found.
     */
    public abstract Set<T> findAll();

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
            System.err.println(Colors.error("DAO getConnection(): " + DRIVER_CLASS_NAME + " not found"));
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
            System.err.println(Colors.error("DAO getRowsCount( + '" + tableName + "'): ", e.getMessage()));
        }

        return ret;
    }

    /**
     * Returns the next available ID for a specified table and column.
     * @param tableName the name of the table to check for the next ID.
     * @param columnName the name of the column to check for the next ID.
     * @return the next available ID, starting from 0.
     */
    protected int nextId(String tableName, String columnName) {
        String query = "SELECT " + columnName + " FROM " + tableName + " ORDER BY " + columnName + " ASC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
             ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int last = rs.getInt(columnName);
                while (rs.next()) {
                    int current = rs.getInt(columnName);
                    if (current != last + 1) {
                        return last + 1;
                    }
                    last = current;
                }
            }
        } catch (SQLException e) {
            System.err.println(Colors.error("DAO nextId: ", e.getMessage()));
        }

        return 0;
    }
}
