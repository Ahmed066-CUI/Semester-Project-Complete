package com.example.projectwithgui;

public class Product {
    private String name;
    private double price;
    private int quantity;
    private double discount;
    private String description;
    private String image;
    private String category;

    public Product(String name, double price, int quantity, double discount, String description, String image, String category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.description = description;
        this.image = image;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name; // 'name' is the product name variable in your Product class
    }

}

