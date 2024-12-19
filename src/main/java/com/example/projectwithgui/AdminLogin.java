package com.example.projectwithgui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdminLogin {
    private final String ADMIN_USERNAME = "Ahmed";
    private final String ADMIN_PASSWORD = "1234";
    private final Stage stage;
    private volatile boolean showError = true;

    public AdminLogin(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Image backgroundImage = new Image("orangebg.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1550);
        backgroundView.setFitHeight(745);
        backgroundView.setPreserveRatio(true);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Didot", 18));
        usernameLabel.setTextFill(Color.BLACK);

        TextField usernameField = new TextField();
        usernameField.setPrefWidth(250);
        usernameField.setStyle("-fx-background-radius: 5; -fx-padding: 5 10;");

        HBox usernameLayout = new HBox(10, usernameLabel, usernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Didot", 18));
        passwordLabel.setTextFill(Color.BLACK);

        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(250);
        passwordField.setStyle("-fx-background-radius: 5; -fx-padding: 5 10;");

        HBox passwordLayout = new HBox(10, passwordLabel, passwordField);
        passwordLayout.setAlignment(Pos.CENTER);

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Didot", 14));
        statusLabel.setTextFill(Color.RED);
        statusLabel.setVisible(false);

        Button loginButton = new Button("Login");
        Button cancelButton = new Button("Cancel");
        styleButton(loginButton);
        styleButton(cancelButton);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim(); //we use trim to remove spaces
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                showBlinkingNotification(statusLabel, "**Fields can't be left empty!**");
            } else if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                showError = false;
                ShowAdminMenu adminMenu = new ShowAdminMenu();
                adminMenu.show(stage);
            } else {
                showBlinkingNotification(statusLabel, "**Invalid Credentials!**");
            }
        });

        cancelButton.setOnAction(e -> {
            showError = false;
            Main main = new Main();
            main.start(stage);
        });

        HBox buttonLayout = new HBox(25, loginButton, cancelButton);
        buttonLayout.setPadding(new Insets(0, 0,0 , 130));
        buttonLayout.setAlignment(Pos.CENTER);

        VBox formLayout = new VBox(20, usernameLayout, passwordLayout, buttonLayout,statusLabel);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(300, 0, 0, 0));

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, formLayout);

        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(null);
        stage.setFullScreenExitHint("");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Admin Login");
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


    private void showBlinkingNotification(Label statusLabel, String message) {
        showError = true;
        statusLabel.setText(message);
        statusLabel.setVisible(true);

        new Thread(() -> {
            while (showError) {
                Platform.runLater(() -> statusLabel.setVisible(!statusLabel.isVisible()));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> statusLabel.setVisible(false));
        }).start();
    }
}

