package com.example.companyinventorygui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable{
    @FXML
    private TableView<Part> partListTable;
    @FXML
    private TableView<Part> associatedPartTable;
    @FXML
    private TableColumn<Part, Number> modifyProductPartID;
    @FXML
    private TableColumn<Part, String> modifyProductPartName;
    @FXML
    private TableColumn<Part, Number> modifyProductInventoryColumn;
    @FXML
    private TableColumn<Part, Number> modifyProductCostUnit;
    @FXML
    private TextField modifyProductSearch;
    @FXML
    private TableColumn<Part, Number> modifyAssociatedPartID;
    @FXML
    private TableColumn<Part, String> modifyAssociatedPartName;
    @FXML
    private TableColumn<Part, Number> modifyAssociatedInventory;
    @FXML
    private TableColumn<Part, Number> modifyAssociatedCostUnit;
    @FXML
    private TextField modifyProductID;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TextField modifyProductInventory;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductMax;
    @FXML
    private TextField modifyProductMin;

    Product selectedProduct = Objects.requireNonNull(InventoryController.productThatIsSelected);

    public int getProductIndex(Product productToFindIndexOf){
        return InventoryController.getAllProducts().indexOf(selectedProduct);
    }

//thisusestheListssetmethod
//TODO update Product with int index,and modified product

    //TODO
//displaypopupiftheminis>max

/*
initialize text entry properties to the selected Products values
initialize the associated parts to display when this scene is displayed
parts table should be set to display the parts from the allParts observablelist

delete should delete associatedparts
add should add associated parts
on save,validate the text fields

get index of product,//allProducts.indexOf(selectedProduct)

*/



    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        private int id;
        private String name;
        private Double price;
        private int stock;
        private int min;
        private int max;

        public void Product(int id, String name, Double price, int stock, int min, int max) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.min = min;
            this.max = max;
        }




    public void closeSceneWindow(){
        Stage stage = (Stage) modifyProductID.getScene().getWindow();
        stage.close();
    }

    public void exitBackToInventory(ActionEvent event){
        closeSceneWindow();
    }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public Double getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public void addAssociatedPart(Part part) {
            associatedParts.add(part);
        }

        public boolean deleteAssociatedPart(Part selectedPart) {
            return associatedParts.remove(selectedPart);
        }

        public ObservableList<Part> getAssociatedParts() {
            return selectedProduct.getAllAssociatedParts();
        }
/*
need to get the selected products associated list
 */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //column set cellvaluefactory (new property value factory<>("attribute from the product to go into the column")

        partListTable.setItems(InventoryController.getAllParts());
        associatedPartTable.setItems(selectedProduct.associatedParts);

        //setting the allParts table with the values of the currently available parts
        modifyProductPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductCostUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        //setting the associated parts table with the values of the selected products associatedParts
        modifyAssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyAssociatedInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyAssociatedCostUnit.setCellValueFactory(new PropertyValueFactory<>("price"));


        modifyProductID.setText(String.valueOf(selectedProduct.getID()));
        modifyProductName.setText(String.valueOf(selectedProduct.getName()));
        modifyProductMin.setText(String.valueOf(selectedProduct.getMin()));
        modifyProductMax.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductPrice.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductInventory.setText(String.valueOf(selectedProduct.getStock()));




    }
}


