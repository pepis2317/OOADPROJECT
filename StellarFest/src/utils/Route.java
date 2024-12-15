package utils;

import java.util.HashMap;

import javafx.stage.Stage;

import views.AcceptedEventsView;
import views.AdminAllUsersView;
import views.AdminAllEventsView;
import views.ChangeProfileView;
import views.EventDetailsView;
import views.HomeView;
import views.LoginView;
import views.ManageProductsView;
import views.IParameterView;
import views.InvitationsView;

import views.RegisterView;
import views.TestView;
import views.View;

public class Route {
//	Class used for redirecting from one view to another
	
	private static Route instance;
	private static Stage stage = null;
	private static HashMap<String, View> routes = null;
	
	public static void createInstance(Stage primaryStage) {
		instance = new Route();
		instance.setStage(primaryStage);
		instance.init();
		instance.addRoutes();
	}
	
	public static Route getInstance() {	
		return instance;
	}

	private void setStage(Stage primaryStage) {
		stage = primaryStage;
	}
	
	private void init() {
		routes = new HashMap<String, View>();
	}
	
	private void addRoutes() {
//		Basic user routes
		routes.put("login", new LoginView());
		routes.put("register", new RegisterView());

		routes.put("changeProfile", new ChangeProfileView());
		routes.put("home", new HomeView());
		routes.put("eventDetails", new EventDetailsView());
		
//		Admin only routes
		routes.put("adminAllUsers", new AdminAllUsersView());
		routes.put("adminAllEvents", new AdminAllEventsView());
		
//		Vendor and Guest routes
		routes.put("organizedEvents", null);
		routes.put("acceptedEvents", new AcceptedEventsView());
		routes.put("invitations", new InvitationsView());
		
//		Vendor only route
		routes.put("manageProducts", new ManageProductsView());

	}
	
	public void redirect(String key) {
		View view = routes.get(key);
		
		if(view == null) {
			System.out.println("Redirect unsuccessful");
			return;
		}
		
		view.load();
		stage.setScene(view.getScene());
		stage.show();
		stage.centerOnScreen();
	}
	
	public void redirect(String key, HashMap<String, Object> params) {
		View view = routes.get(key);
		
		if(view == null || !(view instanceof IParameterView)) {
			System.out.println("Redirect unsuccessful");
			return;
		}
		

//		Set params to view and load the view
		IParameterView parameterView = (IParameterView)view;

		parameterView.setParams(params);
		
		view.load();
		stage.setScene(view.getScene());
		stage.show();
		stage.centerOnScreen();
	}

}
