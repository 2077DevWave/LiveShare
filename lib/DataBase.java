package lib;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {

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
