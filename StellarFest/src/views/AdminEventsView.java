package views;

import java.util.ArrayList;

import controllers.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Event;

public class AdminEventsView extends TopMenuBar{
	private final Stage primaryStage;
	private Scene AdminScene;

    public AdminEventsView(Stage stage) {
    	this.primaryStage = stage;
    }
    public TableView<Event> initializeTableView(ObservableList<Event> events){
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
                            new AdminEventDetailsView(primaryStage, selectedEvent).show();
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
                            AdminController.deleteEvent(selectedEvent.getEvent_id());
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
	public void show() {
		VBox vbox = new VBox();
        AdminScene = new Scene(vbox);
        
		ArrayList<Event> eventsList = (ArrayList<Event>) AdminController.getAllEvents();
		ObservableList<Event> events = FXCollections.observableArrayList(eventsList);
        TableView<Event> tableView = initializeTableView(events);
        MenuBar menuBar = initializeMenuBar(primaryStage);
		Button viewUsersButton = new Button ("View All Users");
		vbox.getChildren().addAll(menuBar, tableView);

        viewUsersButton.setOnAction(e->{
        	new AdminAllUsersView(primaryStage).show();
        });
        primaryStage.setScene(AdminScene);
        primaryStage.show();
		
	}
}
