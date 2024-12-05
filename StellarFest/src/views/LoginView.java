package views;


import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView  {
	private final Stage primaryStage;
	public LoginView(Stage stage) {
        this.primaryStage = stage;
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button goToRegisterButton = new Button("Go to Register");

        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        goToRegisterButton.setOnAction(e -> new RegisterView(primaryStage).show());

        layout.getChildren().addAll(
            usernameLabel, usernameField,
            passwordLabel, passwordField,
            loginButton, goToRegisterButton
        );

        Scene loginScene = new Scene(layout, 300, 200);
        primaryStage.setScene(loginScene);
    }

    private void handleLogin(String username, String password) {
        if ("user".equals(username) && "password".equals(password)) {
            showAlert("Login Successful", "Welcome, " + username + "!");
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
