package controllers;

import java.util.List;

import dao.InvitationDAO;
import dao.UserDAO;
import models.Invitation;
import models.User;

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
}
