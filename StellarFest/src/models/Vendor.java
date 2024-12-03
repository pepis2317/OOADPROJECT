package models;


public class Vendor extends Attendee {

	public Vendor(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void acceptInvitation(String event_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewAcceptedEvents(String user_email) {
		// TODO Auto-generated method stub
		
	}

}
