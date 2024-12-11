package controllers;

import java.util.List;

import dao.EventDAO;
import dao.UserDAO;
import models.Event;
import models.Guest;
import models.User;
import models.Vendor;

public class AdminController {
	private EventDAO eventDAO;
	private UserDAO userDAO;
	
	public AdminController() {
		this.eventDAO = new EventDAO();
		this.userDAO = new UserDAO();
	}

	public List<Event> getAllEvents(){
		return eventDAO.getEvents();
	}
	public List<User> getAllUsers(){
		return userDAO.getUsers();
	}
	public Event getEvent(String event_id) {
		if(event_id.isBlank()) {
			return null;
		}
		return eventDAO.getEventById(event_id);
	}
	public void deleteEvent(String event_id) {
		if(!event_id.isBlank()) {
			eventDAO.deleteEvent(event_id);
		}
	}
	public void deleteUser(String user_id) {
		if(!user_id.isBlank()) {
			userDAO.deleteUser(user_id);
		}
	}
	public List<Guest> getGuestsByTransactionID(String event_id){
		if(event_id.isBlank()) {
			return null;
		}
		return userDAO.getGuests(event_id);
	}
	public List<Vendor> getVendorsByTransactionID(String event_id){
		if(event_id.isBlank()) {
			return null;
		}
		return userDAO.getVendors(event_id);
	}
	
}
