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
import singletons.UserSession;

public class InvitationsView extends TopMenuBar {
	private final Stage primaryStage;
	private Scene scene;
	private User user;
	public InvitationsView(Stage stage) {
        this.primaryStage = stage;
        UserSession session = UserSession.getInstance();
        this.user = session.getUser();
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
                            Event selectedEvent = EventController.getEventDetails(selectedInvite.getEvent_id());
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
        acceptColumn.setCellFactory(new Callback<>() {
        	public TableCell<Invitation, Void> call(TableColumn<Invitation, Void> param) {
                return new TableCell<>() {
                    private final Button button = new Button("Accept");

                    {
                        button.setOnAction(event -> {
                            Invitation selectedInvite = getTableView().getItems().get(getIndex());
                            UserController.acceptInvitation(selectedInvite.getEvent_id());
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
                            UserController.declineInvitation(selectedInvite.getEvent_id());
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
        	String eventName = EventController.getEventDetails(invite.getEvent_id()).getEvent_name();
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

    public void show() {
    	VBox vbox = new VBox();
        scene = new Scene(vbox);
        
		ArrayList<Invitation> invitationsList = (ArrayList<Invitation>) InvitationController.getPendingInvitations(user.getUser_id());
		ObservableList<Invitation> invites = FXCollections.observableArrayList(invitationsList);
        TableView<Invitation> tableView = initializeTableView(invites);
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
