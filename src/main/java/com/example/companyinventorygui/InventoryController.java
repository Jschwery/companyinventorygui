package com.example.companyinventorygui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class InventoryController implements Initializable {

    /*
     * Current Issue that is happening is when the save button or modify button is pressed, both the allParts list
     * and the allProducts observable list is being duplicated
     * */


    /*
    Imports from the inventory page so that we can manipulate the value
    corresponding to the elements loaded from the FXML file
     */
    /*Table additions*/
    @FXML
    TableView<Part> inventoryPartsTable;
    @FXML
    Button inventoryPartsDelete;
    @FXML
    TextField addPartMin;
    @FXML
    TextField addMachineIDorCompany;
    @FXML
    TableView<Product> inventoryProductsTable;
    @FXML
    TextField inventoryPartSearch;

    /* Part related Table Information */
    public static int partID = 1030;

    public static int incrementPartID() {
        return partID += 10;
    }

    @FXML
    TextField addPartCost;
    @FXML
    TextField addPartID;
    @FXML
    TableColumn<Part, Number> inventoryPartsPartID;
    @FXML
    TableColumn<Part, String> partsPartName;
    @FXML
    TableColumn<Part, Number> inventoryPartsInventory;
    @FXML
    TableColumn<Part, Number> inventoryPartsCostUnit;
    @FXML
    TextField addPartName;
    @FXML
    TextField addPartInventory;
    @FXML
    TextField modifyPartName;
    @FXML
    TextField modifyPartInventory;
    @FXML
    TextField modifyPartCost;
    @FXML
    TextField modifyPartMax;
    @FXML
    TextField modifyPartID;
    @FXML
    TextField modifyPartMachineID;
    @FXML
    TextField modifyPartMin;
    @FXML
    TextField addPartMax;

    /* Product Related Table Information*/
    public static int productID = 1020;

    public static int incrementProductID() {
        return productID += 10;
    }

    Set<Part> inventoryPartsTableSet = new HashSet<Part>();
    Set<Product> inventoryProductTableSet = new HashSet<Product>();


    @FXML
    TableColumn<Product, Number> inventoryProductsProductID;

    @FXML
    TextField modifyProductPrice;

    @FXML
    TableColumn<Product, String> inventoryProductsProductName;

    @FXML
    TextField inventoryProductSearch;

    @FXML
    TextField modifyProductMax;

    @FXML
    TextField modifyProductID;

    @FXML
    Button exitApplication;

    @FXML
    TextField modifyProductName;

    @FXML
    TextField modifyProductInventory;

    @FXML
    TextField modifyProductMin;

    @FXML
    Button inventoryProductsDelete;
    @FXML
    TableColumn<Product, Number> inventoryProductsInventory;

    @FXML
    TableColumn<Product, Double> ProductCostColumn;

    /* Takes an input, and checks to see if it is able to be parsed as an Integer, with the parseInt method
     * From the Integer Class, if it isn't it will throw an exception*/

    public boolean checkForInteger(String stringToCheck) {
        if (stringToCheck == null) {
            return false;
            //if there is nothing entered in the field then return false
        }
        try {
            Integer.parseInt(stringToCheck);
            /*
            the reason this works is because everything entered into a textField is automatically
            a String, so even if we type 8 into it, that 8 is still a string
            so I am using the Integer wrappers class parseInt method to parse the string, and
            if the box contains a number, it returns true, else it will pass an exception
            which will then return false, so I will then know that a string was passed in
            */
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static Part partThatIsSelected = null;
    public static Product productThatIsSelected = null;

    public void obtainSelectedPart(MouseEvent event) {
        partThatIsSelected = null;
        partThatIsSelected = allParts.get(inventoryPartsTable.getSelectionModel().getSelectedIndex());
    }

    public void obtainSelectedProduct(MouseEvent event) {
        partThatIsSelected = null;
        productThatIsSelected = allProducts.get(inventoryProductsTable.getSelectionModel().getSelectedIndex());
    }

    public void closeApplication(ActionEvent event) {
        Stage currentStage = (Stage) exitApplication.getScene().getWindow();
        currentStage.close();
        File file = new File("errors.txt");
        if (file.delete()) {
            System.out.println("file deleted");
        }
    }

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static void addPart(Part part) {
        allParts.add(part);
    }


    /*search through the products list & return a new list to display
    as the observable list
    */
    //search through the part list to the required index & replace it with the part passed in

    public static void updateParts(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static int getIndexOfPart(Part selectedPart) {
        return allParts.indexOf(selectedPart);
    }

    public static int getIndexOfProduct(Product selectedProduct) {
        return allProducts.indexOf(selectedProduct);
    }


    /**
     * Get the currently selected part and store it in a variable productToDel
     * Alert to make sure the user wants to delete the selected part, if they select okay
     * the part will be deleted, if they select cancel, it will simply return
     */
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /*Adds a product to the 'allProducts' observable list*/
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }


    /**
     * When the TextField is written in, or is empty,and the user selects 'Enter'
     * it will then check to see if what was entered is an integer
     * if it was an integer then we will parse the text entered with the parseInt method from the Integer class
     * this will return us with an int value that we store inside partInt
     * after that we can loop through the observable list which the table
     * we add an any matching number from the parts in the list
     *
     * @param event enter key is pressed
     */
    public void partSearchFilter(KeyEvent event) {
        //getting the input from search
        String checkString = inventoryPartSearch.getText();
        if (event.getCode() == KeyCode.ENTER || Objects.equals(inventoryPartSearch.getText(), "")) {
            if (checkForInteger(checkString)) {//if the search bar contains an integer store it
                inventoryPartsTable.setItems(checkForMatchingStringPartNumber(checkString));
            } else {
                inventoryPartsTable.setItems(checkPartNameSearch(checkString.toLowerCase()));
            }
        }
    }






    //if the parts table is set with items from the refined search,

    /**
     * @param event enter key is pressed
     *              If the text field is empty, or the enter key is pressed, then it will check to see if what is within
     *              the text field is an integer value,in order to match the product ID, if it is then it will set the items within the table to the filtered
     *              table view based off of the data entered. If what was entered was not a number, then it will set items based off the name entered in.
     */
    public void productSearchFilter(KeyEvent event) {
        String checkProductString = inventoryProductSearch.getText();
        if (event.getCode() == KeyCode.ENTER || Objects.equals(inventoryProductSearch.getText(), "")) {
            if (checkForInteger(checkProductString)) {
                inventoryProductsTable.setItems(checkForMatchingStringProductNumber(checkProductString));
            } else {
                inventoryProductsTable.setItems(checkProductNameSearch(checkProductString));
            }
        }
    }
    /*TODO
    * I type in the id number / part Name into the TextField inventoryPartSearch, this returns
    *
    *Need to find a way to store the original index of the part in the observableList<Part>
    *
    *
    *
    *
    *
    *
    * */















    /**
     * Takes in a string, then search through the product observable list, which is static, and if a match
     * is found add it to the local variable Observable list, which we want to return
     *
     * @param inputToCheck a string input to search through the product observable list
     * @return a product list containing matching strings
     */
    public ObservableList<Product> checkForMatchingStringProductNumber(String inputToCheck) {
        ObservableList<Product> tempListProduct = FXCollections.observableArrayList();
        if (checkForInteger(inputToCheck)) {//check if the string entered is Integer
            int productInt = Integer.parseInt(inputToCheck);
            for (Product p : allProducts) {//looping through all the parts and comparing their IDs to what was typed in the search bar
                if (p.getID() == productInt) {
                    tempListProduct.add(p);
                }
            }
            return tempListProduct;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No matching ID was found with the value of: " + inputToCheck);
            return allProducts;
        }
    }

    public ObservableList<Product> checkProductNameSearch(String nameMatch) {
        ObservableList<Product> productNameListAdd = FXCollections.observableArrayList();
        for (Product p : allProducts) {
            if (p.getName().toLowerCase().contains(nameMatch.toLowerCase())) {
                productNameListAdd.add(p);
                System.out.println("Product was successfully added");
            }
        }
        if (productNameListAdd.size() < 1) {
            return allProducts;
        }
        return productNameListAdd;
    }

    public ObservableList<Part> checkForMatchingStringPartNumber(String inputToCheck) {
        ObservableList<Part> tempListpart = FXCollections.observableArrayList();
        if (checkForInteger(inputToCheck)) {//check if the string entered is Integer
            int partInt = Integer.parseInt(inputToCheck);
            for (Part p : allParts) {//looping through all the parts and comparing their IDs to what was typed in the search bar
                if (p.getId() == partInt) {
                    tempListpart.add(p);
                }
            }
            return tempListpart;
        } else {
            return allParts;
        }
    }

    /**
     * @param nameMatch Takes a string to compare against all the parts name field, and checks to see if
     *                  the name field value contains the value of the string passed in, both are compared with their
     *                  values set to lowercase, this allows more flexibility with the text entered, so that
     *                  it is not case-sensitive
     * @return returns a ObservableList of parts that contain the string passed in within their name field,
     * if there is no match then it will return the original observable list, 'allParts'
     */
    public ObservableList<Part> checkPartNameSearch(String nameMatch) {
        ObservableList<Part> nameListAdd = FXCollections.observableArrayList();
        for (Part p : allParts) {
            if (p.getName().toLowerCase().contains(nameMatch.toLowerCase())) {
                nameListAdd.add(p);
                System.out.println("Part was successfully added");
            }
        }
        if (nameListAdd.size() < 1) {
            return allParts;
        }
        return nameListAdd;
    }
    /*This method takes in a string as a parameter, and will loop through the parts observable list,
        and extract the information from the parts, getname method which will check to see if the part object's
        name contains the substring passed in as an argument, if nothing was found it will return the original list
        else it will return a list with matching criteria
   */

    //when I get the filtered list, the indexes of the parts in the observable list are what they were -1
    ObservableList<Part> tempListPart = FXCollections.observableArrayList();
    ObservableList<Product> tempListProduct = FXCollections.observableArrayList();

    /*When the TextField is written in, or is empty,and the user selects 'Enter'
     * it will then check to see if what was entered is an integer
     * if it was an integer then we will parse the text entered with the parseInt method from the Integer class
     * this will return us with an int value that we store inside partInt
     * after that we can loop through the observable list which the table
     * */

    Scene scene;
    Stage stage;

    /*
    each fxml file should have a corresponding controller class
    the controller will have access to the fxml parts by getting the injected results from the @fxml tag
    the scene is loaded with the information from the node which is loaded by getting the class then the resource of the file

    once we have the node we need to get the stage which, when switching to another stage is dont through
    an event, we get the source of the current event, then cast it to a node
    so that we can get the scene, and then the window of the scene.
    This allows us to then cast it to a new stage

    after we have a new stage we need to place a scene on top of it
    which is where all the content is displayed onto, via a node
    the node is what contains the information written in the fxml file
    after we set the scene we then can show it

     */

    public void deleteProduct(ActionEvent event) {
        Product productToDel = inventoryProductsTable.getSelectionModel().getSelectedItem();
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
            Optional<ButtonType> buttonResult = alert.showAndWait();

            if (buttonResult.get() == ButtonType.OK) {
                getAllProducts().remove(productToDel);
                inventoryProductsTable.setItems(allProducts);
            } else {
                if (buttonResult.get() == ButtonType.CANCEL) {
                    inventoryProductsTable.setItems(allProducts);
                    return;
                }
            }
        }
    }

    //tempListPart needs to get deleted
    public void delPartFromTable(ActionEvent event) {
        //part
        Part partToDel = inventoryPartsTable.getSelectionModel().getSelectedItem();
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            Optional<ButtonType> buttonResult = alert.showAndWait();

            if (buttonResult.get() == ButtonType.OK) {
                getAllParts().remove(partToDel);
                inventoryPartsTable.setItems(allParts);

            } else {
                if (buttonResult.get() == ButtonType.CANCEL) {
                    inventoryPartsTable.setItems(allParts);
                    return;
                }
            }
        }
    }

    public void switchToAddPart(ActionEvent event) throws IOException {
        System.out.println("Add part button pressed");
        FXMLLoader loadAddPart = new FXMLLoader(getClass().getResource("AddPart.fxml"));
        Parent root = loadAddPart.load();
        Scene addPartScene;
        Stage addPartStage = new Stage();
        addPartScene = new Scene(root);
        String styleSheet = this.getClass().getResource("addPart.css").toExternalForm();
        System.out.println(this.getClass());
        addPartScene.getStylesheets().add(styleSheet);
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    //create a new window, so that we can close it without having to switch scene back to inventory controller again, which
    //would initialize the init each time, creating duplicate values.
    public void switchToAddProduct(ActionEvent event) throws IOException {
        System.out.println("Add part button pressed");
        FXMLLoader loadAddProduct = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
        Parent root = loadAddProduct.load();
        Scene addPartScene;
        Stage addPartStage = new Stage();
        addPartScene = new Scene(root);
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }


    /*Switch the scene to Modify Product
     * Every scene needs a node, in this case we are using Parent, to store the details loaded in from the FXML
     * When an event triggers this method, we get the source of the event, and then
     * cast it to a Node, so that we can get the scene from which the event has taken place,
     * and then we can get the window once we have the scene, and finally cast it to stage
     * so that we can set the stage to the new scene that we are loading from the FXML
     * */
    public void switchToModifyPart(ActionEvent event) throws IOException {
        System.out.println("Add part button pressed");
        FXMLLoader loadAddPart = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
        Parent root = loadAddPart.load();
        Scene addPartScene;
        Stage addPartStage = new Stage();
        addPartScene = new Scene(root);
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    public void switchToModifyProduct(ActionEvent event) throws IOException {
        System.out.println("Add part button pressed");
        FXMLLoader loadAddPart = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
        Parent root = loadAddPart.load();
        Scene addPartScene;
        Stage addPartStage = new Stage();
        addPartScene = new Scene(root);
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    public boolean checkDuplicatePart(ObservableList<Part> partList) {
        for (Part part : partList) {
            if (!inventoryPartsTableSet.add(part)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDuplicateProduct(ObservableList<Product> productList) {
        for (Product product : productList) {
            if (!inventoryProductTableSet.add(product)) {
                return false;
            }
        }
        return true;
    }

    //how to use iterator on hashset
    /*Initialize method to be called as soon as the scene is loaded*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //making sure there are no duplicate products
        if (checkDuplicatePart(allParts) && checkDuplicateProduct(allProducts)) {
            inventoryPartsTableSet.add(new InHousePart(990, "Electrical Switch", 25.00, 25, 1, 25, 50));
            inventoryPartsTableSet.add(new InHousePart(1000, "Plastic Key", 10.00, 10, 1, 10, 60));
            inventoryPartsTableSet.add(new OutSourcedPart(1010, "LED", 7.5, 100, 15, 100, "Corsair"));
            inventoryPartsTableSet.add(new OutSourcedPart(1020, "Power Cord", 8.99, 75, 10, 75, "Logitech"));

            inventoryProductTableSet.add(new Product(990, "Mechanical Keyboard", 75.00, 10, 1, 10));
            inventoryProductTableSet.add(new Product(1000, "Membrane Keyboard", 35.00, 35, 5, 35));
            inventoryProductTableSet.add(new Product(1010, "Wireless Keyboard", 50.00, 50, 10, 1));


            //if allParts does not contain duplicate values
            if (checkDuplicatePart(allParts)) {
                allParts.addAll(inventoryPartsTableSet);
                inventoryPartsTable.setItems(allParts);
            }

            allProducts.addAll(inventoryProductTableSet);
            inventoryProductsTable.setItems(allProducts);


            //Set Parts Columns
            inventoryPartsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
            partsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
            inventoryPartsCostUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
            inventoryPartsInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));

            //Set Products columns
            inventoryProductsProductID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            inventoryProductsProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
            ProductCostColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
            inventoryProductsInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        }
    }
}
