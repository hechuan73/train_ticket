package travel.service;

import org.springframework.http.HttpHeaders;
import travel.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
public interface TravelService {

    String create(TravelInfo info, HttpHeaders headers);

    Trip retrieve(String tripId, HttpHeaders headers);

    String update(TravelInfo info, HttpHeaders headers);

    String delete(String tripId, HttpHeaders headers);

    ArrayList<TripResponse> query(Info info, HttpHeaders headers);

    TripAllDetail getTripAllDetailInfo(TripAllDetailInfo gtdi, HttpHeaders headers);

    Route getRouteByTripId(String tripId, HttpHeaders headers);

    TrainType getTrainTypeByTripId(String tripId, HttpHeaders headers);

    List<Trip> queryAll(HttpHeaders headers);

    ArrayList<ArrayList<Trip>>  getTripByRoute(ArrayList<String> routeIds, HttpHeaders headers);

    ArrayList<AdminTrip> adminQueryAll(HttpHeaders headers);
}
