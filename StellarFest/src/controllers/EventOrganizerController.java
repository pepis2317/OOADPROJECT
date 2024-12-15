package controllers;

import java.util.List;

import dao.EventDAO;
import dao.UserDAO;
import models.Event;
import models.Guest;
import models.Vendor;

public class EventOrganizerController {
	private EventDAO eventDAO;
	private UserDAO userDAO;
	
	public EventOrganizerController() {
		this.eventDAO = new EventDAO();
		this.userDAO = new UserDAO();
	}
	
	public List<Event> getOrganizedEvents(String user_id){
		if(user_id.isBlank()) {
			return null;
		}
		return eventDAO.getEventsByOrganizerId(user_id);
	}
	
	public List<Guest> getGuests(){
		return userDAO.getAllGuests();
	}
	
	public List<Vendor> getVendors(){
		return userDAO.getAllVendors();
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

	public void editEventName(String event_id, String event_name) {
		eventDAO.editEventName(event_id, event_name);
	}
}
