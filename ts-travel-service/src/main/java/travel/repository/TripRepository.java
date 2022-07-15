package travel.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import travel.entity.Trip;
import travel.entity.TripId;

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