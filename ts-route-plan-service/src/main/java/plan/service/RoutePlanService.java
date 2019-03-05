package plan.service;

import org.springframework.http.HttpHeaders;
import plan.entity.GetRoutePlanInfo;
import plan.entity.RoutePlanResults;

public interface RoutePlanService {

    RoutePlanResults searchCheapestResult(GetRoutePlanInfo info,HttpHeaders headers);

    RoutePlanResults searchQuickestResult(GetRoutePlanInfo info,HttpHeaders headers);

    RoutePlanResults searchMinStopStations(GetRoutePlanInfo info,HttpHeaders headers);

}
