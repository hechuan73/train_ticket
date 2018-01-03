package other.service;

import other.domain.*;
import other.repository.OrderOtherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OrderOtherServiceImpl implements OrderOtherService{

    @Autowired
    private OrderOtherRepository orderOtherRepository;

    @Override
    public LeftTicketInfo getSoldTickets(SeatRequest seatRequest){
        ArrayList<Order> list = orderOtherRepository.findByTravelDateAndTrainNumber(seatRequest.getTravelDate(),
                seatRequest.getTrainNumber());
        Set ticketSet = new HashSet();
        for(Order tempOrder : list){
            Ticket ticket = new Ticket();
            ticket.setSeatNo(Integer.parseInt(tempOrder.getSeatNumber()));
            ticket.setStartStation(tempOrder.getFrom());
            ticket.setDestStation(tempOrder.getTo());
            ticketSet.add(ticket);
        }
        LeftTicketInfo leftTicketInfo = new LeftTicketInfo();
        leftTicketInfo.setSoldTickets(ticketSet);
        return leftTicketInfo;
    }
    @Override
    public Order findOrderById(UUID id){
        return orderOtherRepository.findById(id);
    }

    @Override
    public CreateOrderResult create(Order order){
        System.out.println("[Order Other Service][Create Order] Ready Create Order");
        ArrayList<Order> accountOrders = orderOtherRepository.findByAccountId(order.getAccountId());
        CreateOrderResult cor = new CreateOrderResult();
        if(accountOrders.contains(order)){
            System.out.println("[Order Other Service][Order Create] Fail.Order already exists.");
            cor.setStatus(false);
            cor.setMessage("Order already exist");
            cor.setOrder(null);
        }else{
            order.setId(UUID.randomUUID());
            orderOtherRepository.save(order);
            System.out.println("[Order Other Service][Order Create] Success.");
            System.out.println("[Order Other Service][Order Create] Price:" + order.getPrice());
            cor.setStatus(true);
            cor.setMessage("Success");
            cor.setOrder(order);
        }
        return cor;
    }

    @Override
    public void initOrder(Order order){
        Order orderTemp = orderOtherRepository.findById(order.getId());
        if(orderTemp == null){
            orderOtherRepository.save(order);
        }else{
            System.out.println("[Order Other Service][Init Order] Order Already Exists ID:" + order.getId());
        }
    }


    @Override
    public OrderAlterResult alterOrder(OrderAlterInfo oai){
        OrderAlterResult oar = new OrderAlterResult();
        UUID oldOrderId = oai.getPreviousOrderId();
        Order oldOrder = findOrderById(oldOrderId);
        if(oldOrder == null){
            System.out.println("[Order Other Service][Alter Order] Fail.Order do not exist.");
            oar.setStatus(false);
            oar.setMessage("Old Order Does Not Exists");
            oar.setOldOrder(null);
            oar.setNewOrder(null);
            return oar;
        }
        oldOrder.setStatus(OrderStatus.CANCEL.getCode());
        saveChanges(oldOrder);
        Order newOrder = oai.getNewOrderInfo();
        newOrder.setId(UUID.randomUUID());
        CreateOrderResult cor = create(oai.getNewOrderInfo());
        if(cor.isStatus() == true){
            oar.setStatus(true);
            oar.setMessage("Success");
            oar.setOldOrder(oldOrder);
            oar.setNewOrder(newOrder);
            System.out.println("[Order Other Service][Alter Order] Success.");
        }else{
            oar.setStatus(false);
            oar.setMessage(cor.getMessage());
            oar.setOldOrder(null);
            oar.setNewOrder(null);
        }
        return oar;
    }

    @Override
    public ArrayList<Order> queryOrders(QueryInfo qi,String accountId){
        //1.Get all orders of the user
        ArrayList<Order> list = orderOtherRepository.findByAccountId(UUID.fromString(accountId));
        System.out.println("[Order Other Service][Query Order][Step 1] Get Orders Number of Account:" + list.size());
        //2.Check is these orders fit the requirement/
        if(qi.isEnableStateQuery() || qi.isEnableBoughtDateQuery() || qi.isEnableTravelDateQuery()){
            ArrayList<Order> finalList = new ArrayList<>();
            for(Order tempOrder : list){
                boolean statePassFlag = false;
                boolean boughtDatePassFlag = false;
                boolean travelDatePassFlag = false;
                //3.Check order state requirement.
                if(qi.isEnableStateQuery()){
                    if(tempOrder.getStatus() != qi.getState()){
                        statePassFlag = false;
                    }else{
                        statePassFlag = true;
                    }
                }else{
                    statePassFlag = true;
                }
                System.out.println("[Order Other Service][Query Order][Step 2][Check Status Fits End]");
                //4.Check order travel date requirement.
                if(qi.isEnableTravelDateQuery()){
                    if(tempOrder.getTravelDate().before(qi.getTravelDateEnd()) &&
                            tempOrder.getTravelDate().after(qi.getBoughtDateStart())){
                        travelDatePassFlag = true;
                    }else{
                        travelDatePassFlag = false;
                    }
                }else{
                    travelDatePassFlag = true;
                }
                System.out.println("[Order Other Service][Query Order][Step 2][Check Travel Date End]");
                //5.Check order bought date requirement.
                if(qi.isEnableBoughtDateQuery()){
                    if(tempOrder.getBoughtDate().before(qi.getBoughtDateEnd()) &&
                            tempOrder.getBoughtDate().after(qi.getBoughtDateStart())){
                        boughtDatePassFlag = true;
                    }else{
                        boughtDatePassFlag = false;
                    }
                }else{
                    boughtDatePassFlag = true;
                }
                System.out.println("[Order Other Service][Query Order][Step 2][Check Bought Date End]");
                //6.check if all requirement fits.
                if(statePassFlag && boughtDatePassFlag && travelDatePassFlag){
                    finalList.add(tempOrder);
                }
                System.out.println("[Order Other Service][Query Order][Step 2][Check All Requirement End]");
            }
            System.out.println("[Order Other Service][Query Order] Get order num:" + finalList.size());
            return finalList;
        }else{
            System.out.println("[Order Other Service][Query Order] Get order num:" + list.size());
            return list;
        }
    }

    @Override
    public ChangeOrderResult saveChanges(Order order){
        Order oldOrder = findOrderById(order.getId());
        ChangeOrderResult cor = new ChangeOrderResult();
        if(oldOrder == null){
            System.out.println("[Order Other Service][Modify Order] Fail.Order not found.");
            cor.setStatus(false);
            cor.setMessage("Order Not Found");
            cor.setOrder(null);
        }else{
            oldOrder.setAccountId(order.getAccountId());
            oldOrder.setBoughtDate(order.getBoughtDate());
            oldOrder.setTravelDate(order.getTravelDate());
            oldOrder.setTravelTime(order.getTravelTime());
            oldOrder.setCoachNumber(order.getCoachNumber());
            oldOrder.setSeatClass(order.getSeatClass());
            oldOrder.setSeatNumber(order.getSeatNumber());
            oldOrder.setFrom(order.getFrom());
            oldOrder.setTo(order.getTo());
            oldOrder.setStatus(order.getStatus());
            oldOrder.setTrainNumber(order.getTrainNumber());
            oldOrder.setPrice(order.getPrice());
            oldOrder.setContactsName(order.getContactsName());
            oldOrder.setContactsDocumentNumber(order.getContactsDocumentNumber());
            oldOrder.setDocumentType(order.getDocumentType());
            orderOtherRepository.save(oldOrder);
            System.out.println("[Order Other Service] Success.");
            cor.setOrder(oldOrder);
            cor.setStatus(true);
            cor.setMessage("Success");
        }
        return cor;
    }

    @Override
    public CancelOrderResult cancelOrder(CancelOrderInfo coi){
        UUID orderId = coi.getOrderId();
        Order oldOrder = orderOtherRepository.findById(orderId);
        CancelOrderResult cor = new CancelOrderResult();
        if(oldOrder == null){
            System.out.println("[Order Other Service][Cancel Order] Fail.Order not found.");
            cor.setStatus(false);
            cor.setMessage("Order Not Found");
            cor.setOrder(null);

        }else{
            oldOrder.setStatus(OrderStatus.CANCEL.getCode());
            orderOtherRepository.save(oldOrder);
            System.out.println("[Order Other Service][Cancel Order] Success.");
            cor.setStatus(true);
            cor.setMessage("Success");
            cor.setOrder(oldOrder);
        }
        return cor;
    }

    @Override
    public  CalculateSoldTicketResult queryAlreadySoldOrders(CalculateSoldTicketInfo csti){
        ArrayList<Order> orders = orderOtherRepository.findByTravelDateAndTrainNumber(csti.getTravelDate(),csti.getTrainNumber());
        CalculateSoldTicketResult cstr = new CalculateSoldTicketResult();
        cstr.setTravelDate(csti.getTravelDate());
        cstr.setTrainNumber(csti.getTrainNumber());
        System.out.println("[Order Other Service][Calculate Sold Ticket] Get Orders Number:" + orders.size());
        for(Order order : orders){
            if(order.getStatus() >= OrderStatus.CHANGE.getCode()){
                continue;
            }
            if(order.getSeatClass() == SeatClass.NONE.getCode()){
                cstr.setNoSeat(cstr.getNoSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.BUSINESS.getCode()){
                cstr.setBusinessSeat(cstr.getBusinessSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.FIRSTCLASS.getCode()){
                cstr.setFirstClassSeat(cstr.getFirstClassSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.SECONDCLASS.getCode()){
                cstr.setSecondClassSeat(cstr.getSecondClassSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.HARDSEAT.getCode()){
                cstr.setHardSeat(cstr.getHardSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.SOFTSEAT.getCode()){
                cstr.setSoftSeat(cstr.getSoftSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.HARDBED.getCode()){
                cstr.setHardBed(cstr.getHardBed() + 1);
            }else if(order.getSeatClass() == SeatClass.SOFTBED.getCode()){
                cstr.setSoftBed(cstr.getSoftBed() + 1);
            }else if(order.getSeatClass() == SeatClass.HIGHSOFTBED.getCode()){
                cstr.setHighSoftBed(cstr.getHighSoftBed() + 1);
            }else{
                System.out.println("[Order Other Service][Calculate Sold Tickets] Seat class not exists. Order ID:" + order.getId());
            }
        }
        return cstr;
    }

    @Override
    public QueryOrderResult getAllOrders(){
        ArrayList<Order> orders = orderOtherRepository.findAll();
        QueryOrderResult result = new QueryOrderResult(true,"Success.",orders);
        return result;
    }

    @Override
    public ModifyOrderStatusResult modifyOrder(ModifyOrderStatusInfo info){
        Order order = orderOtherRepository.findById(UUID.fromString(info.getOrderId()));
        ModifyOrderStatusResult result = new ModifyOrderStatusResult();
        if(order == null){
            result.setStatus(false);
            result.setMessage("Order Not Found");
            result.setOrder(null);
        }else{
            order.setStatus(info.getStatus());
            orderOtherRepository.save(order);
            result.setStatus(true);
            result.setMessage("Success");
            result.setOrder(order);
        }
        return result;
    }

    @Override
    public GetOrderPriceResult getOrderPrice(GetOrderPrice info){
        Order order = orderOtherRepository.findById(UUID.fromString(info.getOrderId()));
        GetOrderPriceResult result = new GetOrderPriceResult();
        if(result == null){
            result.setStatus(false);
            result.setMessage("Order Not Found");
            result.setPrice("-1.0");
        }else{
            result.setStatus(true);
            result.setMessage("Success");
            System.out.println("[Order Other Service][Get Order Price] Price:" + order.getPrice());
            result.setPrice(order.getPrice());
        }
        return result;
    }

    @Override
    public PayOrderResult payOrder(PayOrderInfo info){
        Order order = orderOtherRepository.findById(UUID.fromString(info.getOrderId()));
        PayOrderResult result = new PayOrderResult();
        if(result == null){
            result.setStatus(false);
            result.setMessage("Order Not Found");
            result.setOrder(null);
        }else{
            order.setStatus(OrderStatus.PAID.getCode());
            orderOtherRepository.save(order);
            result.setStatus(true);
            result.setMessage("Success.");
            result.setOrder(order);
        }
        return result;
    }

    @Override
    public GetOrderResult getOrderById(GetOrderByIdInfo info){
        Order order = orderOtherRepository.findById(UUID.fromString(info.getOrderId()));
        GetOrderResult result = new GetOrderResult();
        if(order == null){
            result.setStatus(false);
            result.setMessage("Order Not Found");
            result.setOrder(null);
        }else{
            result.setStatus(true);
            result.setMessage("Success.");
            result.setOrder(order);
        }
        return result;
    }

    @Override
    public GetOrderInfoForSecurityResult checkSecurityAboutOrder(GetOrderInfoForSecurity info){
        GetOrderInfoForSecurityResult result = new GetOrderInfoForSecurityResult();
        ArrayList<Order> orders = orderOtherRepository.findByAccountId(UUID.fromString(info.getAccountId()));
        int countOrderInOneHour = 0;
        int countTotalValidOrder = 0;
        Date dateFrom = info.getCheckDate();
        Calendar ca = Calendar.getInstance();
        ca.setTime(dateFrom );
        ca.add(Calendar.HOUR_OF_DAY, -1);
        dateFrom = ca.getTime();
        for(Order order : orders){
            if(order.getStatus() == OrderStatus.NOTPAID.getCode() ||
                    order.getStatus() == OrderStatus.PAID.getCode() ||
                    order.getStatus() == OrderStatus.COLLECTED.getCode()){
                countTotalValidOrder += 1;
            }
            if(order.getBoughtDate().after(dateFrom)){
                countOrderInOneHour += 1;
            }
        }
        result.setOrderNumInLastOneHour(countOrderInOneHour);
        result.setOrderNumOfValidOrder(countTotalValidOrder);
        return result;
    }

    @Override
    public DeleteOrderResult deleteOrder(DeleteOrderInfo info){
        UUID orderUuid = UUID.fromString(info.getOrderId());
        Order order = orderOtherRepository.findById(orderUuid);
        DeleteOrderResult result = new DeleteOrderResult();
        if(order == null){
            result.setStatus(false);
            result.setMessage("Order Not Exist.");
        }else{
            orderOtherRepository.deleteById(orderUuid);
            result.setStatus(true);
            result.setMessage("Success.");
        }
        return result;
    }

    @Override
    public AddOrderResult addNewOrder(Order order) {
        System.out.println("[Order Service][Admin Add Order] Ready Add Order.");
        ArrayList<Order> accountOrders = orderOtherRepository.findByAccountId(order.getAccountId());
        AddOrderResult result = new AddOrderResult();
        if(accountOrders.contains(order)){
            System.out.println("[Order Service][Admin Add Order] Fail.Order already exists.");
            result.setStatus(false);
            result.setMessage("Order already exist");
            result.setOrder(null);
        }else{
            order.setId(UUID.randomUUID());
            orderOtherRepository.save(order);
            System.out.println("[Order Service][Admin Add Order] Success.");
            System.out.println("[Order Service][Admin Add Order] Price:" + order.getPrice());
            result.setStatus(true);
            result.setMessage("Success");
            result.setOrder(order);
        }
        return result;
    }

    @Override
    public UpdateOrderResult updateOrder(Order order) {
        Order oldOrder = findOrderById(order.getId());
        UpdateOrderResult result = new UpdateOrderResult();
        if(oldOrder == null){
            System.out.println("[Order Service][Admin Update Order] Fail.Order not found.");
            result.setStatus(false);
            result.setMessage("Order Not Found");
            result.setOrder(null);
        }else{
            oldOrder.setAccountId(order.getAccountId());
            oldOrder.setBoughtDate(order.getBoughtDate());
            oldOrder.setTravelDate(order.getTravelDate());
            oldOrder.setTravelTime(order.getTravelTime());
            oldOrder.setCoachNumber(order.getCoachNumber());
            oldOrder.setSeatClass(order.getSeatClass());
            oldOrder.setSeatNumber(order.getSeatNumber());
            oldOrder.setFrom(order.getFrom());
            oldOrder.setTo(order.getTo());
            oldOrder.setStatus(order.getStatus());
            oldOrder.setTrainNumber(order.getTrainNumber());
            oldOrder.setPrice(order.getPrice());
            oldOrder.setContactsName(order.getContactsName());
            oldOrder.setContactsDocumentNumber(order.getContactsDocumentNumber());
            oldOrder.setDocumentType(order.getDocumentType());
            orderOtherRepository.save(oldOrder);
            System.out.println("[Order Service] [Admin Update Order] Success.");
            result.setOrder(oldOrder);
            result.setStatus(true);
            result.setMessage("Success");
        }
        return result;
    }
}

