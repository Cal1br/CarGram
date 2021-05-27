package me.cal1br.cargram.repositories;

import me.cal1br.cargram.entities.Car;
import me.cal1br.cargram.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends CrudRepository<Car, UUID> {
    Optional<Car> findByCarId(final UUID id);
    Optional<List<Car>> findByOwner(final User owner);
}
