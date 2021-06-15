package me.cal1br.cargram.controllers;

import me.cal1br.cargram.entities.Car;
import me.cal1br.cargram.services.CarService;
import me.cal1br.cargram.utils.LoginRequired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
    public Car getCar(@PathVariable long id) {
        return carService.getById(id);
    }

    @PostMapping
    @LoginRequired
    public ResponseEntity createCar(@RequestBody final Car car) throws URISyntaxException { //todo add dtos
        Car savedCar = carService.save(car);
        return ResponseEntity.created(new URI("/cars/" + savedCar.getCarId())).body(savedCar);
    }

    /**
     * Update car Horsepower and description
     */
    @PutMapping("/{id}")
    public ResponseEntity updateCar(@PathVariable long id, @RequestBody Car car) {
        Car currentClient = carService.getCarRepository().findByCarId(id).orElseThrow(RuntimeException::new);
        car.setHorsepower(car.getHorsepower());
        car.setDescription(car.getDescription());
        return ResponseEntity.ok(currentClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable long id) {
        carService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
