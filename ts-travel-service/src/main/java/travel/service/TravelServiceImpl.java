package travel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travel.domain.*;
import travel.repository.TripRepository;
import java.util.*;

@Service
public class TravelServiceImpl implements TravelService{

    @Autowired
    private TripRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GetRouteResult getRouteByTripId(String tripId){
        GetRouteResult result = new GetRouteResult();

        if(null != tripId && tripId.length() >= 2){
            TripId tripId1 = new TripId(tripId);
            Trip trip = repository.findByTripId(tripId1);
            if(trip == null){
                result.setStatus(false);
                result.setMessage("Trip Not Found");
                System.out.println("[Get Route By Trip ID] Trip Not Found:" + tripId);
                result.setRoute(null);
            }else{
                Route route = getRouteByRouteId(trip.getRouteId());
                if(route == null){
                    result.setStatus(false);
                    result.setMessage("Route Not Found");
                    System.out.println("[Get Route By Trip ID] Route Not Found:" + trip.getRouteId());
                    result.setRoute(null);
                }else{
                    result.setStatus(true);
                    result.setMessage("Success");
                    System.out.println("[Get Route By Trip ID] Success");
                    result.setRoute(route);
                }
            }
        } else {
            result.setStatus(false);
            System.out.println("[Get Route By Trip ID] TripId is invaild");
            result.setMessage("TripId is invaild");
            result.setRoute(null);
        }

        return result;
    }

    @Override
    public GetTrainTypeResult getTrainTypeByTripId(String tripId){
        TripId tripId1 = new TripId(tripId);
        GetTrainTypeResult result = new GetTrainTypeResult();
        Trip trip = repository.findByTripId(tripId1);
        if(trip == null){
            result.setStatus(false);
            result.setMessage("Trip Not Found");
            result.setTrainType(null);
        }else{
            TrainType train = getTrainType(trip.getTrainTypeId());
            if(train == null){
                result.setStatus(false);
                result.setMessage("Route Not Found");
                result.setTrainType(null);
            }else{
                result.setStatus(true);
                result.setMessage("Success");
                result.setTrainType(train);
            }
        }
        return result;
    }

    @Override
    public GetTripsByRouteIdResult getTripByRoute(GetTripsByRouteIdInfo info) {
        ArrayList<String> routeIds = info.getRouteIds();
        ArrayList<ArrayList<Trip>> tripList = new ArrayList<>();
        for(String routeId : routeIds){
            ArrayList<Trip> tempTripList = repository.findByRouteId(routeId);
            if(tempTripList == null){
                tempTripList = new ArrayList<>();
            }
            tripList.add(tempTripList);
        }
        GetTripsByRouteIdResult result = new GetTripsByRouteIdResult();
        result.setMessage("Success.");
        result.setTripsSet(tripList);
        return result;
    }

    @Override
    public String create(Information info){
        TripId ti = new TripId(info.getTripId());
        if(repository.findByTripId(ti) == null){
            Trip trip = new Trip(ti,info.getTrainTypeId(),info.getStartingStationId(),
                    info.getStationsId(),info.getTerminalStationId(),info.getStartingTime(),info.getEndTime());
            trip.setRouteId(info.getRouteId());
            repository.save(trip);
            return "Create trip:" + ti.toString() + ".";
        }else{
            return "Trip "+ info.getTripId().toString() +" already exists";
        }
    }

    @Override
    public Trip retrieve(Information2 info){
        TripId ti = new TripId(info.getTripId());
        if(repository.findByTripId(ti) != null){
            return repository.findByTripId(ti);
        }else{
            return null;
        }
    }

    @Override
    public String update(Information info){
        TripId ti = new TripId(info.getTripId());
        if(repository.findByTripId(ti) != null){
            Trip trip = new Trip(ti,info.getTrainTypeId(),info.getStartingStationId(),
                    info.getStationsId(),info.getTerminalStationId(),info.getStartingTime(),info.getEndTime());
            trip.setRouteId(info.getRouteId());
            repository.save(trip);
            return "Update trip:" + ti.toString();
        }else{
            return "Trip "+ info.getTripId().toString() +" doesn't exists";
        }
    }

    @Override
    public String delete(Information2 info){
        TripId ti = new TripId(info.getTripId());
        if(repository.findByTripId(ti) != null){
            repository.deleteByTripId(ti);
            return "Delete trip:" +info.getTripId().toString()+ ".";
        }else{
            return "Trip "+info.getTripId().toString()+" doesn't exist.";
        }
    }

