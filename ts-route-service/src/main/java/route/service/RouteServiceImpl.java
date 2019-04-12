package route.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import route.entity.*;
import route.repository.RouteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public Route createAndModify(RouteInfo info, HttpHeaders headers) {
        System.out.println("[Route Service] Create And Modify Start:" + info.getStartStation() + " End:" + info.getEndStation());
        Route routeResult = null;
        String[] stations = info.getStationList().split(",");
        String[] distances = info.getDistanceList().split(",");
        List<String> stationList = new ArrayList<>();
        List<Integer> distanceList = new ArrayList<>();
        if (stations.length != distances.length) {
            System.out.println("Station Number Not Equal To Distance Number");
            return routeResult;
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
            routeResult = route;
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
            routeResult = route;
        }
        return routeResult;
    }

    @Override
    public boolean deleteRoute(String routeId, HttpHeaders headers) {
        routeRepository.removeRouteById(routeId);
        Route route = routeRepository.findById(routeId);
        if (route == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Route getRouteById(String routeId, HttpHeaders headers) {
        Route route = routeRepository.findById(routeId);
        return route;
    }

    @Override
    public List<Route> getRouteByStartAndTerminal(String startId, String terminalId, HttpHeaders headers) {
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
        return resultList;
    }

    @Override
    public List<Route> getAllRoutes(HttpHeaders headers) {
        ArrayList<Route> routes = routeRepository.findAll();
        return routes;
    }

}
