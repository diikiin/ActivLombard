package com.example.activgoldlombard;

import java.io.Serializable;

public class User implements Serializable {
    public String username;
    public String email;

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
