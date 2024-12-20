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
import models.Vendor;
import utils.Response;

public class InviteVendorsPopup extends PopupView{
	private TableView<Vendor> vendorTableView;
	private ArrayList<Vendor> selectedVendors;
	private EventOrganizerController eventOrganizerController;
	private InvitationController invitationController;
	private VBox vbox;
	private Button inviteButton;
	private String event_id;
	public InviteVendorsPopup(String event_id){
		super();
		this.event_id = event_id;
		load();
		show();
	}
	private TableView<Vendor> initializeTableView(ObservableList<Vendor> vendors){
    	TableView<Vendor> tableView = new TableView<>();
    	
        TableColumn<Vendor, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<Vendor, String>("user_email"));
        
        TableColumn<Vendor, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Vendor, String>("user_name"));
        
        TableColumn<Vendor, Boolean> checkBoxColumn = new TableColumn<>("Select");
        checkBoxColumn.setCellFactory(column -> new TableCell<>() {
            private final javafx.scene.control.CheckBox checkBox = new javafx.scene.control.CheckBox();

            {
                // Add listener to update selectedVendors list when checkbox is toggled
                checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                    Vendor currentUser = getTableView().getItems().get(getIndex());
                    if (currentUser != null) { // Ensure currentUser is not null
                        if (isNowSelected) {
                            if (!selectedVendors.contains(currentUser)) {
                                selectedVendors.add(currentUser);
                            }
                        } else {
                            selectedVendors.remove(currentUser);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Boolean isSelected, boolean empty) {
                super.updateItem(isSelected, empty);

                if (empty || getTableView().getItems().get(getIndex()) == null) {
                    setGraphic(null); // Clear graphic for empty rows
                } else {
                    checkBox.setSelected(isSelected != null && isSelected); // Handle null safely
                    setGraphic(checkBox); // Add CheckBox to the cell
                }
            }
        });
        
        tableView.getColumns().add(emailColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(checkBoxColumn);
        
        tableView.setItems(vendors);
        
        return tableView;
    }
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		vbox = new VBox(20);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20));

		selectedVendors = new ArrayList<>();
		eventOrganizerController = new EventOrganizerController();
		invitationController = new InvitationController();
		
		inviteButton = new Button("Invite Users");
	}
	@Override
	public void load() {
		List<Vendor> vendorsList = eventOrganizerController.getUninvitedVendors(this.event_id);
		ObservableList<Vendor> vendors = FXCollections.observableArrayList(vendorsList);
		vendorTableView = initializeTableView(vendors);
		
		vbox.getChildren().addAll(vendorTableView, inviteButton);
		this.scene = new Scene(vbox);
		
		setEventHandler();
	}
	private void setEventHandler() {
		inviteButton.setOnAction(e->{
			if(selectedVendors.isEmpty()) {
				showAlert(Alert.AlertType.ERROR, "Error in Inviting Vendors", "No vendors selected!");
				return;
			}
			
			for(Vendor v : selectedVendors) {
				Response response = invitationController.sendInvitation(this.event_id, v.getUser_email());
				if(response.isSuccessful()) {
	            	showAlert(Alert.AlertType.INFORMATION, "Vendor Invited Successfully", response.getMessage());
	            }
	            else {
	            	showAlert(Alert.AlertType.ERROR, "Error in Inviting Vendor", response.getMessage());
	            }
			}
			this.stage.close();
		});
	}

}
