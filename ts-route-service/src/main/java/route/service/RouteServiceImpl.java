package route.service;

import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import route.entity.*;
import route.repository.RouteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public Response createAndModify(RouteInfo info, HttpHeaders headers) {
        System.out.println("[Route Service] Create And Modify Start:" + info.getStartStation() + " End:" + info.getEndStation());

        String[] stations = info.getStationList().split(",");
        String[] distances = info.getDistanceList().split(",");
        List<String> stationList = new ArrayList<>();
        List<Integer> distanceList = new ArrayList<>();
        if (stations.length != distances.length) {
            System.out.println("Station Number Not Equal To Distance Number");

            return new Response<>(0, "Station Number Not Equal To Distance Number", null);
        }
        for (int i = 0; i < stations.length; i++) {
            stationList.add(stations[i]);
            distanceList.add(Integer.parseInt(distances[i]));
        }
        if (info.getId() == null || info.getId().length() < 10) {
            Route route = new Route();
            route.setId(UUID.randomUUID().toString());
            route.setStartStationId(info.getStartStation());
            route.setTerminalStationId(info.getEndStation());
            route.setStations(stationList);
            route.setDistances(distanceList);
            routeRepository.save(route);
            System.out.println("Save success");

            return new Response<>(1, "Save Success", route);
        } else {
            Route route = routeRepository.findById(info.getId());
            if (route == null) {
                route = new Route();
                route.setId(info.getId());
            }

            route.setStartStationId(info.getStartStation());
            route.setTerminalStationId(info.getEndStation());
            route.setStations(stationList);
            route.setDistances(distanceList);
            routeRepository.save(route);
            System.out.println("Modify success");
            return new Response<>(1, "Modify success", route);
        }
    }

    @Override
    public Response deleteRoute(String routeId, HttpHeaders headers) {
        routeRepository.removeRouteById(routeId);
        Route route = routeRepository.findById(routeId);
        if (route == null) {
            return new Response<>(1, "Delete Success", routeId);
        } else {
            return new Response<>(0, "Delete failed, Reason unKnown with this routeId", routeId);
        }
    }

    @Override
    public Response getRouteById(String routeId, HttpHeaders headers) {
        Route route = routeRepository.findById(routeId);
        if (route == null) {
            return new Response<>(0, "No content with the routeId", routeId);
        } else {
            return new Response<>(1, "Success", route);
        }

    }

    @Override
    public Response getRouteByStartAndTerminal(String startId, String terminalId, HttpHeaders headers) {
//        ArrayList<Route> routes = routeRepository.findByStartStationIdAndTerminalStationId(info.getStartId(),info.getTerminalId());
        ArrayList<Route> routes = routeRepository.findAll();
        System.out.println("[Route Service] Find All:" + routes.size());
        List<Route> resultList = new ArrayList<>();
        for (Route route : routes) {
            if (route.getStations().contains(startId) &&
                    route.getStations().contains(terminalId) &&
                    route.getStations().indexOf(startId) < route.getStations().indexOf(terminalId)) {
                resultList.add(route);
            }
        }
        if (resultList.size() > 0) {
            return new Response<>(1, "Success", routes);
        } else {
            return new Response<>(0, "No routes with the startId and terminalId", startId + " -- " + terminalId);
        }
    }

    @Override
    public Response getAllRoutes(HttpHeaders headers) {
        ArrayList<Route> routes = routeRepository.findAll();
        if (routes != null && routes.size() > 0) {
            return new Response<>(1, "Success", routes);
        } else {
            return new Response<>(0, "No Content", routes);
        }
    }

}
