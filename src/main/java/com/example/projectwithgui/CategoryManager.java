
package com.example.projectwithgui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
//most important class consists of all method to take products from files etc
public class CategoryManager {
    private static final List<String> categories = new ArrayList<>();
    private static final String CATEGORY_FILE = "categories.txt";
    //load all existing categories
    static {
        loadCategories();
    }

    public static void addCategory(String categoryName) {
        if (!categories.contains(categoryName)) {
            categories.add(categoryName);
            saveCategories();
            createCategoryFile(categoryName);//for creating file for every category
        } else {
            System.out.println("Category already exists!");
        }
    }

    public static List<String> getCategories() {
        return categories;
    }

    public static List<Product> getProductsForCategory(String category) {
        List<Product> products = new ArrayList<>();
        String fileName = category.replaceAll("\\s+", "_") + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                String name = "", description = "", image = "";
                double price = 0.0;
                int quantity = 0;
                double discount = 0.0;

                for (String field : fields) {
                    String[] keyValue = field.split(": ", 2);
                    if (keyValue.length < 2) continue;

                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    switch (key) {
                        case "Product Name":
                            name = value;
                            break;
                        case "Price":
                            price = Double.parseDouble(value);
                            break;
                        case "Quantity":
                            quantity = Integer.parseInt(value);
                            break;
                        case "Discount":
                            discount = Double.parseDouble(value.replace("%", ""));
                            break;
                        case "Description":
                            description = value;
                            break;
                        case "Image":
                            image = value.replace("Image Selected: ", "").trim();
                            break;
                    }
                }

                if (!name.isEmpty()) {
                    products.add(new Product(name, price, quantity, discount, description, image, category));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file for category " + category + ": " + e.getMessage());
        }

        return products;
    }

//
//    // Create a UI display for all products
//    public static VBox createAllProductsDisplay() {
//        VBox allProductsArea = new VBox(10);
//        allProductsArea.setPadding(new Insets(20, 20, 0, 0));
//
//        for (String category : categories) {
//            List<Product> products = getProductsForCategory(category);
//
//            for (Product product : products) {
//                VBox productBox = new VBox(10);
//                productBox.setPadding(new Insets(10));
//                productBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1;");
//
//                Label nameLabel = new Label("Product Name: " + product.getName());
//                Label priceLabel = new Label("Price: " + product.getPrice());
//                Label quantityLabel = new Label("Quantity: " + product.getQuantity());
//                Label discountLabel = new Label("Discount: " + product.getDiscount() + "%");
//                Label descriptionLabel = new Label("Description: " + product.getDescription());
//
//                ImageView productImage = new ImageView();
//                productImage.setFitWidth(100);
//                productImage.setFitHeight(100);
//                try {
//                    productImage.setImage(new Image(product.getImage()));
//                } catch (Exception e) {
//                    productImage.setImage(new Image("file:placeholder.png"));
//                }
//
//                productBox.getChildren().addAll(
//                        nameLabel,
//                        priceLabel,
//                        quantityLabel,
//                        discountLabel,
//                        descriptionLabel,
//                        productImage
//                );
//
//                allProductsArea.getChildren().add(productBox);
//            }
//        }
//
//        if (allProductsArea.getChildren().isEmpty()) {
//            allProductsArea.getChildren().add(new Label("No products found in any category."));
//        }
//
//        return allProductsArea;
//    }

    // Save categories to a file
    private static void saveCategories() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CATEGORY_FILE))) {
            for (String category : categories) {
                writer.write(category);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving categories: " + e.getMessage());
        }
    }

    // Load categories from a file
    private static void loadCategories() {
        File file = new File(CATEGORY_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    categories.add(line);
                }
            } catch (IOException e) {
                System.err.println("Error loading categories: " + e.getMessage());
            }
        }
    }
    public static List<Product> getAllProducts() {
        // Combine all products from all categories into a single list
        List<Product> allProducts = new ArrayList<>();
        for (String category : getCategories()) {
            allProducts.addAll(getProductsForCategory(category));
        }
        return allProducts;
    }


    // Create a file for each category
    private static void createCategoryFile(String categoryName) {
        String fileName = categoryName.replaceAll("\\s+", "_") + ".txt";
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created for category: " + file.getName());
                }
            } catch (IOException e) {
                System.err.println("Error creating category file: " + e.getMessage());
            }
        }
    }

//    public static boolean removeProduct(Product product) {
//        if (product == null || product.getCategory() == null || product.getName() == null) {
//            System.err.println("Invalid product or category provided.");
//            return false;
//        }
//
//        String category = product.getCategory();
//        String fileName = category.replaceAll("\\s+", "_") + ".txt";
//        File file = new File(fileName);
//        boolean productRemoved = false;
//
//        if (!file.exists()) {
//            System.out.println("Category file not found: " + fileName);
//            return false;
//        }
//
//        List<String> updatedLines = new ArrayList<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Check if the current line represents the product to be removed
//                if (!line.contains("Product Name: " + product.getName()) ||
//                        !line.contains("Price: " + product.getPrice()) ||
//                        !line.contains("Quantity: " + product.getQuantity())) {
//                    updatedLines.add(line); // Keep lines that do not match the product
//                } else {
//                    productRemoved = true; // Product match found, mark for removal
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("Error reading category file: " + e.getMessage());
//            return false;
//        }
//
//        if (productRemoved) {
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//                for (String updatedLine : updatedLines) {
//                    writer.write(updatedLine);
//                    writer.newLine();
//                }
//            } catch (IOException e) {
//                System.err.println("Error writing updated category file: " + e.getMessage());
//                return false;
//            }
//        }
//
//        return productRemoved;
//    }


}





