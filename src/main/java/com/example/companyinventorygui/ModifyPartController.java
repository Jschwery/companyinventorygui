package com.example.companyinventorygui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
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

    @FXML
    Label varIdMachineOrComp;
    Part selectedPart = InventoryController.partThatIsSelected;
    //Storage Variables to store data from the TextField entries
    private String name;
    private int inventory;
    private Double cost;
    private int max;
    private int min;
    private int machineId;
    private String companyName;

    @FXML
    TableView<Part> inventoryPartsTable;

    /**
     * Validates each of the fields entry boxes to make sure they are the correct type
     * will also display text of what went wrong, and log the errors into an errors file, named errors.txt
     * @throws IOException
     */
    public void moddedPartValidator() throws IOException {
        BufferedWriter writeErrorsToFile = new BufferedWriter(new FileWriter("errors.txt"));
        if (AddPartController.stringChecker(String.valueOf(modifyPartName.getText()), "Please enter a String for the 'Name' field!")) {
            name = String.valueOf(modifyPartName.getText());
        } else {
            writeErrorsToFile.write("Entry for Part Name is of an invalid type. Required: String");
        }
        if (AddPartController.intChecker(String.valueOf(modifyPartInventory.getText()), "Please enter an int for the 'inventory' field!")) {
            inventory = Integer.parseInt(String.valueOf(modifyPartInventory.getText()));
        } else {
            writeErrorsToFile.append("\nEntry for Part Inventory is of an invalid type. Required: int");
        }
        if (AddPartController.doubleChecker(modifyPartCost.getText(), "Please enter a Double for the 'cost' field!")) {
            cost = Double.parseDouble(String.valueOf(modifyPartCost.getText()));
        } else {
            writeErrorsToFile.append("\nEntry for Part Cost is of an invalid type. Required: double");
        }
        if (AddPartController.intChecker(modifyPartMax.getText(), "Please enter an int for the 'max' field!")) {
            max = Integer.parseInt(String.valueOf(modifyPartMax.getText()));
        } else {
            writeErrorsToFile.append("\nEntry for Part Max is of an invalid type. Required: int");
        }
        //if the selected part is an InHousePart that means the
        if (selectedPart.getClass().getSimpleName().equals("InHousePart")) {
            if (AddPartController.intChecker(String.valueOf(modifyPartMachineID.getText()), "Please enter an int for the 'machineID' field!")) {
                machineId = Integer.parseInt(String.valueOf(modifyPartMachineID.getText()));
            } else {
                writeErrorsToFile.append("\nEntry for Machine ID is of an invalid type. Required: int");
            }
        }
        if (selectedPart.getClass().getSimpleName().equals("OutSourcedPart")) {
            if (AddPartController.stringChecker(String.valueOf(modifyPartMachineID.getText()), "Please enter a String for the 'Company Name' field! ")) {
                companyName = (String.valueOf(modifyPartMachineID.getText()));
            } else {
                writeErrorsToFile.append("\nEntry for Company Name is of an invalid type. Required: String");
            }
        }
        writeErrorsToFile.close();
    }

    public void displayErrors(){
        if(Files.isWritable(Path.of("errors.txt"))){
            try {
                BufferedReader errorFileReader = new BufferedReader(new FileReader("errors.txt"));
                StringBuilder sb = new StringBuilder();
                while(errorFileReader.readLine() != null){
                    sb.append(errorFileReader.readLine());
                }
                displayErrors.setText(sb.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayErrors.setOpacity(100);
        }
        System.out.println("No errors found, all good!");
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
        modifyPartMachineID.setLayoutX(110);
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
        modifyPartMachineID.setLayoutX(73);
        modifyPartMachineID.setPromptText("Enter Machine ID");
    }


    public void saveModifiedPart(ActionEvent event) throws IOException {
        Part selectedPart = InventoryController.partThatIsSelected;
        moddedPartValidator();

        int indexOfSelectedPart = InventoryController.partThatIsSelected.getIndexValue();
        System.out.println("Index of selected part: " + indexOfSelectedPart);
        System.out.println(selectedPart);


        if (selectedPart.getClass().getSimpleName().equals("OutSourcedPart")) {
            OutSourcedPart modifiedPart = new OutSourcedPart(selectedPart.getId(), name , cost, inventory, min, max, companyName);
            InventoryController.updateParts(indexOfSelectedPart,modifiedPart);
        }
            else {
            if(selectedPart.getClass().getSimpleName().equals("OutSourcedPart")) {
                InHousePart modifiedPart = new InHousePart(selectedPart.getId(), name, cost, inventory, min, max, machineId);
                InventoryController.updateParts(indexOfSelectedPart, modifiedPart);
            }
        }
        Stage thisStage = (Stage) modifyPartCancel.getScene().getWindow();
        thisStage.close();
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
    modifySelectInHouse.setSelected(true);
        if(selectedPart != null) {
            modifyPartID.setText(String.valueOf(selectedPart.getId()));
            modifyPartMax.setText(String.valueOf(selectedPart.getMax()));
            modifyPartCost.setText(String.valueOf(selectedPart.getPrice()));
            modifyPartMin.setText(String.valueOf(selectedPart.getMin()));
            modifyPartName.setText(String.valueOf(selectedPart.getName()));
            modifyPartInventory.setText(String.valueOf(selectedPart.getStock()));
            if(selectedPart.getClass().getSimpleName().equals("InHousePart")){
                modifySelectInHouse.setSelected(true);
                modifySelectOutSourced.setDisable(true);
                selectInHouseButton();
                modifyPartMachineID.setText(String.valueOf(((InHousePart) selectedPart).getMachineId()));
            }
            if(selectedPart.getClass().getSimpleName().equals("OutSourcedPart")){
                modifySelectOutSourced.setSelected(true);
                modifySelectInHouse.setDisable(true);
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

    public void switchSelected() {
    if(modifySelectInHouse.isSelected()){
        varIdMachineOrComp.setText("Machine ID");
        }
    else{
        if(modifySelectOutSourced.isSelected()){
            varIdMachineOrComp.setText("Company Name");
            }
        }
    }

    public void switchToMainfromModifyPart(ActionEvent event) {
     Stage thisStage = (Stage) modifyPartCancel.getScene().getWindow();
     selectedPart = null;
     thisStage.close();
    }
}
