package com.example.projectwithgui;
import com.example.projectwithgui.Cart;
public class App {
    private static Cart cart = new Cart(); // Singleton cart instance

    public static Cart getCart() {
        return cart;
    }
}

