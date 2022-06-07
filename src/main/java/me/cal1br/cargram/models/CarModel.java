package me.cal1br.cargram.models;

import lombok.Data;

import java.sql.Date;

@Data
public class CarModel {
    private String name;
    private String model;
    private String description;
    private short horsepower;
    private float engineDisplacement;
    private Date manufactureDate;
}
