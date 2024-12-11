package views;

import java.util.List;

import controllers.AdminController;
import controllers.EventController;
import controllers.UserController;
import controllers.VendorController;
import factories.ProductFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Event;
import models.Guest;
import models.Invitation;
import models.Product;
import models.User;
import models.Vendor;
import singletons.UserSession;

public class ManageProductsView extends TopMenuBar {
	private final Stage primaryStage;
	private Scene scene;
	private User user;
	private TableView<Product> productsTable;

    public ManageProductsView(Stage stage) {
    	this.primaryStage = stage;
    	UserSession session = UserSession.getInstance();
    	this.user = session.getUser();
    }
    public TableView<Product> initializeTableView(ObservableList<Product> products){
    	TableView<Product> tableView = new TableView<>(products);
        TableColumn<Product, String> idColumn = new TableColumn<>("Id");
        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Product Description");
        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(descriptionColumn);

        return tableView;
    }
    public VBox initializeAdd() {
        VBox vbox = new VBox();

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField();

        Button addButton = new Button("Add Product");

        vbox.getChildren().addAll(nameLabel, nameField, descriptionLabel, descriptionField, addButton);

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            List<Product> productsList = VendorController.viewProducts(user.getUser_id());
            // Create a new Product object
            ObservableList<Product> products = FXCollections.observableArrayList(productsList);
            productsTable = initializeTableView(products);

            nameField.clear();
            descriptionField.clear();
        });

        return vbox;
    }
    public void show() {
		VBox vbox = new VBox();
        scene = new Scene(vbox);
        List<Product> productsList = VendorController.viewProducts(user.getUser_id());
		ObservableList<Product> products = FXCollections.observableArrayList(productsList);
        productsTable = initializeTableView(products);

        VBox add = initializeAdd();
        add.setStyle("-fx-padding: 20; ");
        MenuBar menuBar = initializeMenuBar(primaryStage);
		
		vbox.getChildren().add(menuBar);
		vbox.getChildren().add(add);
		
		vbox.getChildren().add(productsTable);

        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
}
