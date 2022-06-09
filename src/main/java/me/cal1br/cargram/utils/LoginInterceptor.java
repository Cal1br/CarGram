package me.cal1br.cargram.utils;

import me.cal1br.cargram.exceptions.AuthException;
import me.cal1br.cargram.services.JWTService;
import me.cal1br.cargram.services.UserService;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    private final JWTService jwtService;
    private final UserService userService;

    public LoginInterceptor(final JWTService jwtService, final UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final HandlerMethod handlerMethod;
        try {
            handlerMethod = (HandlerMethod) handler;
        } catch (Exception ex) {
            return false;
        }
        final LoginRequired loginRequired = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
        if (loginRequired == null) {
            return true;
        }
        try {
            final String token = request.getHeader("Authorization");
            final Long idFromToken = jwtService.getIdFromToken(token);
            if (idFromToken == null) {
                throw new AuthException();
            }
            request.setAttribute("user", userService.getById(idFromToken));
        } catch (Exception e) {
            throw new AuthException();
        }

        return true;
    }
}