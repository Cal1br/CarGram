package me.cal1br.cargram.controllers;

import me.cal1br.cargram.entities.User;
import me.cal1br.cargram.exceptions.AuthException;
import me.cal1br.cargram.models.PhotoUpload;
import me.cal1br.cargram.models.UserDto;
import me.cal1br.cargram.models.UserModel;
import me.cal1br.cargram.services.ImageService;
import me.cal1br.cargram.services.JWTService;
import me.cal1br.cargram.services.UserService;
import me.cal1br.cargram.utils.ImageType;
import me.cal1br.cargram.utils.LoginRequired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private final String CLOUD_REPOSITORY = "https://res.cloudinary.com/calibri-me/image/upload/w_600,q_auto/";
    private final UserService userService;
    private final JWTService jwtService;
    //todo move dependencies to relative controllers and services
    private final ImageService imageService;

    public UserController(final UserService userService, final JWTService jwtService, final ImageService imageService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.imageService = imageService;
    }

    @LoginRequired
    @GetMapping("/{name}")
    public User getUserByName(@PathVariable final String name) {
        return userService.getByName(name);
    }

    @LoginRequired
    @GetMapping("/exists/{name}")
    public boolean checkUserExists(@PathVariable final String name) {
        return userService.userExists(name);
    }

    @LoginRequired
    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getAll();
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody final UserModel model) {
        try {
            return userService.login(model);
        } catch (AuthException authException) {
            return authException.getMessage();
        }
    }

    @LoginRequired
    @GetMapping("/currentuser")
    public UserDto getUser(final HttpServletRequest request) {
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;
        return user.toDto();
    }

    @PostMapping("/register")
    public String createUser(@RequestBody final UserModel model) {
        if (model.getUsername().length() >= 20) {
            throw new RuntimeException("Username length should be less than 20 characters");
        }
        if (model.getPassword().length() <= 5) {
            throw new RuntimeException("Password should be more than 5 characters");
        }
        final User savedUser = userService.register(model);
        return jwtService.sign(savedUser.getUserId(), 24);
    }

    @LoginRequired
    @SuppressWarnings("rawtypes")
    @PostMapping("/uploadPfp")
    public void uploadPhoto(@ModelAttribute final PhotoUpload photoUpload, final HttpServletRequest request) throws IOException {
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;
        userService.savePhoto(imageService.saveImage(photoUpload, ImageType.USER_IMAGE), user);

    }

    @LoginRequired
    @PostMapping("/uploadbiography")
    public void uploadBiography(@RequestBody final String biography, final HttpServletRequest request) {
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;
        if (biography.length() <= 2000) {
            userService.saveBiography(biography, user);
        } else {
            throw new RuntimeException("Biography too large, max is 2000 characters");
        }
    }

    @LoginRequired
    @GetMapping("/logout")
    public void logoutUser(final HttpServletRequest request) {
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;
        userService.logout(user);
    }
}
