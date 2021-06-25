package me.cal1br.cargram.models;

import javax.persistence.Column;
import java.sql.Date;

public class CarModel {
    private String name;
    private String model;
    private String description;
    private short horsepower;
    private float engineDisplacement;
    private Date manufactureDate;

    public CarModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public short getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(final short horsepower) {
        this.horsepower = horsepower;
    }

    public float getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(final float engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(final Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }
}
