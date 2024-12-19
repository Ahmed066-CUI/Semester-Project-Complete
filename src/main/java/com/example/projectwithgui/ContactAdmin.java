package com.example.projectwithgui;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;


public class ContactAdmin {
    private Stage stage;
    public void showAlert(Stage primaryStage) {

        ImageView icon = new ImageView(new Image("C:\\Users\\lenovo\\OneDrive\\Desktop\\Ahmad1\\src\\main\\resources\\icon1.png"));
        icon.setFitWidth(50);
        icon.setFitHeight(50);


        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Email Recipients");
        alert.setHeaderText("Admins contact info:");
        alert.setGraphic(icon);
        alert.setContentText("Email 1: sheikhmahmed113@gmail.com\nEmail 2: sp24-bse-066@cuilahore.edu.pk");
        alert.showAndWait();

    }

    public void show(Stage primaryStage) {
        showAlert(primaryStage);
    }
}