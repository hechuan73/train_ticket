package travel.service;

import travel.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
public interface TravelService {

    String create(Information info);

    Trip retrieve(Information2 info);

    String update(Information info);

    String delete(Information2 info);

    ArrayList<TripResponse> query(QueryInfo info);

    GetTripAllDetailResult getTripAllDetailInfo(GetTripAllDetailInfo gtdi);

    GetRouteResult getRouteByTripId(String tripId);

    GetTrainTypeResult getTrainTypeByTripId(String tripId);

    List<Trip> queryAll();

    GetTripsByRouteIdResult getTripByRoute(GetTripsByRouteIdInfo info);

    AdminFindAllResult adminQueryAll();
}
