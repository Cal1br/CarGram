package me.cal1br.cargram.repositories;

import me.cal1br.cargram.entities.Car;
import me.cal1br.cargram.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    Optional<Car> findByCarId(final long id);

    Optional<List<Car>> findByOwner(final User owner);
}
