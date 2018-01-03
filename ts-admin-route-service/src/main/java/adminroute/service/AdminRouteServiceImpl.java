package adminroute.service;

import adminroute.domain.bean.CreateAndModifyRouteInfo;
import adminroute.domain.bean.DeleteRouteInfo;
import adminroute.domain.request.CreateAndModifyRouteRequest;
import adminroute.domain.request.DeleteRouteRequest;
import adminroute.domain.response.CreateAndModifyRouteResult;
import adminroute.domain.response.DeleteRouteResult;
import adminroute.domain.response.GetRoutesListlResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdminRouteServiceImpl implements AdminRouteService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GetRoutesListlResult getAllRoutes(String id) {
        if(checkId(id)){
            GetRoutesListlResult result = restTemplate.getForObject(
                    "http://ts-route-service:11178/route/queryAll",
                    GetRoutesListlResult.class);
            return result;
        }else {
            System.out.println("[Admin Route Service][Wrong Admin ID]");
            GetRoutesListlResult result = new GetRoutesListlResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + id);
            result.setRoutes(null);
            return result;
        }
    }

    @Override
    public CreateAndModifyRouteResult createAndModifyRoute(CreateAndModifyRouteRequest request) {
        if(checkId(request.getLoginId())){
            CreateAndModifyRouteInfo createAndModifyRouteInfo = new CreateAndModifyRouteInfo();
            createAndModifyRouteInfo.setId(request.getId());
            createAndModifyRouteInfo.setStationList(request.getStationList());
            createAndModifyRouteInfo.setDistanceList(request.getDistanceList());
            createAndModifyRouteInfo.setStartStation(request.getStartStation());
            createAndModifyRouteInfo.setEndStation(request.getEndStation());
            CreateAndModifyRouteResult result = restTemplate.postForObject(
                    "http://ts-route-service:11178/route/createAndModify", createAndModifyRouteInfo,CreateAndModifyRouteResult.class);
            return result;
        }
        else {
            System.out.println("[Admin Route Service][Wrong Admin ID]");
            CreateAndModifyRouteResult result = new CreateAndModifyRouteResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + request.getLoginId());
            result.setRoute(null);
            return result;
        }
    }

    @Override
    public DeleteRouteResult deleteRoute(DeleteRouteRequest request) {
        if(checkId(request.getLoginId())){
            DeleteRouteInfo deleteRouteInfo = new DeleteRouteInfo();
            deleteRouteInfo.setRouteId(request.getRouteId());
            DeleteRouteResult result = restTemplate.postForObject(
                    "http://ts-route-service:11178/route/delete", deleteRouteInfo,DeleteRouteResult.class);
            return result;
        }
        else {
            System.out.println("[Admin Route Service][Wrong Admin ID]");
            DeleteRouteResult result = new DeleteRouteResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + request.getLoginId());
            return result;
        }
    }

    private boolean checkId(String id){
        if("1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f".equals(id)){
            return true;
        }
        else{
            return false;
        }
    }
}
