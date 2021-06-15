package me.cal1br.cargram.services;

import me.cal1br.cargram.entities.User;
import me.cal1br.cargram.models.UserModel;
import me.cal1br.cargram.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final UserRepository userRepository;
    final JWTService jwtService;
    public UserService(final UserRepository userRepository, final JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public User getById(final long id) {
        return userRepository.findByUserId(id).orElse(null);
    }

    public User register(UserModel model) {
        Optional<User> optionalUser = userRepository.findByUsername(model.getUsername());
        if(optionalUser.isPresent()){
            throw new RuntimeException("User already exists");
        }
        String hashedPassword = BCrypt.hashpw(model.getPassword(),BCrypt.gensalt(10));
        //User Creation
        User user = new User();
        user.setUsername(model.getUsername());
        user.setPassword(hashedPassword);
        user.setBiography("");
        user.setLastOnline(Timestamp.from(Instant.now()));
        user.setOnline(true);
        user.setAccountCreation(Date.valueOf(LocalDate.now()));
        //
        return userRepository.save(user);
    }

    public String login(final UserModel model) {
        final Optional<User> optionalUser = userRepository.findByUsername(model.getUsername());
        if(optionalUser.isEmpty()){
            throw new RuntimeException("No such user");
        }
        if(BCrypt.checkpw(model.getPassword(),optionalUser.get().getPassword())){
            return jwtService.sign(optionalUser.get().getUserId(),24);
        }
        else {
            throw new RuntimeException("Incorrect password");
        }
    }
}
