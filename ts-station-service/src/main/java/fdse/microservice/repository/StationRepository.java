package fdse.microservice.repository;

import fdse.microservice.entity.Station;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface StationRepository extends MongoRepository<Station,String> {

    Station findByName(String name);

    Optional<Station> findById(String id);

    @Override
    List<Station> findAll();
}
