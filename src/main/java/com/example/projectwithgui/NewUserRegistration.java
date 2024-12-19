package com.example.projectwithgui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class NewUserRegistration {
    private final Stage stage;
    private static final String FILE_PATH = "users.txt";

    public NewUserRegistration(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        createFileIfNotExists(FILE_PATH);

        Image backgroundImage = new Image("orangebg.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1550);
        backgroundView.setFitHeight(720);
        backgroundView.setPreserveRatio(true);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        nameField.setPrefWidth(300);
        styleLabel(nameLabel);
        HBox nameLayout = new HBox(10, nameLabel, nameField);
        nameLayout.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(300);
        styleLabel(usernameLabel);
        HBox usernameLayout = new HBox(10, usernameLabel, usernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(300);
        styleLabel(passwordLabel);
        HBox passwordLayout = new HBox(10, passwordLabel, passwordField);
        passwordLayout.setAlignment(Pos.CENTER);

        Label confirmPasswordLabel = new Label("Password:");
        PasswordField confirmPasswordField = new PasswordField();
        passwordField.setPrefWidth(300);
        styleLabel(passwordLabel);
        HBox confirmPasswordLayout = new HBox(10, confirmPasswordLabel ,confirmPasswordField);
        passwordLayout.setAlignment(Pos.CENTER);

        Button registerButton = new Button("Register");
        Button cancelButton = new Button("Cancel");
        HBox buttonLayout = new HBox(10, registerButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        Label statusLabel = new Label();
        statusLabel.setTextFill(Color.RED);
        statusLabel.setVisible(false);

        styleButton(registerButton);
        styleButton(cancelButton);

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                showErrorMessage(statusLabel, "**Fields can't be empty!**");
            } else if (userExists(username)) {
                showErrorMessage(statusLabel, "**Username already exists!**");
            } else {
                addUserToFile(username, password);
                statusLabel.setText("Registration Successful!");
                statusLabel.setTextFill(Color.GREEN);
                UserLoginOptions userLoginOptions = new UserLoginOptions(stage);
                userLoginOptions.show();
            }
        });

        cancelButton.setOnAction(e -> {
            UserLoginOptions userLoginOptions = new UserLoginOptions(stage);
            userLoginOptions.show();
        });


        VBox formLayout = new VBox(20, nameLayout, usernameLayout, passwordLayout, buttonLayout, statusLabel);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(200, 0, 0, 0));

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, formLayout);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setTitle("New User Registration");
    }

    private boolean userExists(String username) {
        try {
            List<String> lines = Files.readAllLines(Path.of(FILE_PATH));
            return lines.stream().anyMatch(line -> line.split(",")[0].equals(username));
        } catch (IOException e) {
            return false;
        }
    }

    private void addUserToFile(String username, String password) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(username + "," + password + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    The method addUserToFile uses System.lineSeparator() to add a new line after writing user information (username and password), ensuring that each user entry appears on its own line within the file.
    private void createFileIfNotExists(String filePath) {
        Path path = Path.of(filePath);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showErrorMessage(Label statusLabel, String message) {
        statusLabel.setText(message);
        statusLabel.setVisible(true);

        new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(500);
                    statusLabel.setVisible(!statusLabel.isVisible());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
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


    private void styleLabel(Label label) {
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    }
}

