package me.cal1br.cargram.controllers;

import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import me.cal1br.cargram.entities.Car;
import me.cal1br.cargram.entities.CarMod;
import me.cal1br.cargram.entities.User;
import me.cal1br.cargram.models.CarModel;
import me.cal1br.cargram.models.ModModel;
import me.cal1br.cargram.models.PhotoUpload;
import me.cal1br.cargram.services.CarService;
import me.cal1br.cargram.services.UserService;
import me.cal1br.cargram.utils.LoginRequired;
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
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/cars")
public class CarsController {
    private final String CLOUD_REPOSITORY = "https://res.cloudinary.com/calibri-me/image/upload/h_600,q_auto/";
    private final CarService carService;
    private final UserService userService;

    public CarsController(final CarService carService, final UserService userService) {
        this.carService = carService;
        this.userService = userService;
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
        if(carService.checkOwnership(carId,user)){
            return carService.addCarMod(carId, modModel);
        }
        else {
            throw new RuntimeException("You do not own the car!");
        }
    }

    @LoginRequired
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable long id,final HttpServletRequest request) {
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;
        if(carService.checkOwnership(id,user));
        carService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @LoginRequired
    @SuppressWarnings("rawtypes")
    @PostMapping("/uploadphoto/{carId}")
    public void uploadPhoto(@ModelAttribute final PhotoUpload photoUpload, @PathVariable long carId) throws IOException {

        if (photoUpload.getFile() != null && !photoUpload.getFile().isEmpty()) {
            final Map uploadResult;
            uploadResult = Singleton.getCloudinary().uploader().upload(photoUpload.getFile().getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            photoUpload.setPublicId((String) uploadResult.get("public_id"));
            final Object version = uploadResult.get("version");
            if (version instanceof Integer) {
                photoUpload.setVersion(Long.valueOf((Integer) version));
            } else {
                photoUpload.setVersion((Long) version);
            }

            photoUpload.setSignature((String) uploadResult.get("signature"));
            photoUpload.setFormat((String) uploadResult.get("format"));
            photoUpload.setResourceType((String) uploadResult.get("resource_type"));

            StringBuilder sb = new StringBuilder(CLOUD_REPOSITORY);
            sb.append(photoUpload.getPublicId()).append('.').append(photoUpload.getFormat());
            carService.savePhoto(sb.toString(), carService.getById(carId));
        }
    }
    @LoginRequired
    @SuppressWarnings("rawtypes")
    @PostMapping("/uploadmodphoto/{modId}")
    public void uploadModPhoto(@ModelAttribute final PhotoUpload photoUpload, @PathVariable long modId) throws IOException {
        if (photoUpload.getFile() != null && !photoUpload.getFile().isEmpty()) {
            final Map uploadResult;
            uploadResult = Singleton.getCloudinary().uploader().upload(photoUpload.getFile().getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            photoUpload.setPublicId((String) uploadResult.get("public_id"));
            final Object version = uploadResult.get("version");
            if (version instanceof Integer) {
                photoUpload.setVersion(Long.valueOf((Integer) version));
            } else {
                photoUpload.setVersion((Long) version);
            }

            photoUpload.setSignature((String) uploadResult.get("signature"));
            photoUpload.setFormat((String) uploadResult.get("format"));
            photoUpload.setResourceType((String) uploadResult.get("resource_type"));

            StringBuilder sb = new StringBuilder(CLOUD_REPOSITORY);
            sb.append(photoUpload.getPublicId()).append('.').append(photoUpload.getFormat());
            carService.saveModPhoto(sb.toString(), carService.getModById(modId));
        }
    }
}

