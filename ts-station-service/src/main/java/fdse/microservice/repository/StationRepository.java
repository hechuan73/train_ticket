package fdse.microservice.repository;

import fdse.microservice.domain.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
public interface StationRepository extends CrudRepository<Station,String> {

    Station findByName(String name);

    Station findById(String id);

    List<Station> findAll();
}
