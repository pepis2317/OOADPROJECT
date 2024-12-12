package controllers;

import java.util.List;

import dao.EventDAO;
import dao.InvitationDAO;
import dao.ProductDAO;
import dao.UserDAO;
import models.Event;
import models.User;
import utils.UserSession;

public class VendorController {
	private EventDAO eventDAO;
	private InvitationDAO invitationDAO;
	private ProductDAO productDAO;
	private UserDAO userDAO;
	
	public VendorController() {
		this.eventDAO = new EventDAO();
		this.invitationDAO = new InvitationDAO();
		this.productDAO = new ProductDAO();
		this.userDAO = new UserDAO();
	}
	
	public void acceptInvitation(String event_id) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		if(user != null) {
			invitationDAO.acceptInvitation(user.getUser_id(), event_id);
		}
	}
	public List<Event> viewAcceptedEvents(String user_email){
		if(user_email.isEmpty()) {
			return null;
		}
		User user = userDAO.getUserByEmail(user_email);
		if(user!=null) {
			return eventDAO.getAcceptedEvents(user.getUser_id());
		}
		return null;
	}

	public void manageVendor(String product_description, String product_name){
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		productDAO.addProduct(product_name, product_description, user.getUser_id());
	}
}
