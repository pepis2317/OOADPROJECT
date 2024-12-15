package controllers;

import java.util.List;

import dao.InvitationDAO;
import dao.UserDAO;
import models.Invitation;
import models.User;
import utils.Response;
import utils.Route;
import utils.UserSession;

public class InvitationController {
	private InvitationDAO invitationDAO;
	private UserDAO userDAO;
	private Route route;
	
	public InvitationController() {
		this.invitationDAO = new InvitationDAO();
		this.userDAO = new UserDAO();
		this.route = Route.getInstance();
	}
	
	public Response sendInvitation(String event_id, String user_email) {
		if(!user_email.isEmpty()) {
			User user = userDAO.getUserByEmail(user_email);
			
			if(invitationDAO.createInvitation(event_id, user.getUser_id(), user.getUser_role())) {
				return new Response(true, "Invitation sent to " + 
						userDAO.getUserByEmail(user_email).getUser_name() + 
						" successfully.");
			}
			else {
				return new Response(false, "Something went wrong!");
			}
		}
		
		return new Response(false, "Email field must not be empty!");
	}
	public List<Invitation> getInvitations(){
		return invitationDAO.getInvitations();
	}
	public List<Invitation> getPendingInvitations(){
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		return invitationDAO.getPendingInvitations(user.getUser_id());
	}
	public Response acceptInvitation(String event_id) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		
		if(user != null) {
			if(invitationDAO.respondInvitation(user.getUser_id(), event_id, "accepted")) {
				return new Response(true, "Invitation accepted succesfully.");
			}
			else {
				return new Response(false, "Something went wrong!");
			}
		}
		
		route.redirect("login");
		return new Response(false, "User not found!");
	}
	public Response declineInvitation(String event_id) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		
		if(user != null) {
			if(invitationDAO.respondInvitation(user.getUser_id(), event_id, "declined")) {
				return new Response(true, "Invitation declined succesfully.");
			}
			else {
				return new Response(false, "Something went wrong!");
			}
		}
		
		route.redirect("login");
		return new Response(false, "User not found!");
	}
}
