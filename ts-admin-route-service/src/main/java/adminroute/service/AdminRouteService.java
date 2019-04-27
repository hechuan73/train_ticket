package adminroute.service;

import adminroute.entity.RouteInfo;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

public interface AdminRouteService {
    Response getAllRoutes(HttpHeaders headers);

    Response createAndModifyRoute(RouteInfo request, HttpHeaders headers);

    Response deleteRoute(String routeId, HttpHeaders headers);
}
