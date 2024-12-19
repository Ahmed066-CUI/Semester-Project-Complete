package com.example.projectwithgui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Account {
    private Stage accountStage;

    public Account(String userName) {
        accountStage = new Stage();
        accountStage.setTitle("User Account");

        // Demo profile picture
        ImageView profilePic = new ImageView(new Image("https://via.placeholder.com/100")); // Replace with an actual image URL or file
        profilePic.setFitHeight(100);
        profilePic.setFitWidth(100);

        // Display user's name
        Text userNameText = new Text("Welcome, " + userName + "!");

        // Cancel and Logout buttons
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> accountStage.close()); // Close the Account window

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            // Handle logout logic here
            accountStage.close();
            System.out.println(userName + " logged out.");
        });

        // Layout for the Account window
        VBox layout = new VBox(10, profilePic, userNameText, cancelButton, logoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f0f0f0;");

        Scene scene = new Scene(layout, 300, 250);
        accountStage.setScene(scene);
    }

    public void show() {
        accountStage.show();
    }
}
