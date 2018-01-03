package travel2.service;

import travel2.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenjie Xu on 2017/6/7.
 */
public interface Travel2Service {
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
