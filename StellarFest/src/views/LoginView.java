package views;


import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;
import singletons.UserSession;

public class LoginView  {
	private final Stage primaryStage;
	public LoginView(Stage stage) {
        this.primaryStage = stage;
    }

    public void show() {
        VBox layout = new VBox();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button goToRegisterButton = new Button("Go to Register");

        loginButton.setOnAction(e -> handleLogin(emailField.getText(), passwordField.getText()));
        goToRegisterButton.setOnAction(e -> new RegisterView(primaryStage).show());

        layout.getChildren().addAll(
            emailLabel, emailField,
            passwordLabel, passwordField,
            loginButton, goToRegisterButton
        );

        Scene loginScene = new Scene(layout, 300, 300);
        primaryStage.setScene(loginScene);
    }

    private void handleLogin(String email, String password) {
        UserController.login(email, password);
        UserSession session = UserSession.getInstance();
        User user = session.getUser();
        if(user.getUser_role().equals("admin")) {
        	new AdminEventsView(primaryStage).show();
        }else if(user.getUser_role().equals("eventorganizer")) {
        	
        }else if(user.getUser_role().equals("guest")) {
        	
        }else if(user.getUser_role().equals("vendor")) {
        	
        }
    }
}
