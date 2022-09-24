module com.example.companyinventorygui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.companyinventorygui to javafx.fxml;
    exports com.example.companyinventorygui;
}