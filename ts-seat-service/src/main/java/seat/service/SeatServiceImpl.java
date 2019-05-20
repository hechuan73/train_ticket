package seat.service;

import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
        Response<Route> routeResult;

        LeftTicketInfo leftTicketInfo;
        TrainType trainTypeResult = new TrainType();
        ResponseEntity<Response<Route>> re;
        ResponseEntity<Response<TrainType>> re2;
        ResponseEntity<Response<LeftTicketInfo>> re3;

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
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
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
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });
            System.out.println("Left ticket info is :" + re3.getBody().toString());
            leftTicketInfo = re3.getBody().getData();
//            leftTicketInfo = restTemplate.postForObject(
//                    "http://ts-order-service:12031/order/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            //调用微服务，查询该车次指定座型总数量
            requestEntity = new HttpEntity(headers);
            re2 = restTemplate.exchange(
                    "http://ts-travel-service:12346/api/v1/travelservice/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();
            trainTypeResult = trainTypeResponse.getData();

//            trainTypeResult = restTemplate.getForObject(
//                    "http://ts-travel-service:12346/travel/getTrainTypeByTripId/" + seatRequest.getTrainNumber() ,GetTrainTypeResult.class);
            System.out.println("[SeatService distributeSeat 1] The result of getTrainTypeResult is " + trainTypeResponse.toString());
        } else {
            System.out.println("[SeatService] TrainNumber start with other capital");
            //调用微服务，查询获得车次的所有站点信息
            HttpEntity requestEntity = new HttpEntity(headers);
            re = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/routes/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
            routeResult = re.getBody();
//            routeResult = restTemplate.getForObject(
//                    "http://ts-travel2-service:16346/travel2/getRouteByTripId/" + seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService distributeSeat] The result of getRouteResult is " + routeResult.toString());

            //调用微服务，查询获得余票信息：该车次指定座型已售Ticket的set集合
            requestEntity = new HttpEntity(seatRequest, headers);
            re3 = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });
            System.out.println("Left ticket info is :" + re3.getBody().toString());
            leftTicketInfo = re3.getBody().getData();
//            leftTicketInfo = restTemplate.postForObject(
//                    "http://ts-order-other-service:12032/orderOther/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            //调用微服务，查询该车次指定座型总数量
            requestEntity = new HttpEntity(headers);
            re2 = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();
            trainTypeResult = trainTypeResponse.getData();
//            trainTypeResult = restTemplate.getForObject(
//                    "http://ts-travel2-service:16346/travel2/getTrainTypeByTripId/" + seatRequest.getTrainNumber(), GetTrainTypeResult.class);
            System.out.println("[SeatService distributeSeat 2] The result of getTrainTypeResult is " + trainTypeResponse.toString());
        }


        //分配座位
        List<String> stationList = routeResult.getData().getStations();
        int seatTotalNum = 0;
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

        //分配新的票
        Random rand = new Random();
        int range = seatTotalNum;
        int seat = rand.nextInt(range) + 1;

        if(leftTicketInfo != null) {
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
            while (isContained(soldTickets, seat)) {
                seat = rand.nextInt(range) + 1;
            }
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
        Response<Route> routeResult;
        TrainType trainTypeResult;
        LeftTicketInfo leftTicketInfo;

        ResponseEntity<Response<Route>> re;
        ResponseEntity<Response<TrainType>> re2;
        ResponseEntity<Response<LeftTicketInfo>> re3;

        //区分G\D开头和其它车次
        String trainNumber = seatRequest.getTrainNumber();
        System.out.println("Seat request To String: " + seatRequest.toString());
        if (trainNumber.startsWith("G") || trainNumber.startsWith("D")) {
            System.out.println("[SeatService getLeftTicketOfInterval] TrainNumber start with G|D" + trainNumber);

            //调用微服务，查询获得车次的所有站点信息
            HttpEntity requestEntity = new HttpEntity(headers);
            re = restTemplate.exchange(
                    "http://ts-travel-service:12346/api/v1/travelservice/routes/" + trainNumber,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
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
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });

            System.out.println("Get Order tickets result is : " + re3.toString());
            leftTicketInfo = re3.getBody().getData();
            //System.out.println("Left Ticket info is :  " + leftTicketInfo.toString());
//            leftTicketInfo = restTemplate.postForObject(
//                    "http://ts-order-service:12031/order/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            //调用微服务，查询该车次指定座型总数量
            requestEntity = new HttpEntity(headers);
            re2 = restTemplate.exchange(
                    "http://ts-travel-service:12346/api/v1/travelservice/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();


            trainTypeResult = trainTypeResponse.getData();
//            trainTypeResult = restTemplate.getForObject(
//                    "http://ts-travel-service:12346/travel/getTrainTypeByTripId/" + seatRequest.getTrainNumber() ,GetTrainTypeResult.class);
            System.out.println("[SeatService getLeftTicketOfInterval] The result of getTrainTypeResult is " + trainTypeResponse.toString());
        } else {
            System.out.println("[SeatService getLeftTicketOfInterval] TrainNumber start with other capital");
            //调用微服务，查询获得车次的所有站点信息
            HttpEntity requestEntity = new HttpEntity(headers);
            re = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/routes/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
            routeResult = re.getBody();
//            routeResult = restTemplate.getForObject(
//                    "http://ts-travel2-service:16346/travel2/getRouteByTripId/" + seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService getLeftTicketOfInterval] The result of getRouteResult is " + routeResult.toString());

            //调用微服务，查询获得余票信息：该车次指定座型已售Ticket的set集合
            requestEntity = new HttpEntity(seatRequest, headers);
            re3 = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });
            System.out.println("Get Order tickets result is : " + re3.toString());
            leftTicketInfo = re3.getBody().getData();

