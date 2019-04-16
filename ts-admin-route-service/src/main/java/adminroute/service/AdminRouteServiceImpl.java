package adminroute.service;

import adminroute.entity.RouteInfo;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdminRouteServiceImpl implements AdminRouteService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response getAllRoutes(String id, HttpHeaders headers) {
        if (checkId(id)) {
            HttpEntity requestEntity = new HttpEntity(headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-route-service:11178/api/v1/routeservice/routes",
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            Response result = re.getBody();
//            GetRoutesListlResult result = restTemplate.getForObject(
//                    "http://ts-route-service:11178/route/queryAll",
//                    GetRoutesListlResult.class);
            return result;
        } else {
            System.out.println("[Admin Route Service][Wrong Admin ID]");
            return new Response<>(0, "The loginId is Wrong: \" + id", null);
        }
    }

    @Override
    public Response createAndModifyRoute(RouteInfo request, HttpHeaders headers) {
        if (checkId(request.getLoginId())) {
            HttpEntity requestEntity = new HttpEntity(request, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-route-service:11178/api/v1/routeservice/routes",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
            Response result = re.getBody();
//            CreateAndModifyRouteResult result = restTemplate.postForObject(
//                    "http://ts-route-service:11178/route/createAndModify", createAndModifyRouteInfo,CreateAndModifyRouteResult.class);
            return result;
        } else {
            System.out.println("[Admin Route Service][Wrong Admin ID]");
            return new Response<>(0, "The loginId is Wrong: " + request.getLoginId(), null);
        }
    }

    @Override
    public Response deleteRoute(String loginId, String routeId, HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-route-service:11178/api/v1/routeservice/routes/" + routeId,
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response result = re.getBody();
//            DeleteRouteResult result = restTemplate.postForObject(
//                    "http://ts-route-service:11178/route/delete", deleteRouteInfo,DeleteRouteResult.class);
        return result;

    }

    private boolean checkId(String id) {
        if ("1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f".equals(id)) {
            return true;
        } else {
            return false;
        }
    }
}
