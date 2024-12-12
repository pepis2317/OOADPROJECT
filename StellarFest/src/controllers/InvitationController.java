package controllers;

import java.util.List;

import dao.InvitationDAO;
import dao.UserDAO;
import models.Invitation;
import models.User;
import utils.UserSession;

public class InvitationController {
	private InvitationDAO invitationDAO;
	private UserDAO userDAO;
	
	public InvitationController() {
		this.invitationDAO = new InvitationDAO();
		this.userDAO = new UserDAO();
	}
	
	public void sendInvitation(String event_id, String user_email) {
		if(!user_email.isBlank()) {
			User user = userDAO.getUserByEmail(user_email);
			invitationDAO.createInvitation(event_id, user.getUser_id(), user.getUser_role());
		}
	}
	public List<Invitation> getInvitations(){
		return invitationDAO.getInvitations();
	}
	public List<Invitation> getPendingInvitations(){
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		return invitationDAO.getPendingInvitations(user.getUser_id());
	}
	public void acceptInvitation(String event_id) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		if(user!=null) {
			invitationDAO.respondInvitation(user.getUser_id(), event_id, "accepted");
		}
	}
	public void declineInvitation(String event_id) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		if(user!=null) {
			invitationDAO.respondInvitation(user.getUser_id(), event_id, "declined");
		}
	}
}
