package me.cal1br.cargram.controllers;

import me.cal1br.cargram.entities.Car;
import me.cal1br.cargram.entities.CarMod;
import me.cal1br.cargram.entities.User;
import me.cal1br.cargram.exceptions.InvalidModelException;
import me.cal1br.cargram.models.CarModel;
import me.cal1br.cargram.models.ModModel;
import me.cal1br.cargram.models.PhotoUpload;
import me.cal1br.cargram.services.CarService;
import me.cal1br.cargram.services.ImageService;
import me.cal1br.cargram.services.UserService;
import me.cal1br.cargram.utils.ImageType;
import me.cal1br.cargram.utils.LoginRequired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/cars")
public class CarsController {

    //todo configure logback.xml
    private static final Logger LOGGER = LoggerFactory.getLogger(CarsController.class);

    private final CarService carService;
    private final UserService userService;
    //todo move dependencies to relative controllers and services
    private final ImageService imageService;

    public CarsController(final CarService carService, final UserService userService, final ImageService imageService) {
        this.carService = carService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @LoginRequired
    @GetMapping("/all")
    public List<Car> getCars() {
        return carService.getAll();
    }

    @LoginRequired
    @GetMapping("/feed")
    public List<Car> getRandomCars() {
        final List<Car> all = carService.getAll();
        Collections.shuffle(all);
        return all;
    }

    @LoginRequired
    @GetMapping("/getforuser/{username}")
    public List<Car> getForUser(@PathVariable String username) {
        return carService.getCarsForUser(userService.getByName(username));
    }

    @LoginRequired
    @GetMapping("/{id}")
    public Car getCar(@PathVariable long id) {
        return carService.getById(id);
    }

    @LoginRequired
    @GetMapping("/getmycars")
    public List<Car> getUserCars(final HttpServletRequest request) {
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;
        return carService.getCarsForUser(user);
    }

    @LoginRequired
    @GetMapping("/getmods/{carId}")
    public List<CarMod> getModsForCar(@PathVariable final long carId) {
        return carService.getModsForCarId(carId);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable long id, @RequestBody Car car) {
        final Car currentCar = carService.getCarRepository().findByCarId(id).orElseThrow(RuntimeException::new);
        currentCar.setName(car.getName());
        currentCar.setModel(car.getModel());
        currentCar.setDescription(car.getDescription());
        currentCar.setHorsepower(car.getHorsepower());
        currentCar.setEngineDisplacement(car.getEngineDisplacement());
        carService.getCarRepository().save(currentCar);
        return ResponseEntity.ok(currentCar);
    }

    @LoginRequired
    @PostMapping("/add")
    public Car createCar(@RequestBody final CarModel car, final HttpServletRequest request) throws URISyntaxException { //todo add dtos
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;
        return carService.addCar(car, user);
    }

    @LoginRequired
    @PostMapping("/addmod/{carId}")
    public CarMod createCarMod(@RequestBody final ModModel modModel, @PathVariable final long carId, final HttpServletRequest request) throws URISyntaxException { //todo add dtos
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;
        if (carService.checkOwnership(carId, user)) {
            return carService.addCarMod(carId, modModel);
        } else {
            throw new RuntimeException("You do not own the car!");
        }
    }

    @LoginRequired
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable long id, final HttpServletRequest request) {
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;
        if (carService.checkOwnership(id, user)) ;
        carService.deleteById(id);
        return ResponseEntity.ok().body("Success");
    }

    @LoginRequired
    @SuppressWarnings("rawtypes")
    @PostMapping("/uploadphoto/{carId}")
    public void uploadPhoto(@ModelAttribute final PhotoUpload photoUpload, @PathVariable long carId) throws IOException {
        //TODO ADD DOWNLOAD PICTURE BUTTON!
        //todo FIX db
        carService.savePhoto(imageService.saveImage(photoUpload, ImageType.CAR_IMAGE), carService.getById(carId));

    }

    @LoginRequired
    @SuppressWarnings("rawtypes")
    @PostMapping("/uploadmodphoto/{modId}")
    public void uploadModPhoto(@ModelAttribute final PhotoUpload photoUpload, @PathVariable long modId) throws IOException {
        if (photoUpload.getFile() == null || photoUpload.getFile().isEmpty()) {
            throw new InvalidModelException("Photo can't be empty!");
        }
        final URI fileLocation = URI.create("storage/cars/" + UUID.randomUUID() + ".img");
        final File file = new File(fileLocation); //todo get format?

        try (final FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(photoUpload.getFile().getBytes());
        } catch (IOException ioException) {
            LOGGER.error("An exception has occurred while processing: " + fileLocation + " from user: ");//todo
        }
        //todo save in db
        //todo see what this does
        carService.saveModPhoto(fileLocation.toString(), carService.getModById(modId));

    }
}

