package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factories.InvitationFactory;
import models.Invitation;
import singletons.DatabaseConnection;


public class InvitationRepository {
	private static Connection connection = DatabaseConnection.connect();
	public static List<Invitation> getInvitations(){
		List<Invitation> invites = new ArrayList<>();
		String query = "SELECT * FROM Invitations";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				String id = resultSet.getString("invitation_id");
				String eventId = resultSet.getString("event_id");
				String userId = resultSet.getString("user_id");
				String status = resultSet.getString("invitation_status");
				String role = resultSet.getString("invitation_role");
				Invitation invite = InvitationFactory.create(id, eventId, userId, status, role);
				invites.add(invite);
			}
		} catch (SQLException e) {
			System.err.println("Error fetching invitations: " + e.getMessage());
			e.printStackTrace();
		}
		return invites;
	}
	public static List<Invitation> getInvitationsByUserId(String user_id) {
		List<Invitation> invites = new ArrayList<>();
		String query = "SELECT * FROM Invitations WHERE user_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, user_id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				String id = resultSet.getString("invitation_id");
				String eventId = resultSet.getString("event_id");
				String userId = resultSet.getString("user_id");
				String status = resultSet.getString("invitation_status");
				String role = resultSet.getString("invitation_role");
				Invitation invite = InvitationFactory.create(id, eventId, userId, status, role);
				invites.add(invite);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return invites;
	}
	public static void createInvitation(String event_id, String user_id, String user_role) {
		String query = "INSERT INTO Invitations (event_id, user_id, invitation_status, invitation_role) VALUES (?, ?, 'pending', ?)";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, event_id);
	        preparedStatement.setString(2, user_id);
	        preparedStatement.setString(3, user_role);

	        int rowsInserted = preparedStatement.executeUpdate();

	        if (rowsInserted > 0) {
	            System.out.println("Event created successfully!");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error creating invitation: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	public static void acceptInvitation(String user_id, String event_id) {
		String query = "UPDATE Invitation SET invitation_status = 'accepted' WHERE user_id = ? AND event_id = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, user_id);
	        preparedStatement.setString(2, event_id); 

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
	
}
