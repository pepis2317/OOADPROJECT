package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factories.ProductFactory;
import models.Product;

import utils.DatabaseConnection;

public class ProductDAO {
	private Connection connection;
	
	public ProductDAO() {
		this.connection = DatabaseConnection.connect();
	}
	
	public boolean addProduct(String product_name, String product_description, String vendor_id) {
		String query = "INSERT INTO Products(product_name, product_description, vendor_id) VALUES(?, ?, ?)";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, product_name);
	        preparedStatement.setString(2, product_description);
	        preparedStatement.setString(3, vendor_id);

	        int rowsInserted = preparedStatement.executeUpdate();

	        return rowsInserted > 0;
	    } catch (SQLException e) {
	        System.err.println("Error registering user: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return false;
	}
	public List<Product> getProducts(String vendor_id){
		List<Product> products = new ArrayList<>();
		String query = "SELECT * FROM Products WHERE vendor_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, vendor_id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				String id = resultSet.getString("product_id");
				String name = resultSet.getString("product_name");
				String description = resultSet.getString("product_description");
				String vendor= resultSet.getString("vendor_id");
				Product product = ProductFactory.create(id, name, description, vendor);
				
				products.add(product);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
}
