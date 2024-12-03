package controllers;

import java.util.List;

import models.Event;
import models.Guest;
import models.Vendor;
import repositories.EventRepository;
import repositories.UserRepository;

public class EventOrganizerController {
	//createEvent di EventController
	//getOrganizedEventDetails di EventController, pake getEventDetails aj
	//
	public static List<Event> getOrganizedEvents(String user_id){
		if(user_id.isBlank()) {
			return null;
		}
		return EventRepository.getEventsByOrganizerId(user_id);
	}
	public static List<Guest> getGuests(){
		return UserRepository.getAllGuests();
	}
	public static List<Vendor> getVendors(){
		return UserRepository.getAllVendors();
	}
	public static List<Guest> getGuestsByTransactionID(String event_id){
		if(event_id.isBlank()) {
			return null;
		}
		return UserRepository.getGuests(event_id);
	}
	public static List<Vendor> getVendorsByTransactionID(String event_id){
		if(event_id.isBlank()) {
			return null;
		}
		return UserRepository.getVendors(event_id);
	}
	//checkAddVendorInput sama checkAddGuestInput buat apa :(
	public static void editEventName(String event_id, String event_name) {
		EventRepository.editEventName(event_id, event_name);
	}
}
