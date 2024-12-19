package com.example.projectwithgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CustomersDatabase {
    private TableView<Customers> table;
    private ObservableList<Customers> customerList;

    public void show(Stage stage) {
        // Create a VBox layout to hold the components
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        HBox topMenuBar = new HBox(10);
        topMenuBar.setStyle("-fx-background-color: #e6e1e1; -fx-border-color: #b5b2b2; -fx-border-width: 1;");

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            ShowAdminMenu adminMenu = new ShowAdminMenu();
            adminMenu.show(stage); // Navigate back to the Admin Menu
        });
        topMenuBar.getChildren().add(cancelButton);

        // Create the root layout (VBox or whatever layout you are using)
        VBox root1 = new VBox(20);

        // Add the topMenuBar at the top of the root layout
        root.getChildren().add(topMenuBar);

        Label titleLabel = new Label("Customers Database");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #14309f; -fx-padding: 10px 0;");

        // Create the table and its columns
        table = new TableView<>();
        customerList = FXCollections.observableArrayList();

        // Add columns for username and password
        TableColumn<Customers, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());

        TableColumn<Customers, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPassword());

        // Add columns to table
        table.getColumns().add(usernameColumn);
        table.getColumns().add(passwordColumn);

        // Set table styling
        table.setStyle("-fx-font-size: 14px; -fx-border-color: #e0e0e0; -fx-background-color: #fafafa;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(customerList); // Set the data to be displayed

        // Set column styling
        usernameColumn.setStyle("-fx-font-weight: bold; -fx-background-color: #f0f0f0;");
        passwordColumn.setStyle("-fx-font-weight: bold; -fx-background-color: #f0f0f0;");

        // Set table header styling
        table.getColumns().forEach(column -> {
            column.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #c4c4c4; -fx-text-fill: #333;");
        });

        // Set row styling
        table.setRowFactory(tv -> {
            TableRow<Customers> row = new TableRow<>();
            row.setStyle("-fx-background-color: #ffffff;");
            row.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
                if (isNowHovered) {
                    row.setStyle("-fx-background-color: #e0e0e0;");
                } else {
                    row.setStyle("-fx-background-color: #ffffff;");
                }
            });
            return row;
        });

        // Load customer data from the file
        loadCustomerData();

        // Add components to the layout
        root.getChildren().addAll(titleLabel, table);

        // Create a Scene and set it on the stage
        Scene scene = new Scene(root, 1550, 670);
        scene.setFill(Color.WHITE); // Set scene background color
        stage.setTitle("Customers Database");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    private MenuBar createMenuBar(Stage stage) {
        // Create a MenuBar
        MenuBar menuBar = new MenuBar();

        // Create the "Cancel" MenuItem and set its action
        MenuItem cancelItem = new MenuItem("Cancel");
        cancelItem.setOnAction(e -> {
            // Action for returning to Admin Menu
            ShowAdminMenu adminMenu = new ShowAdminMenu();
            adminMenu.show(stage); // Show Admin Menu
        });

        // Create a Menu and add the "Cancel" item to it
        Menu cancelMenu = new Menu();
        cancelMenu.getItems().add(cancelItem);

        // Add the cancelMenu to the MenuBar
        menuBar.getMenus().add(cancelMenu);

        // Position the Cancel menu to the left
        cancelMenu.setStyle("-fx-padding: 0 20px 0 20px;");

        return menuBar;
    }

    private void loadCustomerData() {
        File file = new File("users.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Assuming each line contains "username,password" format
                    String[] userData = line.split(",");
                    if (userData.length == 2) {
                        customerList.add(new Customers(userData[0].trim(), userData[1].trim()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("users.txt file not found!");
        }
    }
}
