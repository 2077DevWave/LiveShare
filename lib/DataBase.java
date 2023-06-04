package lib;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {

    /**
    * Connects to the database. This method is called by connect () to establish a connection to the database.
    * 
    * 
    * @return Connection to the database null if there is an error connecting to the database or connection could not be established
    */
    private static Connection connect() throws SQLException {  
        Connection conn = null;  
        try {  
            // db parameters  
            String url = "jdbc:mysql://localhost:3306/liveshare";  
            // create a connection to the database  
            conn = DriverManager.getConnection(url,"root","");  
              
            Logger.newLog("Connection to SQL has been established.");  

            return conn;
              
        } catch (SQLException e) {  
            Logger.newError("DataBase -> " + e.getMessage());
            throw new SQLException(e);
        }
    }

    /**
    * Executes a query and returns the number of rows affected. This is useful for INSERT queries that do not take a row count into account
    * 
    * @param sql - The SQL to be
    */
    public static void insertQuery(String sql) throws SQLException {
        try{
            Connection conn = connect();  
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }catch(SQLException e){
            Logger.newError("DataBase -> " + e.getMessage());
            throw new SQLException(e);
        }
    }

    /**
    * Executes a select query and returns the result. This is a convenience method for SELECT queries. It uses #connect () to get a connection to the database.
    * 
    * @param sql - The query to execute. Must not be null.
    * 
    * @return ResultSet The result of the query. It is a ResultSet object with a single row ( 1 ). If you want to iterate over the result set you should use #next ()
    */
    public static ResultSet selectQuery(String sql) throws SQLException{    
          
        try {  
            Connection conn = connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);
            rs.next();
            return rs;
        } catch (SQLException e) {  
            Logger.newError("DataBase -> " + e.getMessage());
            throw new SQLException(e);
        }  
    }  

}
