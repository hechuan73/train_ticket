package fdse.microservice.repository;

import fdse.microservice.entity.Station;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface StationRepository extends MongoRepository<Station,String> {

    Station findByName(String name);

    Station findById(String id);

    @Override
    List<Station> findAll();
}
