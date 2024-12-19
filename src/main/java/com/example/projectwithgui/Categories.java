
package com.example.projectwithgui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;

import java.util.List;

public class Categories {

    public void show(Stage stage) {
        // Retrieve categories from CategoryManager
        List<String> categoriesList = CategoryManager.getCategories();

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        // Title label for categories
        Button title = new Button("Available Categories");
        title.setFont(new Font("Arial", 24));
        title.setStyle("-fx-background-color: #d88f1e; -fx-text-fill: white; -fx-padding: 10 20; "
                + "-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #8c5d15; -fx-border-width: 2;");
        title.setEffect(new DropShadow());
        vBox.getChildren().add(title);

        // Add buttons for each category
        for (String category : categoriesList) {
            Button categoryButton = new Button(category);
            styleButton(categoryButton);
            vBox.getChildren().add(categoryButton);
        }

        // Back button
        Button backButton = new Button("Back to Admin Menu");
        styleButton(backButton);
        backButton.setOnAction(e -> {
            ShowAdminMenu adminMenu = new ShowAdminMenu();
            adminMenu.show(stage);
        });
        vBox.getChildren().add(backButton);

        Scene scene = new Scene(vBox, 1550, 670);
        stage.setScene(scene);
        vBox.setStyle("-fx-background-color: #fdd880");
        stage.setTitle("Categories");
        stage.show();
    }

    // Method to style buttons
    private void styleButton(Button button) {
        button.setFont(new javafx.scene.text.Font("Didot", 16));
        button.setStyle("-fx-background-color: #d88f1e; -fx-text-fill: white; -fx-padding: 10 20; "
                + "-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #8c5d15; -fx-border-width: 2;");
        button.setEffect(new DropShadow());
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #8c5d15; -fx-text-fill: white; "
                + "-fx-padding: 10 20; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #d88f1e; -fx-border-width: 2;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #d88f1e; -fx-text-fill: white; "
                + "-fx-padding: 10 20; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #8c5d15; -fx-border-width: 2;"));
    }

}

