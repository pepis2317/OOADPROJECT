package views;

import java.util.ArrayList;

import controllers.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Admin;
import models.Event;
import models.EventOrganizer;
import models.Guest;
import models.User;
import models.Vendor;

public class AdminAllUsersView extends TopMenuBar {
	private final Stage primaryStage;
	private Scene AdminScene;

    public AdminAllUsersView(Stage stage) {
    	this.primaryStage = stage;
    }
    public TableView<User> initializeTableView(ObservableList<User> users){
    	TableView<User> tableView = new TableView<>(users);
        TableColumn<User, String> idColumn = new TableColumn<>("Id");
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        TableColumn<User, Void> deleteColumn = new TableColumn<>("Delete Users");
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<>() {
                    private final Button button = new Button("Delete");
                    {
                        button.setOnAction(event -> {
                            User selectedUser = getTableView().getItems().get(getIndex());
                            AdminController.deleteUser(selectedUser.getUser_id());
                            getTableView().getItems().remove(getIndex());
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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("user_password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("user_role"));
        
        // Add columns to TableView
        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(emailColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(passwordColumn);
        tableView.getColumns().add(roleColumn);
        tableView.getColumns().add(deleteColumn);
        return tableView;
    }
	public void show() {
		VBox vbox = new VBox();
        AdminScene = new Scene(vbox);
        
		ArrayList<User> usersList = (ArrayList<User>) AdminController.getAllUsers();
		ObservableList<User> users = FXCollections.observableArrayList(usersList);
        TableView<User> tableView = initializeTableView(users);
        
        MenuBar menuBar = initializeMenuBar(primaryStage);
		
		vbox.getChildren().add(menuBar);
		vbox.getChildren().add(tableView);

        primaryStage.setScene(AdminScene);
        primaryStage.show();
		
	}

}