//            leftTicketInfo = restTemplate.postForObject(
//                    "http://ts-order-other-service:12032/orderOther/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            //调用微服务，查询该车次指定座型总数量
            requestEntity = new HttpEntity(headers);
            re2 = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();
            trainTypeResult = trainTypeResponse.getData();
//            trainTypeResult = restTemplate.getForObject(
//                    "http://ts-travel2-service:16346/travel2/getTrainTypeByTripId/" + seatRequest.getTrainNumber(), GetTrainTypeResult.class);
            System.out.println("[SeatService getLeftTicketOfInterval] The result of getTrainTypeResult is " + trainTypeResponse.toString());
        }

        //统计特定区间座位余票
        List<String> stationList = routeResult.getData().getStations();
        int seatTotalNum;
        if (seatRequest.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            seatTotalNum = trainTypeResult.getConfortClass();
            System.out.println("[SeatService getLeftTicketOfInterval] The request seat type is confortClass and the total num is " + seatTotalNum);
        } else {
            seatTotalNum = trainTypeResult.getEconomyClass();
            System.out.println("[SeatService getLeftTicketOfInterval] The request seat type is economyClass and the total num is " + seatTotalNum);
        }

        int solidTicketSize = 0;
        if (leftTicketInfo != null) {
            String startStation = seatRequest.getStartStation();
            Set<Ticket> soldTickets = leftTicketInfo.getSoldTickets();
            solidTicketSize = soldTickets.size();
            //统计已经售出去的票是否可供使用
            for (Ticket soldTicket : soldTickets) {
                String soldTicketDestStation = soldTicket.getDestStation();
                //售出的票的终点站在请求的起点之前，则可以分配出去
                if (stationList.indexOf(soldTicketDestStation) < stationList.indexOf(startStation)) {
                    System.out.println("[SeatService getLeftTicketOfInterval] The previous distributed seat number is usable!" + soldTicket.getSeatNo());
                    numOfLeftTicket++;
                }
            }
        }
        //统计未售出的票

        double direstPart = getDirectProportion(headers);
        Route route = routeResult.getData();
        if (route.getStations().get(0).equals(seatRequest.getStartStation()) &&
                route.getStations().get(route.getStations().size() - 1).equals(seatRequest.getDestStation())) {
            //do nothing
        } else {
            direstPart = 1.0 - direstPart;
        }

        int unusedNum = (int) (seatTotalNum * direstPart) - solidTicketSize;
        numOfLeftTicket += unusedNum;

        return new Response<>(1, "Get Left Ticket of Internal Success", numOfLeftTicket);
    }

    private double getDirectProportion(HttpHeaders headers) {

        String configName = "DirectTicketAllocationProportion";
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<Config>> re = restTemplate.exchange(
                "http://ts-config-service:15679/api/v1/configservice/configs/" + configName,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Config>>() {
                });
        Response<Config> configValue = re.getBody();
//        String configValue = restTemplate.postForObject(
//                "http://ts-config-service:15679//config/query",
//                queryConfig,String.class);
        System.out.println("Configs is :" + configValue.getData().toString());
        return Double.parseDouble(configValue.getData().getValue());
    }
}