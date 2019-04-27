package adminroute.service;

import adminroute.entity.Route;
import adminroute.entity.RouteInfo;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
    public Response getAllRoutes(HttpHeaders headers) {

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

    }

    @Override
    public Response createAndModifyRoute(RouteInfo request, HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(request, headers);
        ResponseEntity<Response<Route>> re = restTemplate.exchange(
                "http://ts-route-service:11178/api/v1/routeservice/routes",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<Route>>() {
                });
//            CreateAndModifyRouteResult result = restTemplate.postForObject(
//                    "http://ts-route-service:11178/route/createAndModify", createAndModifyRouteInfo,CreateAndModifyRouteResult.class);
        return re.getBody();
    }

    @Override
    public Response deleteRoute(String routeId, HttpHeaders headers) {

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
}
