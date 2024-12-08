package views;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import models.EventOrganizer;
import models.User;
import singletons.UserSession;

public abstract class TopMenuBar {
	public MenuBar initializeMenuBar(Stage primaryStage) {
		UserSession session = UserSession.getInstance();
//		User user = session.getUser();
		User user = new EventOrganizer("1", "placeholder mail", "placeholder name", "pass", "admin");
    	Menu menu = new Menu("Menu");
        MenuItem logout = new MenuItem("Logout");
        MenuItem editProfile = new MenuItem("Edit Profile");
        editProfile.setOnAction(e->{
        	new EditProfileView(primaryStage).show();
        });
        menu.getItems().add(editProfile);
        menu.getItems().add(logout);
        if(user.getUser_role().equals("admin")) {
        	MenuItem viewAllUsers = new MenuItem("View All Users");
            MenuItem viewAllEvents = new MenuItem("View All Events");
            viewAllEvents.setOnAction(e->{
            	new AdminEventsView(primaryStage).show();
            });
            viewAllUsers.setOnAction(e->{
            	new AdminAllUsersView(primaryStage).show();
            });
            menu.getItems().add(viewAllEvents);
            menu.getItems().add(viewAllUsers);
        }else if (user.getUser_role().equals("eventorganizer")) {
        	MenuItem viewOrganizedEvents = new MenuItem("View Organized Events");
        	viewOrganizedEvents.setOnAction(e->{
        		
        	});
        }else if(user.getUser_role().equals("vendor") || user.getUser_role().equals("guest")) {
        	MenuItem viewAcceptedEvents = new MenuItem("View Accepted Events");
        	MenuItem viewInvitations = new MenuItem("View Invitations");
        	viewAcceptedEvents.setOnAction(e->{
        		
        		
        	});
        	viewInvitations.setOnAction(e->{
        		
        		
        	});
        	menu.getItems().add(viewAcceptedEvents);
        	menu.getItems().add(viewInvitations);
        }
        
        
       
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        return menuBar;
    }

}
