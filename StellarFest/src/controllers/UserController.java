package controllers;

import dao.UserDAO;
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
		User user = userDAO.getUserByEmail(email);
		
		if(email.isEmpty() || password.isEmpty()) {
			return new Response(false, "One or more fields are empty!");
		}
			
		if(user == null) {
			return new Response(false, "Email is not recognized!");
		}
		
		if(!user.getUser_password().equals(password)) {
			return new Response(false, "Password is incorrect!");
		}
		
		UserSession session = UserSession.getInstance();
		session.setUser(user);
		
//		route.redirect("home");
		return new Response(true, "Success");
	}
	
	private Response checkRegisterInput(String email, String username, String password) {
		if(email.isEmpty() || username.isEmpty() || password.isEmpty()) {
			return new Response(false, "One or more fields are empty!");
		}
		
		if(userDAO.getUserByEmail(email) != null || userDAO.getUserByUsername(username) != null) {
			return new Response(false, "Found another user with the same email or username!");
		}
		
		if(password.length() < 5) {
			return new Response(false, "Password must be at least 5 characters long!");
		}
		
		return new Response(true, "Success");
	}
	
	public Response register(String email, String username, String password, String role) {
		Response inputCheckResponse = checkRegisterInput(email, username, password);
		
		if(inputCheckResponse.isSuccessful()) {
			if(!userDAO.addUser(email, username, password, role)) {
				return new Response(false, "Error in creating new user!");
			}
			
			route.redirect("login");
		}
		
		return inputCheckResponse;
	}
	
}
