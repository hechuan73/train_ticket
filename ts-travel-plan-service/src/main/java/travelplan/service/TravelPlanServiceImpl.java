package travelplan.service;

import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travelplan.entity.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TravelPlanServiceImpl implements TravelPlanService {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Response getTransferSearch(TransferTravelInfo info, HttpHeaders headers) {

        TripInfo queryInfoFirstSection = new TripInfo();
        queryInfoFirstSection.setDepartureTime(info.getTravelDate());
        queryInfoFirstSection.setStartingPlace(info.getFromStationName());
        queryInfoFirstSection.setEndPlace(info.getViaStationName());

        ArrayList<TripResponse> firstSectionFromHighSpeed;
        ArrayList<TripResponse> firstSectionFromNormal;
        firstSectionFromHighSpeed = tripsFromHighSpeed(queryInfoFirstSection, headers);
        firstSectionFromNormal = tripsFromNormal(queryInfoFirstSection, headers);

        TripInfo queryInfoSecondSectoin = new TripInfo();
        queryInfoSecondSectoin.setDepartureTime(info.getTravelDate());
        queryInfoSecondSectoin.setStartingPlace(info.getViaStationName());
        queryInfoSecondSectoin.setEndPlace(info.getToStationName());

        ArrayList<TripResponse> secondSectionFromHighSpeed;
        ArrayList<TripResponse> secondSectionFromNormal;
        secondSectionFromHighSpeed = tripsFromHighSpeed(queryInfoSecondSectoin, headers);
        secondSectionFromNormal = tripsFromNormal(queryInfoSecondSectoin, headers);

        ArrayList<TripResponse> firstSection = new ArrayList<>();
        firstSection.addAll(firstSectionFromHighSpeed);
        firstSection.addAll(firstSectionFromNormal);

        ArrayList<TripResponse> secondSection = new ArrayList<>();
        secondSection.addAll(secondSectionFromHighSpeed);
        secondSection.addAll(secondSectionFromNormal);

        TransferTravelResult result = new TransferTravelResult();
        result.setFirstSectionResult(firstSection);
        result.setSecondSectionResult(secondSection);

        return new Response<>(1, "Success.", result);
    }

    @Override
    public Response getCheapest(TripInfo info, HttpHeaders headers) {
        RoutePlanInfo routePlanInfo = new RoutePlanInfo();
        routePlanInfo.setNum(5);
        routePlanInfo.setFormStationName(info.getStartingPlace());
        routePlanInfo.setToStationName(info.getEndPlace());
        routePlanInfo.setTravelDate(info.getDepartureTime());
        Response routePlanResults = getRoutePlanResultCheapest(routePlanInfo, headers);

        if ("1".equals(routePlanResults.getStatus())) {
            ArrayList<RoutePlanResultUnit> routePlanResultUnits = (ArrayList<RoutePlanResultUnit>) routePlanResults.getData();

            ArrayList<TravelAdvanceResultUnit> lists = new ArrayList<>();
            for (int i = 0; i < routePlanResultUnits.size(); i++) {
                RoutePlanResultUnit tempUnit = routePlanResultUnits.get(i);
                TravelAdvanceResultUnit newUnit = new TravelAdvanceResultUnit();
                newUnit.setTripId(tempUnit.getTripId());
                newUnit.setToStationName(tempUnit.getToStationName());
                newUnit.setTrainTypeId(tempUnit.getTrainTypeId());
                newUnit.setFromStationName(tempUnit.getFromStationName());

                List<String> stops = transferStationIdToStationName(tempUnit.getStopStations(), headers);
                newUnit.setStopStations(stops);
                newUnit.setPriceForFirstClassSeat(tempUnit.getPriceForFirstClassSeat());
                newUnit.setPriceForSecondClassSeat(tempUnit.getPriceForSecondClassSeat());
                newUnit.setStartingTime(tempUnit.getStartingTime());
                newUnit.setEndTime(tempUnit.getEndTime());
                int first = getRestTicketNumber(info.getDepartureTime(), tempUnit.getTripId(),
                        tempUnit.getFromStationName(), tempUnit.getToStationName(), SeatClass.FIRSTCLASS.getCode(), headers);

                int second = getRestTicketNumber(info.getDepartureTime(), tempUnit.getTripId(),
                        tempUnit.getFromStationName(), tempUnit.getToStationName(), SeatClass.SECONDCLASS.getCode(), headers);
                newUnit.setNumberOfRestTicketFirstClass(first);
                newUnit.setNumberOfRestTicketSecondClass(second);
                lists.add(newUnit);
            }

            return new Response<>(1, "Success", lists);
        } else {
            return new Response<>(0, "Cannot Find", null);
        }
    }

    @Override
    public Response getQuickest(TripInfo info, HttpHeaders headers) {
        RoutePlanInfo routePlanInfo = new RoutePlanInfo();
        routePlanInfo.setNum(5);
        routePlanInfo.setFormStationName(info.getStartingPlace());
        routePlanInfo.setToStationName(info.getEndPlace());
        routePlanInfo.setTravelDate(info.getDepartureTime());
        Response routePlanResults = getRoutePlanResultQuickest(routePlanInfo, headers);



        if ("1".equals(routePlanResults.getStatus())) {
            ArrayList<RoutePlanResultUnit> routePlanResultUnits = (ArrayList<RoutePlanResultUnit>) routePlanResults.getData();

            ArrayList<TravelAdvanceResultUnit> lists = new ArrayList<>();
            for (int i = 0; i < routePlanResultUnits.size(); i++) {
                RoutePlanResultUnit tempUnit = routePlanResultUnits.get(i);
                TravelAdvanceResultUnit newUnit = new TravelAdvanceResultUnit();
                newUnit.setTripId(tempUnit.getTripId());
                newUnit.setTrainTypeId(tempUnit.getTrainTypeId());
                newUnit.setToStationName(tempUnit.getToStationName());
                newUnit.setFromStationName(tempUnit.getFromStationName());

                List<String> stops = transferStationIdToStationName(tempUnit.getStopStations(), headers);
                newUnit.setStopStations(stops);

                newUnit.setPriceForFirstClassSeat(tempUnit.getPriceForFirstClassSeat());
                newUnit.setPriceForSecondClassSeat(tempUnit.getPriceForSecondClassSeat());
                newUnit.setStartingTime(tempUnit.getStartingTime());
                newUnit.setEndTime(tempUnit.getEndTime());
                int first = getRestTicketNumber(info.getDepartureTime(), tempUnit.getTripId(),
                        tempUnit.getFromStationName(), tempUnit.getToStationName(), SeatClass.FIRSTCLASS.getCode(), headers);

                int second = getRestTicketNumber(info.getDepartureTime(), tempUnit.getTripId(),
                        tempUnit.getFromStationName(), tempUnit.getToStationName(), SeatClass.SECONDCLASS.getCode(), headers);
                newUnit.setNumberOfRestTicketFirstClass(first);
                newUnit.setNumberOfRestTicketSecondClass(second);
                lists.add(newUnit);
            }
            return new Response<>(1, "Success", lists);
        } else {
            return new Response<>(0, "Cannot Find", null);
        }
    }

    @Override
    public Response getMinStation(TripInfo info, HttpHeaders headers) {
        RoutePlanInfo routePlanInfo = new RoutePlanInfo();
        routePlanInfo.setNum(5);
        routePlanInfo.setFormStationName(info.getStartingPlace());
        routePlanInfo.setToStationName(info.getEndPlace());
        routePlanInfo.setTravelDate(info.getDepartureTime());
        Response routePlanResults = getRoutePlanResultMinStation(routePlanInfo, headers);
        /// TravelAdvanceResult travelAdvanceResult = new TravelAdvanceResult();

        if ("1".equals(routePlanResults.getStatus())) {
            ArrayList<RoutePlanResultUnit> routePlanResultUnits = (ArrayList<RoutePlanResultUnit>) routePlanResults.getData();

            ArrayList<TravelAdvanceResultUnit> lists = new ArrayList<>();
            for (int i = 0; i < routePlanResultUnits.size(); i++) {
                RoutePlanResultUnit tempUnit = routePlanResultUnits.get(i);
                TravelAdvanceResultUnit newUnit = new TravelAdvanceResultUnit();
                newUnit.setTripId(tempUnit.getTripId());
                newUnit.setTrainTypeId(tempUnit.getTrainTypeId());
                newUnit.setFromStationName(tempUnit.getFromStationName());
                newUnit.setToStationName(tempUnit.getToStationName());

                List<String> stops = transferStationIdToStationName(tempUnit.getStopStations(), headers);
                newUnit.setStopStations(stops);

                newUnit.setPriceForFirstClassSeat(tempUnit.getPriceForFirstClassSeat());
                newUnit.setPriceForSecondClassSeat(tempUnit.getPriceForSecondClassSeat());
                newUnit.setEndTime(tempUnit.getEndTime());
                newUnit.setStartingTime(tempUnit.getStartingTime());

                int first = getRestTicketNumber(info.getDepartureTime(), tempUnit.getTripId(),
                        tempUnit.getFromStationName(), tempUnit.getToStationName(), SeatClass.FIRSTCLASS.getCode(), headers);

                int second = getRestTicketNumber(info.getDepartureTime(), tempUnit.getTripId(),
                        tempUnit.getFromStationName(), tempUnit.getToStationName(), SeatClass.SECONDCLASS.getCode(), headers);
                newUnit.setNumberOfRestTicketFirstClass(first);
                newUnit.setNumberOfRestTicketSecondClass(second);
                lists.add(newUnit);
            }
            return new Response<>(1, "Success", lists);
        } else {
            return new Response<>(0, "Cannot Find", null);
        }
    }

    private int getRestTicketNumber(Date travelDate, String trainNumber, String startStationName, String endStationName, int seatType, HttpHeaders headers) {
        Seat seatRequest = new Seat();

        String fromId = queryForStationId(startStationName, headers);
        String toId = queryForStationId(endStationName, headers);

        seatRequest.setDestStation(toId);
        seatRequest.setStartStation(fromId);
        seatRequest.setTrainNumber(trainNumber);
        seatRequest.setTravelDate(travelDate);
        seatRequest.setSeatType(seatType);

        HttpEntity requestEntity = new HttpEntity(seatRequest, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-seat-service:18898/api/v1/seatservice/seats/left_tickets",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response restNumberResponse = re.getBody();

//        int restNumber = restTemplate.postForObject(
//                "http://ts-seat-service:18898/seat/getLeftTicketOfInterval",
//                seatRequest,Integer.class
//                );

        return (int) restNumberResponse.getData();
    }

    private Response getRoutePlanResultCheapest(RoutePlanInfo info, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(info, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-route-plan-service:14578/api/v1/routeplanservice/routePlan/cheapestRoute",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response routePlanResults = re.getBody();
//        RoutePlanResults routePlanResults =
//                restTemplate.postForObject(
//                        "http://ts-route-plan-service:14578/routePlan/cheapestRoute",
//                        info,RoutePlanResults.class
//                );
        return routePlanResults;
    }

    private Response getRoutePlanResultQuickest(RoutePlanInfo info, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(info, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-route-plan-service:14578/api/v1/routeplanservice/routePlan/quickestRoute",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response routePlanResults = re.getBody();
//        RoutePlanResults routePlanResults =
//                restTemplate.postForObject(
//                        "http://ts-route-plan-service:14578/routePlan/quickestRoute",
//                        info,RoutePlanResults.class
//                );
        return routePlanResults;
    }

    private Response getRoutePlanResultMinStation(RoutePlanInfo info, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(info, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-route-plan-service:14578/api/v1/routeplanservice/routePlan/minStopStations",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response routePlanResults = re.getBody();
//        RoutePlanResults routePlanResults =
//                restTemplate.postForObject(
//                        "http://ts-route-plan-service:14578/routePlan/minStopStations",
//                        info,RoutePlanResults.class
//                );
        return routePlanResults;
    }

    private ArrayList<TripResponse> tripsFromHighSpeed(TripInfo info, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(info, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-travel-service:12346/api/v1/travelservice/trips/left",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response resultRes = re.getBody();
        ArrayList<TripResponse> result = (ArrayList<TripResponse>) resultRes.getData();
//        result = restTemplate.postForObject("http://ts-travel-service:12346/travel/query",info,result.getClass());
        return result;
    }

    private ArrayList<TripResponse> tripsFromNormal(TripInfo info, HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(info, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-travel2-service:16346/api/v1/travel2service/trips/left",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response resultRes = re.getBody();
        ArrayList<TripResponse> result = (ArrayList<TripResponse>) resultRes.getData();
//        result = restTemplate.postForObject("http://ts-travel2-service:16346/travel2/query",info,result.getClass());
        return result;
    }

    private String queryForStationId(String stationName, HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-ticketinfo-service:15681/api/v1/ticketinfoservice/ticketinfo/" + stationName,
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response id = re.getBody();
//        String id = restTemplate.postForObject(
//                "http://ts-ticketinfo-service:15681/ticketinfo/queryForStationId", query ,String.class);
        return (String) id.getData();
    }

    private List<String> transferStationIdToStationName(ArrayList<String> stations, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(stations, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations/namelist",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response result = re.getBody();
        return (List<String>) result.getData();
    }
}
