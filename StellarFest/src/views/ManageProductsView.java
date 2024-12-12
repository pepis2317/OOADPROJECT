package views;

import java.util.List;

import controllers.VendorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.Product;

public class ManageProductsView extends View{
	private VendorController vendorController;
	private TableView<Product> productsTable;
	private MenuBar menuBar;
	private VBox vbox;
	private VBox add;
	private Button addButton;
	private Label nameLabel, descriptionLabel;
	private TextField nameField, descriptionField;
    public ManageProductsView() {
    	super();
        init();
        layout();
        style();
        setEventHandler();
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

        nameLabel = new Label("Name:");
        nameField = new TextField();

        descriptionLabel = new Label("Description:");
        descriptionField = new TextField();

        addButton = new Button("Add Product");

        vbox.getChildren().addAll(nameLabel, nameField, descriptionLabel, descriptionField, addButton);

        

        return vbox;
    }
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		vbox = new VBox();
		vendorController = new VendorController();
		this.scene = new Scene(vbox);
		List<Product> productsList = vendorController.viewProducts();
		ObservableList<Product> products = FXCollections.observableArrayList(productsList);
        productsTable = initializeTableView(products);
        add = initializeAdd();
        TopMenuBar topMenu = new TopMenuBar();
		menuBar = topMenu.initializeMenuBar();
		
		
	}
	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		vbox.getChildren().add(menuBar);
		vbox.getChildren().add(add);
		vbox.getChildren().add(productsTable);
	}
	@Override
	protected void style() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void setEventHandler() {
		// TODO Auto-generated method stub
		addButton.setOnAction(e -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            vendorController.manageVendor(description, name);
            List<Product> productsList = vendorController.viewProducts();
            ObservableList<Product> products = FXCollections.observableArrayList(productsList);
            productsTable = initializeTableView(products);
            nameField.clear();
            descriptionField.clear();
        });
		
	}
}
