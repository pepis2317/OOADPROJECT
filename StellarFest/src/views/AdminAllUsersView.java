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
import utils.Route;

public class AdminAllUsersView extends View {
	private AdminController adminController;
	private MenuBar menuBar;
	private VBox vbox;
	private TableView<User> tableView;
	private Route route;
	public AdminAllUsersView() {
		super();
		init();
		layout();
		style();
		setEventHandler();
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
                            adminController.deleteUser(selectedUser.getUser_id());
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
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		adminController = new AdminController();
		vbox = new VBox();
		this.scene = new Scene(vbox);
		ArrayList<User> usersList = (ArrayList<User>) adminController.getAllUsers();
		ObservableList<User> users = FXCollections.observableArrayList(usersList);
		tableView = initializeTableView(users);
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
