package seat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import seat.domain.*;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Ticket distributeSeat(SeatRequest seatRequest){
        GetRouteResult routeResult;
        GetTrainTypeResult trainTypeResult;
        LeftTicketInfo leftTicketInfo;

        String trainNumber = seatRequest.getTrainNumber();
        if(trainNumber.startsWith("G") || trainNumber.startsWith("D") ){
            System.out.println("[SeatService distributeSeat] TrainNumber start with G|D");

            routeResult = restTemplate.getForObject(
                    "http://ts-travel-service:12346/travel/getRouteByTripId/"+ seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService distributeSeat] The result of getRouteResult is " + routeResult.getMessage());

            leftTicketInfo = restTemplate.postForObject(
                    "http://ts-order-service:12031/order/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            trainTypeResult = restTemplate.getForObject(
                    "http://ts-travel-service:12346/travel/getTrainTypeByTripId/" + seatRequest.getTrainNumber() ,GetTrainTypeResult.class);
            System.out.println("[SeatService distributeSeat] The result of getTrainTypeResult is " + trainTypeResult.getMessage());
        }
        else{
            System.out.println("[SeatService] TrainNumber start with other capital");

            routeResult = restTemplate.getForObject(
                    "http://ts-travel2-service:16346/travel2/getRouteByTripId/" + seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService distributeSeat] The result of getRouteResult is " + routeResult.getMessage());


            leftTicketInfo = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);


            trainTypeResult = restTemplate.getForObject(
                    "http://ts-travel2-service:16346/travel2/getTrainTypeByTripId/" + seatRequest.getTrainNumber(), GetTrainTypeResult.class);
            System.out.println("[SeatService distributeSeat] The result of getTrainTypeResult is " + trainTypeResult.getMessage());
        }



        List<String> stationList = routeResult.getRoute().getStations();
        int seatTotalNum;
        if(seatRequest.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            seatTotalNum = trainTypeResult.getTrainType().getConfortClass();
            System.out.println("[SeatService distributeSeat] The request seat type is confortClass and the total num is " + seatTotalNum);
        }
        else {
            seatTotalNum = trainTypeResult.getTrainType().getEconomyClass();
            System.out.println("[SeatService distributeSeat] The request seat type is economyClass and the total num is " + seatTotalNum);
        }
        String startStation = seatRequest.getStartStation();
        Ticket ticket = new Ticket();
        ticket.setStartStation(startStation);
        ticket.setDestStation(seatRequest.getDestStation());
        Set<Ticket> soldTickets = leftTicketInfo.getSoldTickets();


        for(Ticket soldTicket : soldTickets){
            String soldTicketDestStation = soldTicket.getDestStation();

            if(stationList.indexOf(soldTicketDestStation) < stationList.indexOf(startStation)){
                ticket.setSeatNo(soldTicket.getSeatNo());
                System.out.println("[SeatService distributeSeat] Use the previous distributed seat number!" + soldTicket.getSeatNo());
                return ticket;
            }
        }


        Random rand = new Random();
        int range = seatTotalNum;
        int seat = rand.nextInt(range) + 1;
        while (isContained(soldTickets, seat)){
            seat = rand.nextInt(range) + 1;
        }
        ticket.setSeatNo(seat);
        System.out.println("[SeatService distributeSeat] Use a new seat number!" + seat);
        return ticket;
    }


    private boolean isContained( Set<Ticket> soldTickets, int seat){
        boolean result = false;
        for(Ticket soldTicket : soldTickets){
            if(soldTicket.getSeatNo() == seat){
                return true;
            }
        }
        return result;
    }

    @Override
    public int getLeftTicketOfInterval(SeatRequest seatRequest){
        int numOfLeftTicket = 0;
        GetRouteResult routeResult;
        GetTrainTypeResult trainTypeResult;
        LeftTicketInfo leftTicketInfo;


        String trainNumber = seatRequest.getTrainNumber();
        if(trainNumber.startsWith("G") || trainNumber.startsWith("D") ){
            System.out.println("[SeatService getLeftTicketOfInterval] TrainNumber start with G|D");


            routeResult = restTemplate.getForObject(
                    "http://ts-travel-service:12346/travel/getRouteByTripId/"+ seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService getLeftTicketOfInterval] The result of getRouteResult is " + routeResult.getMessage());


            leftTicketInfo = restTemplate.postForObject(
                    "http://ts-order-service:12031/order/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);


            trainTypeResult = restTemplate.getForObject(
                    "http://ts-travel-service:12346/travel/getTrainTypeByTripId/" + seatRequest.getTrainNumber() ,GetTrainTypeResult.class);
            System.out.println("[SeatService getLeftTicketOfInterval] The result of getTrainTypeResult is " + trainTypeResult.getMessage());
        }
        else{
            System.out.println("[SeatService getLeftTicketOfInterval] TrainNumber start with other capital");

            routeResult = restTemplate.getForObject(
                    "http://ts-travel2-service:16346/travel2/getRouteByTripId/" + seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService getLeftTicketOfInterval] The result of getRouteResult is " + routeResult.getMessage());


            leftTicketInfo = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            trainTypeResult = restTemplate.getForObject(
                    "http://ts-travel2-service:16346/travel2/getTrainTypeByTripId/" + seatRequest.getTrainNumber(), GetTrainTypeResult.class);
            System.out.println("[SeatService getLeftTicketOfInterval] The result of getTrainTypeResult is " + trainTypeResult.getMessage());
        }


        List<String> stationList = routeResult.getRoute().getStations();
        int seatTotalNum;
        if(seatRequest.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            seatTotalNum = trainTypeResult.getTrainType().getConfortClass();
            System.out.println("[SeatService getLeftTicketOfInterval] The request seat type is confortClass and the total num is " + seatTotalNum);
        }
        else {
            seatTotalNum = trainTypeResult.getTrainType().getEconomyClass();
            System.out.println("[SeatService getLeftTicketOfInterval] The request seat type is economyClass and the total num is " + seatTotalNum);
        }
        String startStation = seatRequest.getStartStation();
        Set<Ticket> soldTickets = leftTicketInfo.getSoldTickets();

        for(Ticket soldTicket : soldTickets){
            String soldTicketDestStation = soldTicket.getDestStation();

            if(stationList.indexOf(soldTicketDestStation) < stationList.indexOf(startStation)){
                System.out.println("[SeatService getLeftTicketOfInterval] The previous distributed seat number is usable!" + soldTicket.getSeatNo());
                numOfLeftTicket++;
            }
        }


        double direstPart = getDirectProportion();
        Route route = routeResult.getRoute();
        if(route.getStations().get(0).equals(seatRequest.getStartStation()) &&
                route.getStations().get(route.getStations().size()-1).equals(seatRequest.getDestStation())){
            //do nothing
        }else{
            direstPart = 1.0 - direstPart;
        }

        int unusedNum = (int)(seatTotalNum * direstPart) - soldTickets.size();
        numOfLeftTicket += unusedNum;

        return numOfLeftTicket;
    }

    private double getDirectProportion(){

        QueryConfig queryConfig = new QueryConfig("DirectTicketAllocationProportion");

        String configValue = restTemplate.postForObject(
                "http://ts-config-service:15679//config/query",
                queryConfig,String.class);

        return Double.parseDouble(configValue);
    }
}