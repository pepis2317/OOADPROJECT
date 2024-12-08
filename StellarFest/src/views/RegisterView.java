package views;

import java.util.HashMap;

import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class RegisterView {
	private final Stage primaryStage;

    public RegisterView(Stage stage) {
        this.primaryStage = stage;
    }

    public void show() {
        VBox layout = new VBox();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Label roleLabel = new Label("Select Role:");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "Vendor", "Guest", "Event Organizer"); // Add roles here
        roleComboBox.setPromptText("Choose your role");

        Button registerButton = new Button("Register");
        Button goToLoginButton = new Button("Back to Login");

        registerButton.setOnAction(e -> handleRegister(
        		emailField.getText(),
                usernameField.getText(),
                passwordField.getText(),
                roleComboBox.getValue()
            ));
        goToLoginButton.setOnAction(e -> new LoginView(primaryStage).show());

        layout.getChildren().addAll(
                usernameLabel, usernameField,
                emailLabel, emailField,
                passwordLabel, passwordField,
                roleLabel, roleComboBox,
                registerButton, goToLoginButton
            );

        Scene registerScene = new Scene(layout, 300, 300);
        primaryStage.setScene(registerScene);
    }

    private void handleRegister(String email, String username, String password, String role) {
    	HashMap<String, String> map = new HashMap<>();
    	map.put("Admin", "admin");
    	map.put("Vendor", "vendor");
    	map.put("Guest", "guest");
    	map.put("Event Organizer", "eventorganizer");
    	String selectedRole = map.get(role);
    	if(UserController.checkRegisterInput(email, username, password)) {
    		UserController.register(email, selectedRole, password, role);
    	}
        new LoginView(primaryStage).show();
    }
}