    @Override
    public ArrayList<TripResponse> query(QueryInfo info){

        String startingPlaceName = info.getStartingPlace();
        String endPlaceName = info.getEndPlace();
        String startingPlaceId = queryForStationId(startingPlaceName);
        String endPlaceId = queryForStationId(endPlaceName);


        ArrayList<TripResponse> list = new ArrayList<>();

        ArrayList<Trip> allTripList = repository.findAll();
        for(Trip tempTrip : allTripList){
            Route tempRoute = getRouteByRouteId(tempTrip.getRouteId());

            if(tempRoute.getStations().contains(startingPlaceId) &&
                    tempRoute.getStations().contains(endPlaceId) &&
                    tempRoute.getStations().indexOf(startingPlaceId) < tempRoute.getStations().indexOf(endPlaceId)){
                TripResponse response = getTickets(tempTrip,tempRoute,startingPlaceId,endPlaceId,startingPlaceName,endPlaceName,info.getDepartureTime());
                list.add(response);
            }
        }
        return list;
    }

    @Override
    public GetTripAllDetailResult getTripAllDetailInfo(GetTripAllDetailInfo gtdi){
        GetTripAllDetailResult gtdr = new GetTripAllDetailResult();
        System.out.println("[TravelService] [GetTripAllDetailInfo] TripId:" + gtdi.getTripId());
        Trip trip = repository.findByTripId(new TripId(gtdi.getTripId()));
        if(trip == null){
            gtdr.setStatus(false);
            gtdr.setMessage("Trip Not Exist");
            gtdr.setTripResponse(null);
            gtdr.setTrip(null);
        }else{

            String startingPlaceName = gtdi.getFrom();
            String endPlaceName = gtdi.getTo();
            String startingPlaceId = queryForStationId(startingPlaceName);
            String endPlaceId = queryForStationId(endPlaceName);
            Route tempRoute = getRouteByRouteId(trip.getRouteId());

            TripResponse tripResponse = getTickets(trip,tempRoute,startingPlaceId,endPlaceId,gtdi.getFrom(),gtdi.getTo(),gtdi.getTravelDate());
            if(tripResponse == null){
                gtdr.setStatus(false);
                gtdr.setMessage("Cannot found TripResponse");
                gtdr.setTripResponse(null);
                gtdr.setTrip(null);
            }else{
                gtdr.setStatus(true);
                gtdr.setMessage("Success");
                gtdr.setTripResponse(tripResponse);
                gtdr.setTrip(repository.findByTripId(new TripId(gtdi.getTripId())));
            }
        }
        return gtdr;
    }

    private TripResponse getTickets(Trip trip,Route route,String startingPlaceId,String endPlaceId,String startingPlaceName, String endPlaceName, Date departureTime){


        if(!afterToday(departureTime)){
            return null;
        }

        QueryForTravel query = new QueryForTravel();
        query.setTrip(trip);
        query.setStartingPlace(startingPlaceName);
        query.setEndPlace(endPlaceName);
        query.setDepartureTime(departureTime);

        ResultForTravel resultForTravel = restTemplate.postForObject(
                "http://ts-ticketinfo-service:15681/ticketinfo/queryForTravel", query ,ResultForTravel.class);


        QuerySoldTicket information = new QuerySoldTicket(departureTime,trip.getTripId().toString());
        ResultSoldTicket result = restTemplate.postForObject(
                "http://ts-order-service:12031/order/calculate", information ,ResultSoldTicket.class);
        if(result == null){
            System.out.println("soldticket Info doesn't exist");
            return null;
        }

        TripResponse response = new TripResponse();
        if(queryForStationId(startingPlaceName).equals(trip.getStartingStationId()) &&
                queryForStationId(endPlaceName).equals(trip.getTerminalStationId())){
            response.setConfortClass(50);
            response.setEconomyClass(50);
        }else{
            response.setConfortClass(50);
            response.setEconomyClass(50);
        }

        int first = getRestTicketNumber(departureTime,trip.getTripId().toString(),
                startingPlaceName,endPlaceName,SeatClass.FIRSTCLASS.getCode());

        int second = getRestTicketNumber(departureTime,trip.getTripId().toString(),
                startingPlaceName,endPlaceName,SeatClass.SECONDCLASS.getCode());
        response.setConfortClass(first);
        response.setEconomyClass(second);

        response.setStartingStation(startingPlaceName);
        response.setTerminalStation(endPlaceName);


        int indexStart = route.getStations().indexOf(startingPlaceId);
        int indexEnd = route.getStations().indexOf(endPlaceId);
        int distanceStart = route.getDistances().get(indexStart) - route.getDistances().get(0);
        int distanceEnd = route.getDistances().get(indexEnd) - route.getDistances().get(0);
        TrainType trainType = getTrainType(trip.getTrainTypeId());

        int minutesStart = 60 * distanceStart / trainType.getAverageSpeed();
        int minutesEnd = 60 * distanceEnd / trainType.getAverageSpeed();

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(trip.getStartingTime());
        calendarStart.add(Calendar.MINUTE,minutesStart);
        response.setStartingTime(calendarStart.getTime());

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(trip.getStartingTime());
        calendarEnd.add(Calendar.MINUTE,minutesEnd);
        response.setEndTime(calendarEnd.getTime());

        response.setTripId(new TripId(result.getTrainNumber()));
        response.setTrainTypeId(trip.getTrainTypeId());
        response.setPriceForConfortClass(resultForTravel.getPrices().get("confortClass"));
        response.setPriceForEconomyClass(resultForTravel.getPrices().get("economyClass"));

        return response;
}

