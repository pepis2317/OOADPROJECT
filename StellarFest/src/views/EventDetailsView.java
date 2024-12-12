package views;

import java.util.List;

import controllers.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Event;
import models.Guest;
import models.User;
import models.Vendor;
import utils.UserSession;

public class EventDetailsView extends View{
	private User user;
	private AdminController adminController;
	private Event event;
	private VBox info;
	private VBox vbox;
	private MenuBar menuBar;
	private HBox tables;
	private TableView<User> guestsTableView;
	private TableView<User> vendorsTableView;
    public EventDetailsView() {
    	super();
		init();
		layout();
		style();
		setEventHandler();
    }
    public TableView<User> initializeTableView(ObservableList<User> users){
    	TableView<User> tableView = new TableView<>(users);
        TableColumn<User, String> idColumn = new TableColumn<>("Id");
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        if(user.getUser_role().equals("admin")) {
        	TableColumn<User, Void> deleteColumn = new TableColumn<>("Delete Users");
            deleteColumn.setCellFactory(new Callback<>() {
                @Override
                public TableCell<User, Void> call(TableColumn<User, Void> param) {
                    return new TableCell<>() {
                        private final Button button = new Button("Delete");

                        {
                            button.setOnAction(event -> {
                            	User selectedUser = getTableView().getItems().get(getIndex());
                                adminController.deleteUser(selectedUser.getUser_id());
                                getTableView().getItems().remove(getIndex());
                            });
                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(button);
                            }
                        }
                    };
                }
            });
            tableView.getColumns().add(deleteColumn);
        }
        

        idColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("user_password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("user_role"));
        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(emailColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(passwordColumn);
        tableView.getColumns().add(roleColumn);
        
        return tableView;
    }
    public VBox initializeInfo() {
    	VBox vbox = new VBox();
    	Label idLabel = new Label(event.getEvent_id());
        Label nameLabel = new Label(event.getEvent_name());
        Label dateLabel = new Label(event.getEvent_date());
        Label locationLabel = new Label(event.getEvent_location());
        Label descriptionLabel = new Label(event.getEvent_description());
        Label organizerLabel = new Label(event.getOrganizer_id());
        vbox.getChildren().add(idLabel);
		vbox.getChildren().add(nameLabel);
		vbox.getChildren().add(dateLabel);
		vbox.getChildren().add(locationLabel);
		vbox.getChildren().add(descriptionLabel);
		vbox.getChildren().add(organizerLabel);
		return vbox;
        
    }

	public HBox initializeTables() {
		List<Guest> guestsList = adminController.getGuestsByTransactionID(event.getEvent_id());
		ObservableList<User> guests = FXCollections.observableArrayList(guestsList);
		List<Vendor> vendorsList = adminController.getVendorsByTransactionID(event.getEvent_id());
		ObservableList<User> vendors = FXCollections.observableArrayList(vendorsList);
        guestsTableView = initializeTableView(guests);
        vendorsTableView = initializeTableView(vendors);
        HBox tables = new HBox();
        tables.getChildren().add(guestsTableView);
        tables.getChildren().add(vendorsTableView);
        return tables;
	}
	@Override
	protected void init() {
		adminController = new AdminController();
		UserSession session = UserSession.getInstance();
    	user = session.getUser();
		event = null;//blm buat passing params di routing
		vbox = new VBox();
		this.scene = new Scene(vbox);
		info = initializeInfo();
		TopMenuBar topMenu = new TopMenuBar();
		menuBar = topMenu.initializeMenuBar();
		
		tables = initializeTables();
		
	}
	@Override
	protected void layout() {
		vbox.getChildren().addAll(menuBar, info, tables);
	}
	@Override
	protected void style() {
		info.setStyle("-fx-padding: 20; ");
	}
	@Override
	protected void setEventHandler() {
		// TODO Auto-generated method stub
		
	}
}
