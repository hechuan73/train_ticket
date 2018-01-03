package plan.service;

import plan.domain.GetRoutePlanInfo;
import plan.domain.RoutePlanResults;

public interface RoutePlanService {

    RoutePlanResults searchCheapestResult(GetRoutePlanInfo info);

    RoutePlanResults searchQuickestResult(GetRoutePlanInfo info);

    RoutePlanResults searchMinStopStations(GetRoutePlanInfo info);

}
