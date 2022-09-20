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


    public void switchToMainFromAddProduct(ActionEvent event) {
    Stage stage = (Stage)productPartSearch.getScene().getWindow();
    stage.close();
    }

    /*

    Steps in order

    Need parts table & Products table

    when we click add on parts table
    brings us to add part section

    if we choose in-house radio button,
    switch to outsourced, machine id tag gets switched to company name

    when we hit save then the part shows in the part table

    if company in selected and saved, next time we click on modify all the
    data in forms are saved

    ID is auto generated so once we hit save we give display the ID value ++ in the field

    In search the ID will get highlighted
    The part name will be filtered

    Next add an associated part

    select a row from top part table, then click add
    it will then be placed in the associated part table

    to remove the product highlight it then click remove button
    pop up  asking if sure to delete when this happens
    this will then remove from the list once the popup is check 'OK'
    once save is clicked add the bike to product table

    the product that we chose to get modified gets displayed in text fields
    the table of associated parts for the selected product are also displayed

    logical error checks when text entered is not a valid value to the
    array/list
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}

/*
has table view
Identicle to parts table view main menu


 */