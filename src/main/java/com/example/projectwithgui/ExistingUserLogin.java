package com.example.projectwithgui;

import javafx.application.Platform;
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
//imports for file handling
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExistingUserLogin {
    private final Stage stage;
    //file to save users ddata
    private static final String FILE_PATH = "users.txt";
    private volatile boolean showError = true;

    public ExistingUserLogin(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        createFileIfNotExists(FILE_PATH);

        Image backgroundImage = new Image("orangebg.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1550);
        backgroundView.setFitHeight(720);
        backgroundView.setPreserveRatio(true);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(300);
        styleLabel(usernameLabel);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(300);
        styleLabel(passwordLabel);

        HBox usernameLayout = new HBox(10, usernameLabel, usernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        HBox passwordLayout = new HBox(10, passwordLabel, passwordField);
        passwordLayout.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        Button cancelButton = new Button("Cancel");
        styleButton(loginButton);
        styleButton(cancelButton);

        Label statusLabel = new Label();
        statusLabel.setTextFill(Color.RED);
        statusLabel.setVisible(false);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (validateCredentials(username, password)) {
                showError = false;
                statusLabel.setText("Login Successful!");
                statusLabel.setTextFill(Color.GREEN);
                statusLabel.setVisible(true);
                UserInterface userInterface = new UserInterface();
                userInterface.show(stage);
            } else {
                //blinking notification till the user doesnt enters valid values
                showBlinkingNotification(statusLabel, "**Invalid Username or Password**");
            }
        });

        cancelButton.setOnAction(e -> {
            UserLoginOptions userLoginOptions = new UserLoginOptions(stage);
            userLoginOptions.show();
        });

        HBox buttonLayout = new HBox(20, loginButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        //form layout formation
        VBox formLayout = new VBox(20, usernameLayout, passwordLayout, buttonLayout, statusLabel);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(200, 0, 0, 0));
        //adding everything to stackpane
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, formLayout);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setTitle("Existing User Login");
    }

    private boolean validateCredentials(String username, String password) {
        try {
            List<String> lines = Files.readAllLines(Path.of(FILE_PATH));
            return lines.stream().anyMatch(line -> {
                String[] parts = line.split(",");
                return parts.length == 2 && parts[0].equals(username) && parts[1].equals(password);
            });
        } catch (IOException e) {
            return false;
        }
    }
//    he line must have exactly two parts (ensured by parts.length == 2).
    private void showBlinkingNotification(Label statusLabel, String message) {
        showError = true;
        statusLabel.setText(message);
        statusLabel.setVisible(true);

        new Thread(() -> {
            while (showError) {
                Platform.runLater(() -> statusLabel.setVisible(!statusLabel.isVisible()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> statusLabel.setVisible(false));
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
}
