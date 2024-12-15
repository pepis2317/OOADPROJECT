package controllers;

import java.util.List;

import dao.EventDAO;
import dao.InvitationDAO;
import dao.UserDAO;
import models.Event;
import models.User;
import utils.UserSession;

public class GuestController {
	private EventDAO eventDAO;
	private InvitationDAO invitationDAO;
	private UserDAO userDAO;
	
	public GuestController() {
		this.eventDAO = new EventDAO();
		this.invitationDAO = new InvitationDAO();
		this.userDAO = new UserDAO();
	}
	
	public void acceptInvitation(String event_id) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		if(user!=null) {
			invitationDAO.respondInvitation(user.getUser_id(), event_id, "accepted");
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

}
