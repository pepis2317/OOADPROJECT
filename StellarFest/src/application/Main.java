package application;
	


import controllers.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import repositories.InvitationRepository;
import views.LoginView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Login and Register Example");
        new LoginView(primaryStage).show();
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
