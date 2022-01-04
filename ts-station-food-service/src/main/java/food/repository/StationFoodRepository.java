package food.repository;

import food.entity.StationFoodStore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StationFoodRepository extends MongoRepository<StationFoodStore, String> {

    StationFoodStore findById(UUID id);

    @Query("{ 'stationId' : ?0 }")
    List<StationFoodStore> findByStationId(String stationId);
    List<StationFoodStore> findByStationIdIn(List<String> stationIds);


    @Override
    List<StationFoodStore> findAll();

    void deleteById(UUID id);
}
