package controllers;

import java.util.List;

import models.Invitation;
import models.User;
import repositories.InvitationRepository;
import repositories.UserRepository;

public class InvitationController {
	//acceptinvitation kenapa di invitation???
	public static void sendInvitation(String event_id, String user_email) {
		if(!user_email.isBlank()) {
			User user = UserRepository.getUserByEmail(user_email);
			InvitationRepository.createInvitation(event_id, user.getUser_id(), user.getUser_role());
		}
	}
	public static List<Invitation> getInvitations(){
		return InvitationRepository.getInvitations();
	}
}
