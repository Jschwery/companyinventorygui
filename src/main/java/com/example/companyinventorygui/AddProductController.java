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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

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
    private TableColumn<Part, Number> productCostUnit;
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

    Product tempProduct = new Product(0, "", 00.00, 0, 0, 1);

    private int id = InventoryController.productID;
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

    public boolean checkForInteger(String stringToCheck) {
        if (stringToCheck == null) {
            return false;
            //if there is nothing entered in the field then return false
        }
        try {
            Integer.parseInt(stringToCheck);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean stringChecker(String checkForString, String textToDisplay) {
        try {
            for (int i = 0; i < checkForString.length(); i++) {
                if (Character.isDigit((checkForString.charAt(i)))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, textToDisplay);
                    alert.showAndWait();
                    return false;
                }
            }
        } catch (Exception e) {
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
    public ObservableList<Part> checkForMatchingStringPartNumber(String inputToCheck) {
        ObservableList<Part> tempListpart = FXCollections.observableArrayList();
        if (intChecker(inputToCheck, "No Part with the entered String was found") ) {//check if the string entered is Integer
            int partInt = Integer.parseInt(inputToCheck);
            for (Part p : InventoryController.getAllParts()) {//looping through all the parts and comparing their IDs to what was typed in the search bar
                if (p.getId() == partInt) {
                    tempListpart.add(p);
                }
            }
            return tempListpart;
        } else {
            return InventoryController.getAllParts();
        }
    }

    public ObservableList<Part> checkPartNameSearch(String nameMatch) {
        ObservableList<Part> nameListAdd = FXCollections.observableArrayList();
        for (Part p : InventoryController.getAllParts()) {
            if (p.getName().toLowerCase().contains(nameMatch.toLowerCase())) {
                nameListAdd.add(p);
                System.out.println("Part was successfully added");
            }
        }
        if (nameListAdd.size() < 1) {
            return InventoryController.getAllParts();
        }
        return nameListAdd;
    }


    public void partSearchFilter(KeyEvent event) {
        //getting the input from search
        String checkString = productPartSearch.getText();
        if (event.getCode() == KeyCode.ENTER || Objects.equals(productPartSearch.getText(), "")) {
            if (checkForInteger(checkString)){//if the search bar contains an integer store it
                addProductPartTable.setItems(checkForMatchingStringPartNumber(checkString));
            } else {
                addProductPartTable.setItems(checkPartNameSearch(checkString.toLowerCase()));
            }
        }
    }

    Part partThatIsSelected = null;
    public Part obtainSelectedPart() throws NullPointerException {
        if (addProductPartTable != null) {
            partThatIsSelected = (Part) addProductPartTable.getSelectionModel().getSelectedItem();
            return partThatIsSelected;
        }
        return null;
    }

    public Part obtainSelectedPartFromAssociatedParts() throws NullPointerException {
        if (associatedPartTable != null) {
            partThatIsSelected = (Part) associatedPartTable.getSelectionModel().getSelectedItem();
            return partThatIsSelected;
        }
        return null;
    }

    public void switchToMainFromAddProduct(ActionEvent event) {
        Stage stage = (Stage) productPartSearch.getScene().getWindow();
        stage.close();
    }


    public boolean productFieldValidator() {
        if (doubleChecker(String.valueOf(addProductPrice.getText()), "Please enter a Double for the 'Cost' field!")) {
            cost = Double.parseDouble(String.valueOf(addProductPrice.getText()));
        } else {
            System.out.println("Cost field check failed");
            return false;
        }
        if (intChecker(String.valueOf(addProductInventory.getText()), "Please enter an int for the 'Inventory' field!")) {
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
        if (stringChecker(addProductName.getText(), "Please enter a String for the 'Name' field!")) {
            name = String.valueOf(addProductName.getText());
        } else {
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


    public void closeSceneWindow() {
        Stage stage = (Stage) saveProduct.getScene().getWindow();
        stage.close();
    }

    public void exitBackToInventory(ActionEvent event) {
        closeSceneWindow();
    }


    /**
     *
     * @param event add part is clicked
     */
    public void addButtonClicked(ActionEvent event){
        Part tempPart = Objects.requireNonNull(obtainSelectedPart());
            if(tempPart.getClass().getSimpleName().equals("InHousePart")){
                tempProduct.addAssociatedPart(tempPart);
            }
            else if(tempPart.getClass().getSimpleName().equals("OutSourcedPart")){
                tempProduct.addAssociatedPart(tempPart);
            }
            else{
                System.out.println("Failed to add selected part to associated part");
            }
        associatedPartTable.setItems(tempProduct.getAllAssociatedParts());
    }



    public void removeButtonClicked(ActionEvent event) {
        try {
            Part tempPartToRemove = Objects.requireNonNull(obtainSelectedPartFromAssociatedParts());
            for (Part tempPart : tempProduct.getAllAssociatedParts()) {
                if (tempPartToRemove.equals(tempPart)) {
                    tempProduct.getAllAssociatedParts().remove(tempPart);
                } else {
                    System.out.println("No part removed");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //now that we have the associatedParts observableList, we need to get
    //the product info by using the productFieldValidator, go through each of the fields, store the value
    //in a temp variable. When the save button is clicked, we will create a new product, that will hold this
    //current Product, with the values of the fields, and the values of the associatedPartList
    public void saveButtonClicked(ActionEvent event) {
    if(productFieldValidator()){
        Product p = new Product(id, name, cost, inventory, min, max);
        p.associatedParts = tempProduct.getAllAssociatedParts();
        InventoryController.addProduct(p);
        closeSceneWindow();
        InventoryController.incrementProductID();
        }
        else{
        System.out.println("Product was not successfully saved");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Product was not successfully saved");
        alert.showAndWait();
        }
    }

    /*
    TODO
    * Each product has its own associated parts Observable List
    * so when the add button is pressed, it will get the selected part value from the
    * addProductPart table, store all the values from the selected part
    * into a variables then create a new part each time the add button
    * is clicked and store it into the products associatedPart observableList
    *
    *
    * when the SAVE button is clicked we can call the validator to
    * validate each textfield and store their values into variables
    * and add the product to the allProducts observableList int the inventory controller
    * */

    public void onAddProduct(ActionEvent event) {



            closeSceneWindow();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductPartTable.setItems(InventoryController.getAllParts());
        associatedPartTable.setItems(tempProduct.getAllAssociatedParts());


        productPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Set Products columns
        productAssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productAssociatedInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productAssociatedCostUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
