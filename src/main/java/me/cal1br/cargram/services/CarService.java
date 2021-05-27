package me.cal1br.cargram.services;

import me.cal1br.cargram.entities.Car;
import me.cal1br.cargram.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final UserService userService;
    public CarService(final CarRepository carRepository,UserService userService) {
        this.carRepository = carRepository;
        this.userService = userService;
    }

    public Car getById(final UUID id) {
        final Optional<Car> optionalCar = carRepository.findByCarId(id);
        return optionalCar.orElse(null);
    }

    public List<Car> getAll() {
        return (List<Car>) carRepository.findAll();
    }
}
