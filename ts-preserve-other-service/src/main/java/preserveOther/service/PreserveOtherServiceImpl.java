package preserveOther.service;

import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import preserveOther.entity.*;

import java.util.Date;
import java.util.UUID;

@Service
public class PreserveOtherServiceImpl implements PreserveOtherService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response preserve(OrderTicketsInfo oti, HttpHeaders httpHeaders) {

        System.out.println("[Preserve Other Service][Verify Login] Success");
        //1.黄牛检测
        System.out.println("[Preserve Service] [Step 1] Check Security");

        Response result = checkSecurity(oti.getAccountId(), httpHeaders);

        if ("0".equals(result.getStatus())) {
            System.out.println("[Preserve Service] [Step 1] Check Security Fail. Return soon.");
            return new Response<>(0, result.getMsg(), null);
        }
        System.out.println("[Preserve Service] [Step 1] Check Security Complete. ");
        //2.查询联系人信息 -- 修改，通过基础信息微服务作为中介
        System.out.println("[Preserve Other Service] [Step 2] Find contacts");

        System.out.println("[Preserve Other Service] [Step 2] Contacts Id:" + oti.getContactsId());

        Response gcr = getContactsById(oti.getContactsId(), httpHeaders);
        if ("0".equals(gcr.getStatus())) {
            System.out.println("[Preserve Service][Get Contacts] Fail." + gcr.getMsg());
            return new Response<>(0, gcr.getMsg(), null);
        }

        System.out.println("[Preserve Other Service][Step 2] Complete");
        //3.查询座位余票信息和车次的详情
        System.out.println("[Preserve Other Service] [Step 3] Check tickets num");
        TripAllDetailInfo gtdi = new TripAllDetailInfo();

        gtdi.setFrom(oti.getFrom());
        gtdi.setTo(oti.getTo());

        gtdi.setTravelDate(oti.getDate());
        gtdi.setTripId(oti.getTripId());
        System.out.println("[Preserve Other Service] [Step 3] TripId:" + oti.getTripId());
        Response response = getTripAllDetailInformation(gtdi, httpHeaders);
        TripAllDetail gtdr = (TripAllDetail) response.getData();
        if ("0".equals(response.getStatus())) {
            System.out.println("[Preserve Service][Search For Trip Detail Information] " + response.getMsg());
            return new Response<>(0, response.getMsg(), null);
        } else {
            TripResponse tripResponse = gtdr.getTripResponse();
            if (oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
                if (tripResponse.getConfortClass() == 0) {
                    System.out.println("[Preserve Service][Check seat is enough] ");
                    return new Response<>(0, "Seat Not Enough", null);
                }
            } else {
                if (tripResponse.getEconomyClass() == SeatClass.SECONDCLASS.getCode()) {
                    if (tripResponse.getConfortClass() == 0) {
                        System.out.println("[Preserve Service][Check seat is Not enough] ");
                        return new Response<>(0, "Check Seat Not Enough", null);
                    }
                }
            }
        }
        Trip trip = gtdr.getTrip();
        System.out.println("[Preserve Other Service] [Step 3] Tickets Enough");
        //4.下达订单请求 设置order的各个信息
        System.out.println("[Preserve Other Service] [Step 4] Do Order");
        Contacts contacts = (Contacts) gcr.getData();
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setTrainNumber(oti.getTripId());
        order.setAccountId(UUID.fromString(oti.getAccountId()));

        String fromStationId = queryForStationId(oti.getFrom(), httpHeaders);
        String toStationId = queryForStationId(oti.getTo(), httpHeaders);

        order.setFrom(fromStationId);
        order.setTo(toStationId);
        order.setBoughtDate(new Date());
        order.setStatus(OrderStatus.NOTPAID.getCode());
        order.setContactsDocumentNumber(contacts.getDocumentNumber());
        order.setContactsName(contacts.getName());
        order.setDocumentType(contacts.getDocumentType());


