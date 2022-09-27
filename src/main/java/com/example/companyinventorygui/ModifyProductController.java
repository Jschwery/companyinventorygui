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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyProductController{
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

    Scene modifyProductScene;
    Stage modifyProductStage;


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
        //TODO
        public void obtainSelectedProductIndex(){
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

            return FXCollections.observableArrayList();
        }


}


