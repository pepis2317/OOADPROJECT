package views;

import java.util.ArrayList;

import controllers.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.User;
import utils.Response;

public class AdminAllUsersView extends View {
	private AdminController adminController;
	private BorderPane borderPane;
	private MenuBar menuBar;
	private VBox vbox;
	private TableView<User> tableView;
	
//	admin
	
	public AdminAllUsersView() {
		super();
	}
    public TableView<User> initializeTableView(ObservableList<User> users){
    	TableView<User> tableView = new TableView<>();
    	
        TableColumn<User, String> idColumn = new TableColumn<>("User Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<User, String>("user_id"));
        
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("user_email"));

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("user_name"));

        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("user_password"));

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("user_role"));

        TableColumn<User, Void> deleteColumn = new TableColumn<>("Delete Users");
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<>() {
                    private final Button button = new Button("Delete");
                    {
                        button.setOnAction(event -> {
                            User selectedUser = getTableView().getItems().get(getIndex());
                            Response response = adminController.deleteUser(selectedUser.getUser_id());
							
							if(response.isSuccessful()) {
                            	refreshTable();
                            	showAlert(Alert.AlertType.INFORMATION, "Event deleted successfully", response.getMessage());
                            }
                            else {
                            	showAlert(Alert.AlertType.ERROR, "Error in Deleting Event", response.getMessage());
                            }
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
        
        // Add columns to TableView
        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(emailColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(passwordColumn);
        tableView.getColumns().add(roleColumn);
        tableView.getColumns().add(deleteColumn);
        
        tableView.setItems(users);
        
        return tableView;
    }
    
    private void refreshTable() {
    	vbox.getChildren().clear();
		
		ArrayList<User> usersList = (ArrayList<User>) adminController.getAllUsers();
		ObservableList<User> users = FXCollections.observableArrayList(usersList);
		tableView = initializeTableView(users);
		
		vbox.getChildren().add(tableView);
    }
    
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		borderPane = new BorderPane();
		this.scene = new Scene(borderPane, 800, 600);
		
		vbox = new VBox();
		
		adminController = new AdminController();
	}
	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		borderPane.setCenter(vbox);
	}
	@Override
	protected void style() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		refreshTable();
		
		TopMenuBar topMenu = new TopMenuBar();
		menuBar = topMenu.initializeMenuBar();
		
		borderPane.setTop(menuBar);
	}

}
