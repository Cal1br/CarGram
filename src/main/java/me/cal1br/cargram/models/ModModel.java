package me.cal1br.cargram.models;

import java.sql.Date;

public class ModModel {
    private float price;
    private String description;
    private Date date;

    public ModModel() {
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(final float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }
}
