package controllers;

import java.util.List;

import models.Event;
import models.Product;
import models.User;
import repositories.EventRepository;
import repositories.InvitationRepository;
import repositories.ProductRepository;
import repositories.UserRepository;
import singletons.UserSession;

public class VendorController {
	public static void acceptInvitation(String event_id) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		if(user!=null) {
			InvitationRepository.acceptInvitation(user.getUser_id(), event_id);
		}
	}
	public static List<Event> viewAcceptedEvents(String user_email){
		if(user_email.isEmpty()) {
			return null;
		}
		User user = UserRepository.getUserByEmail(user_email);
		if(user!=null) {
			return EventRepository.getAcceptedEvents(user.getUser_id());
		}
		return null;
	}
	//ini buat apa masyalah
	public static void manageVendor(String product_description, String product_name){
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		ProductRepository.addProduct(product_name, product_description, user.getUser_id());
	}
}
