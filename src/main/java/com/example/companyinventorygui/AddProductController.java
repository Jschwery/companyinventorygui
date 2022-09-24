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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    Scene addProductScene;
    Stage addProductStage;

    @FXML
    private TableView<Part> addProductPartTable;

    @FXML
    private TableColumn<Part, Number> productPartID;
    @FXML
    private TableColumn<Part, String> productPartName;
    @FXML
    private TableColumn<Part, Number> productInventory;
    @FXML
    private TableColumn<Part,Number> productCostUnit;
    @FXML
    private TextField productPartSearch;
    @FXML
    TableView<Part> associatedPartTable;
    @FXML
    TableColumn<Part, Number> productAssociatedPartID;
    @FXML
    TableColumn<Part, String> productAssociatedPartName;
    @FXML
    TableColumn<Part, Number> productAssociatedInventory;
    @FXML
    TableColumn<Part, Number> productAssociatedCostUnit;
    @FXML
    private TextField finalProductID;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductInventory;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductMin;
    @FXML
    private Button saveProduct;
    @FXML
    private Button cancelProduct;
    @FXML
    private Button removeAssociatedPart;
    @FXML
    private Button addProductPart;

    private String name;
    private int inventory;
    private Double cost;
    private int max;
    private int min;
    private int machineId;
    private String companyName;
    private Part tempSelectedPart;
    private Part tempSelectedAssociatedPart;
    private int startingProductID = InventoryController.productID;




    public void switchToMainFromAddProduct(ActionEvent event) {
    Stage stage = (Stage)productPartSearch.getScene().getWindow();
    stage.close();
    }
    public static boolean intChecker(String checkForInt, String textToDisplay) {
        try {
            Integer.parseInt(checkForInt);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("An int was not entered");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, textToDisplay);
            alert.showAndWait();
            return false;
        }
    }
    public static boolean stringChecker(String checkForString, String textToDisplay){
        try{
            for(int i = 0; i < checkForString.length(); i++){
                if(Character.isDigit((checkForString.charAt(i)))){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, textToDisplay);
                    alert.showAndWait();
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public static boolean doubleChecker(String checkDouble, String textToDisplay) {
        try {
            Double.parseDouble(checkDouble);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("A Double was not entered");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, textToDisplay);
            alert.showAndWait();
            return false;
        }
    }

    /*TODO
    Add Product table on the top is identical, to our parts table view from the InventoryController


     */


    public boolean partValidator() {
        if (doubleChecker(String.valueOf(addProductPrice.getText()),"Please enter a Double for the 'Cost' field!")) {
            cost = Double.parseDouble(String.valueOf(addProductPrice.getText()));
        } else {
            System.out.println("Cost field check failed");
            return false;
        }
        if (intChecker(String.valueOf(addProductInventory.getText()),"Please enter an int for the 'Inventory' field!")) {
            inventory = Integer.parseInt(String.valueOf(addProductInventory.getText()));
        } else {
            System.out.println("Inventory field check failed");
            return false;
        }
        if (intChecker(String.valueOf(addProductMax.getText()), "Please enter an int for the 'Max' field!")) {
            max = Integer.parseInt(String.valueOf(addProductMax.getText()));
        } else {
            System.out.println("Max field check failed");
            return false;
        }
        if(stringChecker(addProductName.getText(), "Please enter a String for the 'Name' field!")){
            name = String.valueOf(addProductName.getText());
        }else{
            System.out.println("Name field check failed");
            return false;
        }
        if (intChecker(String.valueOf(addProductMin.getText()), "Please enter an int for the 'Min' field!")) {
            min = Integer.parseInt(String.valueOf(addProductMin.getText()));
        } else {
            System.out.println("Min field check failed");
            return false;
        }
        return true;
    }

    public void closeSceneWindow(){
        Stage stage = (Stage) saveProduct.getScene().getWindow();
        stage.close();
    }

    public void exitBackToInventory(ActionEvent event){
        closeSceneWindow();
    }

//    public void onAddProduct(ActionEvent event) {
//
//        if (partValidator()) {
//
//            int tempId = InventoryController.partID;
//
//            if (selectInHouse.isSelected()) {
//                InventoryController.addPart(new InHousePart(tempId, name, cost, inventory, min, max, machineId));
//            }
//            InventoryController.incrementPartID();
//            if (selectOutSourced.isSelected()) {
//                InventoryController.addPart(new OutSourcedPart(tempId, name, cost, inventory, min, max, companyName));
//            }
//            InventoryController.incrementPartID();
//            closeSceneWindow();
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Product tempProduct = new Product(0, "temp", 00.00, 0, 0, 100000);
        addProductPartTable.setItems(InventoryController.getAllParts());




        productPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("price"));
        productCostUnit.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //Set Products columns
        productAssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productAssociatedInventory.setCellValueFactory(new PropertyValueFactory<>("cost"));
        productAssociatedCostUnit.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

}



/*
has table view
Identicle to parts table view main menu
 */