package com.example.projectwithgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class RemoveProduct {

    private ObservableList<String> categoriesList = FXCollections.observableArrayList();
    private ObservableList<Product> productsList = FXCollections.observableArrayList();

    private String selectedCategory;

    public void show(Stage stage) {
        // Load categories
        categoriesList.addAll(CategoryManager.getCategories());

        stage.setTitle("Delete Product");

        // Main layout
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #fdd880;");

        // Header
        Label header = new Label("Delete Product");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // ComboBox for categories
        ComboBox<String> categoryComboBox = new ComboBox<>(categoriesList);
        categoryComboBox.setPromptText("Select Category");
        categoryComboBox.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #fff; -fx-border-radius: 5;");

        // ComboBox for products
        ComboBox<Product> productComboBox = new ComboBox<>();
        productComboBox.setPromptText("Select Product");
        productComboBox.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #fff; -fx-border-radius: 5;");

        Button deleteButton = new Button("Delete Product");
        styleButton(deleteButton);
        Button backButton = new Button("Back");
        styleButton(backButton);

        // Event: Load products when a category is selected
        categoryComboBox.setOnAction(e -> {
            selectedCategory = categoryComboBox.getValue();
            loadProductsForCategory(selectedCategory);
            productComboBox.setItems(productsList);
        });

        // Event: Delete the selected product
        deleteButton.setOnAction(e -> {
            Product selectedProduct = productComboBox.getValue();
            if (selectedProduct != null) {
                // Confirm deletion
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Confirm Deletion");
                alert.setHeaderText(null);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        // Delete product from file
                        deleteProductFromFile(selectedCategory, selectedProduct);
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Product deleted successfully!");

                        // Reload products
                        loadProductsForCategory(selectedCategory);
                        productComboBox.setItems(productsList);
                    }
                });
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a product to delete.");
            }
        });
        backButton.setOnAction(e -> {
           ProductsManagement productsManagement = new ProductsManagement();
           productsManagement.show(stage);
        });

        // Layout setup
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setAlignment(Pos.CENTER);
        inputGrid.addRow(0, new Label("Category:"), categoryComboBox);
        inputGrid.addRow(1, new Label("Product:"), productComboBox);

        HBox buttonBox = new HBox(20, deleteButton,backButton);
        buttonBox.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(header, inputGrid, buttonBox);

        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setScene(scene);
         stage.centerOnScreen();
        stage.show();

    }

    // Load products for a specific category
    private void loadProductsForCategory(String category) {
        productsList.clear();
        List<Product> products = CategoryManager.getProductsForCategory(category);
        productsList.addAll(products);
    }

    // Delete product details from the file
    private void deleteProductFromFile(String category, Product productToDelete) {
        String fileName = category.replaceAll("\\s+", "_") + ".txt";
        File file = new File(fileName);

        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("Product Name: " + productToDelete.getName())) {
                    updatedLines.add(line); // Keep the lines that don't match the product to delete
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading product file: " + e.getMessage());
        }

        // Write the updated lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing product file: " + e.getMessage());
        }
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #d88f1e; -fx-text-fill: white; -fx-padding: 10 20; -fx-border-radius: 5;");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
