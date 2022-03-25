package trainFood.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import trainFood.entity.TrainFood;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainFoodRepository extends MongoRepository<TrainFood, String> {

    TrainFood findById(UUID id);

    @Override
    List<TrainFood> findAll();

    @Query("{ 'tripId' : ?0 }")
    List<TrainFood> findByTripId(String tripId);

    void deleteById(UUID id);
}
