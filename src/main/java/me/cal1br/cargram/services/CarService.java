package me.cal1br.cargram.services;

import me.cal1br.cargram.entities.Car;
import me.cal1br.cargram.entities.CarMod;
import me.cal1br.cargram.entities.User;
import me.cal1br.cargram.models.CarModel;
import me.cal1br.cargram.models.ModModel;
import me.cal1br.cargram.repositories.CarModRepository;
import me.cal1br.cargram.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarModRepository carModRepository;
    private final UserService userService;

    public CarService(final CarRepository carRepository, final CarModRepository carModRepository, UserService userService) {
        this.carRepository = carRepository;
        this.carModRepository = carModRepository;
        this.userService = userService;
    }

    public Car getById(final long id) {
        final Optional<Car> optionalCar = carRepository.findByCarId(id);
        return optionalCar.orElse(null);
    }

    public CarMod getModById(final long id) {
        final Optional<CarMod> optionalMod = carModRepository.findByModId(id);
        return optionalMod.orElse(null);
    }

    public void deleteById(final long id) {
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

    public CarRepository getCarRepository() {
        return carRepository;
    }

    public List<Car> getCarsForUser(final User user) {
        Optional<List<Car>> optionalCars = carRepository.findByOwner(user);
        return optionalCars.orElse(null);
    }

    public boolean checkOwnership(final long carId, final User user) {
        final Optional<Car> car = carRepository.findByCarId(carId);
        if (car.isPresent() && car.get().getOwner().equals(user)) {
            return true;
        }
        return false;
    }

    public CarMod addCarMod(final long carId, final ModModel modModel) {
        final CarMod mod = new CarMod(this.getById(carId), modModel.getPrice(), modModel.getDescription(), modModel.getDate());
        return carModRepository.save(mod);
    }

    public void saveModPhoto(final String picLink, final CarMod mod) {
        mod.setModPicture(picLink);
        carModRepository.save(mod);
    }

    public void savePhoto(final String profilePicLink, final Car car) {
        car.setPhoto(profilePicLink);
        carRepository.save(car);
    }

    public List<CarMod> getModsForCarId(final long carId) {
        final Optional<List<CarMod>> carMods = carModRepository.findByCar(this.getById(carId));
        return carMods.orElse(null);
    }
}
