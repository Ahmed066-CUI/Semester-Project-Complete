module com.example.projectwithgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.projectwithgui to javafx.fxml;
    exports com.example.projectwithgui;
}