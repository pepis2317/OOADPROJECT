package views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import controllers.AdminController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Event;
import utils.Response;
import utils.Route;

public class AdminAllEventsView extends View {
	private AdminController adminController;
	private BorderPane borderPane;
	private MenuBar menuBar;
	private VBox vbox;
	private TableView<Event> tableView;
	private SimpleDateFormat sdf;
	private Route route;
//	admin
	
	public AdminAllEventsView() {
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
		
		TableColumn<Event, Void> deleteColumn = new TableColumn<>("Delete Event");
		deleteColumn.setCellFactory(new Callback<>() {
			@Override
			public TableCell<Event, Void> call(TableColumn<Event, Void> param) {
				return new TableCell<>() {
					private final Button button = new Button("Delete");
					{
						button.setOnAction(event -> {
							Event selectedEvent = getTableView().getItems().get(getIndex());
							Response response = adminController.deleteEvent(selectedEvent.getEvent_id());
							
							if(response.isSuccessful()) {
                            	refreshTable();
                            	showAlert(Alert.AlertType.INFORMATION, "Event deleted successfully", response.getMessage());
                            }
                            else {
                            	showAlert(Alert.AlertType.ERROR, "Error in Deleting Event", response.getMessage());
                            }
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
		tableView.getColumns().add(deleteColumn);
		
		tableView.setItems(events);
		
		return tableView;
	}
	
	private void refreshTable() {
		vbox.getChildren().clear();
		
		ArrayList<Event> eventList = (ArrayList<Event>) adminController.getAllEvents();
		ObservableList<Event> events = FXCollections.observableArrayList(eventList);
		tableView = initializeTableView(events);
		
		vbox.getChildren().add(tableView);
	}

	@Override
	protected void init() {
		borderPane = new BorderPane();
		this.scene = new Scene(borderPane, 600, 600);
		
		vbox = new VBox();
		
		adminController = new AdminController();

		sdf = new SimpleDateFormat("dd-MM-yyyy");

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

	@Override
	public void load() {
		// TODO Auto-generated method stub
		refreshTable();
		
		TopMenuBar topMenu = new TopMenuBar();
		menuBar = topMenu.initializeMenuBar();
		
		borderPane.setTop(menuBar);
	}
}
