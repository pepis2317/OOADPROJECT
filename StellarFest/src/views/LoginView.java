package views;


import controllers.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import utils.Response;
import utils.Route;


public class LoginView extends View {
	private BorderPane borderPane;
	private GridPane gridPane;
	private VBox vbox;
	private Label emailLabel, passwordLabel;
	private TextField emailField;
	private PasswordField passwordField;
	private Button loginBtn;
	private Button registerBtn;
	private UserController userController;
	
	public LoginView() {
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

        passwordLabel = new Label("Password:");
        passwordField = new PasswordField();


        loginBtn = new Button("Login");
        registerBtn = new Button("Register");
        
        userController = new UserController();
	}
	
	@Override
	protected void layout() {

		gridPane.add(emailLabel, 0, 0);
		gridPane.add(emailField, 1, 0);
		gridPane.add(passwordLabel, 0, 1);
		gridPane.add(passwordField, 1, 1);
		
		vbox.getChildren().addAll(gridPane, loginBtn, registerBtn);
		
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
		passwordField.clear();
		
		setEventHandler();
	}
	
	private void setEventHandler() {
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
        
        if(!response.isSuccessful()) {
        	showAlert(Alert.AlertType.ERROR, "Error", response.getMessage());
        }
    }
}
