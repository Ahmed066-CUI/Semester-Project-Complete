
package com.example.projectwithgui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Chatbot {
    private GeminiAPIHandler geminiAPIHandler;

    public Chatbot() {
        String apiKey = "AIzaSyAE_5jufrjZW01mJ6O7Dgz7P6eKJvYzCCM";
        this.geminiAPIHandler = new GeminiAPIHandler(apiKey);
    }

    public String getChatbotResponse(String prompt) {
        try {
            return geminiAPIHandler.getResponse(prompt);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: Unable to get a response from the API.";
        }
    }

    public void start(Stage primaryStage) {
        // Chat Area
        TextArea chatArea = new TextArea();
        chatArea.setStyle("-fx-background-color: #fdf5e6; -fx-text-fill: black; -fx-border-color: #d88f1e; -fx-border-width: 2px; -fx-font-size: 14px;");
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        chatArea.setPrefHeight(500); //heighth of chat area

        ScrollPane scrollPane = new ScrollPane(chatArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-border-color: #d88f1e; -fx-border-width: 2px;");

        // User Input Field
        TextField userInput = new TextField();
        userInput.setStyle("-fx-background-color: #f5deb3; -fx-text-fill: black; -fx-prompt-text-fill: #8b4513; -fx-font-size: 14px; -fx-border-color: #d88f1e; -fx-border-width: 2px;");
        userInput.setPrefWidth(450);
        userInput.setPromptText("Type your message here...");
//Adjusting Width:
//
//If you want the input field to dynamically adjust its width, you can avoid setting a preferred width.
//Alternatively, you can set a smaller or larger value depending on your interface design.
        // Send Button
        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #d88f1e; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px;");
        sendButton.setOnAction(e -> handleUserInput(chatArea, userInput));

        // Back Button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #d88f1e; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px;");
        backButton.setOnAction(e -> {
            UserInterface ui = new UserInterface();
            ui.show(primaryStage);
        });

        // Enable Enter key to send messages
        userInput.setOnAction(e -> handleUserInput(chatArea, userInput));

        // Input Box Layout
        HBox inputBox = new HBox(10, userInput, sendButton, backButton);
        inputBox.setStyle("-fx-padding: 10px;");
        inputBox.setSpacing(10);

        // Main Layout
        VBox layout = new VBox(10, scrollPane, inputBox);
        layout.setStyle("-fx-background-color: #fdd880; -fx-padding: 15px;");
        layout.setSpacing(10);

        // Scene Setup
        Scene scene = new Scene(layout, 700, 600);
        primaryStage.setTitle("Chatbot");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void handleUserInput(TextArea chatArea, TextField userInput) {
        String prompt = userInput.getText();
        if (!prompt.isEmpty()) {
            chatArea.appendText("You: " + prompt + "\n");
            String response = getChatbotResponse(prompt);
            chatArea.appendText( response + "\n\n");
            chatArea.setScrollTop(Double.MAX_VALUE); // ensure that the scroll bar of the TextArea (chatArea) automatically scrolls to the bottom whenever new text is added.
            userInput.clear();
        }
    }
}
