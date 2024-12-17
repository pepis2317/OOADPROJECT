package views;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class PopupView {
	protected Stage stage;
	protected Scene scene;
	
	public PopupView() {
		this.init();
	}
	
	public void show() {
		this.stage = new Stage();
		this.stage.setScene(scene);
		this.stage.show();
	}
	protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	protected abstract void init();
	public abstract void load();
}