        Travel query = new Travel();
        query.setTrip(trip);
        query.setStartingPlace(oti.getFrom());
        query.setEndPlace(oti.getTo());
        query.setDepartureTime(new Date());


        HttpEntity requestEntity = new HttpEntity(query, httpHeaders);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-ticketinfo-service:15681/api/v1/ticketinfoservice/ticketinfo",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        TravelResult resultForTravel = (TravelResult) re.getBody().getData();
//            TravelResult resultForTravel = restTemplate.postForObject(
//                    "http://ts-ticketinfo-service:15681/ticketinfo/queryForTravel", query ,TravelResult.class);

        order.setSeatClass(oti.getSeatType());
        System.out.println("[Preserve Other Service][Order] Order Travel Date:" + oti.getDate().toString());
        order.setTravelDate(oti.getDate());
        order.setTravelTime(gtdr.getTripResponse().getStartingTime());

        if (oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {//Dispatch the seat
            Ticket ticket =
                    dipatchSeat(oti.getDate(),
                            order.getTrainNumber(), fromStationId, toStationId,
                            SeatClass.FIRSTCLASS.getCode(), httpHeaders);
            order.setSeatClass(SeatClass.FIRSTCLASS.getCode());
            order.setSeatNumber("" + ticket.getSeatNo());
//                int firstClassRemainNum = gtdr.getTripResponse().getConfortClass();
//                order.setSeatNumber("FirstClass-" + firstClassRemainNum);
            order.setPrice(resultForTravel.getPrices().get("confortClass"));
        } else {
            Ticket ticket =
                    dipatchSeat(oti.getDate(),
                            order.getTrainNumber(), fromStationId, toStationId,
                            SeatClass.SECONDCLASS.getCode(), httpHeaders);
            order.setSeatClass(SeatClass.SECONDCLASS.getCode());
            order.setSeatNumber("" + ticket.getSeatNo());
//                int secondClassRemainNum = gtdr.getTripResponse().getEconomyClass();
//                order.setSeatNumber("SecondClass-" + secondClassRemainNum);
            order.setPrice(resultForTravel.getPrices().get("economyClass"));
        }
        System.out.println("[Preserve Other Service][Order Price] Price is: " + order.getPrice());

        Response cor = createOrder(order, httpHeaders);
        if ("0".equals(cor.getStatus())) {
            System.out.println("[Preserve Other Service][Create Order Fail] Create Order Fail." +
                    "Reason:" + cor.getMsg());
            return new Response<>(0, cor.getMsg(), null);
        }

        System.out.println("[Preserve Other Service] [Step 4] Do Order Complete");
        Response returnResponse = new Response<>(1, "Success.", cor.getMsg());
        //5.检查保险的选择
        if (oti.getAssurance() == 0) {
            System.out.println("[Preserve Service][Step 5] Do not need to buy assurance");
        } else {
            Response addAssuranceResult = addAssuranceForOrder(
                    oti.getAssurance(), ((Order) cor.getData()).getId().toString(), httpHeaders);
            if ("1".equals(addAssuranceResult.getStatus())) {
                System.out.println("[Preserve Service][Step 5] Preserve Buy Assurance Success");
            } else {
                System.out.println("[Preserve Service][Step 5] Buy Assurance Fail.");
                returnResponse.setMsg("Success.But Buy Assurance Fail.");
            }
        }

        //6.增加订餐
        if (oti.getFoodType() != 0) {
            FoodOrder foodOrder = new FoodOrder();
            foodOrder.setOrderId(((Order) cor.getData()).getId());
            foodOrder.setFoodType(oti.getFoodType());
            foodOrder.setFoodName(oti.getFoodName());
            foodOrder.setPrice(oti.getFoodPrice());
            if (oti.getFoodType() == 2) {
                foodOrder.setStationName(oti.getStationName());
                foodOrder.setStoreName(oti.getStoreName());
            }
            Response afor = createFoodOrder(foodOrder, httpHeaders);
            if ("1".equals(afor.getStatus())) {
                System.out.println("[Preserve Service][Step 6] Buy Food Success");
            } else {
                System.out.println("[Preserve Service][Step 6] Buy Food Fail.");
                returnResponse.setMsg("Success.But Buy Food Fail.");
            }
        } else {
            System.out.println("[Preserve Service][Step 6] Do not need to buy food");
        }

        //7.增加托运
        if (null != oti.getConsigneeName() && !"".equals(oti.getConsigneeName())) {
            Consign consignRequest = new Consign();
            consignRequest.setAccountId(((Order) cor.getData()).getAccountId());
            consignRequest.setHandleDate(oti.getHandleDate());
            consignRequest.setTargetDate(((Order) cor.getData()).getTravelDate().toString());
            consignRequest.setFrom(((Order) cor.getData()).getFrom());
            consignRequest.setTo(((Order) cor.getData()).getTo());
            consignRequest.setConsignee(oti.getConsigneeName());
            consignRequest.setPhone(oti.getConsigneePhone());
            consignRequest.setWeight(oti.getConsigneeWeight());
            consignRequest.setWithin(oti.isWithin());
            Response icresult = createConsign(consignRequest, httpHeaders);
            if ("1".equals(icresult.getStatus())) {
                System.out.println("[Preserve Service][Step 7] Consign Success");
            } else {
                System.out.println("[Preserve Service][Step 7] Preserve Consign Fail.");
                returnResponse.setMsg("Consign Fail.");
            }
        } else {
            System.out.println("[Preserve Service][Step 7] Do not need to consign");
        }

        //8.发送notification
        System.out.println("[Preserve Service]");
        GetAccountByIdInfo getAccountByIdInfo = new GetAccountByIdInfo();
        getAccountByIdInfo.setAccountId(order.getAccountId().toString());
        GetAccountByIdResult getAccountByIdResult = getAccount(getAccountByIdInfo, httpHeaders);

        NotifyInfo notifyInfo = new NotifyInfo();
        notifyInfo.setDate(new Date().toString());

        notifyInfo.setEmail(getAccountByIdResult.getAccount().getEmail());
        notifyInfo.setStartingPlace(order.getFrom());
        notifyInfo.setEndPlace(order.getTo());
        notifyInfo.setUsername(getAccountByIdResult.getAccount().getName());
        notifyInfo.setSeatNumber(order.getSeatNumber());
        notifyInfo.setOrderNumber(order.getId().toString());
        notifyInfo.setPrice(order.getPrice());
        notifyInfo.setSeatClass(SeatClass.getNameByCode(order.getSeatClass()));
        notifyInfo.setStartingTime(order.getTravelTime().toString());

        sendEmail(notifyInfo, httpHeaders);

        return returnResponse;
    }

