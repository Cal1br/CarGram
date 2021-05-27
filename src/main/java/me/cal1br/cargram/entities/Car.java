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
import java.util.UUID;

@Entity
@Table
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID carId; //todo maybe replace with long?
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User owner;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String model;
    @Column
    private String description;
    @Column(nullable = false)
    private short horsepower;
    @Column(nullable = false)
    private float engineDisplacement;
    @Column(nullable = false, updatable = false)
    private Date manufactureDate;

    public UUID getCarId() {
        return carId;
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getDescription() {
        return description;
    }

    public short getHorsepower() {
        return horsepower;
    }

    public float getEngineDisplacement() {
        return engineDisplacement;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }
}
