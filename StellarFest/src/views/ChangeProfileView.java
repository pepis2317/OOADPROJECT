package views;

import java.util.List;

import controllers.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.User;
import utils.Response;
import utils.UserSession;

public class ChangeProfileView extends View {
	private BorderPane borderPane;
	private GridPane gridPane;
	private VBox vbox;
	private MenuBar menuBar;
	private Label emailLabel, usernameLabel, oldPasswordLabel, newPasswordLabel;
	private TextField emailField, usernameField, oldPasswordField, newPasswordField;
	private Button changeProfileBtn;
	private UserController userController;
	
    public ChangeProfileView() {
    	super();
    }
    
    @Override
    protected void init() {
    	borderPane = new BorderPane();
    	this.scene = new Scene(borderPane, 400, 400);
    	
    	gridPane = new GridPane();
    	vbox = new VBox();
    	
    	emailLabel = new Label("Email:");
        emailField = new TextField();
        
        usernameLabel = new Label("Username:");
        usernameField = new TextField();

        oldPasswordLabel = new Label("Old Password:");
        oldPasswordField = new PasswordField();
        
        newPasswordLabel = new Label("New Password:");
        newPasswordField = new PasswordField();
        
        changeProfileBtn = new Button("Change Profile");
        
        userController = new UserController();
	}
	
    @Override
    protected void layout() {
    	gridPane.add(emailLabel, 0, 0);
    	gridPane.add(emailField, 1, 0);
    	gridPane.add(usernameLabel, 0, 1);
    	gridPane.add(usernameField, 1, 1);
    	gridPane.add(oldPasswordLabel, 0, 2);
    	gridPane.add(oldPasswordField, 1, 2);
    	gridPane.add(newPasswordLabel, 0, 3);
    	gridPane.add(newPasswordField, 1, 3);
    	    	
		vbox.getChildren().addAll(gridPane, changeProfileBtn);
		
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
    	TopMenuBar topMenu = new TopMenuBar();
        menuBar = topMenu.initializeMenuBar();
        
        borderPane.setTop(menuBar);
    	
    	UserSession session = new UserSession();
    	User user = session.getUser();
    	
    	emailField.setText(user.getUser_email());
    	usernameField.setText(user.getUser_name());
    	oldPasswordField.clear();
    	newPasswordField.clear();
    	
		setEventHandler();
	}

	protected void setEventHandler() {
		changeProfileBtn.setOnAction((e) -> {
			changeProfile(emailField.getText(), usernameField.getText(), 
					oldPasswordField.getText(), newPasswordField.getText());
		});
	}

    private void changeProfile(String email, String username, String oldPassword, String newPassword) {
    	List<Response> responses = userController.changeProfile(email, username, oldPassword, newPassword);
    	
    	for(Response response : responses) {
    		if(response.isSuccessful()) {
    			showAlert(Alert.AlertType.INFORMATION, "Success", response.getMessage());
    		}
    		else {
    			showAlert(Alert.AlertType.ERROR, "Error", response.getMessage());
    		}
    	}
    }
}
