package me.cal1br.cargram.controllers;

import me.cal1br.cargram.entities.Car;
import me.cal1br.cargram.services.CarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class CarsController {

    private CarService carService;

    public CarsController(final CarService carService) {
        this.carService = carService;
    }


    @GetMapping("/all")
    public List<Car> getCars() {
        return carService.getAll();
    }
    @GetMapping("/{id}")
    public Car getCar(@PathVariable UUID id) {
        return carService.getById(id);
    }
}
