package views;

import java.util.ArrayList;

import controllers.AdminController;
import controllers.UserController;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Event;
import models.User;

public class AcceptedEventsView extends TopMenuBar{
	private final Stage primaryStage;
	private Scene scene;
	private User user;

    public AcceptedEventsView(Stage stage) {
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
                            new EventDetailView(primaryStage, selectedEvent).show();
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

        return tableView;
    }
	public void show() {
		VBox vbox = new VBox();
        scene = new Scene(vbox);
        
		ArrayList<Event> eventsList = (ArrayList<Event>) UserController.viewAcceptedEvents(user.getUser_email());
		ObservableList<Event> events = FXCollections.observableArrayList(eventsList);
        TableView<Event> tableView = initializeTableView(events);
        MenuBar menuBar = initializeMenuBar(primaryStage);
		Button viewUsersButton = new Button ("View All Users");
		vbox.getChildren().addAll(menuBar, tableView);

        viewUsersButton.setOnAction(e->{
        	new AdminAllUsersView(primaryStage).show();
        });
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}

}