    @Override
    public List<Trip> queryAll(){
        return repository.findAll();
    }

    private static boolean afterToday(Date date) {
        Calendar calDateA = Calendar.getInstance();
        Date today = new Date();
        calDateA.setTime(today);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date);

        if(calDateA.get(Calendar.YEAR) > calDateB.get(Calendar.YEAR)){
            return false;
        }else if(calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)){
            if(calDateA.get(Calendar.MONTH) > calDateB.get(Calendar.MONTH)){
                return false;
            }else if(calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)){
                if(calDateA.get(Calendar.DAY_OF_MONTH) > calDateB.get(Calendar.DAY_OF_MONTH)){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        }else{
            return true;
        }
    }

    private TrainType getTrainType(String trainTypeId){
        GetTrainTypeInformation info = new GetTrainTypeInformation();
        info.setId(trainTypeId);
        TrainType trainType = restTemplate.postForObject(
                "http://ts-train-service:14567/train/retrieve", info, TrainType.class);
        return trainType;
    }

    private String queryForStationId(String stationName){
        QueryForStationId query = new QueryForStationId();
        query.setName(stationName);
        String id = restTemplate.postForObject(
                "http://ts-ticketinfo-service:15681/ticketinfo/queryForStationId", query ,String.class);
        return id;
    }

    private Route getRouteByRouteId(String routeId){
        System.out.println("[Travel Service][Get Route By Id] Route ID：" + routeId);
        GetRouteResult result = restTemplate.getForObject(
                "http://ts-route-service:11178/route/queryById/" + routeId,
                GetRouteResult.class);
        if(result.isStatus() == false){
            System.out.println("[Travel Service][Get Route By Id] Fail." + result.getMessage());
            return null;
        }else{
            System.out.println("[Travel Service][Get Route By Id] Success.");
            return result.getRoute();
        }
    }

    private int getRestTicketNumber(Date travelDate, String trainNumber, String startStationName, String endStationName, int seatType) {
        SeatRequest seatRequest = new SeatRequest();

        String fromId = queryForStationId(startStationName);
        String toId = queryForStationId(endStationName);

        seatRequest.setDestStation(toId);
        seatRequest.setStartStation(fromId);
        seatRequest.setTrainNumber(trainNumber);
        seatRequest.setTravelDate(travelDate);
        seatRequest.setSeatType(seatType);

        int restNumber = restTemplate.postForObject(
                "http://ts-seat-service:18898/seat/getLeftTicketOfInterval",
                seatRequest,Integer.class
        );

        return restNumber;
    }

    @Override
    public AdminFindAllResult adminQueryAll() {
        List<Trip> trips = repository.findAll();
        ArrayList<AdminTrip> adminTrips = new ArrayList<AdminTrip>();
        for(Trip trip : trips){
            AdminTrip adminTrip = new AdminTrip();
            adminTrip.setTrip(trip);
            adminTrip.setRoute(getRouteByRouteId(trip.getRouteId()));
            adminTrip.setTrainType(getTrainType(trip.getTrainTypeId()));
            adminTrips.add(adminTrip);
        }
        AdminFindAllResult result = new AdminFindAllResult();
        result.setStatus(true);
        result.setMessage("Travel Service Admin Query All Travel Success");
        result.setTrips(adminTrips);
        return result;
    }
}
