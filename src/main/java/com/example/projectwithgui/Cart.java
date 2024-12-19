package com.example.projectwithgui;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }
    public List<Product> getProducts() {
        return cartItems;
    }
    public void addProduct(Product product) {
        cartItems.add(product);
    }

    public void removeProduct(Product product) {
        cartItems.remove(product);
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public double calculateTotal() {
        return cartItems.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clearCart() {
        cartItems.clear();
    }
}
