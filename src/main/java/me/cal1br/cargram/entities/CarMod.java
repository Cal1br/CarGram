package me.cal1br.cargram.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table
public class CarMod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long modId;
    @ManyToOne
    @JoinColumn(name = "carId",nullable = false)
    private Car car;
    @Column()
    private float price;
    @Column(length = 500)
    private String description;
    //Picture;

}
