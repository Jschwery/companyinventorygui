package com.example.companyinventorygui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent node = FXMLLoader.load((getClass().getResource("Inventory.fxml")));
        Scene scene = new Scene(node);
        String styleSheet = this.getClass().getResource("CSS-Styling/styles.css").toExternalForm();
        scene.getStylesheets().add(styleSheet);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

