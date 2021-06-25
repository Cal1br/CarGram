package me.cal1br.cargram.repositories;

import me.cal1br.cargram.entities.Car;
import me.cal1br.cargram.entities.CarMod;
import me.cal1br.cargram.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarModRepository extends CrudRepository<CarMod, Long> {
    Optional<CarMod> findByModId(final long id);
    Optional<List<CarMod>> findByCar(final Car car);
}