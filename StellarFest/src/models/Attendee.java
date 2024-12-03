package models;

import java.util.List;

public abstract class Attendee extends User{
	private List<Invitation> accepted_invitations;
	public Attendee(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
		this.accepted_invitations = null;
		// TODO Auto-generated constructor stub
	}
	public List<Invitation> getAccepted_invitation() {
		return accepted_invitations;
	}
	public void setAccepted_invitation(List<Invitation> accepted_invitation) {
		this.accepted_invitations = accepted_invitation;
	}
	//ini bedua harusnya diimplement di guest sama vendor controller, sini ditulis spy g lupa aj
	public abstract void acceptInvitation(String event_id);
	public abstract void viewAcceptedEvents(String user_email);
	
}
