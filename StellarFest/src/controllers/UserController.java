package controllers;

import models.User;
import repositories.UserRepository;
import singletons.UserSession;

public class UserController {
	public static void register(String user_email, String user_name, String user_password, String user_role) {
		if(checkRegisterInput(user_email,user_name, user_password)) {
			UserRepository.register(user_email, user_name, user_password, user_role);
		}
	}
	public static void login(String user_email, String user_password) {
		if(!user_email.isBlank()&&!user_password.isBlank()) {
			User user = UserRepository.login(user_email, user_password);
			if(user!=null) {
				UserSession session = UserSession.getInstance();
				session.setUser(user);
			}
		}
	}
	public static void changeProfile(String user_email, String user_name, String old_password, String new_password) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		if(user!=null && checkChangeProfileInput(user_email,user_name, old_password, new_password)) {
			UserRepository.changeProfile(user.getUser_id(), user_email, user_name, new_password);
		}
	}
	public static User getUserByEmail(String user_email) {
		if(user_email.isBlank()) {
			return null;
		}
		return UserRepository.getUserByEmail(user_email);
	}
	public static User getUserByUsername(String user_name) {
		if(user_name.isBlank()) {
			return null;
		}
		return UserRepository.getUserByUsername(user_name);
	}
	public static boolean checkRegisterInput(String user_email, String user_name, String user_password) {
		if(user_email.isBlank()|| user_name.isBlank()||user_password.isBlank()) {
			return false;
		}
		User user = UserRepository.getUserByEmail(user_email);
		if(user!=null) {
			return false;
		}
		user = UserRepository.getUserByUsername(user_name);
		if(user!=null) {
			return false;
		}
		if(user_password.length() < 5) {
			return false;
		}
		return true;
	}
	public static boolean checkChangeProfileInput(String user_email, String user_name, String old_password, String new_password) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		if(!old_password.equals(user.getUser_password())) {
			return false;
		}
		if(!checkRegisterInput(user_email, user_name, new_password)) {
			return false;
		}
		return true;
	}
	
}
