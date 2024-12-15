package views;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import models.User;
import utils.UserSession;

public class HomeView extends View {
	private BorderPane borderPane;
	private VBox vbox;
	private MenuBar menuBar;
	private Label welcomeLabel, roleLabel;
	
    public HomeView() {
    	super();
    }
    
    @Override
    protected void init() {
    	borderPane = new BorderPane();
    	this.scene = new Scene(borderPane, 600, 600);
    	
    	vbox = new VBox();
    	
    	welcomeLabel = new Label();
    	roleLabel = new Label();
	}
	
    @Override
    protected void layout() {
    	vbox.getChildren().addAll(welcomeLabel, roleLabel);
    	
		borderPane.setCenter(vbox);
	}
	
    @Override
    protected void style() {
		vbox.setSpacing(10);
		vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
		
    	welcomeLabel.setFont(new Font(32));
    	roleLabel.setFont(new Font(16));
	}
    
    @Override
	public void load() {
    	UserSession session = UserSession.getInstance();
    	User user = session.getUser();
    	
    	welcomeLabel.setText("Welcome, " + user.getUser_name() + "!");
    	
    	roleLabel.setText("Your current role is " + 
    			user.getUser_role().substring(0, 1).toUpperCase() + 
    			user.getUser_role().substring(1) + ".");
    	
    	TopMenuBar topMenu = new TopMenuBar();
        menuBar = topMenu.initializeMenuBar();
        
        borderPane.setTop(menuBar);	
	}
}
