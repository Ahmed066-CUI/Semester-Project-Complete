
package com.example.projectwithgui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProductsManagement {
    public void show(Stage stage) {

        VBox vBox = new VBox(30);  //to add spacing between the button
        vBox.setAlignment(Pos.CENTER);

        Button addProductButton = new Button("Add Product");
        Button removeProductButton = new Button("Remove Product");
        Button modifyProductButton = new Button("Modify Product");
        Button goback = new Button("Go Back");
        styleButton(addProductButton);
        styleButton(removeProductButton);
        styleButton(modifyProductButton);
        styleButton(goback);
        //functionalitites of buttons
        addProductButton.setOnAction(e -> {
           ProductsManagementFunctions addProduct = new ProductsManagementFunctions();
            addProduct.show(stage);
            });

        goback.setOnAction(e -> {
            ShowAdminMenu showAdminMenu = new ShowAdminMenu();
            showAdminMenu.show(stage);
        });

        removeProductButton.setOnAction(e->{
            RemoveProduct rp =  new RemoveProduct();
            rp.show(stage);
        });
        modifyProductButton.setOnAction(e -> {
            ModifyProduct modifyProduct = new ModifyProduct();
            modifyProduct.show(stage);
        });

        vBox.getChildren().addAll(addProductButton, removeProductButton, modifyProductButton, goback);
        Scene scene = new Scene(vBox, 1550, 670);
        stage.setScene(scene);
        vBox.setStyle("-fx-background-color: #fdd880");
        stage.setTitle("Products Management");
        stage.setFullScreen(true);
        stage.show();
    }
//    private void styleButton(Button button) {
//        button.setFont(new Font("Didot", 35));  // Set font size
//        button.setStyle("-fx-background-color: #263988; -fx-text-fill: white; -fx-padding: 10 20; "
//                + "-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #031b73; -fx-border-width: 2;");
//        button.setEffect(new DropShadow());
//
//        button.setMinHeight(100);
//        button.setMaxHeight(100);
//
//        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #031b73; -fx-text-fill: white; "
//                + "-fx-padding: 10 20; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #263988; -fx-border-width: 2;"));
//        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #263988; -fx-text-fill: white; "
//                + "-fx-padding: 10 20; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #031b73; -fx-border-width: 2;"));
//    }

    private void styleButton(Button button) {
        button.setFont(new javafx.scene.text.Font("Didot", 35));
        button.setStyle("-fx-background-color: #d88f1e; -fx-text-fill: white; -fx-padding: 10 20; "
                + "-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #8c5d15; -fx-border-width: 2;");

        button.setMinHeight(100);
        button.setMaxHeight(100);

        button.setEffect(new DropShadow());
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #8c5d15; -fx-text-fill: white; "
                + "-fx-padding: 10 20; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #d88f1e; -fx-border-width: 2;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #d88f1e; -fx-text-fill: white; "
                + "-fx-padding: 10 20; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #8c5d15; -fx-border-width: 2;"));
    }

}
