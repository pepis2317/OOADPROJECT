package controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import models.Event;
import repositories.EventRepository;

public class EventController {
	//view event details keknya di views deh idk
	public static void createEvent(String event_name, String event_date,String event_location,String event_description, String organizer_id ) {
		if(!event_name.isBlank() && !event_date.isBlank() && !event_description.isBlank() && !organizer_id.isBlank() && !event_location.isBlank()) {
			if(event_location.length() < 5 || event_description.length() > 200) {
				return;
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        
	        try {
	            LocalDate inputDate = LocalDate.parse(event_date, formatter);
	            LocalDate today = LocalDate.now();
	            if (inputDate.isAfter(today)) {
	                EventRepository.createEvent(event_name, event_date, event_location, event_description, organizer_id);
	            }
	        } catch (DateTimeParseException e) {
	            System.out.println("Invalid date format: " + e.getMessage());
	        }
			
			
		}
	}
	public static Event getEventDetails(String event_id) {
		if(event_id.isBlank()) {
			return null;
		}
		return EventRepository.getEventById(event_id);
	}
}