    public Ticket dipatchSeat(Date date, String tripId, String startStationId, String endStataionId, int seatType, HttpHeaders httpHeaders) {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setTravelDate(date);
        seatRequest.setTrainNumber(tripId);
        seatRequest.setStartStation(startStationId);
        seatRequest.setSeatType(seatType);
        seatRequest.setDestStation(endStataionId);

        HttpEntity requestEntityTicket = new HttpEntity(seatRequest, httpHeaders);
        ResponseEntity<Ticket> reTicket = restTemplate.exchange(
                "http://ts-seat-service:18898/seat/getSeat",
                HttpMethod.POST,
                requestEntityTicket,
                Ticket.class);
        Ticket ticket = reTicket.getBody();

//        Ticket ticket = restTemplate.postForObject(
//                "http://ts-seat-service:18898/seat/getSeat"
//                ,seatRequest,Ticket.class);
        return ticket;
    }

    public boolean sendEmail(NotifyInfo notifyInfo, HttpHeaders httpHeaders) {
        System.out.println("[Preserve Service][Send Email]");

        HttpEntity requestEntitySendEmail = new HttpEntity(notifyInfo, httpHeaders);
        ResponseEntity<Boolean> reSendEmail = restTemplate.exchange(
                "http://ts-notification-service:17853/notification/order_cancel_success",
                HttpMethod.POST,
                requestEntitySendEmail,
                Boolean.class);
        boolean result = reSendEmail.getBody();
//        boolean result = restTemplate.postForObject(
//                "http://ts-notification-service:17853/notification/order_cancel_success",
//                notifyInfo,
//                Boolean.class
//        );
        return result;
    }

