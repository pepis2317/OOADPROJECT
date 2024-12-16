package views;

import java.util.function.Consumer;

import controllers.EventOrganizerController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Event;
import utils.Response;
import javafx.scene.control.Alert;

public class EditEventNamePopup extends PopupView {
	private EventOrganizerController eventOrganizerController;
	private VBox vbox;
	private GridPane gridPane;
	private Label editNameLabel;
	private TextField editNameField;
	private Button editButton;
	private Event event;
	private Consumer<Void> onEditSuccess;
	public EditEventNamePopup(Event event, Consumer<Void> onEditSuccess) {
		super();
		this.event = event;
		this.onEditSuccess = onEditSuccess;
		editNameField.setText(event.getEvent_name());
		editButtonPress();
	}
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		vbox = new VBox(20);
	    gridPane = new GridPane();
	    eventOrganizerController = new EventOrganizerController();
        editNameLabel = new Label("Event Name:");
        editNameField = new TextField();
        editButton = new Button("Edit Event Name");
        gridPane.add(editNameLabel, 0, 0);
        gridPane.add(editNameField, 1, 0);
	    
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHgrow(editNameField, Priority.ALWAYS);
        
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        
        vbox.getChildren().addAll(gridPane, editButton);
		this.scene = new Scene(vbox);
		
		
	}

	protected void editButtonPress() {
		editButton.setOnAction(e->{
			String name = editNameField.getText();
			Response response = eventOrganizerController.editEventName(event.getEvent_id(), name);
            
			
			if(response.isSuccessful()) {
            	showAlert(Alert.AlertType.INFORMATION, "Event Name Edited Successfully", response.getMessage());
                if (onEditSuccess != null) {
                    onEditSuccess.accept(null);
                }
                this.stage.close();
            }
            else {
            	showAlert(Alert.AlertType.ERROR, "Error in Editing Event Name", response.getMessage());
            }
			
		});
	}
}
