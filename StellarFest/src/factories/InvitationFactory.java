package factories;

import models.Invitation;

public class InvitationFactory {
	public static Invitation create(String invitation_id, String event_id, String user_id, String invitation_status, String invitation_role) {
		return new Invitation(invitation_id, event_id, user_id, invitation_status, invitation_role);
	}
}
