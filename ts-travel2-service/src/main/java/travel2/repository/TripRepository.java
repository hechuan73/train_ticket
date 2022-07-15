package travel2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import travel2.entity.Trip;
import travel2.entity.TripId;
import java.util.ArrayList;

/**
 * @author fdse
 */
@Repository
public interface TripRepository extends CrudRepository<Trip,TripId> {

    Trip findByTripId(TripId tripId);

    void deleteByTripId(TripId tripId);

    @Override
    ArrayList<Trip> findAll();

    ArrayList<Trip> findByRouteId(String routeId);
}