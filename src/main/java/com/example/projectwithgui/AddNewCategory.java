package com.example.projectwithgui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AddNewCategory {
//
//    // List to store category names
//    private static final List<String> categories = new ArrayList<>();
//
//    public void show(Stage stage) {
//        // Main layout
//        VBox vBox = new VBox(20);
//        vBox.setAlignment(Pos.CENTER);
//
//        // Button to initiate adding a new category
//        Button addCategoryButton = new Button("Add New Category");
//        styleButton(addCategoryButton);
//
//        // Back button to return to Admin Menu
//        Button backButton = new Button("Back to Admin Menu");
//        styleButton(backButton);
//
//        // VBox to dynamically hold category buttons
//        VBox categoryBox = new VBox(10);
//        categoryBox.setAlignment(Pos.CENTER);
//
//        // Action to show input for new category
//        addCategoryButton.setOnAction(e -> {
//            VBox inputBox = new VBox(10);
//            inputBox.setAlignment(Pos.CENTER);
//
//            Label inputLabel = new Label("Enter Category Name:");
//            TextField categoryField = new TextField();
//            categoryField.setPromptText("Category Name");
//            categoryField.setPrefWidth(250);
//            HBox hBox = new HBox(10);
//            hBox.setAlignment(Pos.CENTER);
//            hBox.getChildren().addAll(inputLabel, categoryField);
//
//            Button confirmButton = new Button("Add Category");
//            styleButton(confirmButton);
//
//            // Action to add the category
//            confirmButton.setOnAction(ev -> {
//                String categoryName = categoryField.getText().trim();
//                if (!categoryName.isEmpty()) {
//                    if (!categories.contains(categoryName)) {
//                        categories.add(categoryName); // Add to list of categories
//
//                        // Create the .txt file for the category
//                        createCategoryFile(categoryName);
//
//                        // Create a button for the new category
//                        Button newCategoryButton = new Button(categoryName);
//                        styleButton(newCategoryButton);
//
//                        // Action for the category button
//                        newCategoryButton.setOnAction(categoryEvent -> {
//                            System.out.println("Selected category: " + categoryName);
//                        });
//
//                        // Add the new button to the category box
//                        categoryBox.getChildren().add(newCategoryButton);
//
//                        // Remove the input box after adding
//                        vBox.getChildren().remove(inputBox);
//                    } else {
//                        System.out.println("Category already exists!");
//                    }
//                } else {
//                    System.out.println("Please enter a category name.");
//                }
//            });
//
//            inputBox.getChildren().addAll(hBox, confirmButton);
//            vBox.getChildren().add(1, inputBox); // Add the input box below the "Add New Category" button
//        });
//
//        // Action to return to Admin Menu
//        backButton.setOnAction(e -> {
//            ShowAdminMenu adminMenu = new ShowAdminMenu();
//            adminMenu.show(stage);
//        });
//
//        // Add components to the main layout
//        vBox.getChildren().addAll(addCategoryButton, categoryBox, backButton);
//
//        // Create and set the scene
//        Scene scene = new Scene(vBox, 1550, 670);
//        stage.setScene(scene);
//        stage.setTitle("Add New Category");
//        stage.show();
//    }
//
//    // Method to retrieve categories
//    public static List<String> getCategories() {
//        return categories;
//    }
//
//    // Method to create a file for the category
//    private void createCategoryFile(String categoryName) {
//        String fileName = categoryName.replaceAll("\\s+", "_") + ".txt";
//        File file = new File(fileName);
//        if (!file.exists()) {
//            try {
//                if (file.createNewFile()) {
//                    System.out.println("Category file created: " + file.getName());
//                }
//            } catch (IOException e) {
//                System.err.println("An error occurred while creating the file: " + e.getMessage());
//            }
//        } else {
//            System.out.println("File already exists: " + file.getName());
//        }
//    }
//
//    // Method to style buttons
//    private void styleButton(Button button) {
//        button.setFont(new Font("Didot", 16));
//        button.setStyle("-fx-background-color: #263988; -fx-text-fill: white; -fx-padding: 10 20; "
//                + "-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #031b73; -fx-border-width: 2;");
//        button.setEffect(new DropShadow());
//        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #031b73; -fx-text-fill: white; "
//                + "-fx-padding: 10 20; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #263988; -fx-border-width: 2;"));
//        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #263988; -fx-text-fill: white; "
//                + "-fx-padding: 10 20; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #031b73; -fx-border-width: 2;"));
//    }
//}




//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AddNewCategory {
//
//    // List to store category names
//    private static final List<String> categories = new ArrayList<>();
//
//    // File to save category names
//    private static final String CATEGORY_FILE = "categories.txt";
//
//    public void show(Stage stage) {
//        // Main layout
//        VBox vBox = new VBox(20);
//        vBox.setAlignment(Pos.CENTER);
//
//        // Button to initiate adding a new category
//        Button addCategoryButton = new Button("Add New Category");
//        styleButton(addCategoryButton);
//
//        // Back button to return to Admin Menu
//        Button backButton = new Button("Back to Admin Menu");
//        styleButton(backButton);
//
//        // VBox to dynamically hold category buttons
//        VBox categoryBox = new VBox(10);
//        categoryBox.setAlignment(Pos.CENTER);
//
//        // Load saved categories and add their buttons
//        loadCategories();
//        for (String category : categories) {
//            addCategoryButtonToBox(category, categoryBox);
//        }
//
//        // Action to show input for new category
//        addCategoryButton.setOnAction(e -> {
//            VBox inputBox = new VBox(10);
//            inputBox.setAlignment(Pos.CENTER);
//
//            Label inputLabel = new Label("Enter Category Name:");
//            TextField categoryField = new TextField();
//            categoryField.setPromptText("Category Name");
//            categoryField.setPrefWidth(250);
//            HBox hBox = new HBox(10);
//            hBox.setAlignment(Pos.CENTER);
//            hBox.getChildren().addAll(inputLabel, categoryField);
//
//            Button confirmButton = new Button("Add Category");
//            styleButton(confirmButton);
//
//            // Action to add the category
//            confirmButton.setOnAction(ev -> {
//                String categoryName = categoryField.getText().trim();
//                if (!categoryName.isEmpty()) {
//                    if (!categories.contains(categoryName)) {
//                        categories.add(categoryName); // Add to list of categories
//                        saveCategories(); // Save the updated categories list
//                        createCategoryFile(categoryName); // Create the file for the category
//                        addCategoryButtonToBox(categoryName, categoryBox); // Add button for the category
//
//                        // Remove the input box after adding
//                        vBox.getChildren().remove(inputBox);
//                    } else {
//                        System.out.println("Category already exists!");
//                    }
//                } else {
//                    System.out.println("Please enter a category name.");
//                }
//            });
//
//            inputBox.getChildren().addAll(hBox, confirmButton);
//            vBox.getChildren().add(1, inputBox); // Add the input box below the "Add New Category" button
//        });
//
//        // Action to return to Admin Menu
//        backButton.setOnAction(e -> {
//            ShowAdminMenu adminMenu = new ShowAdminMenu();
//            adminMenu.show(stage);
//        });
//
//        // Add components to the main layout
//        vBox.getChildren().addAll(addCategoryButton, categoryBox, backButton);
//
//        // Create and set the scene
//        Scene scene = new Scene(vBox, 1550, 670);
//        stage.setScene(scene);
//        stage.setTitle("Add New Category");
//        stage.show();
//    }
//
//    // Add button for a category to the VBox
//    private void addCategoryButtonToBox(String categoryName, VBox categoryBox) {
//        Button newCategoryButton = new Button(categoryName);
//        styleButton(newCategoryButton);
//
//        newCategoryButton.setOnAction(e -> {
//            System.out.println("Selected category: " + categoryName);
//        });
//
//        categoryBox.getChildren().add(newCategoryButton);
//    }
//
//    // Save categories to a file
//    private void saveCategories() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CATEGORY_FILE))) {
//            for (String category : categories) {
//                writer.write(category);
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            System.err.println("An error occurred while saving categories: " + e.getMessage());
//        }
//    }
//
//    // Load categories from a file
//    private void loadCategories() {
//        File file = new File(CATEGORY_FILE);
//        if (file.exists()) {
//            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    categories.add(line);
//                }
//            } catch (IOException e) {
//                System.err.println("An error occurred while loading categories: " + e.getMessage());
//            }
//        }
//    }
//
//    // Method to create a file for the category
//    private void createCategoryFile(String categoryName) {
//        String fileName = categoryName.replaceAll("\\s+", "_") + ".txt";
//        File file = new File(fileName);
//        if (!file.exists()) {
//            try {
//                if (file.createNewFile()) {
//                    System.out.println("Category file created: " + file.getName());
//                }
//            } catch (IOException e) {
//                System.err.println("An error occurred while creating the file: " + e.getMessage());
//            }
//        } else {
//            System.out.println("File already exists: " + file.getName());
//        }
//    }
//
//    // Method to style buttons
//    private void styleButton(Button button) {
//        button.setFont(new Font("Didot", 16));
//        button.setStyle("-fx-background-color: #263988; -fx-text-fill: white; -fx-padding: 10 20; "
//                + "-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #031b73; -fx-border-width: 2;");
//        button.setEffect(new DropShadow());
//        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #031b73; -fx-text-fill: white; "
//                + "-fx-padding: 10 20; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #263988; -fx-border-width: 2;"));
//        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #263988; -fx-text-fill: white; "
//                + "-fx-padding: 10 20; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #031b73; -fx-border-width: 2;"));
//    }
//}

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AddNewCategory {

    public void show(Stage stage) {
        // Main layout
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        // Button to initiate adding a new category
        Button addCategoryButton = new Button("Add New Category");
        styleButton(addCategoryButton);

        // Back button to return to Admin Menu
        Button backButton = new Button("Back to Admin Menu");
        styleButton(backButton);

        // VBox to dynamically hold category buttons
        VBox categoryBox = new VBox(10);
        categoryBox.setAlignment(Pos.CENTER);

        // Action to show input for new category
        addCategoryButton.setOnAction(e -> {
            VBox inputBox = new VBox(10);
            inputBox.setAlignment(Pos.CENTER);

            Label inputLabel = new Label("Enter Category Name:");
            TextField categoryField = new TextField();
            categoryField.setPromptText("Category Name");
            categoryField.setPrefWidth(250);
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(inputLabel, categoryField);

            Button confirmButton = new Button("Add Category");
            styleButton(confirmButton);

            // Action to add the category
            confirmButton.setOnAction(ev -> {
                String categoryName = categoryField.getText().trim();
                if (!categoryName.isEmpty()) {
                    CategoryManager.addCategory(categoryName);

                    // Create a button for the new category
                    Button newCategoryButton = new Button(categoryName);
                    styleButton(newCategoryButton);

                    // Action for the category button
                    newCategoryButton.setOnAction(categoryEvent -> {
                        System.out.println("Selected category: " + categoryName);
                    });

                    // Add the new button to the category box
                    categoryBox.getChildren().add(newCategoryButton);

                    // Remove the input box after adding
                    vBox.getChildren().remove(inputBox);
                } else {
                    System.out.println("Please enter a category name.");
                }
            });

            inputBox.getChildren().addAll(hBox, confirmButton);
            vBox.getChildren().add(1, inputBox); // Add the input box below the "Add New Category" button
        });

        // Action to return to Admin Menu
        backButton.setOnAction(e -> {
            ShowAdminMenu adminMenu = new ShowAdminMenu();
            adminMenu.show(stage);
        });

        // Add components to the main layout
        vBox.getChildren().addAll(addCategoryButton, categoryBox, backButton);

        // Create and set the scene
        Scene scene = new Scene(vBox, 1550, 670);
        stage.setScene(scene);
        vBox.setStyle("-fx-background-color: #fdd880;");
        stage.setTitle("Add New Category");
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
