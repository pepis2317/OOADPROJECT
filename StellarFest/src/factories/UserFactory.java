package factories;

import models.Admin;
import models.EventOrganizer;
import models.Guest;
import models.User;
import models.Vendor;

public class UserFactory {
	public static User create(String user_id, String user_email, String user_name, String user_password, String user_role) {
		if(user_role.toLowerCase().equals("admin")) {
			return new Admin(user_id, user_email, user_name, user_password, user_role);
		}else if (user_role.toLowerCase().equals("guest")) {
			return new Guest(user_id, user_email, user_name, user_password, user_role);
		}else if (user_role.toLowerCase().equals("vendor")) {
			return new Vendor(user_id, user_email, user_name, user_password, user_role);
		}else if (user_role.toLowerCase().equals("eventorganizer")) {
			return new EventOrganizer(user_id, user_email, user_name, user_password, user_role);
		}
		return null;
	}
	
}
