package me.cal1br.cargram.repositories;

import me.cal1br.cargram.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByUserId(final long id);

    Optional<User> findByUsername(final String username);
}
