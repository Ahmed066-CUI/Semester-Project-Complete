package com.example.projectwithgui;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Main extends Application {
    //This is my main class that further leads us to respective stages
    @Override
    public void start(Stage primaryStage) {
        //background image set
        Image icon = new Image("Icon.png");  //icon of my store
        primaryStage.getIcons().add(icon);

        Image backgroundImage = new Image("orangebg.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1550);
        backgroundView.setFitHeight(720);
        backgroundView.setPreserveRatio(true);
        primaryStage.setFullScreen(true);

        //welcome label
        Label welcomeLabel = new Label("Welcome to ATDOOR");
        welcomeLabel.setFont(Font.font("Halvetica", FontWeight.BOLD, 52));          //can also change FontPostire:to ITALIC etc
        welcomeLabel.setTextFill(Color.web("#d83724"));
        welcomeLabel.setStyle("-fx-effect: dropshadow(gaussian, #ffffff, 5, 0.5, 2, 2);");
        welcomeLabel.setAlignment(Pos.CENTER);
        welcomeLabel.setStyle("-fx-font-weight: bold;");


        Button adminLoginButton = new Button("Admin Login");
        Button userLoginButton = new Button("User Login");
        Button exitbutton = new Button("Exit");
        adminLoginButton.setEffect(new DropShadow());
        userLoginButton.setEffect(new DropShadow());
        //beautifying the buttons
        styleButton(adminLoginButton);
        styleButton(userLoginButton);
        styleexitbutton(exitbutton);
        addButtonAnimation(adminLoginButton);
        addButtonAnimation(userLoginButton);
        //defining functionalities
        adminLoginButton.setOnAction(e -> {
            AdminLogin adminLogin = new AdminLogin(primaryStage);
            adminLogin.show();
        });
        userLoginButton.setOnAction(e -> {
            UserLoginOptions userLoginOptions = new UserLoginOptions(primaryStage);
            userLoginOptions.show();
        });
        exitbutton.setOnAction(e -> {
            primaryStage.close();
        });

        VBox buttonLayout = new VBox(20);
        buttonLayout.getChildren().addAll(adminLoginButton, userLoginButton, exitbutton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(30);
        mainLayout.getChildren().addAll(welcomeLabel, buttonLayout);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(330, 0, 0, 0));
        //using staack pane bcz buttons are stacked over backgorund
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, mainLayout);
        //adding everything to scene
        Scene scene = new Scene(root);
        primaryStage.setTitle("E-Commerce System - Login");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(null);
       // primaryStage.centerOnScreen();


        primaryStage.show();
    }
    //Admin User login button styling

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


    private void styleexitbutton(Button button) {
        button.setFont(new Font("Didot", 20));
        button.setStyle("-fx-background-color: #a85700; -fx-text-fill: white; -fx-padding: 10 20; "
                + "-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #813303;-fx-border-width: 2;");
    }

    //buttons animations
    private void addButtonAnimation(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        button.setOnMouseEntered(e -> scaleTransition.playFromStart());
        button.setOnMouseExited(e -> scaleTransition.stop());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
