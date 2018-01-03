package travelplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travelplan.domain.*;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TravelPlanServiceImpl implements TravelPlanService{

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public TransferTravelSearchResult getTransferSearch(TransferTravelSearchInfo info) {

        QueryInfo queryInfoFirstSection = new QueryInfo();
        queryInfoFirstSection.setDepartureTime(info.getTravelDate());
        queryInfoFirstSection.setStartingPlace(info.getFromStationName());
        queryInfoFirstSection.setEndPlace(info.getViaStationName());

        ArrayList<TripResponse> firstSectionFromHighSpeed;
        ArrayList<TripResponse> firstSectionFromNormal;
        firstSectionFromHighSpeed = tripsFromHighSpeed(queryInfoFirstSection);
        firstSectionFromNormal = tripsFromNormal(queryInfoFirstSection);

        QueryInfo queryInfoSecondSectoin = new QueryInfo();
        queryInfoSecondSectoin.setDepartureTime(info.getTravelDate());
        queryInfoSecondSectoin.setStartingPlace(info.getViaStationName());
        queryInfoSecondSectoin.setEndPlace(info.getToStationName());

        ArrayList<TripResponse> secondSectionFromHighSpeed;
        ArrayList<TripResponse> secondSectionFromNormal;
        secondSectionFromHighSpeed = tripsFromHighSpeed(queryInfoSecondSectoin);
        secondSectionFromNormal = tripsFromNormal(queryInfoSecondSectoin);

        ArrayList<TripResponse> firstSection = new ArrayList<>();
        firstSection.addAll(firstSectionFromHighSpeed);
        firstSection.addAll(firstSectionFromNormal);

        ArrayList<TripResponse> secondSection = new ArrayList<>();
        secondSection.addAll(secondSectionFromHighSpeed);
        secondSection.addAll(secondSectionFromNormal);

        TransferTravelSearchResult result = new TransferTravelSearchResult();
        result.setStatus(true);
        result.setMessage("Success.");
        result.setFirstSectionResult(firstSection);
        result.setSecondSectionResult(secondSection);

        return result;
    }

    @Override
    public TravelAdvanceResult getCheapest(QueryInfo info) {
        GetRoutePlanInfo routePlanInfo = new GetRoutePlanInfo();
        routePlanInfo.setNum(5);
        routePlanInfo.setFormStationName(info.getStartingPlace());
        routePlanInfo.setToStationName(info.getEndPlace());
        routePlanInfo.setTravelDate(info.getDepartureTime());
        RoutePlanResults routePlanResults = getRoutePlanResultCheapest(routePlanInfo);

        TravelAdvanceResult travelAdvanceResult = new TravelAdvanceResult();

        if(routePlanResults.isStatus() == true){
            ArrayList<RoutePlanResultUnit> routePlanResultUnits = routePlanResults.getResults();
            travelAdvanceResult.setStatus(true);
            travelAdvanceResult.setMessage("Success");
            ArrayList<TravelAdvanceResultUnit> lists = new ArrayList<>();
            for(int i = 0; i < routePlanResultUnits.size(); i++){
                RoutePlanResultUnit tempUnit = routePlanResultUnits.get(i);
                TravelAdvanceResultUnit newUnit = new TravelAdvanceResultUnit();
                newUnit.setTripId(tempUnit.getTripId());
                newUnit.setTrainTypeId(tempUnit.getTrainTypeId());
                newUnit.setFromStationName(tempUnit.getFromStationName());
                newUnit.setToStationName(tempUnit.getToStationName());
                ArrayList<String> stops = transferStationIdToStationName(tempUnit.getStopStations());
                newUnit.setStopStations(stops);
                newUnit.setPriceForFirstClassSeat(tempUnit.getPriceForFirstClassSeat());
                newUnit.setPriceForSecondClassSeat(tempUnit.getPriceForSecondClassSeat());
                newUnit.setStartingTime(tempUnit.getStartingTime());
                newUnit.setEndTime(tempUnit.getEndTime());
                int first = getRestTicketNumber(info.getDepartureTime(),tempUnit.getTripId(),
                        tempUnit.getFromStationName(),tempUnit.getToStationName(),SeatClass.FIRSTCLASS.getCode());

                int second = getRestTicketNumber(info.getDepartureTime(),tempUnit.getTripId(),
                        tempUnit.getFromStationName(),tempUnit.getToStationName(),SeatClass.SECONDCLASS.getCode());
                newUnit.setNumberOfRestTicketFirstClass(first);
                newUnit.setNumberOfRestTicketSecondClass(second);
                lists.add(newUnit);
            }
            travelAdvanceResult.setTravelAdvanceResultUnits(lists);
        }else{
            travelAdvanceResult.setStatus(false);
            travelAdvanceResult.setMessage("Cannot Find");
            travelAdvanceResult.setTravelAdvanceResultUnits(new ArrayList<>());
        }

        return travelAdvanceResult;
}

    @Override
    public TravelAdvanceResult getQuickest(QueryInfo info) {
        GetRoutePlanInfo routePlanInfo = new GetRoutePlanInfo();
        routePlanInfo.setNum(5);
        routePlanInfo.setFormStationName(info.getStartingPlace());
        routePlanInfo.setToStationName(info.getEndPlace());
        routePlanInfo.setTravelDate(info.getDepartureTime());
        RoutePlanResults routePlanResults = getRoutePlanResultQuickest(routePlanInfo);

        TravelAdvanceResult travelAdvanceResult = new TravelAdvanceResult();

        if(routePlanResults.isStatus() == true){
            ArrayList<RoutePlanResultUnit> routePlanResultUnits = routePlanResults.getResults();
            travelAdvanceResult.setStatus(true);
            travelAdvanceResult.setMessage("Success");
            ArrayList<TravelAdvanceResultUnit> lists = new ArrayList<>();
            for(int i = 0; i < routePlanResultUnits.size(); i++){
                RoutePlanResultUnit tempUnit = routePlanResultUnits.get(i);
                TravelAdvanceResultUnit newUnit = new TravelAdvanceResultUnit();
                newUnit.setTripId(tempUnit.getTripId());
                newUnit.setTrainTypeId(tempUnit.getTrainTypeId());
                newUnit.setFromStationName(tempUnit.getFromStationName());
                newUnit.setToStationName(tempUnit.getToStationName());

                ArrayList<String> stops = transferStationIdToStationName(tempUnit.getStopStations());
                newUnit.setStopStations(stops);

                newUnit.setPriceForFirstClassSeat(tempUnit.getPriceForFirstClassSeat());
                newUnit.setPriceForSecondClassSeat(tempUnit.getPriceForSecondClassSeat());
                newUnit.setStartingTime(tempUnit.getStartingTime());
                newUnit.setEndTime(tempUnit.getEndTime());
                int first = getRestTicketNumber(info.getDepartureTime(),tempUnit.getTripId(),
                        tempUnit.getFromStationName(),tempUnit.getToStationName(),SeatClass.FIRSTCLASS.getCode());

                int second = getRestTicketNumber(info.getDepartureTime(),tempUnit.getTripId(),
                        tempUnit.getFromStationName(),tempUnit.getToStationName(),SeatClass.SECONDCLASS.getCode());
                newUnit.setNumberOfRestTicketFirstClass(first);
                newUnit.setNumberOfRestTicketSecondClass(second);
                lists.add(newUnit);
            }
            travelAdvanceResult.setTravelAdvanceResultUnits(lists);
        }else{
            travelAdvanceResult.setStatus(false);
            travelAdvanceResult.setMessage("Cannot Find");
            travelAdvanceResult.setTravelAdvanceResultUnits(new ArrayList<>());
        }

        return travelAdvanceResult;

    }

    @Override
    public TravelAdvanceResult getMinStation(QueryInfo info) {
        GetRoutePlanInfo routePlanInfo = new GetRoutePlanInfo();
        routePlanInfo.setNum(5);
        routePlanInfo.setFormStationName(info.getStartingPlace());
        routePlanInfo.setToStationName(info.getEndPlace());
        routePlanInfo.setTravelDate(info.getDepartureTime());
        RoutePlanResults routePlanResults = getRoutePlanResultMinStation(routePlanInfo);
        TravelAdvanceResult travelAdvanceResult = new TravelAdvanceResult();

        if(routePlanResults.isStatus() == true){
            ArrayList<RoutePlanResultUnit> routePlanResultUnits = routePlanResults.getResults();
            travelAdvanceResult.setStatus(true);
            travelAdvanceResult.setMessage("Success");
            ArrayList<TravelAdvanceResultUnit> lists = new ArrayList<>();
            for(int i = 0; i < routePlanResultUnits.size(); i++){
                RoutePlanResultUnit tempUnit = routePlanResultUnits.get(i);
                TravelAdvanceResultUnit newUnit = new TravelAdvanceResultUnit();
                newUnit.setTripId(tempUnit.getTripId());
                newUnit.setTrainTypeId(tempUnit.getTrainTypeId());
                newUnit.setFromStationName(tempUnit.getFromStationName());
                newUnit.setToStationName(tempUnit.getToStationName());

                ArrayList<String> stops = transferStationIdToStationName(tempUnit.getStopStations());
                newUnit.setStopStations(stops);

                newUnit.setPriceForFirstClassSeat(tempUnit.getPriceForFirstClassSeat());
                newUnit.setPriceForSecondClassSeat(tempUnit.getPriceForSecondClassSeat());
                newUnit.setStartingTime(tempUnit.getStartingTime());
                newUnit.setEndTime(tempUnit.getEndTime());
                int first = getRestTicketNumber(info.getDepartureTime(),tempUnit.getTripId(),
                        tempUnit.getFromStationName(),tempUnit.getToStationName(),SeatClass.FIRSTCLASS.getCode());

                int second = getRestTicketNumber(info.getDepartureTime(),tempUnit.getTripId(),
                        tempUnit.getFromStationName(),tempUnit.getToStationName(),SeatClass.SECONDCLASS.getCode());
                newUnit.setNumberOfRestTicketFirstClass(first);
                newUnit.setNumberOfRestTicketSecondClass(second);
                lists.add(newUnit);
            }
            travelAdvanceResult.setTravelAdvanceResultUnits(lists);
        }else{
            travelAdvanceResult.setStatus(false);
            travelAdvanceResult.setMessage("Cannot Find");
            travelAdvanceResult.setTravelAdvanceResultUnits(new ArrayList<>());
        }

        return travelAdvanceResult;
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

    private RoutePlanResults getRoutePlanResultCheapest(GetRoutePlanInfo info){
        RoutePlanResults routePlanResults =
                restTemplate.postForObject(
                        "http://ts-route-plan-service:14578/routePlan/cheapestRoute",
                        info,RoutePlanResults.class
                );
        return routePlanResults;
    }

    private RoutePlanResults getRoutePlanResultQuickest(GetRoutePlanInfo info){
        RoutePlanResults routePlanResults =
                restTemplate.postForObject(
                        "http://ts-route-plan-service:14578/routePlan/quickestRoute",
                        info,RoutePlanResults.class
                );
        return routePlanResults;
    }

    private RoutePlanResults getRoutePlanResultMinStation(GetRoutePlanInfo info){
        RoutePlanResults routePlanResults =
                restTemplate.postForObject(
                        "http://ts-route-plan-service:14578/routePlan/minStopStations",
                        info,RoutePlanResults.class
                );
        return routePlanResults;
    }

    private ArrayList<TripResponse> tripsFromHighSpeed(QueryInfo info){
        ArrayList<TripResponse> result = new ArrayList<>();
        result = restTemplate.postForObject("http://ts-travel-service:12346/travel/query",info,result.getClass());
        return result;
    }

    private ArrayList<TripResponse> tripsFromNormal(QueryInfo info){
        ArrayList<TripResponse> result = new ArrayList<>();
        result = restTemplate.postForObject("http://ts-travel2-service:16346/travel2/query",info,result.getClass());
        return result;
    }

    private String queryForStationId(String stationName){
        QueryForStationId query = new QueryForStationId();
        query.setName(stationName);
        String id = restTemplate.postForObject(
                "http://ts-ticketinfo-service:15681/ticketinfo/queryForStationId", query ,String.class);
        return id;
    }

    private ArrayList<String> transferStationIdToStationName(ArrayList<String> stations){
        ArrayList<String> stationNames = new ArrayList<>();
        for(int i = 0;i < stations.size();i++){
            String name = queryForStaionNameByStationId(stations.get(i));
            stationNames.add(name);
        }
        return stationNames;
    }

    private String queryForStaionNameByStationId(String stationId) {

        QueryByStationIdForName queryByStationIdForName = new QueryByStationIdForName(stationId);

        return restTemplate.postForObject(
                "http://ts-station-service:12345/station/queryByIdForName",
                queryByStationIdForName,String.class
        );
    }

}
