package com.logistica.applogistic;

public class cSpinnerItem {
    private int id;
    private String  description;
    private String field;

    public cSpinnerItem(int id, String description) {
        this.id = id;
        this.description = description;
        this.field = field;
    }

    public cSpinnerItem(int id, String description, String  field) {
        this.id = id;
        this.description = description;
        this.field = field;
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

    public String getField() {
        return field;
    }

    @Override
    public String toString() {
        return description;
    }


}
