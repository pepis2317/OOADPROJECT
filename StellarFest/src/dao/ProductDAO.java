package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import utils.DatabaseConnection;

public class ProductDAO {
	private Connection connection;
	
	public ProductDAO() {
		this.connection = DatabaseConnection.connect();
	}
	
	public void addProduct(String product_name, String product_description, String vendor_id) {
		String query = "INSERT INTO Products (product_name, product_description, vendor_id) VALUES (?, ?, ?)";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, product_name);
	        preparedStatement.setString(2, product_description);
	        preparedStatement.setString(3, vendor_id);

	        int rowsInserted = preparedStatement.executeUpdate();

	        if (rowsInserted > 0) {
	            System.out.println("User registered successfully!");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error registering user: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}
