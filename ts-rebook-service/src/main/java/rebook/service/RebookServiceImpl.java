package rebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rebook.domain.*;
import rebook.domain.RebookInfo;
import rebook.domain.RebookResult;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Service
public class RebookServiceImpl implements RebookService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public RebookResult rebook(RebookInfo info, String loginId, String loginToken){
        RebookResult rebookResult = new RebookResult();

        QueryOrderResult queryOrderResult = getOrderByRebookInfo(info);

        if(!queryOrderResult.isStatus()){
            rebookResult.setStatus(false);
            rebookResult.setMessage(queryOrderResult.getMessage());
            rebookResult.setOrder(null);
            return rebookResult;
        }

        Order order = queryOrderResult.getOrder();
        int status = order.getStatus();
        if(status == OrderStatus.NOTPAID.getCode()){
            rebookResult.setStatus(false);
            rebookResult.setMessage("You haven't paid the original ticket!");
            rebookResult.setOrder(null);
            return rebookResult;
        }else if(status == OrderStatus.PAID.getCode()){
            // do nothing
        }else if(status == OrderStatus.CHANGE.getCode()){
            rebookResult.setStatus(false);
            rebookResult.setMessage("You have already changed your ticket and you can only change one time.");
            rebookResult.setOrder(null);
            return rebookResult;
        }else if(status == OrderStatus.COLLECTED.getCode()){
            rebookResult.setStatus(false);
            rebookResult.setMessage("You have already collected your ticket and you can change it now.");
            rebookResult.setOrder(null);
            return rebookResult;
        } else{
            rebookResult.setStatus(false);
            rebookResult.setMessage("You can't change your ticket.");
            rebookResult.setOrder(null);
            return rebookResult;
        }


        if(!checkTime(order.getTravelDate(),order.getTravelTime())){
            rebookResult.setStatus(false);
            rebookResult.setMessage("You can only change the ticket before the train start or within 2 hours after the train start.");
            rebookResult.setOrder(null);
            return rebookResult;
        }


        GetTripAllDetailInfo gtdi = new GetTripAllDetailInfo();
        gtdi.setFrom(queryForStationName(order.getFrom()));
        gtdi.setTo(queryForStationName(order.getTo()));
        gtdi.setTravelDate(info.getDate());
        gtdi.setTripId(info.getTripId());
        GetTripAllDetailResult gtdr = getTripAllDetailInformation(gtdi,info.getTripId());
        if(gtdr.isStatus() == false){
            rebookResult.setStatus(false);
            rebookResult.setMessage(gtdr.getMessage());
            rebookResult.setOrder(null);
            return rebookResult;
        }else{
            TripResponse tripResponse = gtdr.getTripResponse();
            if(info.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
                if(tripResponse.getConfortClass() <= 0){
                    rebookResult.setStatus(false);
                    rebookResult.setMessage("Seat Not Enough");
                    rebookResult.setOrder(null);
                    return rebookResult;
                }
            }else{
                if(tripResponse.getEconomyClass() == SeatClass.SECONDCLASS.getCode()){
                    if(tripResponse.getConfortClass() <= 0){
                        rebookResult.setStatus(false);
                        rebookResult.setMessage("Seat Not Enough");
                        rebookResult.setOrder(null);
                    }
                }
            }
        }
        Trip trip = gtdr.getTrip();


//        QueryPriceInfo queryPriceInfo = new QueryPriceInfo();
//        queryPriceInfo.setStartingPlaceId(order.getFrom());
//        queryPriceInfo.setEndPlaceId(order.getTo());
//
//        if(info.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
//            queryPriceInfo.setSeatClass("confortClass");
//        }else if(info.getSeatType() == SeatClass.SECONDCLASS.getCode()) {
//            queryPriceInfo.setSeatClass("economyClass");
//        }
//        queryPriceInfo.setTrainTypeId(trip.getTrainTypeId());
//
//        String ticketPrice = getPrice(queryPriceInfo);

