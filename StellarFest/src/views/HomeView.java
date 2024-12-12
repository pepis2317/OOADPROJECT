package views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utils.Route;

public class HomeView extends View {
	private BorderPane borderPane;
	private VBox vbox;
	private Button changeProfileBtn;
	
    public HomeView() {
    	super();
    }
    
    @Override
    protected void init() {
    	borderPane = new BorderPane();
    	this.scene = new Scene(borderPane, 600, 600);
    	
    	vbox = new VBox(20);
    	
        changeProfileBtn = new Button("Change Profile");  
	}
	
    @Override
    protected void layout() {
		vbox.getChildren().addAll(changeProfileBtn);
		
		borderPane.setCenter(vbox);
	}
	
    @Override
    protected void style() {
		vbox.setSpacing(10);
		vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
	}
    
    @Override
	public void load() {
		setEventHandler();
	}
	
    private void setEventHandler() {
		changeProfileBtn.setOnAction((e) -> {
			Route route = Route.getInstance();
			route.redirect("changeProfile");
		});
	}
}
