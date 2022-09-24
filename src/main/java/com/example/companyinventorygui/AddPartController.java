package com.example.companyinventorygui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    @FXML
    private RadioButton selectInHouse;

    @FXML
    private RadioButton selectOutSourced;


    @FXML
    TextField addPartName;
    @FXML
    TextField addPartInventory;
    @FXML
    TextField addPartCost;
    @FXML
    TextField addPartMax;
    @FXML
    TextField addPartID;
    @FXML
    TextField addPartMin;
    @FXML
    TextField addMachineIDorCompany;
    @FXML
    Button saveAddPart;
    @FXML
    Button cancelAddPart;
    @FXML
    Label variableLabel;
    Scene addpartScene;
    Stage addpartstage;
//Temp storage for saving FieldData
    private String name;
    private int inventory;
    private Double cost;
    private int max;
    private int min;
    private int machineId;
    private String companyName;

    public void closeSceneWindow(){
        Stage stage = (Stage) saveAddPart.getScene().getWindow();
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

    public void cancelButtonCloseWindow(ActionEvent event){
        closeSceneWindow();
    }

    /**
     * Validate each of the fields within the add part form, and display a label indicating an issue if there
     * was an unexpected entry into one of the form fields. Then save the data into placeholders variables, that will later be used to
     * create new part objects to add to the allParts observable list
     */
    public boolean partValidator() {
        if (doubleChecker(String.valueOf(addPartCost.getText()),"Please enter a Double for the 'Cost' field!")) {
            cost = Double.parseDouble(String.valueOf(addPartCost.getText()));
        } else {
            System.out.println("Cost field check failed");
            return false;
        }
        if (intChecker(String.valueOf(addPartInventory.getText()),"Please enter an int for the 'Inventory' field!")) {
            inventory = Integer.parseInt(String.valueOf(addPartInventory.getText()));
        } else {
            System.out.println("Inventory field check failed");
            return false;
        }
        if (intChecker(String.valueOf(addPartMax.getText()), "Please enter an int for the 'Max' field!")) {
            max = Integer.parseInt(String.valueOf(addPartMax.getText()));
        } else {
            System.out.println("Part Max field check failed");
            return false;
        }
        if(stringChecker(addPartName.getText(), "Please enter a String for the 'Name' field!")){
            name = String.valueOf(addPartName.getText());
        }else{
            System.out.println("Part name field check failed");
            return false;
        }
        if (intChecker(String.valueOf(addPartMin.getText()), "Please enter an int for the 'Min' field!")) {
            min = Integer.parseInt(String.valueOf(addPartMin.getText()));
        } else {
            System.out.println("Part min field check failed");
            return false;
        }
        if (selectOutSourced.isSelected()) {
            if (stringChecker(addMachineIDorCompany.getText(), "Please enter a String for the 'company name' field!")){
                companyName = addMachineIDorCompany.getText();
            }
            else {
                return false;
            }
        }
        else if (selectInHouse.isSelected()) {
            if (intChecker(String.valueOf(addMachineIDorCompany.getText()), "Please enter an int for the 'machine ID' field!")) {
            machineId = Integer.parseInt(String.valueOf(addMachineIDorCompany.getText()));
            }
            else {
                return false;
            }
        }
        return true;
    }

    /*Initialize in-house button to be selected*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectInHouse.setSelected(true);
    }

    /**
     * This method will add to the Observable parts list in the Inventory Controller class, if all the fields are validated
     * and depending on which radio button is selected, the part to the table will be either InHouse or Outsourced
     * @param event save button Click event to add parts to the observable list
     */
    public void onSaveClick(ActionEvent event) {

        if (partValidator()) {

            int tempId = InventoryController.partID;

            if (selectInHouse.isSelected()) {
                InventoryController.addPart(new InHousePart(tempId, name, cost, inventory, min, max, machineId));
            }
            InventoryController.incrementPartID();
            if (selectOutSourced.isSelected()) {
                InventoryController.addPart(new OutSourcedPart(tempId, name, cost, inventory, min, max, companyName));
            }
            InventoryController.incrementPartID();
            closeSceneWindow();
        }
    }
    /**
     * If the outSourced button is selected, then unselect the inHouse button, and change the variable label to 'Company Name'
     * then set the textField entry 9 units to the right on the x-axis to allow space for the text label to fit nicely.
     */
    public void selectOutSourcedButton() {
        selectInHouse.setSelected(false);
        selectOutSourced.setSelected(true);
        variableLabel.setText("Company Name");
        addMachineIDorCompany.setLayoutX(124);

    }

    /**
     *
     */
    public void selectInHouseButton() {
        selectOutSourced.setSelected(false);
        selectInHouse.setSelected(true);
        variableLabel.setText("Machine ID");
        addMachineIDorCompany.setLayoutX(101);

    }
}
