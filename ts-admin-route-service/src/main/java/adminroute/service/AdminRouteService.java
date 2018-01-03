package adminroute.service;

import adminroute.domain.request.CreateAndModifyRouteRequest;
import adminroute.domain.request.DeleteRouteRequest;
import adminroute.domain.response.CreateAndModifyRouteResult;
import adminroute.domain.response.DeleteRouteResult;
import adminroute.domain.response.GetRoutesListlResult;

public interface AdminRouteService {
    GetRoutesListlResult getAllRoutes(String id);
    CreateAndModifyRouteResult createAndModifyRoute(CreateAndModifyRouteRequest request);
    DeleteRouteResult deleteRoute(DeleteRouteRequest request);
}
