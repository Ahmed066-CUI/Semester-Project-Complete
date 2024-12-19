package com.example.projectwithgui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customers {
    private final StringProperty username;
    private final StringProperty password;

    public Customers(String username, String password) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public StringProperty getUsername() {
        return username;
    }

    public StringProperty getPassword() {
        return password;
    }
}
