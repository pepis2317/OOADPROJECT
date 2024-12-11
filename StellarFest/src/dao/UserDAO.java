package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factories.UserFactory;
import models.Guest;
import models.User;
import models.Vendor;
import utils.DatabaseConnection;

public class UserDAO {
	private Connection connection;
	
	public UserDAO() {
		this.connection = DatabaseConnection.connect();
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM Users";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				String id = resultSet.getString("user_id");
				String email = resultSet.getString("user_email");
				String name = resultSet.getString("user_name");
				String password = resultSet.getString("user_password");
				String role = resultSet.getString("user_role");
				User user = UserFactory.create(id, email, name, password, role);
				users.add(user);
			}
		} catch (SQLException e) {
			System.err.println("Error fetching users: " + e.getMessage());
			e.printStackTrace();
		}
		return users;
	}

	public User getUserByEmail(String user_email) {
		String query = "SELECT * FROM Users WHERE user_email = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, user_email);
			ResultSet res = preparedStatement.executeQuery();

			if (res.next()) {
				String id = res.getString("user_id");
				String email = res.getString("user_email");
				String name = res.getString("user_name");
				String password = res.getString("user_password");
				String role = res.getString("user_role");
				
				return UserFactory.create(id, email, name, password, role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean deleteUser(String user_id) {
		String query = "DELETE FROM Users WHERE user_id = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, user_id);

	        int rowsDeleted = preparedStatement.executeUpdate();

	        return rowsDeleted > 0;
	        
	    } catch (SQLException e) {
	        System.err.println("Error deleting user: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return false;
	}
	public User getUserByUsername(String user_name) {
		String query = "SELECT * FROM Users WHERE user_name = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, user_name);
			ResultSet res = preparedStatement.executeQuery();

			if (res.next()) {
				String id = res.getString("user_id");
				String email = res.getString("user_email");
				String name = res.getString("user_name");
				String password = res.getString("user_password");
				String role = res.getString("user_role");
				return UserFactory.create(id, email, name, password, role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean addUser(String user_email, String user_name, String user_password, String user_role) {
		String query = "INSERT INTO Users (user_email, user_name, user_password, user_role) VALUES (?, ?, ?, ?)";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, user_email);
	        preparedStatement.setString(2, user_name);
	        preparedStatement.setString(3, user_password);
	        preparedStatement.setString(4, user_role);

	        int rowsInserted = preparedStatement.executeUpdate();

	        return rowsInserted > 0;
	    } catch (SQLException e) {
	        System.err.println("Error registering user: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return false;
	}
	
	public void changeProfile(String user_id, String user_email, String user_name, String user_password) {
	    String query = "UPDATE Users SET user_email = ?, user_name = ?, user_password = ? WHERE user_id = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, user_email);
	        preparedStatement.setString(2, user_name); 
	        preparedStatement.setString(3, user_password);
	        preparedStatement.setString(4, user_id);

	        int rowsUpdated = preparedStatement.executeUpdate();

	        if (rowsUpdated > 0) {
	            System.out.println("Profile updated successfully!");
	        } else {
	            System.out.println("No user found with the given id.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error updating profile: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	public List<Vendor> getVendors(String event_id) {
	    List<Vendor> vendors = new ArrayList<>();
	    String query = "SELECT Users.user_id, Users.user_email, Users.user_name, Users.user_password, Users.user_role "
	            + "FROM Users "
	            + "JOIN Invitations ON Invitations.user_id = Users.user_id "
	            + "WHERE Invitations.event_id = ? "
	            + "AND Invitations.invitation_role = 'vendor';";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, event_id);
	        
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                String id = resultSet.getString("user_id");
	                String email = resultSet.getString("user_email");
	                String name = resultSet.getString("user_name");
	                String password = resultSet.getString("user_password");
	                String role = resultSet.getString("user_role");
	                User user = UserFactory.create(id, email, name, password, role);
	                
	                // Ensure the user is castable to Vendor before adding
	                if (user instanceof Vendor) {
	                    vendors.add((Vendor) user);
	                } else {
	                    System.err.println("Error: User is not a Vendor.");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error retrieving vendors: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return vendors;
	}
	public List<Guest> getGuests(String event_id) {
	    List<Guest> guests = new ArrayList<>();
	    String query = "SELECT Users.user_id, Users.user_email, Users.user_name, Users.user_password, Users.user_role "
	            + "FROM Users "
	            + "JOIN Invitations ON Invitations.user_id = Users.user_id "
	            + "WHERE Invitations.event_id = ? "
	            + "AND Invitations.invitation_role = 'guest';";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        // Set the parameter before executing the query
	        preparedStatement.setString(1, event_id);
	        
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            // Process the result set
	            while (resultSet.next()) {
	                String id = resultSet.getString("user_id");
	                String email = resultSet.getString("user_email");
	                String name = resultSet.getString("user_name");
	                String password = resultSet.getString("user_password");
	                String role = resultSet.getString("user_role");
	                User user = UserFactory.create(id, email, name, password, role);
	                
	                // Ensure the user is castable to Vendor before adding
	                if (user instanceof Guest) {
	                    guests.add((Guest) user);
	                } else {
	                    System.err.println("Error: User is not a Vendor.");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error retrieving vendors: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return guests;
	}
	public List<Guest> getAllGuests(){
		List<Guest> guests = new ArrayList<>();
	    String query = "SELECT Users.user_id, Users.user_email, Users.user_name, Users.user_password, Users.user_role "
	            + "FROM Users "
	            + "JOIN Invitations ON Invitations.user_id = Users.user_id "
	            + "WHERE Invitations.invitation_role = 'guest';";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        // Set the parameter before executing the query
	        
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            // Process the result set
	            while (resultSet.next()) {
	                String id = resultSet.getString("user_id");
	                String email = resultSet.getString("user_email");
	                String name = resultSet.getString("user_name");
	                String password = resultSet.getString("user_password");
	                String role = resultSet.getString("user_role");
	                User user = UserFactory.create(id, email, name, password, role);
	                
	                // Ensure the user is castable to Vendor before adding
	                if (user instanceof Guest) {
	                    guests.add((Guest) user);
	                } else {
	                    System.err.println("Error: User is not a Vendor.");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error retrieving vendors: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return guests;
	}
	public List<Vendor> getAllVendors() {
	    List<Vendor> vendors = new ArrayList<>();
	    String query = "SELECT Users.user_id, Users.user_email, Users.user_name, Users.user_password, Users.user_role "
	            + "FROM Users "
	            + "JOIN Invitations ON Invitations.user_id = Users.user_id "
	            + "WHERE Invitations.invitation_role = 'vendor';";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                String id = resultSet.getString("user_id");
	                String email = resultSet.getString("user_email");
	                String name = resultSet.getString("user_name");
	                String password = resultSet.getString("user_password");
	                String role = resultSet.getString("user_role");
	                User user = UserFactory.create(id, email, name, password, role);
	                
	                // Ensure the user is castable to Vendor before adding
	                if (user instanceof Vendor) {
	                    vendors.add((Vendor) user);
	                } else {
	                    System.err.println("Error: User is not a Vendor.");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error retrieving vendors: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return vendors;
	}

}
