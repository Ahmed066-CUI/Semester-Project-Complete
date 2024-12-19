package com.example.projectwithgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModifyProduct {

    private ObservableList<String> categoriesList = FXCollections.observableArrayList();//to notify user if anything add remove modify in list
    private ObservableList<Product> productsList = FXCollections.observableArrayList();

    private String selectedCategory;

    public void show(Stage stage) {
        // Load categories
        categoriesList.addAll(CategoryManager.getCategories());

        stage.setTitle("Modify Product");

        // Main layout
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #fdd880;");

        // Header
        Label header = new Label("Modify Product");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // ComboBox for categories
        ComboBox<String> categoryComboBox = new ComboBox<>(categoriesList);
        categoryComboBox.setPromptText("Select Category");
        categoryComboBox.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #fff; -fx-border-radius: 5;");

        // ComboBox for products
        ComboBox<Product> productComboBox = new ComboBox<>();
        productComboBox.setPromptText("Select Product");
        productComboBox.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #fff; -fx-border-radius: 5;");

        // Input fields for modifying product details
        TextField nameField = new TextField();
        nameField.setPromptText("Enter New Product Name");
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #fff; -fx-border-radius: 5;");

        TextField priceField = new TextField();
        priceField.setPromptText("Enter New Price");
        priceField.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #fff; -fx-border-radius: 5;");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter New Quantity");
        quantityField.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #fff; -fx-border-radius: 5;");

        TextField discountField = new TextField();
        discountField.setPromptText("Enter New Discount");
        discountField.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #fff; -fx-border-radius: 5;");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Enter New Description");
        descriptionField.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #fff; -fx-border-radius: 5;");

        Button updateButton = new Button("Update Product");
        styleButton(updateButton);
        Button backButton = new Button("Back");
        styleButton(backButton);

        // Event: Load products when a category is selected
        categoryComboBox.setOnAction(e -> {
            selectedCategory = categoryComboBox.getValue();
            loadProductsForCategory(selectedCategory);
            productComboBox.setItems(productsList);
        });

        // Event: Prefill product details when a product is selected
        productComboBox.setOnAction(e -> {
            Product selectedProduct = productComboBox.getValue();
            if (selectedProduct != null) {
                nameField.setText(selectedProduct.getName());
                priceField.setText(String.valueOf(selectedProduct.getPrice()));
                quantityField.setText(String.valueOf(selectedProduct.getQuantity()));
                discountField.setText(String.valueOf(selectedProduct.getDiscount()));
                descriptionField.setText(selectedProduct.getDescription());
            }
        });

        // Event: Update the product details
        updateButton.setOnAction(e -> {
            Product selectedProduct = productComboBox.getValue();
            if (selectedProduct != null) {
                String newName = nameField.getText();
                double newPrice = Double.parseDouble(priceField.getText());
                int newQuantity = Integer.parseInt(quantityField.getText());
                double newDiscount = Double.parseDouble(discountField.getText());
                String newDescription = descriptionField.getText();
//In your code, parsing is used to convert the text entered by the user (in the form of a String) into appropriate data types such as double or int. This is necessary because data entered in a TextField is always retrieved as a String, but the product details (e.g., price, quantity, and discount) are stored and processed as numeric types in your application.
                // Update product in the file
                updateProductInFile(selectedCategory, selectedProduct, newName, newPrice, newQuantity, newDiscount, newDescription);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product updated successfully!");

                // Reload products
                loadProductsForCategory(selectedCategory);
                productComboBox.setItems(productsList);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a product and fill all fields.");
            }
            ProductsManagement productsManagement=new ProductsManagement();
            productsManagement.show(stage);
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
        inputGrid.addRow(2, new Label("New Name:"), nameField);
        inputGrid.addRow(3, new Label("New Price:"), priceField);
        inputGrid.addRow(4, new Label("New Quantity:"), quantityField);
        inputGrid.addRow(5, new Label("New Discount:"), discountField);
        inputGrid.addRow(6, new Label("New Description:"), descriptionField);

        HBox buttonBox = new HBox(20, updateButton,backButton);
        buttonBox.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(header, inputGrid, buttonBox);

        Scene scene = new Scene(mainLayout, 900, 600);
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

    // Update product details in the file
    private void updateProductInFile(String category, Product oldProduct, String newName, double newPrice, int newQuantity, double newDiscount, String newDescription) {
        String fileName = category.replaceAll("\\s+", "_") + ".txt";
        File file = new File(fileName);

        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Product Name: " + oldProduct.getName())) {
                    // Replace with updated product details
                    updatedLines.add("Product Name: " + newName + "|Price: " + newPrice +
                            "|Quantity: " + newQuantity + "|Discount: " + newDiscount +
                            "%|Description: " + newDescription + "|Image: " + oldProduct.getImage());
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading product file: " + e.getMessage());
        }

        // Write updated lines back to the file
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
        button.setEffect(new DropShadow(10, 2, 2, javafx.scene.paint.Color.BLACK));

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #d17e1f; -fx-text-fill: white; -fx-padding: 10 20; -fx-border-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #d88f1e; -fx-text-fill: white; -fx-padding: 10 20; -fx-border-radius: 5;"));
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    }

