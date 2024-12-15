package controllers;


import java.util.ArrayList;
import java.util.List;
import dao.UserDAO;
import factories.UserFactory;
import models.User;
import utils.Response;
import utils.Route;
import utils.UserSession;

public class UserController {
	private UserDAO userDAO;
	private Route route;
	
	public UserController() {
		this.userDAO = new UserDAO();
		this.route = Route.getInstance();
	}
	
	public Response login(String email, String password) {
		if(email.isEmpty() || password.isEmpty()) {
			return new Response(false, "One or more fields are empty!");
		}
		
		User user = userDAO.getUserByEmail(email);
			
		if(user == null) {
			return new Response(false, "Email is not recognized!");
		}
		
		if(!user.getUser_password().equals(password)) {
			return new Response(false, "Password is incorrect!");
		}
		
		UserSession session = UserSession.getInstance();
		session.setUser(user);
		
		route.redirect("home");
		return new Response(true, "Success");
	}
	
	private Response checkRegisterInput(String email, String username, String password, String role) {
		if(email.isEmpty() || username.isEmpty() || password.isEmpty()) {
			return new Response(false, "One or more fields are empty!");
		}
		
		if(role == null) {
			return new Response(false, "Role has not been selected!");
		}
		
		if(userDAO.getUserByEmail(email) != null) {
			return new Response(false, "Found another user with the same email!");
		}
		
		if(userDAO.getUserByUsername(username) != null) {
			return new Response(false, "Found another user with the same username!");
		}
		
		if(password.length() < 5) {
			return new Response(false, "Password must be at least 5 characters long!");
		}
		
		return new Response(true, "Registered successfully.");
	}
	
	public Response register(String email, String username, String password, String role) {
		Response inputCheckResponse = checkRegisterInput(email, username, password, role);
		
		if(inputCheckResponse.isSuccessful()) {
			if(!userDAO.addUser(email, username, password, role)) {
				return new Response(false, "Register unsucessful: Something went wrong!");
			}
			
			route.redirect("login");
		}
		
		return inputCheckResponse;
	}
	
	private List<Response> checkChangeProfileInput(String email, String username, String oldPassword, String newPassword) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		
		List<Response> responses = new ArrayList<>();
		
		Response emailResponse = null;
		Response usernameResponse = null;
		Response passwordResponse = null;
				
		// If email/username are not different from current email/username, return successful response
		// so user does not get error alert
		
		if(!email.equals(user.getUser_email()) && userDAO.getUserByEmail(email) != null) {
			emailResponse = new Response(false, "Email not updated: Found another user with the same email!");
		}
		else {
			emailResponse = new Response(true, "Email updated successfully.");
		}
		
		if(!username.equals(user.getUser_name()) && userDAO.getUserByUsername(username) != null) {
			usernameResponse = new Response(false, "Username not updated: Found another user with the same username!");
		}
		else {
			usernameResponse = new Response(true, "Username updated successfully.");
		}
		
		if(newPassword.length() < 5) {
			passwordResponse = new Response(false, "Password not updated: New password must be at least 5 characters long!");
		}
		else if(!oldPassword.equals(user.getUser_password())) {
			passwordResponse = new Response(false, "Password not updated: Old password must be the same as the current password!");
		}
		else {
			passwordResponse = new Response(true, "Password updated successfully.");
		}
		
		responses.add(emailResponse);
		responses.add(usernameResponse);
		responses.add(passwordResponse);
		
		return responses;
	}
	
	public List<Response> changeProfile(String email, String username, String oldPassword, String newPassword) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		
//		Get responses for each input check
		List<Response> responses = checkChangeProfileInput(email, username, oldPassword, newPassword);
		Response emailResponse = responses.get(0);
		Response usernameResponse = responses.get(1);
		Response passwordResponse = responses.get(2);
		
		if(emailResponse.isSuccessful() 
				&& usernameResponse.isSuccessful() 
				&& passwordResponse.isSuccessful()) {
			
			if(userDAO.updateProfile(user.getUser_id(), email, username, newPassword)) {	
				session.setUser(UserFactory.create(user.getUser_id(), email, username, newPassword, user.getUser_role()));
			}
			else {
				emailResponse = new Response(false, "Email not updated: Something went wrong!");
				usernameResponse = new Response(false, "Username not updated: Something went wrong!");
				passwordResponse = new Response(false, "Password not updated: Something went wrong!");
				
				responses.set(0, emailResponse);
				responses.set(1, usernameResponse);
				responses.set(2, passwordResponse);			
			}
		}
		else if(emailResponse.isSuccessful()
				&& usernameResponse.isSuccessful()) {
			
			if(userDAO.updateProfile(user.getUser_id(), email, username, user.getUser_password())) {
				session.setUser(UserFactory.create(user.getUser_id(), email, username, user.getUser_password(), user.getUser_role()));
			}
			else {
				emailResponse = new Response(false, "Email not updated: Something went wrong!");
				usernameResponse = new Response(false, "Username not updated: Something went wrong!");
				
				responses.set(0, emailResponse);
				responses.set(1, usernameResponse);
			}
		}
		else if(emailResponse.isSuccessful()) {
			if(userDAO.updateProfile(user.getUser_id(), email, user.getUser_name(), user.getUser_password())) {
				session.setUser(UserFactory.create(user.getUser_id(), email, user.getUser_name(), user.getUser_password(), user.getUser_role()));			
			}
			else {
				emailResponse = new Response(false, "Email not updated: Something went wrong!");
				
				responses.set(0, emailResponse);
			}
		}
		
		return responses;
	}
}