    public GetAccountByIdResult getAccount(GetAccountByIdInfo info, HttpHeaders httpHeaders) {
        System.out.println("[Cancel Order Service][Get Account By Id]");

        HttpEntity requestEntitySendEmail = new HttpEntity(info, httpHeaders);
        ResponseEntity<GetAccountByIdResult> reSendEmail = restTemplate.exchange(
                "http://ts-sso-service:12349/account/findById",
                HttpMethod.POST,
                requestEntitySendEmail,
                GetAccountByIdResult.class);
        GetAccountByIdResult result = reSendEmail.getBody();
//        GetAccountByIdResult result = restTemplate.postForObject(
//                "http://ts-sso-service:12349/account/findById",
//                info,
//                GetAccountByIdResult.class
//        );
        return result;
    }

    private Response addAssuranceForOrder(int assuranceType, String orderId, HttpHeaders httpHeaders) {
        System.out.println("[Preserve Service][Add Assurance Type For Order]");
        HttpEntity requestAddAssuranceResult = new HttpEntity(httpHeaders);
        ResponseEntity<Response> reAddAssuranceResult = restTemplate.exchange(
                "http://ts-assurance-service:18888/api/v1/assuranceservice/assurances/" + assuranceType + "/" + orderId,
                HttpMethod.GET,
                requestAddAssuranceResult,
                Response.class);
        Response result = reAddAssuranceResult.getBody();
//        AddAssuranceResult result = restTemplate.postForObject(
//                "http://ts-assurance-service:18888/assurance/create",
//                info,
//                AddAssuranceResult.class
//        );
        return result;
    }


    private String queryForStationId(String stationName, HttpHeaders httpHeaders) {
        System.out.println("[Preserve Other Service][Get Station By  Name]");


        HttpEntity requestQueryForStationId = new HttpEntity(httpHeaders);
        ResponseEntity<Response> reQueryForStationId = restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations/id/" + stationName,
                HttpMethod.GET,
                requestQueryForStationId,
                Response.class);
        String stationId = (String) reQueryForStationId.getBody().getData();
//        String stationId = restTemplate.postForObject(
//                "http://ts-station-service:12345/station/queryForId",queryForId,String.class);
        return stationId;
    }

    private Response checkSecurity(String accountId, HttpHeaders httpHeaders) {
        System.out.println("[Preserve Other Service][Check Account Security] Checking....");

        HttpEntity requestCheckResult = new HttpEntity(httpHeaders);
        ResponseEntity<Response> reCheckResult = restTemplate.exchange(
                "http://ts-security-service:11188/api/v1/securityservice/securityConfigs/" + accountId,
                HttpMethod.POST,
                requestCheckResult,
                Response.class);
        Response response = reCheckResult.getBody();
//        CheckResult result = restTemplate.postForObject("http://ts-security-service:11188/security/check",
//                info,CheckResult.class);
        return response;
    }

