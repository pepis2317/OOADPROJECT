package controllers;

import java.util.List;

import dao.EventDAO;
import dao.UserDAO;
import models.Event;
import models.Guest;
import models.Vendor;
import utils.Response;

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
	public List<Vendor> getUninvitedVendors(String event_id){
		return userDAO.getUninvitedVendors(event_id);
	}
	public List<Guest> getUninvitedGuests(String event_id){
		return userDAO.getUninvitedGuests(event_id);
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

	public Response editEventName(String event_id, String event_name) {
		if(event_id.isBlank() || event_name.isBlank()) {
			return new Response(false, "One or more fields are missing!");
		}
		eventDAO.editEventName(event_id, event_name);
		return new Response(true, "Event Name Edited Successfully.");
	}
}
