package travel.repository;

import org.springframework.data.repository.CrudRepository;
import travel.entity.Trip;
import travel.entity.TripId;

import java.util.ArrayList;

public interface TripRepository extends CrudRepository<Trip,TripId> {

    Trip findByTripId(TripId tripId);

    void deleteByTripId(TripId tripId);

    ArrayList<Trip> findAll();

    ArrayList<Trip> findByRouteId(String routeId);
}
