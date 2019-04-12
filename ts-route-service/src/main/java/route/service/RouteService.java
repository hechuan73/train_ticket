package route.service;

import org.springframework.http.HttpHeaders;
import route.entity.*;

import java.util.List;

public interface RouteService {

    List<Route> getRouteByStartAndTerminal(String startId, String terminalId, HttpHeaders headers);

    List<Route> getAllRoutes(HttpHeaders headers);

    Route getRouteById(String routeId, HttpHeaders headers);

    boolean deleteRoute(String routeId, HttpHeaders headers);

    Route createAndModify(RouteInfo info, HttpHeaders headers);

}
