package route.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import route.entity.*;

import java.util.List;

public interface RouteService {

    Response getRouteByStartAndTerminal(String startId, String terminalId, HttpHeaders headers);

    Response getAllRoutes(HttpHeaders headers);

    Response getRouteById(String routeId, HttpHeaders headers);

    Response deleteRoute(String routeId, HttpHeaders headers);

    Response createAndModify(RouteInfo info, HttpHeaders headers);

}
