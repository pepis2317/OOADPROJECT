package utils;

import java.util.HashMap;

import javafx.stage.Stage;
import views.ChangeProfileView;
import views.HomeView;
import views.LoginView;
import views.ParameterView;
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
		routes.put("login", new LoginView());
		routes.put("register", new RegisterView());
		routes.put("changeProfile", new ChangeProfileView());
		routes.put("home", new HomeView());
		routes.put("test", new TestView());
	}
	
	public void redirect(String key) {
		View view = routes.get(key);
		
		if(view == null) {
			return;
		}
		
		view.load();
		stage.setScene(view.getScene());
		stage.show();
	}
	
	public void redirect(String key, HashMap<String, Object> params) {
		View view = routes.get(key);
		
		if(view == null || !(view instanceof ParameterView)) {
			return;
		}
		
//		Set params to view and load the view
		ParameterView parameterView = (ParameterView)view;
		parameterView.setParams(params);
		
		view.load();
		stage.setScene(view.getScene());
		stage.show();
	}

}
