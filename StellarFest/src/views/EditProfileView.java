package views;

import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.EventOrganizer;
import models.User;
import singletons.UserSession;

public class EditProfileView extends TopMenuBar{
	private final Stage primaryStage;
	private User user;
	public EditProfileView(Stage stage) {
		this.primaryStage = stage;
		UserSession session = UserSession.getInstance();
		this.user = session.getUser();

	}
	public void show() {
        VBox layout = new VBox();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setText(user.getUser_email());
        
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setText(user.getUser_name());

        Label newPasswordLabel = new Label("New Password:");
        PasswordField newPasswordField = new PasswordField();
        Label oldPasswordLabel = new Label("Old Password:");
        PasswordField oldPasswordField = new PasswordField();

        Button editButton  = new Button("Edit");
        editButton.setOnAction(e->{
        	handleUpdate(emailField.getText(), usernameField.getText(), newPasswordField.getText(), oldPasswordField.getText());
        });


        layout.getChildren().addAll(
                usernameLabel, 
                usernameField,
                emailLabel, 
                emailField,
                newPasswordLabel, 
                newPasswordField,
                oldPasswordLabel,
                oldPasswordField,
                editButton
            );
        MenuBar menuBar = initializeMenuBar(primaryStage);
        VBox container = new VBox();
        container.getChildren().addAll(menuBar, layout);
        
        Scene registerScene = new Scene(container, 300, 300);
        primaryStage.setScene(registerScene);
    }
	public void handleUpdate(String email, String username, String newPassword, String oldPassword) {
		UserController.changeProfile(email, username, oldPassword, newPassword);
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
