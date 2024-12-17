package views;

import java.util.ArrayList;
import java.util.List;

import controllers.EventOrganizerController;
import controllers.InvitationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import models.Guest;
import utils.Response;

public class InviteGuestsPopup extends PopupView {

	private TableView<Guest> guestTableView;
	private ArrayList<Guest> selectedGuests;
	private EventOrganizerController eventOrganizerController;
	private InvitationController invitationController;
	private VBox vbox;
	private Button inviteButton;
	private String event_id;
	public InviteGuestsPopup(String event_id){
		super();
		this.event_id = event_id;
		load();
		show();
		
	}
	private TableView<Guest> initializeTableView(ObservableList<Guest> guests){
    	TableView<Guest> tableView = new TableView<>();
    	
        TableColumn<Guest, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<Guest, String>("user_email"));
        
        TableColumn<Guest, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Guest, String>("user_name"));
        
        TableColumn<Guest, Boolean> checkBoxColumn = new TableColumn<>("Select");
        checkBoxColumn.setCellFactory(column -> new TableCell<>() {
            private final javafx.scene.control.CheckBox checkBox = new javafx.scene.control.CheckBox();

            {
                // Add listener to update selectedGuests list when checkbox is toggled
                checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                    Guest currentUser = getTableView().getItems().get(getIndex());
                    if (currentUser != null) {
                        if (isNowSelected) {
                            if (!selectedGuests.contains(currentUser)) {
                                selectedGuests.add(currentUser);
                            }
                        } else {
                            selectedGuests.remove(currentUser);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Boolean isSelected, boolean empty) {
                super.updateItem(isSelected, empty);

                if (empty || getTableView().getItems().get(getIndex()) == null) {
                    setGraphic(null);
                } else {
                    checkBox.setSelected(isSelected != null && isSelected);
                    setGraphic(checkBox);
                }
            }
        });
        
        tableView.getColumns().add(emailColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(checkBoxColumn);
        
        tableView.setItems(guests);
        
        return tableView;
    }
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		vbox = new VBox(20);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20));

		selectedGuests = new ArrayList<>();
		eventOrganizerController = new EventOrganizerController();
		invitationController = new InvitationController();
		
		inviteButton = new Button("Invite Users");
	}
	@Override
	public void load() {
		List<Guest> guestsList = eventOrganizerController.getUninvitedGuests(this.event_id);
		ObservableList<Guest> guests = FXCollections.observableArrayList(guestsList);
		guestTableView = initializeTableView(guests);
		
		vbox.getChildren().addAll(guestTableView, inviteButton);
		this.scene = new Scene(vbox);
		
		setEventHandler();
	}
	private void setEventHandler() {
		inviteButton.setOnAction(e->{
			if(selectedGuests.isEmpty()) {
				showAlert(Alert.AlertType.ERROR, "Error in Inviting Guests", "No guests selected!");
				return;
			}
			
			for(Guest v : selectedGuests) {
				Response response = invitationController.sendInvitation(this.event_id, v.getUser_email());
				if(response.isSuccessful()) {
	            	showAlert(Alert.AlertType.INFORMATION, "Guest Invited Successfully", response.getMessage());
	            }
	            else {
	            	showAlert(Alert.AlertType.ERROR, "Error in Inviting Guest", response.getMessage());
	            }
			}
			
			this.stage.close();
		});
	}
}
