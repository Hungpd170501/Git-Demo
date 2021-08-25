package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	    private static Connection con;
	  
	    static {
	        String url = "jdbc:mysql:// localhost:3306/new_schema";
	        String user = "root";
	        String pass = "se150222";
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection(url, user, pass);
	        }
	        catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }
	    } 
	    
	    public static Connection getConnection() {
	        return con;
	    }
}
