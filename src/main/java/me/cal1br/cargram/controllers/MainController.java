package me.cal1br.cargram.controllers;

import me.cal1br.cargram.services.CarService;
import me.cal1br.cargram.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {
    private final String HOME_PAGE = "redirect:/";
    private final CarService carService;
    private final UserService userService;

    public MainController(final CarService carService, final UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }
}
