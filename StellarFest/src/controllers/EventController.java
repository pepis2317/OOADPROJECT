package controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import dao.EventDAO;
import models.Event;
import models.User;
import utils.Response;
import utils.UserSession;

public class EventController {
	private EventDAO eventDAO;
	
	public EventController() {
		this.eventDAO = new EventDAO();
	}

	public Response createEvent(String event_name, String event_date, String event_location,String event_description, String organizer_id ) {
		if(!event_name.isBlank() && !event_date.isBlank() && !event_description.isBlank() && !organizer_id.isBlank() && !event_location.isBlank()) {
			if(event_location.length() < 5) {
				return new Response(false, "Event location must be more than 5 characters long!");
			}
			if(event_description.length() > 200) {
				return new Response(false, "Event description must be less than 200 characters long!");
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        
	        try {
	            LocalDate inputDate = LocalDate.parse(event_date, formatter);
	            LocalDate today = LocalDate.now();
	            if (inputDate.isAfter(today)) {
	            	Date date = Date.valueOf(inputDate);
	                if(eventDAO.createEvent(event_name, date, event_location, event_description, organizer_id)) {
	                	return new Response(true, "Date added sucessfully.");
	                }
	                else {
	                	return new Response(false, "Something went wrong!");
	                }
	            }
	            
	            return new Response(false, "Date must be after today!");
	        } catch (DateTimeParseException e) {
	            return new Response(false, "Invalid date format! Please use dd-MM-yyyy format!");
	        }
		}
		
		return new Response(false, "One or more fields are missing!");
	}
	public Event getEventDetails(String event_id) {
		if(event_id.isBlank()) {
			return null;
		}
		return eventDAO.getEventById(event_id);
	}
	public List<Event> viewAcceptedEvents(){
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		if(user!=null) {
			return eventDAO.getAcceptedEvents(user.getUser_id());
		}
		return null;
	}
}
