package com.logistica.applogistic;

import androidx.annotation.NonNull;

public class User {
    private int id;
    private String  description;

    public User(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return description;
    }
}
