package adminroute.service;

import adminroute.entity.Route;
import adminroute.entity.RouteInfo;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author fdse
 */
@Service
public class AdminRouteServiceImpl implements AdminRouteService {
    @Autowired
    private RestTemplate restTemplate;

    public static final Logger logger = LoggerFactory.getLogger(AdminRouteServiceImpl.class);

    @Value("${route-service.url}")
    String route_service_url;

    @Override
    public Response getAllRoutes(HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response> re = restTemplate.exchange(
                 route_service_url + "/api/v1/routeservice/routes",
                HttpMethod.GET,
                requestEntity,
                Response.class);
        if (re.getStatusCode() != HttpStatus.ACCEPTED) {
            logger.error("[getAllRoutes][receive response][Get routes error][response code: {}]", re.getStatusCodeValue());
        }
        return re.getBody();

    }

    @Override
    public Response createAndModifyRoute(RouteInfo request, HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(request, null);
        ResponseEntity<Response<Route>> re = restTemplate.exchange(
                route_service_url + "/api/v1/routeservice/routes",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<Route>>() {
                });
        if (re.getStatusCode() != HttpStatus.ACCEPTED) {
            logger.error("[createAndModifyRoute][receive response][Get status error][response code: {}]", re.getStatusCodeValue());
        }
        return re.getBody();
    }

    @Override
    public Response deleteRoute(String routeId, HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response> re = restTemplate.exchange(
                route_service_url + "/api/v1/routeservice/routes/" + routeId,
                HttpMethod.DELETE,
                requestEntity,
                Response.class);
        if (re.getStatusCode() != HttpStatus.ACCEPTED) {
            logger.error("[deleteRoute][response response][Delete error][response code: {}]", re.getStatusCodeValue());
        }
        return re.getBody();

    }
}
