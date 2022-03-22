package train.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import train.entity.TrainType;

import java.util.List;


public interface TrainTypeRepository extends MongoRepository<TrainType,String> {

    TrainType findById(String id);
    @Override
    List<TrainType> findAll();
    void deleteById(String id);
}
