package views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import controllers.EventController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
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
import utils.Route;

public class AcceptedEventsView extends View{
	private Route route;
	private BorderPane borderPane;
	private MenuBar menuBar;
	private VBox vbox;
	private TableView<Event> tableView;
	private SimpleDateFormat sdf;
	private EventController eventController;	
//	Guest and Vendor

    public AcceptedEventsView() {
    	super();
    }
    public TableView<Event> initializeTableView(ObservableList<Event> events){
    	TableView<Event> tableView = new TableView<>();
        
        TableColumn<Event, String> nameColumn = new TableColumn<>("Event Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("event_name"));
        
        TableColumn<Event, String> dateColumn = new TableColumn<>("Event Date");
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Event,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Event, String> param) {
				return new SimpleStringProperty(sdf.format(param.getValue().getEvent_date()));
			}
		});
        
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

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(dateColumn);
        tableView.getColumns().add(viewDetailsColumn);
        
        tableView.setItems(events);

        return tableView;
    }
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		borderPane = new BorderPane();
		this.scene = new Scene(borderPane, 600, 600);
		
		vbox = new VBox();
		
		route = Route.getInstance();
		
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		eventController = new EventController();
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
		vbox.getChildren().clear();
		
		ArrayList<Event> eventsList = (ArrayList<Event>) eventController.viewAcceptedEvents();
		ObservableList<Event> events = FXCollections.observableArrayList(eventsList);
		tableView = initializeTableView(events);
		
		vbox.getChildren().add(tableView);
		
		TopMenuBar topMenuBar = new TopMenuBar();
		menuBar = topMenuBar.initializeMenuBar();
		
		borderPane.setTop(menuBar);
	}

}
