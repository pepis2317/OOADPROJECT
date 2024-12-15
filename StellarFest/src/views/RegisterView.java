package views;

import java.util.HashMap;

import controllers.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import utils.Response;
import utils.Route;

public class RegisterView extends View {
	private BorderPane borderPane;
	private GridPane gridPane;
	private VBox vbox;
	private Label emailLabel, usernameLabel, passwordLabel, roleLabel;
	private TextField emailField, usernameField, passwordField;
	private ComboBox<String> roleComboBox;
	private Button registerBtn, loginBtn;
	private HashMap<String, String> roleMap;
	private UserController userController;
	
    public RegisterView() {
    	super();
    }

    
    @Override
    protected void init() {
    	borderPane = new BorderPane();
    	this.scene = new Scene(borderPane, 400, 300);
    	
    	gridPane = new GridPane();
    	vbox = new VBox();
    	
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
        
        roleMap.put("Admin", "admin");
    	roleMap.put("Vendor", "vendor");
    	roleMap.put("Guest", "guest");
    	roleMap.put("Event Organizer", "eventorganizer");
    	
    	gridPane.add(emailLabel, 0, 0);
    	gridPane.add(emailField, 1, 0);
    	gridPane.add(usernameLabel, 0, 1);
    	gridPane.add(usernameField, 1, 1);
    	gridPane.add(passwordLabel, 0, 2);
    	gridPane.add(passwordField, 1, 2);
    	gridPane.add(roleLabel, 0, 3);
    	gridPane.add(roleComboBox, 1, 3);
    	
		vbox.getChildren().addAll(gridPane, registerBtn, loginBtn);
		
		borderPane.setCenter(vbox);
	}
	
    @Override
    protected void style() {
    	gridPane.setAlignment(Pos.CENTER);
    	gridPane.setVgap(10);
    	gridPane.setHgap(10);
    	
		vbox.setSpacing(20);
		vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
	}
    
    @Override
	public void load() {
    	emailField.clear();
    	usernameField.clear();
    	passwordField.clear();
    	roleComboBox.valueProperty().set(null);
    	
		setEventHandler();
	}
	
    private void setEventHandler() {
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
    		
    	Response response = userController.register(email, username, password, selectedRole);
    	
    	if(response.isSuccessful()) {
    		showAlert(Alert.AlertType.INFORMATION, "Registered Successfully", response.getMessage());
    	}
    	else {
    		showAlert(Alert.AlertType.ERROR, "Error in Registering", response.getMessage());
    	}
    }
}
