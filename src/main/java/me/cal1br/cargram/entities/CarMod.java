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
public class CarMod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long modId;
    @ManyToOne
    @JoinColumn(name = "carId", nullable = false)
    private Car car;
    @Column()
    private float price;
    @Column(length = 1500, nullable = false)
    private String description;
    @Column()
    private String modPicture;
    @Column()
    private Date date;

    public CarMod(final Car car, final float price, final String description, final Date date) {
        this.car = car;
        this.price = price;
        this.description = description;
        this.date = date;
    }

    public CarMod() {

    }

    public long getModId() {
        return modId;
    }

    public void setModId(final long modId) {
        this.modId = modId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(final Car car) {
        this.car = car;
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

    public String getModPicture() {
        return modPicture;
    }

    public void setModPicture(final String modPicture) {
        this.modPicture = modPicture;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }
}
