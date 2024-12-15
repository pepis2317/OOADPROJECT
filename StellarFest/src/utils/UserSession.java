package utils;

import models.User;

public class UserSession {
//	Class used to store data of user that is logged in
	
	private static volatile UserSession instance;
	private static User user = null;
	
	public static UserSession getInstance() {
		if(instance == null) {
			synchronized(UserSession.class) {
				if(instance == null) {
					instance = new UserSession();
				}
			}
		}
		
		return instance;
	}
	
	public void setUser(User user) {
		UserSession.user = user;
	}
	
	public User getUser() {
		return UserSession.user;
	}
}
