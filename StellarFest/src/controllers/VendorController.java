package controllers;

import java.util.List;

import dao.EventDAO;
import dao.ProductDAO;
import dao.UserDAO;
import models.Event;
import models.Product;
import models.User;
import utils.Response;
import utils.Route;
import utils.UserSession;

public class VendorController {
	private EventDAO eventDAO;
	private ProductDAO productDAO;
	private UserDAO userDAO;
	private Route route;
	
	public VendorController() {
		this.eventDAO = new EventDAO();
		this.productDAO = new ProductDAO();
		this.userDAO = new UserDAO();
		this.route = Route.getInstance();
	}
	
	public List<Event> viewAcceptedEvents(String user_email){
		if(user_email.isEmpty()) {
			return null;
		}
		
		User user = userDAO.getUserByEmail(user_email);
		
		if(user != null) {
			return eventDAO.getAcceptedEvents(user.getUser_id());
		}
		
		return null;
	}
	
	private Response checkManagerVendorInput(String product_description, String product_name) {
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		
		if(product_description.isBlank() || product_name.isBlank()) {
			return new Response(false, "Product name and description cannot be empty!");
		}
		if(product_description.length() > 200) {
			return new Response(false, "Product description cannot be more than 200 characters long!");
		}
		
		
		return new Response(true, "Product " + product_name + " was added successfully!");
	}

	public Response manageVendor(String product_description, String product_name){
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		
		if(user == null) {
			route.redirect("login");
			return new Response(false, "User not found!");
		}
		
		Response response = checkManagerVendorInput(product_description, product_name);
		
		if(response.isSuccessful()) {
			if(productDAO.addProduct(product_name, product_description, user.getUser_id())) {
				return new Response(true, "Product " + product_name + " was added successfully!");
			}
			else {
				return new Response(false, "Something went wrong!");
			}
		}
		
		return response;
	}
	
	public List<Product> viewProducts(){
		UserSession session = UserSession.getInstance();
		User user = session.getUser();
		
		if(user == null) {
			route.redirect("login");
			return null;
		}
		
		return productDAO.getProducts(user.getUser_id());
	}
}
