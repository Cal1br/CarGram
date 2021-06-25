package me.cal1br.cargram.services;

import me.cal1br.cargram.entities.Car;
import me.cal1br.cargram.entities.User;
import me.cal1br.cargram.models.CarModel;
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

    public Car getById(final long id) {
        final Optional<Car> optionalCar = carRepository.findByCarId(id);
        return optionalCar.orElse(null);
    }
    public void deleteById(final long id){
        carRepository.deleteById(id);
    }

    public List<Car> getAll() {
        return (List<Car>) carRepository.findAll();
    }

    public Car addCar(final CarModel carmodel, User user) {
        final Car car = new Car();
        car.setOwner(user);
        car.setName(carmodel.getName());
        car.setModel(carmodel.getModel());
        car.setDescription(carmodel.getDescription());
        car.setHorsepower(carmodel.getHorsepower());
        car.setEngineDisplacement(carmodel.getEngineDisplacement());
        car.setManufactureDate(carmodel.getManufactureDate());
        return carRepository.save(car);
    }
    public void savePhoto(final String profilePicLink, final Car car) {
        car.setPhoto(profilePicLink);
        carRepository.save(car);
    }
    public CarRepository getCarRepository() {
        return carRepository;
    }

    public List<Car> getCarsForUser(final User user) {
        Optional<List<Car>> optionalCars = carRepository.findByOwner(user);
        return optionalCars.orElse(null);
    }
}
