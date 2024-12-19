package com.example.projectwithgui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class ProductsManagementFunctions {

    public void show(Stage stage) {
        GridPane gridPane = new GridPane();

        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);

        ComboBox<String> categoryComboBox = new ComboBox<>();
        List<String> categories = CategoryManager.getCategories();
        if (categories.isEmpty()) {
            categoryComboBox.setPromptText("No categories available");
        } else {
            categoryComboBox.getItems().addAll(categories);
            categoryComboBox.setPromptText("Select a Category");
        }

        Label productNameLabel = new Label("Product Name:");
        TextField productNameField = new TextField();
        productNameField.setPromptText("Enter Product Name");

        Label productPriceLabel = new Label("Product Price:");
        TextField productPriceField = new TextField();
        productPriceField.setPromptText("Enter Product Price");

        Label productQuantityLabel = new Label("Product Quantity:");
        TextField productQuantityField = new TextField();
        productQuantityField.setPromptText("Enter Product Quantity");

        Label discountLabel = new Label("Discount (if any):");
        TextField discountField = new TextField();
        discountField.setPromptText("Enter Discount (if any)");

        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Enter Product Description");


        // File chooser for product image
        Button chooseImageButton = new Button("Choose Product Image");
        styleButton(chooseImageButton);
        Label selectedImageLabel = new Label("No file selected");
        ImageView productImageView = new ImageView();
        //it is used to store absolute path of image
        String[] absolutePath = new String[1]; // Use an array to modify it inside the lambda

        chooseImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Product Image");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );


            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                try {
                    // Save the absolute path of the selected image
                    absolutePath[0] = selectedFile.getAbsolutePath();

                    // Load and display the image using the selected file path
                    Image productImage = new Image(new FileInputStream(selectedFile), 250, 250, true, true);
                    productImageView.setImage(productImage);

                    // Display the absolute path of the selected image in the label
                    selectedImageLabel.setText("Image Selected: " + absolutePath[0]);
                } catch (FileNotFoundException ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to load image.");
                }
            } else {
                selectedImageLabel.setText("No file selected");
            }
        });

        Button addProductButton = new Button("Add Product");
        styleButton(addProductButton);
        Button goBackButton = new Button("Go Back");
        styleButton(goBackButton);


        addProductButton.setOnAction(e -> {
            saveProductDetails(
                    categoryComboBox,
                    productNameField,
                    productPriceField,
                    productQuantityField,
                    discountField,
                    descriptionField,
                    selectedImageLabel,
                    productImageView
            );
            ProductsManagement productsManagement = new ProductsManagement();
            productsManagement.show(stage);
        });

        // Action to go back
        goBackButton.setOnAction(e -> {
            ProductsManagement productsManagement = new ProductsManagement();
            productsManagement.show(stage);
        });

        // Add components to layout
        gridPane.add(new Label("Category:"), 0, 0);
        gridPane.add(categoryComboBox, 1, 0);

        gridPane.add(productNameLabel, 0, 1);
        gridPane.add(productNameField, 1, 1);

        gridPane.add(productPriceLabel, 0, 2);
        gridPane.add(productPriceField, 1, 2);

        gridPane.add(productQuantityLabel, 0, 3);
        gridPane.add(productQuantityField, 1, 3);

        gridPane.add(discountLabel, 0, 4);
        gridPane.add(discountField, 1, 4);

        gridPane.add(descriptionLabel, 0, 5);
        gridPane.add(descriptionField, 1, 5);

        gridPane.add(chooseImageButton, 0, 6);
        gridPane.add(selectedImageLabel, 1, 6);
        gridPane.add(productImageView, 1, 7);

        gridPane.add(addProductButton, 0, 8);
        gridPane.add(goBackButton, 4, 8);


        Scene scene = new Scene(gridPane, 1550, 670);
        stage.setScene(scene);
        stage.setTitle("Manage Products");
        stage.show();
    }

    private void saveProductDetails(
            ComboBox<String> categoryComboBox,
            TextField productNameField,
            TextField productPriceField,
            TextField productQuantityField,
            TextField discountField,
            TextField descriptionField,
            Label selectedImageLabel,
            ImageView productImageView
    ) {
        String category = categoryComboBox.getValue();
        String productName = productNameField.getText().trim();
        String productPrice = productPriceField.getText().trim();
        String productQuantity = productQuantityField.getText().trim();
        String discount = discountField.getText().trim();
        String description = descriptionField.getText().trim();
        String image = selectedImageLabel.getText();

        if (category == null || category.isEmpty() || productName.isEmpty() || productPrice.isEmpty() || productQuantity.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields except Discount are required.");
            return;
        }

        try {
            double price = Double.parseDouble(productPrice);
            int quantity = Integer.parseInt(productQuantity);
            double discountValue = discount.isEmpty() ? 0 : Double.parseDouble(discount);

            // Save product details to category file
            File categoryFile = new File(category.replaceAll("\\s+", "_") + ".txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(categoryFile, true))) {
                writer.write("Product Name: " + productName + "|");
                writer.write("Price: " + price + "|");
                writer.write("Quantity: " + quantity + "|");
                writer.write("Discount: " + discountValue + "%|");
                writer.write("Description: " + description + "|");
                writer.write("Image: " + (image.equals("No file selected") ? "None" : image));
                writer.write("\n");  //seperator
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully!");

        } catch (NumberFormatException | IOException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid input or error while saving.");
        }
    }

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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

