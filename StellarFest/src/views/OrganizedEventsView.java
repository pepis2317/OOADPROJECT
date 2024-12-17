package views;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import controllers.EventController;
import controllers.EventOrganizerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Event;
import models.User;
import utils.Response;
import utils.Route;
import utils.UserSession;

//have the event organizer change event names here and add events here too
public class OrganizedEventsView extends View{
	private EventController eventController;
	private Button addButton;
	private Label nameLabel, descriptionLabel, dateLabel, locationLabel;
	private TextField nameField, descriptionField, locationField;
	private DatePicker dateField;
	private User user;
	private EventOrganizerController eventOrganizerController;
	private VBox vbox;
	private BorderPane borderPane;
	private MenuBar menuBar;
	private TableView<Event> tableView;
	private SimpleDateFormat sdf;
	private VBox add;
	private Route route;


	public OrganizedEventsView() {
		super();
	}
	public TableView<Event> initializeTableView(ObservableList<Event> events) {
		TableView<Event> tableView = new TableView<>();
		
		TableColumn<Event, String> idColumn = new TableColumn<>("Event Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("event_id"));

		TableColumn<Event, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("event_name"));

		TableColumn<Event, String> dateColumn = new TableColumn<>("Event Date");
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Event,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Event, String> param) {
				return new SimpleStringProperty(sdf.format(param.getValue().getEvent_date()));
			}
		});
        
        TableColumn<Event, String> locationColumn = new TableColumn<>("Event Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("event_location"));

		TableColumn<Event, Void> viewDetailsColumn = new TableColumn<>("View Details");
		viewDetailsColumn.setCellFactory(new Callback<>() {
			@Override
			public TableCell<Event, Void> call(TableColumn<Event, Void> param) {
				return new TableCell<>() {
					private final Button button = new Button("View");
					{
						button.setOnAction(event -> {
							Event selectedEvent = getTableView().getItems().get(getIndex());
							HashMap<String, Object> param = new HashMap<String, Object>();
                            param.put("event_id", selectedEvent.getEvent_id());
                            route.redirect("eventDetails", param);
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
		TableColumn<Event, Void> editColumn = new TableColumn<>("Edit Event Name");
		editColumn.setCellFactory(new Callback<>() {
			@Override
			public TableCell<Event, Void> call(TableColumn<Event, Void> param) {
				return new TableCell<>() {
					private final Button button = new Button("Edit Name");
					{
						button.setOnAction(event -> {
							Event selectedEvent = getTableView().getItems().get(getIndex());
							new EditEventNamePopup(selectedEvent, unused -> refreshTable());
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
		
		tableView.getColumns().add(idColumn);
		tableView.getColumns().add(nameColumn);
		tableView.getColumns().add(dateColumn);
		tableView.getColumns().add(locationColumn);
		tableView.getColumns().add(viewDetailsColumn);
		tableView.getColumns().add(editColumn);
		
		tableView.setItems(events);
		
		return tableView;
	}

	private void refreshTable() {
		UserSession session = UserSession.getInstance();
    	user = session.getUser();
		vbox.getChildren().clear();
		
		ArrayList<Event> eventList = (ArrayList<Event>) eventOrganizerController.getOrganizedEvents(user.getUser_id());
		ObservableList<Event> events = FXCollections.observableArrayList(eventList);
		tableView = initializeTableView(events);
		nameField.clear();
		dateField.setValue(LocalDate.now());
		locationField.clear();
		descriptionField.clear();
		vbox.getChildren().addAll(add, tableView);
	}
	public VBox initializeAdd() {
        VBox vbox = new VBox(20);
        GridPane gridPane = new GridPane();

        nameLabel = new Label("Event Name:");
        nameField = new TextField();
        
        dateLabel = new Label("Event Date:");
        dateField = new DatePicker(LocalDate.now());
        
        locationLabel = new Label("Event Location:");
        locationField = new TextField();
        
        descriptionLabel = new Label("Event Description:");
        descriptionField = new TextField();
        
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(dateLabel, 0, 1);
        gridPane.add(dateField, 1, 1);
        gridPane.add(locationLabel, 0, 2);
        gridPane.add(locationField, 1, 2);
        gridPane.add(descriptionLabel, 0, 3);
        gridPane.add(descriptionField, 1, 3);
        
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHgrow(nameField, Priority.ALWAYS);
        GridPane.setHgrow(dateField, Priority.ALWAYS);
        GridPane.setHgrow(locationField, Priority.ALWAYS);
        GridPane.setHgrow(descriptionField, Priority.ALWAYS);

        addButton = new Button("Add Event");
        
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        
        vbox.getChildren().addAll(gridPane, addButton);

        return vbox;
    }
		
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		borderPane = new BorderPane();
		this.scene = new Scene(borderPane, 600, 600);
		
		vbox = new VBox();
		
		eventOrganizerController = new EventOrganizerController();
		eventController = new EventController();
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		add = initializeAdd();

		route = Route.getInstance();
		
	}

	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		borderPane.setCenter(vbox);
		
	}

	@Override
	protected void style() {
		// TODO Auto-generated method stub
		
	}
	protected void setEventHandler() {
		// TODO Auto-generated method stub
		addButton.setOnAction(e -> {
            String name = nameField.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String date = dateField.getValue().format(formatter);
            String location = locationField.getText();
            String description = descriptionField.getText();
            
            Response response = eventController.createEvent(name, date, location, description, user.getUser_id());
            
            if(response.isSuccessful()) {
            	showAlert(Alert.AlertType.INFORMATION, "Event Created Successfully", response.getMessage());
            	
            	refreshTable();
            }
            else {
            	showAlert(Alert.AlertType.ERROR, "Error in Creating Event", response.getMessage());
            }
        });
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		refreshTable();
		
		TopMenuBar topMenu = new TopMenuBar();
		menuBar = topMenu.initializeMenuBar();
		
		borderPane.setTop(menuBar);
		setEventHandler();
	}

}
