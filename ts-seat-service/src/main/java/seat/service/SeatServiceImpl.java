package seat.service;

import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import seat.entity.*;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Response distributeSeat(Seat seatRequest, HttpHeaders headers) {
        Response routeResult;

        LeftTicketInfo leftTicketInfo;
        TrainType trainTypeResult = new TrainType();
        ResponseEntity<Response> re;
        ResponseEntity<Response> re2;
        ResponseEntity<Response> re3;

        //区分G\D开头和其它车次
        String trainNumber = seatRequest.getTrainNumber();
        if (trainNumber.startsWith("G") || trainNumber.startsWith("D")) {
            System.out.println("[SeatService distributeSeat] TrainNumber start with G|D");

            //调用微服务，查询获得车次的所有站点信息
            HttpEntity requestEntity = new HttpEntity(headers);
            re = restTemplate.exchange(
                    "http://ts-travel-service:12346/api/v1/travelservice/routes/" + trainNumber,
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            routeResult = re.getBody();
//            routeResult = restTemplate.getForObject(
//                    "http://ts-travel-service:12346/travel/getRouteByTripId/"+ seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService distributeSeat] The result of getRouteResult is " + routeResult.getMsg());

            //调用微服务，查询获得余票信息：该车次指定座型已售Ticket的set集合
            requestEntity = new HttpEntity(seatRequest, headers);
            re3 = restTemplate.exchange(
                    "http://ts-order-service:12031/api/v1/orderservice/order/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
            Response leftTicketResponse = re3.getBody();
            leftTicketInfo = (LeftTicketInfo) leftTicketResponse.getData();
//            leftTicketInfo = restTemplate.postForObject(
//                    "http://ts-order-service:12031/order/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            //调用微服务，查询该车次指定座型总数量
            requestEntity = new HttpEntity(headers);
            re2 = restTemplate.exchange(
                    "http://ts-travel-service:12346/api/v1/travelservice/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            Response trainTypeResponse = re2.getBody();


//            trainTypeResult = restTemplate.getForObject(
//                    "http://ts-travel-service:12346/travel/getTrainTypeByTripId/" + seatRequest.getTrainNumber() ,GetTrainTypeResult.class);
            System.out.println("[SeatService distributeSeat] The result of getTrainTypeResult is " + trainTypeResponse.getMsg());
        } else {
            System.out.println("[SeatService] TrainNumber start with other capital");
            //调用微服务，查询获得车次的所有站点信息
            HttpEntity requestEntity = new HttpEntity(headers);
            re = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/routes/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            routeResult = re.getBody();
//            routeResult = restTemplate.getForObject(
//                    "http://ts-travel2-service:16346/travel2/getRouteByTripId/" + seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService distributeSeat] The result of getRouteResult is " + routeResult.getMsg());

            //调用微服务，查询获得余票信息：该车次指定座型已售Ticket的set集合
            requestEntity = new HttpEntity(seatRequest, headers);
            re3 = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOthers/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
            Response leftTicketResponse = re3.getBody();
            leftTicketInfo = (LeftTicketInfo) leftTicketResponse.getData();
//            leftTicketInfo = restTemplate.postForObject(
//                    "http://ts-order-other-service:12032/orderOther/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            //调用微服务，查询该车次指定座型总数量
            requestEntity = new HttpEntity(headers);
            re2 = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            Response trainTypeResponse = re2.getBody();
            trainTypeResult = (TrainType) trainTypeResponse.getData();
//            trainTypeResult = restTemplate.getForObject(
//                    "http://ts-travel2-service:16346/travel2/getTrainTypeByTripId/" + seatRequest.getTrainNumber(), GetTrainTypeResult.class);
            System.out.println("[SeatService distributeSeat] The result of getTrainTypeResult is " + trainTypeResponse.getMsg());
        }


        //分配座位
        List<String> stationList = ((Route) routeResult.getData()).getStations();
        int seatTotalNum;
        if (seatRequest.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            seatTotalNum = trainTypeResult.getConfortClass();
            System.out.println("[SeatService distributeSeat] The request seat type is confortClass and the total num is " + seatTotalNum);
        } else {
            seatTotalNum = trainTypeResult.getEconomyClass();
            System.out.println("[SeatService distributeSeat] The request seat type is economyClass and the total num is " + seatTotalNum);
        }
        String startStation = seatRequest.getStartStation();
        Ticket ticket = new Ticket();
        ticket.setStartStation(startStation);
        ticket.setDestStation(seatRequest.getDestStation());
        Set<Ticket> soldTickets = leftTicketInfo.getSoldTickets();

        //优先分配已经售出的票
        for (Ticket soldTicket : soldTickets) {
            String soldTicketDestStation = soldTicket.getDestStation();
            //售出的票的终点站在请求的起点之前，则可以分配出去
            if (stationList.indexOf(soldTicketDestStation) < stationList.indexOf(startStation)) {
                ticket.setSeatNo(soldTicket.getSeatNo());
                System.out.println("[SeatService distributeSeat] Use the previous distributed seat number!" + soldTicket.getSeatNo());
                return new Response<>(1, "Use the previous distributed seat number!", ticket);
            }
        }

        //分配新的票
        Random rand = new Random();
        int range = seatTotalNum;
        int seat = rand.nextInt(range) + 1;
        while (isContained(soldTickets, seat)) {
            seat = rand.nextInt(range) + 1;
        }
        ticket.setSeatNo(seat);
        System.out.println("[SeatService distributeSeat] Use a new seat number!" + seat);
        return new Response<>(1, "Use a new seat number!", ticket);
    }

    //检查座位号是否已经被使用
    private boolean isContained(Set<Ticket> soldTickets, int seat) {
        boolean result = false;
        for (Ticket soldTicket : soldTickets) {
            if (soldTicket.getSeatNo() == seat) {
                return true;
            }
        }
        return result;
    }

    @Override
    public Response getLeftTicketOfInterval(Seat seatRequest, HttpHeaders headers) {
        int numOfLeftTicket = 0;
        Response routeResult;
        TrainType trainTypeResult;
        LeftTicketInfo leftTicketInfo;

        ResponseEntity<Response> re;
        ResponseEntity<Response> re2;
        ResponseEntity<Response> re3;

        //区分G\D开头和其它车次
        String trainNumber = seatRequest.getTrainNumber();
        if (trainNumber.startsWith("G") || trainNumber.startsWith("D")) {
            System.out.println("[SeatService getLeftTicketOfInterval] TrainNumber start with G|D");


            //调用微服务，查询获得车次的所有站点信息
            HttpEntity requestEntity = new HttpEntity(headers);
            re = restTemplate.exchange(
                    "http://ts-travel-service:12346/api/v1/travelservice/routes/" + trainNumber,
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            routeResult = re.getBody();
//            routeResult = restTemplate.getForObject(
//                    "http://ts-travel-service:12346/travel/getRouteByTripId/"+ seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService getLeftTicketOfInterval] The result of getRouteResult is " + routeResult.getMsg());

            //调用微服务，查询获得余票信息：该车次指定座型已售Ticket的set集合
            requestEntity = new HttpEntity(seatRequest, headers);
            re3 = restTemplate.exchange(
                    "http://ts-order-service:12031/api/v1/orderservice/order/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
            Response leftTicketResponse = re3.getBody();
            leftTicketInfo = (LeftTicketInfo) leftTicketResponse.getData();

//            leftTicketInfo = restTemplate.postForObject(
//                    "http://ts-order-service:12031/order/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            //调用微服务，查询该车次指定座型总数量
            requestEntity = new HttpEntity(headers);
            re2 = restTemplate.exchange(
                    "http://ts-travel-service:12346/api/v1/travelservice/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            Response trainTypeResponse = re2.getBody();


            trainTypeResult = (TrainType) trainTypeResponse.getData();
//            trainTypeResult = restTemplate.getForObject(
//                    "http://ts-travel-service:12346/travel/getTrainTypeByTripId/" + seatRequest.getTrainNumber() ,GetTrainTypeResult.class);
            System.out.println("[SeatService getLeftTicketOfInterval] The result of getTrainTypeResult is " + trainTypeResponse.getMsg());
        } else {
            System.out.println("[SeatService getLeftTicketOfInterval] TrainNumber start with other capital");
            //调用微服务，查询获得车次的所有站点信息
            HttpEntity requestEntity = new HttpEntity(headers);
            re = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/routes/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            routeResult = re.getBody();
//            routeResult = restTemplate.getForObject(
//                    "http://ts-travel2-service:16346/travel2/getRouteByTripId/" + seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService getLeftTicketOfInterval] The result of getRouteResult is " + routeResult.getMsg());

            //调用微服务，查询获得余票信息：该车次指定座型已售Ticket的set集合
            requestEntity = new HttpEntity(seatRequest, headers);
            re3 = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOthers/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
            Response leftTicketResponse = re3.getBody();
            leftTicketInfo = (LeftTicketInfo) leftTicketResponse.getData();
//            leftTicketInfo = restTemplate.postForObject(
//                    "http://ts-order-other-service:12032/orderOther/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            //调用微服务，查询该车次指定座型总数量
            requestEntity = new HttpEntity(headers);
            re2 = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            Response trainTypeResponse = re2.getBody();
            trainTypeResult = (TrainType) trainTypeResponse.getData();
//            trainTypeResult = restTemplate.getForObject(
//                    "http://ts-travel2-service:16346/travel2/getTrainTypeByTripId/" + seatRequest.getTrainNumber(), GetTrainTypeResult.class);
            System.out.println("[SeatService getLeftTicketOfInterval] The result of getTrainTypeResult is " + trainTypeResponse.getMsg());
        }

        //统计特定区间座位余票
        List<String> stationList = ((Route) routeResult.getData()).getStations();
        int seatTotalNum;
        if (seatRequest.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            seatTotalNum = trainTypeResult.getConfortClass();
            System.out.println("[SeatService getLeftTicketOfInterval] The request seat type is confortClass and the total num is " + seatTotalNum);
        } else {
            seatTotalNum = trainTypeResult.getEconomyClass();
            System.out.println("[SeatService getLeftTicketOfInterval] The request seat type is economyClass and the total num is " + seatTotalNum);
        }
        String startStation = seatRequest.getStartStation();
        Set<Ticket> soldTickets = leftTicketInfo.getSoldTickets();

        //统计已经售出去的票是否可供使用
        for (Ticket soldTicket : soldTickets) {
            String soldTicketDestStation = soldTicket.getDestStation();
            //售出的票的终点站在请求的起点之前，则可以分配出去
            if (stationList.indexOf(soldTicketDestStation) < stationList.indexOf(startStation)) {
                System.out.println("[SeatService getLeftTicketOfInterval] The previous distributed seat number is usable!" + soldTicket.getSeatNo());
                numOfLeftTicket++;
            }
        }
        //统计未售出的票

        double direstPart = getDirectProportion(headers);
        Route route = (Route) routeResult.getData();
        if (route.getStations().get(0).equals(seatRequest.getStartStation()) &&
                route.getStations().get(route.getStations().size() - 1).equals(seatRequest.getDestStation())) {
            //do nothing
        } else {
            direstPart = 1.0 - direstPart;
        }

        int unusedNum = (int) (seatTotalNum * direstPart) - soldTickets.size();
        numOfLeftTicket += unusedNum;

        return new Response<>(1, "Get Left Ticket of Internal Success", numOfLeftTicket);
    }

    private double getDirectProportion(HttpHeaders headers) {

        String configName = "DirectTicketAllocationProportion";
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-config-service:15679/api/v1/config/configs/" + configName,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response configValue = re.getBody();
//        String configValue = restTemplate.postForObject(
//                "http://ts-config-service:15679//config/query",
//                queryConfig,String.class);

        return Double.parseDouble(((Config) configValue.getData()).getValue());
    }
}