package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/stellarfestdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static volatile Connection instance = null;

    // Method to connect to the database
    public static Connection connect() {
        if (instance == null) {
        	synchronized(DatabaseConnection.class) {
        		if(instance == null) {
        			try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        instance = DriverManager.getConnection(URL, USER, PASSWORD);
                        System.out.println("Database connection established successfully.");
                    } catch (ClassNotFoundException e) {
                        System.err.println("MySQL JDBC Driver not found.");
                        e.printStackTrace();
                    } catch (SQLException e) {
                        System.err.println("Failed to connect to the database.");
                        e.printStackTrace();
                    }
        		}
        	}
        }
        
        return instance;
    }
}
