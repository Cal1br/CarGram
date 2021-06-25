package me.cal1br.cargram.controllers;

import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import me.cal1br.cargram.entities.User;
import me.cal1br.cargram.exceptions.AuthException;
import me.cal1br.cargram.models.PhotoUpload;
import me.cal1br.cargram.models.UserDto;
import me.cal1br.cargram.models.UserModel;
import me.cal1br.cargram.services.JWTService;
import me.cal1br.cargram.services.UserService;
import me.cal1br.cargram.utils.LoginRequired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private final String CLOUD_REPOSITORY = "https://res.cloudinary.com/calibri-me/image/upload/h_50,q_auto/";
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
    public void uploadPhoto(@ModelAttribute final PhotoUpload photoUpload,final HttpServletRequest request) throws IOException {
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;

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
           userService.savePhoto(sb.toString(),user);
        }
    }
    @LoginRequired
    @PostMapping("/uploadbiography")
    public void uploadBiography(@RequestBody final String biography, final HttpServletRequest request) {
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;
        if (biography.length() <= 2000) {
            userService.saveBiography(biography,user);
        }
        else {
            throw new RuntimeException("Biography too large, max is 2000 characters");
        }
    }
    @LoginRequired
    @GetMapping("/logout")
    public void logoutUser(final HttpServletRequest request){
        final Object userObj = request.getAttribute("user");
        final User user = (User) userObj;
        userService.logout(user);
    }
}
