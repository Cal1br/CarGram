package me.cal1br.cargram.controllers;

import me.cal1br.cargram.entities.User;
import me.cal1br.cargram.exceptions.AuthException;
import me.cal1br.cargram.models.UserModel;
import me.cal1br.cargram.services.JWTService;
import me.cal1br.cargram.services.UserService;
import me.cal1br.cargram.utils.LoginRequired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JWTService jwtService;
    public UserController(final UserService userService, final JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/login")
    public String[] loginUser(@RequestBody final UserModel model){
        String[] stringarr = new String[2];
        try{
            stringarr[0] = userService.login(model);
        }catch (AuthException authException){
            stringarr[1] = authException.getMessage();
        }
        return stringarr;
    }

    @LoginRequired
    @GetMapping("/currentuser")
    public User getUser(final HttpServletRequest request) {
        final Object user = request.getAttribute("user");
        System.out.println(user);
        return (User) user;
    }

    @PostMapping("/register")
    public String createUser(@RequestBody final UserModel model) {
        if(model.getUsername().length()>=20){
            throw new RuntimeException("Username length should be less than 20 characters");
        }
        if(model.getPassword().length()<=5){
            throw new RuntimeException("Password should be more than 5 characters");
        }
        final User savedUser = userService.register(model);
        return jwtService.sign(savedUser.getUserId(),24);
    }

/*    @GetMapping("/login")
    public */
}
