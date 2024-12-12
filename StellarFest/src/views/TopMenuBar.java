package views;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import models.User;
import utils.UserSession;
import utils.Route;

public class TopMenuBar{
	private Route route;
	private User user;
	private Menu menu;
	private MenuItem logout;
	private MenuItem editProfile;
	private MenuBar menuBar;
	private MenuItem viewAllUsers;
	private MenuItem viewAllEvents;
	private MenuItem viewOrganizedEvents;
	private MenuItem viewAcceptedEvents;
	private MenuItem viewInvitations;
	private MenuItem manageProducts;
	
	public TopMenuBar(){
		this.route = new Route();
		UserSession session = UserSession.getInstance();
		this.user = session.getUser();
	}
	public MenuBar initializeMenuBar() {
    	menu = new Menu("Menu");
        logout = new MenuItem("Logout");
        editProfile = new MenuItem("Edit Profile");
        editProfile.setOnAction(e->{
        	route.redirect("edit");
        });
        menu.getItems().add(editProfile);
        menu.getItems().add(logout);
        if(user!=null) {
        	if(user.getUser_role().equals("admin")) {
            	initializeAdminMenu();
            }else if (user.getUser_role().equals("eventorganizer")) {
            	initializeOrganizerMenu();
            }else if(user.getUser_role().equals("vendor") || user.getUser_role().equals("guest")) {
            	initializeAttendeeMenu();
            }
        }
        menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        return menuBar;
    }
	private void initializeAdminMenu() {
		viewAllUsers = new MenuItem("View All Users");
        viewAllEvents = new MenuItem("View All Events");
        viewAllEvents.setOnAction(e->{
        	route.redirect("adminEvents");
        });
        viewAllUsers.setOnAction(e->{
        	route.redirect("adminAllUsers");
        });
        menu.getItems().add(viewAllEvents);
        menu.getItems().add(viewAllUsers);
		
	}
	private void initializeOrganizerMenu() {
		viewOrganizedEvents = new MenuItem("View Organized Events");
    	viewOrganizedEvents.setOnAction(e->{
    		route.redirect("organizedEvents");
    	});
	}
	private void initializeAttendeeMenu() {
		viewAcceptedEvents = new MenuItem("View Accepted Events");
    	viewInvitations = new MenuItem("View Invitations");
    	viewAcceptedEvents.setOnAction(e->{
    		route.redirect("acceptedEvents");
    	});
    	viewInvitations.setOnAction(e->{
    		route.redirect("invitations");
    	});
    	if(user.getUser_role().equals("vendor")) {
    		manageProducts = new MenuItem("Manage Products");
    		manageProducts.setOnAction(e->{
    			route.redirect("manageProducts");
    		});
    		menu.getItems().add(manageProducts);
    	}
    	menu.getItems().add(viewAcceptedEvents);
    	menu.getItems().add(viewInvitations);
		
	}

}
