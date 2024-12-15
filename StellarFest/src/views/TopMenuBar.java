package views;

import controllers.UserController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import models.User;
import utils.UserSession;
import utils.Route;

public class TopMenuBar{
	private Route route;
	private User user;
	private MenuBar menuBar;
	private Menu homeMenu, accountMenu, invitationMenu, eventMenu, productMenu, adminMenu;
	private MenuItem homeMI;
	private MenuItem logoutMI;
	private MenuItem changeProfileMI;
	private MenuItem manageAllUsersMI;
	private MenuItem manageAllEventsMI;
	private MenuItem manageOrganizedEventsMI;
	private MenuItem viewAcceptedEventsMI;
	private MenuItem viewInvitationsMI;
	private MenuItem manageProductsMI;
	
	public TopMenuBar(){
		this.route = new Route();
		UserSession session = UserSession.getInstance();
		this.user = session.getUser();
	}
	public MenuBar initializeMenuBar() {
		menuBar = new MenuBar();
		
		homeMenu = new Menu("Home");
    	accountMenu = new Menu("Account");
    	invitationMenu = new Menu("Invitation");
    	eventMenu = new Menu("Event");
    	productMenu = new Menu("Product");
    	adminMenu = new Menu("Admin");
    	
    	homeMI = new MenuItem("Return to Home");
    	
    	homeMI.setOnAction((e) -> {
    		route.redirect("home");
    	});
    	
    	homeMenu.getItems().add(homeMI);
    	
        logoutMI = new MenuItem("Logout");
        changeProfileMI = new MenuItem("Edit Profile");
        
        logoutMI.setOnAction((e) -> {
        	UserController userController = new UserController();
        	userController.logout();
        });
        changeProfileMI.setOnAction(e->{
        	route.redirect("changeProfile");
        });
        
        accountMenu.getItems().addAll(changeProfileMI, logoutMI);
        menuBar.getMenus().addAll(homeMenu, accountMenu);
        
        if(user != null) {
        	if(user.getUser_role().equals("admin")) {
            	initializeAdminMenu();
            }else if (user.getUser_role().equals("eventorganizer")) {
            	initializeOrganizerMenu();
            }else if(user.getUser_role().equals("vendor") || user.getUser_role().equals("guest")) {
            	initializeAttendeeMenu();
            }
        }
        
        return menuBar;
    }
	private void initializeAdminMenu() {
//		View All Users, Delete Users
		manageAllUsersMI = new MenuItem("Manage All Users");
		
//		View All Events, View Event Details, Delete Events
        manageAllEventsMI = new MenuItem("Manage All Events");
        
        manageAllUsersMI.setOnAction(e->{
        	route.redirect("adminAllUsers");
        });
        manageAllEventsMI.setOnAction(e->{
        	route.redirect("adminAllEvents");
        });
        
        adminMenu.getItems().addAll(manageAllUsersMI, manageAllEventsMI);
		menuBar.getMenus().add(adminMenu);
	}
	private void initializeOrganizerMenu() {
//		View Organized Events, View Organized Event Details, Add Vendors, Add Guests, Edit Event Name
		manageOrganizedEventsMI = new MenuItem("Manage Organized Events");
		
    	manageOrganizedEventsMI.setOnAction(e->{
    		route.redirect("organizedEvents");
    	});
    	
    	eventMenu.getItems().add(manageOrganizedEventsMI);
    	menuBar.getMenus().add(eventMenu);
	}
	private void initializeAttendeeMenu() {
//		Function to init menu for both Guest and Vendor
		
//		View Accepted Events, View Accepted Event Details
		viewAcceptedEventsMI = new MenuItem("View Accepted Events");
		
//		View Invitations, Accept Invitations
    	viewInvitationsMI = new MenuItem("View and Accept Invitations");
    	
    	viewAcceptedEventsMI.setOnAction(e->{
    		route.redirect("acceptedEvents");
    	});
    	viewInvitationsMI.setOnAction(e->{
    		route.redirect("invitations");
    	});
    	
    	eventMenu.getItems().add(viewAcceptedEventsMI);
    	invitationMenu.getItems().add(viewInvitationsMI);
    	menuBar.getMenus().addAll(eventMenu, invitationMenu);
    	
    	if(user.getUser_role().equals("vendor")) {
//    		Manage Vendor
    		manageProductsMI = new MenuItem("Manage Products");
    		
    		manageProductsMI.setOnAction(e->{
    			route.redirect("manageProducts");
    		});
    		
    		productMenu.getItems().add(manageProductsMI);
    		menuBar.getMenus().add(productMenu);
    	}
	}

}
