package me.cal1br.cargram.configuration;

import me.cal1br.cargram.services.JWTService;
import me.cal1br.cargram.services.UserService;
import me.cal1br.cargram.utils.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    private final JWTService jwtService;
    private final UserService userService;

    public AppConfig(final JWTService jwtService, final UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(jwtService,userService));
    }
}