//    private VerifyResult verifySsoLogin(String loginToken, HttpHeaders httpHeaders) {
//        System.out.println("[Preserve Other Service][Verify Login] Verifying....");
//
//        HttpEntity requestCheckResult = new HttpEntity(null, httpHeaders);
//        ResponseEntity<VerifyResult> reCheckResult = restTemplate.exchange(
//                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
//                HttpMethod.GET,
//                requestCheckResult,
//                VerifyResult.class);
//        VerifyResult tokenResult = reCheckResult.getBody();
////        VerifyResult tokenResult = restTemplate.getForObject(
////                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
////                VerifyResult.class);
//        return tokenResult;
//    }

    private Response getTripAllDetailInformation(TripAllDetailInfo gtdi, HttpHeaders httpHeaders) {
        System.out.println("[Preserve Other Service][Get Trip All Detail Information] Getting....");

        HttpEntity requestGetTripAllDetailResult = new HttpEntity(gtdi, httpHeaders);
        ResponseEntity<Response> reGetTripAllDetailResult = restTemplate.exchange(
                "http://ts-travel2-service:16346/api/v1/travel2service/trip_detail",
                HttpMethod.POST,
                requestGetTripAllDetailResult,
                Response.class);
        Response gtdr = reGetTripAllDetailResult.getBody();
//        TripAllDetail gtdr = restTemplate.postForObject(
//                "http://ts-travel2-service:16346/travel2/getTripAllDetailInfo/"
//                ,gtdi,TripAllDetail.class);
        return gtdr;
    }

    private Response getContactsById(String contactsId, HttpHeaders httpHeaders) {
        System.out.println("[Preserve Other Service][Get Contacts By Id is] Getting....");

        HttpEntity requestGetContactsResult = new HttpEntity(httpHeaders);
        ResponseEntity<Response> reGetContactsResult = restTemplate.exchange(
                "http://ts-contacts-service:12347/api/v1/contactservice/contacts/" + contactsId,
                HttpMethod.GET,
                requestGetContactsResult,
                Response.class);
        Response gcr = reGetContactsResult.getBody();
//        GetContactsResult gcr = restTemplate.postForObject(
//                "http://ts-contacts-service:12347/contacts/getContactsById/"
//                ,gci,GetContactsResult.class);
        return gcr;
    }

    private Response createOrder(Order coi, HttpHeaders httpHeaders) {
        System.out.println("[Preserve Other Service][Get Contacts By Id] Creating....");

        HttpEntity requestEntityCreateOrderResult = new HttpEntity(coi, httpHeaders);
        ResponseEntity<Response> reCreateOrderResult = restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOthers",
                HttpMethod.POST,
                requestEntityCreateOrderResult,
                Response.class);
        Response cor = reCreateOrderResult.getBody();

//        CreateOrderResult cor = restTemplate.postForObject(
//                "http://ts-order-other-service:12032/orderOther/create"
//                ,coi,CreateOrderResult.class);
        return cor;
    }

    private Response createFoodOrder(FoodOrder afi, HttpHeaders httpHeaders) {
        System.out.println("[Preserve Service][Add Preserve food Order] Creating....");

        HttpEntity requestEntityAddFoodOrderResult = new HttpEntity(afi, httpHeaders);
        ResponseEntity<Response> reAddFoodOrderResult = restTemplate.exchange(
                "http://ts-food-service:18856/api/v1/foodservice/orders",
                HttpMethod.POST,
                requestEntityAddFoodOrderResult,
                Response.class);
        Response afr = reAddFoodOrderResult.getBody();
//        AddFoodOrderResult afr = restTemplate.postForObject(
//                "http://ts-food-service:18856/food/createFoodOrder"
//                ,afi,AddFoodOrderResult.class);
        return afr;
    }

    private Response createConsign(Consign cr, HttpHeaders httpHeaders) {
        System.out.println("[Preserve Service][Add Condign] Creating Consign...");

        HttpEntity requestEntityResultForTravel = new HttpEntity(cr, httpHeaders);
        ResponseEntity<Response> reResultForTravel = restTemplate.exchange(
                "http://ts-consign-service:16111/api/v1/consignservice/consigns",
                HttpMethod.POST,
                requestEntityResultForTravel,
                Response.class);
        Response icr = reResultForTravel.getBody();
//        InsertConsignRecordResult icr = restTemplate.postForObject(
//                "http://ts-consign-service:16111/consign/insertConsign"
//                ,cr,InsertConsignRecordResult.class);

        return icr;
    }
}
