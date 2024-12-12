package application;
	


import controllers.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.Route;
import views.AdminEventsView;

import views.LoginView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private Stage primaryStage;
	private Route route;
	
	public void init() {
		Route.createInstance(primaryStage);
		route = Route.getInstance();
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("StellarFest");
		
		this.primaryStage = primaryStage;
		init();
		route.redirect("login");
	}
	public static void main(String[] args) {
		launch(args);
	}

}
