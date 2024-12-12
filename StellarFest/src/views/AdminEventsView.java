package views;

import java.util.ArrayList;

import controllers.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Event;
import utils.Route;

public class AdminEventsView extends View {
	private AdminController adminController;
	private MenuBar menuBar;
	private VBox vbox;
	private TableView<Event> tableView;
	private Route route;
	public AdminEventsView() {
		super();
		init();
		layout();
		style();
		setEventHandler();
	}

	public TableView<Event> initializeTableView(ObservableList<Event> events) {
		TableView<Event> tableView = new TableView<>(events);
		TableColumn<Event, String> idColumn = new TableColumn<>("Id");
		TableColumn<Event, String> nameColumn = new TableColumn<>("Name");
		TableColumn<Event, String> dateColumn = new TableColumn<>("Date");
		TableColumn<Event, String> locationColumn = new TableColumn<>("Location");
		TableColumn<Event, String> descriptionColumn = new TableColumn<>("Description");
		TableColumn<Event, String> organizerColumn = new TableColumn<>("Organizer");
		TableColumn<Event, Void> viewDetailsColumn = new TableColumn<>("View Details");
		viewDetailsColumn.setCellFactory(new Callback<>() {
			@Override
			public TableCell<Event, Void> call(TableColumn<Event, Void> param) {
				return new TableCell<>() {
					private final Button button = new Button("View");
					{
						button.setOnAction(event -> {
							Event selectedEvent = getTableView().getItems().get(getIndex());
							System.out.println("Action on: " + selectedEvent.getEvent_name());
							route.redirect("selectedEvent");
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
		TableColumn<Event, Void> deleteDetailsColumn = new TableColumn<>("Delete Event");
		deleteDetailsColumn.setCellFactory(new Callback<>() {
			@Override
			public TableCell<Event, Void> call(TableColumn<Event, Void> param) {
				return new TableCell<>() {
					private final Button button = new Button("Delete");
					{
						button.setOnAction(event -> {
							Event selectedEvent = getTableView().getItems().get(getIndex());
							adminController.deleteEvent(selectedEvent.getEvent_id());
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
		idColumn.setCellValueFactory(new PropertyValueFactory<>("event_id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("event_name"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("event_date"));
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("event_location"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("event_description"));
		organizerColumn.setCellValueFactory(new PropertyValueFactory<>("organizer_id"));
		tableView.getColumns().add(idColumn);
		tableView.getColumns().add(nameColumn);
		tableView.getColumns().add(dateColumn);
		tableView.getColumns().add(locationColumn);
		tableView.getColumns().add(descriptionColumn);
		tableView.getColumns().add(organizerColumn);
		tableView.getColumns().add(viewDetailsColumn);
		tableView.getColumns().add(deleteDetailsColumn);
		return tableView;
	}

	@Override
	protected void init() {
		adminController = new AdminController();
		vbox = new VBox();
		this.scene = new Scene(vbox);
		TopMenuBar topMenu = new TopMenuBar();
		menuBar = topMenu.initializeMenuBar();
		ArrayList<Event> eventsList = (ArrayList<Event>) adminController.getAllEvents();
		ObservableList<Event> events = FXCollections.observableArrayList(eventsList);
		tableView = initializeTableView(events);
		route = Route.getInstance();
	}

	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		vbox.getChildren().addAll(menuBar, tableView);

	}

	@Override
	protected void style() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setEventHandler() {
		// TODO Auto-generated method stub

	}
}
