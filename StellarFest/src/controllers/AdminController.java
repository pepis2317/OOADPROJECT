package controllers;

import java.util.List;

import models.Event;
import models.Guest;
import models.User;
import models.Vendor;
import repositories.EventRepository;
import repositories.UserRepository;

public class AdminController {
	//viewAllEvents sama viewEventDetails(event_id) mungkin lebih masuk akal kalo di views
	public static List<Event> getAllEvents(){
		return EventRepository.getEvents();
	}
	public static List<User> getAllUsers(){
		return UserRepository.getUsers();
	}
	public static Event getEvent(String event_id) {
		if(event_id.isBlank()) {
			return null;
		}
		return EventRepository.getEventById(event_id);
	}
	public static void deleteEvent(String event_id) {
		if(!event_id.isBlank()) {
			EventRepository.deleteEvent(event_id);
		}
	}
	public static void deleteUser(String user_id) {
		if(!user_id.isBlank()) {
			UserRepository.deleteUser(user_id);
		}
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
	
}