        String ticketPrice =  "0";
        if(info.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
            ticketPrice = gtdr.getTripResponse().getPriceForConfortClass();
        }else if(info.getSeatType() == SeatClass.SECONDCLASS.getCode()) {
            ticketPrice = gtdr.getTripResponse().getPriceForEconomyClass();
        }
        String oldPrice = order.getPrice();
        BigDecimal priceOld = new BigDecimal(oldPrice);
        BigDecimal priceNew = new BigDecimal(ticketPrice);
        if(priceOld.compareTo(priceNew) > 0){
            //退差价
            String difference = priceOld.subtract(priceNew).toString();
            if(!drawBackMoney(loginId,difference)){
                rebookResult.setStatus(false);
                rebookResult.setMessage("Can't draw back the difference money, please try again!");
                rebookResult.setOrder(null);
                return rebookResult;
            }
            return updateOrder(order,info,gtdr,ticketPrice,loginId,loginToken);

        }else if(priceOld.compareTo(priceNew) == 0){
            //do nothing
            return updateOrder(order,info,gtdr,ticketPrice,loginId,loginToken);
        }else{

            String difference = priceNew.subtract(priceOld).toString();
            rebookResult.setStatus(false);
            rebookResult.setMessage("Please pay the different money!");
            rebookResult.setOrder(null);
            rebookResult.setPrice(difference);
            return rebookResult;
        }
    }

    @Override
    public RebookResult payDifference(RebookInfo info, String loginId, String loginToken){
        RebookResult rebookResult = new RebookResult();

        QueryOrderResult queryOrderResult = getOrderByRebookInfo(info);
//        if(info.getOldTripId().startsWith("G") || info.getOldTripId().startsWith("D")){
//            queryOrderResult = restTemplate.postForObject(
//                    "http://ts-order-service:12031/order/getById", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
//        }else{
//            queryOrderResult = restTemplate.postForObject(
//                    "http://ts-order-other-service:12032/orderOther/getById", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
//        }

        if(!queryOrderResult.isStatus()){
            rebookResult.setStatus(false);
            rebookResult.setMessage(queryOrderResult.getMessage());
            rebookResult.setOrder(null);
            return rebookResult;
        }

        Order order = queryOrderResult.getOrder();

        GetTripAllDetailInfo gtdi = new GetTripAllDetailInfo();
        gtdi.setFrom(queryForStationName(order.getFrom()));
        gtdi.setTo(queryForStationName(order.getTo()));
        gtdi.setTravelDate(info.getDate());
        gtdi.setTripId(info.getTripId());
        GetTripAllDetailResult gtdr = getTripAllDetailInformation(gtdi,info.getTripId());

//        QueryPriceInfo queryPriceInfo = new QueryPriceInfo();
//        queryPriceInfo.setStartingPlaceId(order.getFrom());
//        queryPriceInfo.setEndPlaceId(order.getTo());
//        if(info.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
//            queryPriceInfo.setSeatClass("confortClass");
//        }else if(info.getSeatType() == SeatClass.SECONDCLASS.getCode()) {
//            queryPriceInfo.setSeatClass("economyClass");
//        }
//        Trip trip = gtdr.getTrip();
//        queryPriceInfo.setTrainTypeId(trip.getTrainTypeId());

        //String ticketPrice = getPrice(queryPriceInfo);
        String ticketPrice =  "0";
        if(info.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
            ticketPrice = gtdr.getTripResponse().getPriceForConfortClass();
        }else if(info.getSeatType() == SeatClass.SECONDCLASS.getCode()) {
            ticketPrice = gtdr.getTripResponse().getPriceForEconomyClass();
        }
        String oldPrice = order.getPrice();
        BigDecimal priceOld = new BigDecimal(oldPrice);
        BigDecimal priceNew = new BigDecimal(ticketPrice);

        if(payDifferentMoney(info.getOrderId(),info.getTripId(),loginId,priceNew.subtract(priceOld).toString())){
            return updateOrder(order,info,gtdr,ticketPrice,loginId,loginToken);
        }else{
            rebookResult.setStatus(false);
            rebookResult.setMessage("Can't pay the difference,please try again");
            rebookResult.setOrder(null);
            return rebookResult;
        }
    }

    private RebookResult updateOrder(Order order, RebookInfo info, GetTripAllDetailResult gtdr,String ticketPrice, String loginId, String loginToken){
        RebookResult rebookResult = new RebookResult();
        Trip trip = gtdr.getTrip();
        String oldTripId = order.getTrainNumber();
        order.setTrainNumber(info.getTripId());
        order.setBoughtDate(new Date());
        order.setStatus(OrderStatus.CHANGE.getCode());
        order.setPrice(ticketPrice);//Set ticket price
        order.setSeatClass(info.getSeatType());
        order.setTravelDate(info.getDate());
        order.setTravelTime(trip.getStartingTime());

        if(info.getSeatType() == SeatClass.FIRSTCLASS.getCode()){//Dispatch the seat
            Ticket ticket =
                    dipatchSeat(info.getDate(),
                            order.getTrainNumber(),order.getFrom(),order.getTo(),
                            SeatClass.FIRSTCLASS.getCode());
            order.setSeatClass(SeatClass.FIRSTCLASS.getCode());
            order.setSeatNumber("" + ticket.getSeatNo());
        }else{
            Ticket ticket =
                    dipatchSeat(info.getDate(),
                            order.getTrainNumber(),order.getFrom(),order.getTo(),
                            SeatClass.SECONDCLASS.getCode());
            order.setSeatClass(SeatClass.SECONDCLASS.getCode());
            order.setSeatNumber("" + ticket.getSeatNo());
        }

//        if(info.getSeatType() == SeatClass.FIRSTCLASS.getCode()){//Dispatch the seat
//            int firstClassRemainNum = gtdr.getTripResponse().getConfortClass();
//            order.setSeatNumber("FirstClass-" + firstClassRemainNum);
//        }else{
//            int secondClassRemainNum = gtdr.getTripResponse().getEconomyClass();
//            order.setSeatNumber("SecondClass-" + secondClassRemainNum);
//        }


        if((tripGD(oldTripId) && tripGD(info.getTripId())) || (!tripGD(oldTripId) && !tripGD(info.getTripId()))){
            ChangeOrderInfo changeOrderInfo = new ChangeOrderInfo();
            changeOrderInfo.setLoginToken(loginToken);
            changeOrderInfo.setOrder(order);
            ChangeOrderResult changeOrderResult = updateOrder(changeOrderInfo,info.getTripId());
            if(changeOrderResult.isStatus()){
                rebookResult.setStatus(true);
                rebookResult.setMessage("Success!");
                rebookResult.setOrder(order);
                return rebookResult;
            }else{
                rebookResult.setStatus(false);
                rebookResult.setMessage("Can't update Order!");
                rebookResult.setOrder(null);
                return rebookResult;
            }
        }else{

            deleteOrder(order.getId().toString(), oldTripId);

            createOrder(order,loginToken,order.getTrainNumber());
            rebookResult.setStatus(true);
            rebookResult.setMessage("Success!");
            rebookResult.setOrder(order);
            return rebookResult;
        }
    }

    public Ticket dipatchSeat(Date date,String tripId,String startStationId,String endStataionId,int seatType){
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setTravelDate(date);
        seatRequest.setTrainNumber(tripId);
        seatRequest.setStartStation(startStationId);
        seatRequest.setDestStation(endStataionId);
        seatRequest.setSeatType(seatType);
        Ticket ticket = restTemplate.postForObject(
                "http://ts-seat-service:18898/seat/getSeat"
                ,seatRequest,Ticket.class);
        return ticket;
    }

    private boolean tripGD(String tripId){
        if(tripId.startsWith("G") || tripId.startsWith("D")){
            return true;
        }else{
            return false;
        }
    }

    private VerifyResult verifySsoLogin(String loginToken){
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }

    private boolean checkTime(Date travelDate, Date travelTime) {
        boolean result = true;
        Calendar calDateA = Calendar.getInstance();
        Date today = new Date();
        calDateA.setTime(today);
        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(travelDate);
        Calendar calDateC = Calendar.getInstance();
        calDateC.setTime(travelTime);
        if(calDateA.get(Calendar.YEAR) > calDateB.get(Calendar.YEAR)){
            result = false;
        }else if(calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)){
            if(calDateA.get(Calendar.MONTH) > calDateB.get(Calendar.MONTH)){
                result = false;
            }else if(calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)){
                if(calDateA.get(Calendar.DAY_OF_MONTH) > calDateB.get(Calendar.DAY_OF_MONTH)){
                    result = false;
                }else if(calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH)){
                    if(calDateA.get(Calendar.HOUR_OF_DAY) > calDateC.get(Calendar.HOUR_OF_DAY)+2){
                        result = false;
                    }else if(calDateA.get(Calendar.HOUR_OF_DAY) == calDateC.get(Calendar.HOUR_OF_DAY)+2){
                        if(calDateA.get(Calendar.MINUTE) > calDateC.get(Calendar.MINUTE)){
                            result = false;
                        }
                    }
                }
            }
        }
        return result;
    }

    private GetTripAllDetailResult getTripAllDetailInformation(GetTripAllDetailInfo gtdi, String tripId){
        GetTripAllDetailResult gtdr;
        if(tripId.startsWith("G") || tripId.startsWith("D")){
            gtdr = restTemplate.postForObject(
                    "http://ts-travel-service:12346/travel/getTripAllDetailInfo"
                    ,gtdi,GetTripAllDetailResult.class);
        }else{
            gtdr = restTemplate.postForObject(
                    "http://ts-travel2-service:16346/travel2/getTripAllDetailInfo"
                    ,gtdi,GetTripAllDetailResult.class);
        }
        return gtdr;
    }

    private CreateOrderResult createOrder(Order order, String loginToken, String tripId){
        CreateOrderInfo createOrderInfo = new CreateOrderInfo();
        createOrderInfo.setOrder(order);
        createOrderInfo.setLoginToken(loginToken);
        CreateOrderResult createOrderResult;
        if(tripId.startsWith("G") || tripId.startsWith("D")){
            createOrderResult = restTemplate.postForObject(
                    "http://ts-order-service:12031/order/create"
                    ,createOrderInfo,CreateOrderResult.class);
        }else{
            createOrderResult = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/create"
                    ,createOrderInfo,CreateOrderResult.class);
        }
        return createOrderResult;
    }

    private ChangeOrderResult updateOrder(ChangeOrderInfo info, String tripId){
        ChangeOrderResult result;
        if(tripGD(tripId)){
            result = restTemplate.postForObject("http://ts-order-service:12031/order/update",
                    info,ChangeOrderResult.class);
        }else{
            result = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",
                    info,ChangeOrderResult.class);
        }
        return result;
    }

    private DeleteOrderResult deleteOrder(String orderId, String tripId){
        DeleteOrderInfo deleteOrderInfo = new DeleteOrderInfo();
        deleteOrderInfo.setOrderId(orderId);
        DeleteOrderResult deleteOrderResult;
        if(tripGD(tripId)){
            deleteOrderResult = restTemplate.postForObject("http://ts-order-service:12031/order/delete",
                    deleteOrderInfo,DeleteOrderResult.class);
        }else{
            deleteOrderResult = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/delete",
                    deleteOrderInfo,DeleteOrderResult.class);
        }
        return deleteOrderResult;
    }

    private QueryOrderResult getOrderByRebookInfo(RebookInfo info){
        QueryOrderResult queryOrderResult;

        if(info.getOldTripId().startsWith("G") || info.getOldTripId().startsWith("D")){
            queryOrderResult = restTemplate.postForObject(
                    "http://ts-order-service:12031/order/getById", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }else{
            queryOrderResult = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/getById", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }
        return queryOrderResult;
    }

    private String queryForStationName(String stationId){
        QueryById query = new QueryById();
        query.setStationId(stationId);
        QueryStation station = restTemplate.postForObject(
                "http://ts-station-service:12345/station/queryById"
                ,query,QueryStation.class);
        return station.getName();
    }

    private boolean payDifferentMoney(String orderId, String tripId, String userId, String money){
        PaymentDifferenceInfo info = new PaymentDifferenceInfo();
        info.setOrderId(orderId);
        info.setTripId(tripId);
        info.setUserId(userId);
        info.setPrice(money);
        boolean result = restTemplate.postForObject(
                "http://ts-inside-payment-service:18673/inside_payment/payDifference"
                ,info,Boolean.class);
        return result;
    }

    private boolean drawBackMoney(String userId,String money){
        DrawBackInfo info = new DrawBackInfo();
        info.setUserId(userId);
        info.setMoney(money);
        boolean result = restTemplate.postForObject(
                "http://ts-inside-payment-service:18673/inside_payment/drawBack"
                ,info,Boolean.class);
        return result;
    }

}
