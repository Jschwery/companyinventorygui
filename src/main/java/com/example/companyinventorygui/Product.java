package com.example.companyinventorygui;

import com.example.companyinventorygui.Part;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

        public class Product implements Cloneable{
        private String name;
        private Double cost;
        private int stock;
        private int min;
        private int max;
        private int ID;
        ObservableList<Part> associatedParts=FXCollections.observableArrayList();

        public Product(int ID,String name,Double cost,int stock,int min,int max){
        this.name=name;
        this.cost=cost;
        this.stock=stock;
        this.min=min;
        this.max=max;
        this.ID=ID;
        }


        public Object clone()throws CloneNotSupportedException{
        return super.clone();
        }
        public int getID(){
        return ID;
        }

        public void addAssociatedPart(Part part){
        associatedParts.add(part);
        }

        public boolean deleteAssociatedPart(Part part){
        return associatedParts.remove(part);
        }

        public ObservableList<Part>getAllAssociatedParts(){
        return associatedParts;
        }


        public void setID(int ID){
        this.ID=ID;
        }

        public String getName(){
        return name;
        }

        public void setName(String name){
        this.name=name;
        }

        public Double getPrice(){
        return cost;
        }

        public void setPrice(Double cost){
        this.cost=cost;
        }
        public void setCost(Double cost){this.cost=cost;}

        public Double getCost(){
        return cost;
        }

        public int getStock(){
        return stock;
        }

        public void setStock(int stock){
        this.stock=stock;
        }

        public int getMin(){
        return min;
        }

        public void setMin(int min){
        this.min=min;
        }

        public int getMax(){
        return max;
        }

        public void setMax(int max){
        this.max=max;
        }
        }