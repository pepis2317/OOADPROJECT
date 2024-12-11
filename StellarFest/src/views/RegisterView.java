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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;
import utils.Response;
import utils.Route;
public class RegisterView extends View {
	private BorderPane borderPane;
	private VBox vbox;
	private Label emailLabel, usernameLabel, passwordLabel, roleLabel;
	private TextField emailField, usernameField, passwordField;
	private ComboBox<String> roleComboBox;
	private Button registerBtn, loginBtn;
	private HashMap<String, String> roleMap;
	private UserController userController;
	
    public RegisterView() {
    	super();
    	init();
        layout();
        style();
        setEventHandler();
    }
    
    @Override
    protected void init() {
    	borderPane = new BorderPane();
    	this.scene = new Scene(borderPane, 600, 400);
    	
    	vbox = new VBox(20);
    	
    	emailLabel = new Label("Email:");
        emailField = new TextField();
        
        usernameLabel = new Label("Username:");
        usernameField = new TextField();

        passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        roleLabel = new Label("Select Role:");
        roleComboBox = new ComboBox<>();

        registerBtn = new Button("Register");
        loginBtn = new Button("Login");
        
        roleMap = new HashMap<>();
        
        userController = new UserController();
	}
	
    @Override
    protected void layout() {
		roleComboBox.getItems().addAll("Admin", "Vendor", "Guest", "Event Organizer");
        roleComboBox.setPromptText("Choose your role");
        
        roleMap.put("Admin", "admin");
    	roleMap.put("Vendor", "vendor");
    	roleMap.put("Guest", "guest");
    	roleMap.put("Event Organizer", "eventorganizer");
    	
		vbox.getChildren().addAll(emailLabel, emailField, usernameLabel, usernameField, passwordLabel, 
				passwordField, roleLabel, roleComboBox, registerBtn, loginBtn);
		
		borderPane.setCenter(vbox);
	}
	
    @Override
    protected void style() {
		vbox.setSpacing(10);
		vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
	}
	
    @Override
    protected void setEventHandler() {
		registerBtn.setOnAction((e) -> {
			register(emailField.getText(), usernameField.getText(), passwordField.getText(), roleComboBox.getValue());
		});
		
		loginBtn.setOnAction((e) -> {
			Route route = Route.getInstance();
			route.redirect("login");
		});
	}

    private void register(String email, String username, String password, String role) {
    	String selectedRole = roleMap.get(role);
    	
    	if(selectedRole == null) {
    		showAlert(Alert.AlertType.ERROR, "Error", "No role selected!");
    		
    		return;
    	}
    		
    	Response response = userController.register(email, username, password, selectedRole);
    	
    	if(!response.isSuccessful()) {
    		showAlert(Alert.AlertType.ERROR, "Error", response.getMessage());
    	}
    }
}
