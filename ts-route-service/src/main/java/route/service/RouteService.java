package route.service;

import route.domain.*;

public interface RouteService {

    GetRoutesListlResult getRouteByStartAndTerminal(GetRouteByStartAndTerminalInfo info);

    GetRoutesListlResult getAllRoutes();

    GetRouteByIdResult getRouteById(String routeId);

    DeleteRouteResult deleteRoute(DeleteRouteInfo info);

    CreateAndModifyRouteResult createAndModify(CreateAndModifyRouteInfo info);

}
