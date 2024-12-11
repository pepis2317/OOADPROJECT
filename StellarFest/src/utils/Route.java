package utils;

import java.util.HashMap;

import javafx.stage.Stage;
import views.LoginView;
import views.RegisterView;
import views.View;

public class Route {
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
		routes.put("login", new LoginView());
		routes.put("register", new RegisterView());
	}
	
	public void redirect(String key) {
		View view = routes.get(key);
		
		if(view == null) {
			return;
		}
		
		stage.setScene(view.getScene());
		stage.show();
	}

}
