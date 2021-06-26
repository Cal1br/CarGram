package me.cal1br.cargram.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carId; //todo maybe replace with long?
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User owner;
    @Column(nullable = false, length = 15)
    private String name;
    @Column(nullable = false, length = 25)
    private String model;
    @Column(length = 1500)
    private String description;
    @Column(nullable = false)
    private short horsepower;
    @Column(nullable = false)
    private float engineDisplacement;
    @Column(nullable = false, updatable = false)
    private Date manufactureDate;
    @Column
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    public long getCarId() {
        return carId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(final User owner) {
        this.owner = owner;
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
