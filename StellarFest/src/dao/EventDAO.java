package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factories.EventFactory;
import models.Event;
import utils.DatabaseConnection;

public class EventDAO {
	private Connection connection;
	
	public EventDAO() {
		this.connection = DatabaseConnection.connect();
	}
	
	public List<Event> getEvents(){
		List<Event> events = new ArrayList<>();
		String query = "SELECT * FROM Events";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				String id = resultSet.getString("event_id");
				String name = resultSet.getString("event_name");
				Date date = resultSet.getDate("event_date");
				String location = resultSet.getString("event_location");
				String description = resultSet.getString("event_description");
				String organizerId = resultSet.getString("organizer_id");
				Event event = EventFactory.create(id,name,date,location, description, organizerId);
				events.add(event);
			}
		} catch (SQLException e) {
			System.err.println("Error fetching events: " + e.getMessage());
			e.printStackTrace();
		}
		return events;
	}
	
	public List<Event> getEventsByOrganizerId(String organizer_id) {
	    List<Event> events = new ArrayList<>();
	    String query = "SELECT * FROM Events WHERE organizer_id = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, organizer_id);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) { 
	            String id = resultSet.getString("event_id");
	            String name = resultSet.getString("event_name");
	            Date date = resultSet.getDate("event_date");
	            String location = resultSet.getString("event_location");
	            String description = resultSet.getString("event_description");
	            String organizerId = resultSet.getString("organizer_id");

	            Event event = EventFactory.create(id, name, date, location, description, organizerId);
	            events.add(event);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }


	    return events;
	}
	
	public Event getEventById(String event_id){
		String query = "SELECT * FROM Events WHERE event_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, event_id);
			ResultSet res = preparedStatement.executeQuery();

			if (res.next()) {
				String id = res.getString("event_id");
				String name = res.getString("event_name");
				Date date = res.getDate("event_date");
				String location = res.getString("event_location");
				String description = res.getString("event_description");
				String organizerId = res.getString("organizer_id");
				return EventFactory.create(id, name, date, location, description, organizerId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean createEvent(String event_name, Date event_date, String event_location, String event_description, String organizer_id) {
		String query = "INSERT INTO "
				+ "Events(event_name, event_date, event_location, event_description, organizer_id) "
				+ "VALUES(?, ?, ?, ?, ?)";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, event_name);
	        preparedStatement.setDate(2, event_date);
	        preparedStatement.setString(3, event_location);
	        preparedStatement.setString(4, event_description);
	        preparedStatement.setString(5, organizer_id);

	        int rowsInserted = preparedStatement.executeUpdate();

	        return rowsInserted > 0;
	    } catch (SQLException e) {
	        System.err.println("Error creating event: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return false;
	}
	public boolean deleteEvent(String event_id) {
		String query = "DELETE FROM Events WHERE event_id = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, event_id);

	        int rowsDeleted = preparedStatement.executeUpdate();

	        return rowsDeleted > 0;
	    } catch (SQLException e) {
	        System.err.println("Error deleting event: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return false;
	}
	public boolean editEventName(String event_id, String event_name) {
		String query = "UPDATE Events SET event_name = ? WHERE event_id = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, event_name);
	        preparedStatement.setString(2, event_id); 

	        int rowsUpdated = preparedStatement.executeUpdate();

	        return rowsUpdated > 0;
	    } catch (SQLException e) {
	        System.err.println("Error updating profile: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return false;
	}
	public List<Event> getAcceptedEvents(String user_id) {
		List<Event> events = new ArrayList<>();
	    String query = "SELECT Events.event_id, Events.event_name, Events.event_date, Events.event_location, Events.event_description, Events.organizer_id FROM Events "
	    		+ "JOIN Invitations ON Invitations.event_id = Events.event_id "
	    		+ "WHERE invitations.user_id = ?"
	    		+ " AND Invitations.invitation_status = 'accepted';";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, user_id);
	        
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	            	String id = resultSet.getString("event_id");
					String name = resultSet.getString("event_name");
					Date date = resultSet.getDate("event_date");
					String location = resultSet.getString("event_location");
					String description = resultSet.getString("event_description");
					String organizerId = resultSet.getString("organizer_id");
					Event event = EventFactory.create(id,name,date,location, description, organizerId);
					events.add(event);
	                
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error retrieving vendors: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return events;
	}
	
}
