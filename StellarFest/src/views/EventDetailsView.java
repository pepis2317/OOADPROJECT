package views;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import controllers.AdminController;
import controllers.EventController;
import controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import models.Event;
import models.Guest;
import models.User;
import models.Vendor;
import utils.UserSession;

public class EventDetailsView extends View implements IParameterView {
	private User user;
	private AdminController adminController;
	private EventController eventController;
	private UserController userController;
	private Event event;
	private BorderPane borderPane;
	private VBox info;
	private VBox vbox;
	private MenuBar menuBar;
	private HBox attendeeTable;
	private SimpleDateFormat sdf;
	private TableView<User> guestsTableView;
	private TableView<User> vendorsTableView;
	
//	admin, guest, vendor
	
    public EventDetailsView() {
    	super();
    }
    public TableView<User> initializeTableView(ObservableList<User> users){
    	TableView<User> tableView = new TableView<>();
    	
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("user_email"));
        
        TableColumn<User, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("user_name"));
        
        tableView.getColumns().add(emailColumn);
        tableView.getColumns().add(nameColumn);
        
        tableView.setItems(users);
        
        return tableView;
    }
    
    public VBox initializeInfo() {
    	VBox vbox = new VBox();
        Label nameLabel = new Label("Event Name: " + event.getEvent_name());
        Label dateLabel = new Label("Event Date: " + sdf.format(event.getEvent_date()));
        Label locationLabel = new Label("Event Location: " + event.getEvent_location());
        Label descriptionLabel = new Label("Event Description: " + event.getEvent_description());
        Label organizerLabel = new Label("Event Organizer: " + userController.getUserById(event.getOrganizer_id()).getUser_name());
        
        nameLabel.setWrapText(true);
        descriptionLabel.setWrapText(true);
        
        Font font = new Font(16);
        nameLabel.setFont(font);
        dateLabel.setFont(font);
        locationLabel.setFont(font);
        descriptionLabel.setFont(font);
        organizerLabel.setFont(font);
        
		vbox.getChildren().add(nameLabel);
		vbox.getChildren().add(dateLabel);
		vbox.getChildren().add(locationLabel);
		vbox.getChildren().add(descriptionLabel);
		vbox.getChildren().add(organizerLabel);
		
		vbox.setAlignment(Pos.CENTER);
		
		return vbox;
        
    }

	public HBox initializeAttendeeTable() {
        HBox tables = new HBox();
        
        Label guestsTableLabel = new Label("Guests attending");
        Label vendorsTableLabel = new Label("Vendors attending");
        
        Font font = new Font(16);
        guestsTableLabel.setFont(font);
        vendorsTableLabel.setFont(font);
        
        VBox guestsTable = new VBox();
        VBox vendorsTable = new VBox();
        
        guestsTable.getChildren().addAll(guestsTableLabel, guestsTableView);
        vendorsTable.getChildren().addAll(vendorsTableLabel, vendorsTableView);
        
        guestsTable.setAlignment(Pos.CENTER);
        vendorsTable.setAlignment(Pos.CENTER);
        
        guestsTable.setSpacing(10);
        vendorsTable.setSpacing(10);
        
        tables.getChildren().addAll(guestsTable, vendorsTable);
        
        tables.setAlignment(Pos.CENTER);
        tables.setSpacing(30);
        
        return tables;
	}
	
	private void refreshTable() {
		UserSession session = UserSession.getInstance();
    	user = session.getUser();
		
		vbox.getChildren().clear();
		
		switch(user.getUser_role()) {
			case "admin":
			case "eventorganizer":
				List<Guest> guestsList = adminController.getGuestsByTransactionID(event.getEvent_id());
				ObservableList<User> guests = FXCollections.observableArrayList(guestsList);
				
				List<Vendor> vendorsList = adminController.getVendorsByTransactionID(event.getEvent_id());
				ObservableList<User> vendors = FXCollections.observableArrayList(vendorsList);
				
		        guestsTableView = initializeTableView(guests);
		        vendorsTableView = initializeTableView(vendors);
		        
				attendeeTable = initializeAttendeeTable();
				
				vbox.getChildren().add(attendeeTable);
			default:
				info = initializeInfo();
				vbox.getChildren().add(info);
		}
	}
	
	@Override
	protected void init() {
		borderPane = new BorderPane();
		this.scene = new Scene(borderPane, 800, 600);
		
		vbox = new VBox();
		
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		
    	eventController = new EventController();
		adminController = new AdminController();
		userController = new UserController();
	}
	@Override
	protected void layout() {
		borderPane.setCenter(vbox);
	}
	@Override
	protected void style() {
		vbox.setSpacing(30);
	}
	
	@Override
	public void setParams(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		String eventId = (String)params.get("event_id");
		
		event = eventController.getEventDetails(eventId);
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		refreshTable();
		
		TopMenuBar topMenu = new TopMenuBar();
		menuBar = topMenu.initializeMenuBar();
		
		borderPane.setTop(menuBar);
	}
}
