module com.example.companyinventorygui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.companyinventorygui to javafx.fxml;
    exports com.example.companyinventorygui;
}