package views;

import java.util.ArrayList;

import controllers.AdminController;
import controllers.EventController;
import controllers.InvitationController;
import controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

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
import models.Invitation;
import models.User;
import utils.Route;

public class InvitationsView extends View {
	private EventController eventController;
	private InvitationController invitationController;
	private VBox vbox;
	private TableView<Invitation> tableView;
	private MenuBar menuBar;
	private Route route;
	public InvitationsView() {
		super();
		init();
		layout();
		style();
		setEventHandler();
    }
	public TableView<Invitation> initializeTableView(ObservableList<Invitation> invites){
    	TableView<Invitation> tableView = new TableView<>(invites);
        TableColumn<Invitation, String> idColumn = new TableColumn<>("Id");
        TableColumn<Invitation, String> nameColumn = new TableColumn<>("Event Name");
        TableColumn<Invitation, String> statusColumn = new TableColumn<>("Date");
        TableColumn<Invitation, String> roleColumn = new TableColumn<>("Role");
        TableColumn<Invitation, Void> viewDetailsColumn = new TableColumn<>("View Details");
        TableColumn<Invitation, Void> acceptColumn = new TableColumn<>("Accept");
        TableColumn<Invitation, Void> declineColumn = new TableColumn<>("Decline");
        viewDetailsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Invitation, Void> call(TableColumn<Invitation, Void> param) {
                return new TableCell<>() {
                    private final Button button = new Button("View");

                    {
                        button.setOnAction(event -> {
                            Invitation selectedInvite = getTableView().getItems().get(getIndex());
                            Event selectedEvent = eventController.getEventDetails(selectedInvite.getEvent_id());
                            
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
        acceptColumn.setCellFactory(new Callback<>() {
        	public TableCell<Invitation, Void> call(TableColumn<Invitation, Void> param) {
                return new TableCell<>() {
                    private final Button button = new Button("Accept");

                    {
                        button.setOnAction(event -> {
                            Invitation selectedInvite = getTableView().getItems().get(getIndex());
                            invitationController.acceptInvitation(selectedInvite.getEvent_id());
                            selectedInvite.setInvitation_status("accepted");
                            getTableView().refresh();
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
        declineColumn.setCellFactory(new Callback<>() {
        	public TableCell<Invitation, Void> call(TableColumn<Invitation, Void> param) {
                return new TableCell<>() {
                    private final Button button = new Button("Accept");

                    {
                        button.setOnAction(event -> {
                            Invitation selectedInvite = getTableView().getItems().get(getIndex());
                            invitationController.declineInvitation(selectedInvite.getEvent_id());
                            selectedInvite.setInvitation_status("declined");
                            getTableView().refresh();
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("invitation_id"));
        nameColumn.setCellValueFactory(cellData->{
        	Invitation invite = cellData.getValue();
        	String eventName = eventController.getEventDetails(invite.getEvent_id()).getEvent_name();
        	return new SimpleStringProperty(eventName);
        });

        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(statusColumn);
        tableView.getColumns().add(roleColumn);
        tableView.getColumns().add(viewDetailsColumn);
        tableView.getColumns().add(acceptColumn);
        tableView.getColumns().add(declineColumn);
        return tableView;
    }
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		eventController = new EventController();
		invitationController = new InvitationController();
		vbox = new VBox();
		this.scene = new Scene(vbox);
		ArrayList<Invitation> invitationsList = (ArrayList<Invitation>) invitationController.getPendingInvitations();
		ObservableList<Invitation> invites = FXCollections.observableArrayList(invitationsList);
        tableView = initializeTableView(invites);
        TopMenuBar topMenu = new TopMenuBar();
		menuBar = topMenu.initializeMenuBar();
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
