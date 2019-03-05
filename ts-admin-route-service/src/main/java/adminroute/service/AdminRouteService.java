package adminroute.service;

import adminroute.entity.CreateAndModifyRouteRequest;
import adminroute.entity.DeleteRouteRequest;
import adminroute.entity.CreateAndModifyRouteResult;
import adminroute.entity.DeleteRouteResult;
import adminroute.entity.GetRoutesListlResult;
import org.springframework.http.HttpHeaders;

public interface AdminRouteService {
    GetRoutesListlResult getAllRoutes(String id, HttpHeaders headers);
    CreateAndModifyRouteResult createAndModifyRoute(CreateAndModifyRouteRequest request, HttpHeaders headers);
    DeleteRouteResult deleteRoute(DeleteRouteRequest request, HttpHeaders headers);
}
