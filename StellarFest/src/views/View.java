package views;

import javafx.scene.Scene;
import javafx.scene.control.Alert;

public abstract class View {
	protected Scene scene;

	public View() {
		this.scene = null;
		
    	this.init();
        this.layout();
        this.style();
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	protected abstract void init();
	protected abstract void layout();
	protected abstract void style();
	public abstract void load();
}
