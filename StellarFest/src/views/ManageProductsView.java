package views;

import java.util.List;

import controllers.VendorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Product;
import utils.Response;

public class ManageProductsView extends View{
	private VendorController vendorController;
	private BorderPane borderPane;
	private TableView<Product> productsTable;
	private MenuBar menuBar;
	private VBox vbox;
	private VBox add;
	private Button addButton;
	private Label nameLabel, descriptionLabel;
	private TextField nameField, descriptionField;
//	vendor
	
    public ManageProductsView() {
    	super();
    }
    public TableView<Product> initializeTableView(ObservableList<Product> products){
    	TableView<Product> tableView = new TableView<>();
    	
        TableColumn<Product, String> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("product_id"));
        
        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("product_name"));
        
        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Product Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("product_description"));
        
        idColumn.setMinWidth(borderPane.getWidth() / 3);
		nameColumn.setMinWidth(borderPane.getWidth() / 3);
		descriptionColumn.setMinWidth(borderPane.getWidth() / 3);
		
		tableView.setItems(products);
        
        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(descriptionColumn);

        return tableView;
    }
    public VBox initializeAdd() {
        VBox vbox = new VBox(20);
        GridPane gridPane = new GridPane();

        nameLabel = new Label("Name:");
        nameField = new TextField();

        descriptionLabel = new Label("Description:");
        descriptionField = new TextField();
        
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(descriptionLabel, 0, 1);
        gridPane.add(descriptionField, 1, 1);
        
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHgrow(nameField, Priority.ALWAYS);
        GridPane.setHgrow(descriptionField, Priority.ALWAYS);

        addButton = new Button("Add Product");
        
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        
        vbox.getChildren().addAll(gridPane, addButton);

        return vbox;
    }
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		borderPane = new BorderPane();
		this.scene = new Scene(borderPane, 600, 600);
		
		vbox = new VBox();
		
		vendorController = new VendorController();

		add = initializeAdd();
	}
	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		borderPane.setCenter(vbox);
	}
	@Override
	protected void style() {
		// TODO Auto-generated method stub
		vbox.setSpacing(30);
	}
	
	private void refreshTable() {
		vbox.getChildren().clear();
		
		List<Product> productsList = vendorController.viewProducts();
        ObservableList<Product> products = FXCollections.observableArrayList(productsList);
        productsTable = initializeTableView(products);
        nameField.clear();
        descriptionField.clear();
        
        vbox.getChildren().addAll(add, productsTable);
	}
	
	protected void setEventHandler() {
		// TODO Auto-generated method stub
		addButton.setOnAction(e -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            
            Response response = vendorController.manageVendor(description, name);
            
            if(response.isSuccessful()) {
            	showAlert(Alert.AlertType.INFORMATION, "Product Added Successfully", response.getMessage());
            	
            	refreshTable();
            }
            else {
            	showAlert(Alert.AlertType.ERROR, "Error in Adding Product", response.getMessage());
            }
        });
		
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		refreshTable();
		
        TopMenuBar topMenu = new TopMenuBar();
		menuBar = topMenu.initializeMenuBar();
		borderPane.setTop(menuBar);
		
		setEventHandler();
	}
}
