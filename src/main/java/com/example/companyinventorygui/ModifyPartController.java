package com.example.companyinventorygui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {
    Scene modifypartScene;
    Stage modifypartstage;

    //GUI components imported from the FXML file
    @FXML
    private RadioButton modifySelectInHouse;
    @FXML
    private RadioButton modifySelectOutSourced;
    @FXML
    private TextField modifyPartName;
    @FXML
    private TextField modifyPartInventory;
    @FXML
    private TextField modifyPartCost;
    @FXML
    private TextField modifyPartMax;
    @FXML
    private TextField modifyPartID;
    @FXML
    private TextField modifyPartMachineID;
    @FXML
    private TextField modifyPartMin;
    @FXML
    private Button modifyPartSave;
    @FXML
    private Button modifyPartCancel;
    @FXML
    TextArea displayErrors;

    /*
    * from the part table, selected part will be whatever is highlighted
    * so when we modify that part, the fields will be set to what class that part is
    *
    * if its an inhouse part
    * then the fields will be populated to an inHouse part, and machine id will be displayed,
    * inhouse radio button will be selected
    *
    * we want to change the part from an InHousePart, to an OutSourcedPart
    *
    * what steps do we need in order to do this?
    * Get the current selected parts class, check if it is an InHousePart or OutSourcedPart
    * */

    @FXML
    Label varIdMachineOrComp;
    Part selectedPart = InventoryController.partThatIsSelected;
    //Storage Variables to store data from the TextField entries
    private String name;
    private int indexOfSavedPart;
    private int inventory;
    private Double cost;
    private int max;
    private int min;
    private int machineId;
    private String companyName;
    private InHousePart inhousePart = null;
    private OutSourcedPart outSourcedPart = null;

    @FXML
    TableView<Part> inventoryPartsTable;

    /**
     * Validates each of the fields entry boxes to make sure they are the correct type
     * will also display text of what went wrong, and log the errors into an errors file, named errors.txt
     * @throws IOException
     */
    public boolean moddedPartValidator() throws IOException {
        if (AddPartController.stringChecker(modifyPartName.getText(), "Please enter a String for the 'Company Name' field! ")) {
            name = modifyPartName.getText();
        } else {
            return false;
        }
        if (AddPartController.intChecker(String.valueOf(modifyPartInventory.getText()), "Please enter an int for the 'inventory' field!")) {
            inventory = Integer.parseInt(String.valueOf(modifyPartInventory.getText()));
        } else {
            return false;
        }
        if (AddPartController.doubleChecker(String.valueOf(modifyPartCost.getText()), "Please enter a Double for the 'cost' field!")) {
            cost = Double.parseDouble(String.valueOf(modifyPartCost.getText()));
        } else {
            return false;
        }
        if (AddPartController.intChecker(String.valueOf(modifyPartMax.getText()), "Please enter an int for the 'max' field!")) {
            max = Integer.parseInt(String.valueOf(modifyPartMax.getText()));
        } else {
            return false;
        }
        //if the selected part is an InHousePart that means the
        if (modifySelectInHouse.isSelected()) {
            if (AddPartController.intChecker(String.valueOf(modifyPartMachineID.getText()), "Please enter an int for the 'machineID' field!")) {
                machineId = Integer.parseInt(String.valueOf(modifyPartMachineID.getText()));
            } else {
                return false;
            }
        }
        if (modifySelectOutSourced.isSelected()) {
            if (AddPartController.stringChecker(String.valueOf(modifyPartMachineID.getText()), "Please enter a String for the 'Company Name' field! ")) {
                companyName = (modifyPartMachineID.getText());
            } else {
                return false;
            }
        }
        /*if the selected part is an inHouse part, but the OutSourcedPart is selected, we need to store the values from the
            we need to store inHouse value properties aside from inHouse into OutSourced, then
            need to validate the companyName field
            outSourcedPart
         */

        System.out.println("All parts validated");
        return true;
    }

    /**
     * When this method is invoked, it will unselect the InHouse radio button, also will change the text
     * label to Company name to reflect the part being outsourced. The text label 'company name' was a bit too
     * long so the 'modifyPartMachineID' layout-x property had to be shifted slightly on the x-axis to
     * accommodate for the extra room.
     */
    public void selectOutSourcedButton() {
        modifySelectInHouse.setSelected(false);
        switchSelected();
        modifyPartMachineID.setLayoutX(132);
        modifyPartMachineID.setPromptText("Enter Company Name");
    }

    /**
     * When this method is invoked, unselects the outsourced radio button, and changes
     * text label to Machine ID, instead of Company Name. Also sets the Text field x-layout
     * accordingly
     */
    public void selectInHouseButton() {
        modifySelectOutSourced.setSelected(false);
        switchSelected();
        modifyPartMachineID.setLayoutX(104);
        modifyPartMachineID.setPromptText("Enter Machine ID");
    }
    public void closeSceneWindow(){
        Stage stage = (Stage) modifyPartCancel.getScene().getWindow();
        stage.close();
    }

    public void exitBackToInventory(ActionEvent event){
        selectedPart = null;
        closeSceneWindow();
    }



    /**
     * If all the fields are validated successfully, we will get the index of the selected part, so that we can
     * apply the changes to the correct part within the allParts displayable list, with the variable values.
     * @param event Save Button Clicked
     * @throws IOException
     */
    public void saveModifiedPart(ActionEvent event) throws IOException {
        if (moddedPartValidator()) {
            int indexOfSelectedPart = InventoryController.getIndexOfPart(InventoryController.partThatIsSelected);

            if (modifySelectOutSourced.isSelected()) {
                String partCompanyName = modifyPartMachineID.getText();
                OutSourcedPart modifiedPart = new OutSourcedPart(selectedPart.getId(), name, cost, inventory, min, max, partCompanyName);
                InventoryController.updateParts(indexOfSelectedPart, modifiedPart);
            } else {
                if (modifySelectInHouse.isSelected()) {
                    int partMachineID = Integer.parseInt(String.valueOf(modifyPartMachineID.getText()));
                    InHousePart modifiedPart = new InHousePart(selectedPart.getId(), name, cost, inventory, min, max, partMachineID);
                    InventoryController.updateParts(indexOfSelectedPart, modifiedPart);
                }
            }
            Stage thisStage = (Stage) modifyPartCancel.getScene().getWindow();
            thisStage.close();
        }
    }
    public void partInititiation(){

        modifySelectInHouse.setSelected(true);
        if(inhousePart != null) {
            modifyPartID.setText(String.valueOf(selectedPart.getId()));
            modifyPartMax.setText(String.valueOf(selectedPart.getMax()));
            modifyPartCost.setText(String.valueOf(selectedPart.getPrice()));
            modifyPartMin.setText(String.valueOf(selectedPart.getMin()));
            modifyPartName.setText(String.valueOf(selectedPart.getName()));
            modifyPartInventory.setText(String.valueOf(selectedPart.getStock()));
            if(selectedPart.getClass().getSimpleName().equals("InHousePart") || modifySelectInHouse.isSelected()){
                modifySelectInHouse.setSelected(true);
                selectInHouseButton();
                modifyPartMachineID.setText(String.valueOf(((InHousePart) selectedPart).getMachineId()));
            }
        }else if(outSourcedPart != null){
            modifyPartID.setText(String.valueOf(selectedPart.getId()));
            modifyPartMax.setText(String.valueOf(selectedPart.getMax()));
            modifyPartCost.setText(String.valueOf(selectedPart.getPrice()));
            modifyPartMin.setText(String.valueOf(selectedPart.getMin()));
            modifyPartName.setText(String.valueOf(selectedPart.getName()));
            modifyPartInventory.setText(String.valueOf(selectedPart.getStock()));
            if(selectedPart.getClass().getSimpleName().equals("OutSourcedPart") || modifySelectOutSourced.isSelected()){
                modifySelectOutSourced.setSelected(true);
                selectOutSourcedButton();
                modifyPartMachineID.setText(String.valueOf(((OutSourcedPart) selectedPart).getCompanyName()));
            }
        }
        if (selectedPart == null) {
            modifyPartCost.setDisable(true);
            modifyPartMax.setDisable(true);
            modifyPartCost.setDisable(true);
            modifyPartMax.setDisable(true);
            modifyPartMin.setDisable(true);
            modifyPartName.setDisable(true);
            modifyPartInventory.setDisable(true);
            modifyPartMachineID.setDisable(true);
            modifySelectInHouse.setSelected(false);
            modifySelectInHouse.setDisable(true);
            modifySelectOutSourced.setDisable(true);
            modifyPartSave.setDisable(true);
        }
    }

    /**
     * Selects the InHouse button by default
     * If the user selected a part prior to clicking modify, when this scene
     * is switched to 'ModifyPartController'(this scene) the text fields will be filled
     * with the data pulled from the part that was selected.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
        if(InventoryController.partThatIsSelected.getClass().getSimpleName().equals("InHousePart") || modifySelectInHouse.isSelected()){
            inhousePart = (InHousePart) InventoryController.partThatIsSelected;
        }else if(InventoryController.partThatIsSelected.getClass().getSimpleName().equals("OutSourcedPart") || modifySelectOutSourced.isSelected()) {
            outSourcedPart = (OutSourcedPart) InventoryController.partThatIsSelected;
            }
        else{
            System.out.println("No Part found");
        }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a part to Modify!");
            alert.showAndWait();
            }
        partInititiation();
    }

    public void switchSelected() {
    if(modifySelectInHouse.isSelected()){
        varIdMachineOrComp.setText("Machine ID");
        }
    else if(modifySelectOutSourced.isSelected()) {
            varIdMachineOrComp.setText("Company Name");
        }
        else{
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a part to Modify!");
        alert.showAndWait();
         }
    }

}
