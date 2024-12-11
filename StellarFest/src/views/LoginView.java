package views;


import controllers.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;
import utils.Response;
import utils.Route;

public class LoginView extends View {
	private BorderPane borderPane;
	private VBox vbox;
	private Label emailLabel, passwordLabel;
	private TextField emailField;
	private PasswordField passwordField;
	private Button loginBtn;
	public Button registerBtn;
	private UserController userController;
	
	public LoginView() {
		super();
        init();
        layout();
        style();
        setEventHandler();
    }
	
	@Override
	protected void init() {
		borderPane = new BorderPane();
		this.scene = new Scene(borderPane, 600, 300);
		vbox = new VBox(20);
		
        emailLabel = new Label("Email:");
        emailField = new TextField();

        passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        loginBtn = new Button("Login");
        registerBtn = new Button("Register");
        
        userController = new UserController();
	}
	
	@Override
	protected void layout() {
		vbox.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField, loginBtn, registerBtn);
		
		borderPane.setCenter(vbox);
	}
	
	@Override
	protected void style() {
		vbox.setSpacing(10);
		vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
	}
	
	@Override
	protected void setEventHandler() {
		 loginBtn.setOnAction((e) -> {
			 login(emailField.getText(), passwordField.getText());
		 });
		 
		 registerBtn.setOnAction((e) -> {
			 Route route = Route.getInstance();
			 route.redirect("register");
		 });
	}

    private void login(String email, String password) {
        Response response = userController.login(email, password);
        
        if(response.isSuccessful()) {
        	showAlert(Alert.AlertType.CONFIRMATION, "Success", response.getMessage());
        }
        
        if(!response.isSuccessful()) {
        	showAlert(Alert.AlertType.ERROR, "Error", response.getMessage());
        }
    }
}
