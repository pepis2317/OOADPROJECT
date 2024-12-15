package views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import controllers.EventController;
import controllers.InvitationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import models.Invitation;
import utils.Response;
import utils.Route;

public class InvitationsView extends View {
	private EventController eventController;
	private InvitationController invitationController;
	private BorderPane borderPane;
	private VBox vbox;
	private TableView<Invitation> tableView;
	private SimpleDateFormat sdf;
	private MenuBar menuBar;
	private Route route;
//	guest, vendor
	
	public InvitationsView() {
		super();
    }
	public TableView<Invitation> initializeTableView(ObservableList<Invitation> invites){
    	TableView<Invitation> tableView = new TableView<>(invites);
    	
        TableColumn<Invitation, String> idColumn = new TableColumn<>("Invitation Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<Invitation, String>("invitation_id"));
        
        TableColumn<Invitation, String> nameColumn = new TableColumn<>("Event Name");
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Invitation,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Invitation, String> param) {
				// TODO Auto-generated method stub
				Invitation invite = param.getValue();
	        	Event event = eventController.getEventDetails(invite.getEvent_id());
	        	return new SimpleStringProperty(event.getEvent_name());
			}
		});
        
        TableColumn<Invitation, String> dateColumn = new TableColumn<>("Event Date");
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Invitation,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Invitation, String> param) {
				// TODO Auto-generated method stub
				Invitation invite = param.getValue();
	        	Event event = eventController.getEventDetails(invite.getEvent_id());
				return new SimpleStringProperty(sdf.format(event.getEvent_date()));
			}
		});
        
        TableColumn<Invitation, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<Invitation, String>("invitation_status"));
        
        TableColumn<Invitation, Void> viewDetailsColumn = new TableColumn<>("View Event Details");
        viewDetailsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Invitation, Void> call(TableColumn<Invitation, Void> param) {
                return new TableCell<>() {
                    private final Button button = new Button("View");

                    {
                        button.setOnAction(event -> {
                            Invitation selectedInvite = getTableView().getItems().get(getIndex());
                            Event selectedEvent = eventController.getEventDetails(selectedInvite.getEvent_id());
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
        
        TableColumn<Invitation, Void> acceptColumn = new TableColumn<>("Accept Invitation");
        acceptColumn.setCellFactory(new Callback<>() {
        	public TableCell<Invitation, Void> call(TableColumn<Invitation, Void> param) {
                return new TableCell<>() {
                    private final Button button = new Button("Accept");

                    {
                        button.setOnAction(event -> {
                            Invitation selectedInvite = getTableView().getItems().get(getIndex());
                            Response response = invitationController.acceptInvitation(selectedInvite.getEvent_id());
                            
                            if(response.isSuccessful()) {
                            	selectedInvite.setInvitation_status("accepted");
                                getTableView().refresh();
                            	showAlert(Alert.AlertType.INFORMATION, "Invitation accepted successfully", response.getMessage());
                            }
                            else {
                            	showAlert(Alert.AlertType.ERROR, "Error in Accepting Invitation", response.getMessage());
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
        
        TableColumn<Invitation, Void> declineColumn = new TableColumn<>("Decline Invitation");
        declineColumn.setCellFactory(new Callback<>() {
        	public TableCell<Invitation, Void> call(TableColumn<Invitation, Void> param) {
                return new TableCell<>() {
                    private final Button button = new Button("Accept");

                    {
                        button.setOnAction(event -> {
                            Invitation selectedInvite = getTableView().getItems().get(getIndex());
                            Response response = invitationController.declineInvitation(selectedInvite.getEvent_id());
                            
                            if(response.isSuccessful()) {
                            	selectedInvite.setInvitation_status("declined");
                                getTableView().refresh();
                            	showAlert(Alert.AlertType.INFORMATION, "Invitation declined successfully", response.getMessage());
                            }
                            else {
                            	showAlert(Alert.AlertType.ERROR, "Error in Declining Invitation", response.getMessage());
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
        tableView.getColumns().add(statusColumn);
        tableView.getColumns().add(viewDetailsColumn);
        tableView.getColumns().add(acceptColumn);
        tableView.getColumns().add(declineColumn);
        
        tableView.setItems(invites);
        
        return tableView;
    }
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		borderPane = new BorderPane();
		this.scene = new Scene(borderPane, 800, 600);

		vbox = new VBox();
		
		route = Route.getInstance();
		
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		eventController = new EventController();
		invitationController = new InvitationController();
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
		
		ArrayList<Invitation> invitationsList = (ArrayList<Invitation>) invitationController.getPendingInvitations();
		ObservableList<Invitation> invites = FXCollections.observableArrayList(invitationsList);
        tableView = initializeTableView(invites);
        
		vbox.getChildren().add(tableView);
		
        TopMenuBar topMenu = new TopMenuBar();
		menuBar = topMenu.initializeMenuBar();
		
		borderPane.setTop(menuBar);
	}

}
