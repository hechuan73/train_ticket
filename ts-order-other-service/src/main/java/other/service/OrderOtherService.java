package other.service;

import other.domain.*;
import java.util.ArrayList;
import java.util.UUID;

public interface OrderOtherService {

    Order findOrderById(UUID id);

    CreateOrderResult create(Order newOrder);

    ChangeOrderResult saveChanges(Order order);

    CancelOrderResult cancelOrder(CancelOrderInfo coi);

    ArrayList<Order> queryOrders(QueryInfo qi,String accountId);

    OrderAlterResult alterOrder(OrderAlterInfo oai);

    CalculateSoldTicketResult queryAlreadySoldOrders(CalculateSoldTicketInfo csti);

    QueryOrderResult getAllOrders();

    ModifyOrderStatusResult modifyOrder(ModifyOrderStatusInfo info);

    GetOrderPriceResult getOrderPrice(GetOrderPrice info);

    PayOrderResult payOrder(PayOrderInfo info);

    GetOrderResult getOrderById(GetOrderByIdInfo info);

    GetOrderInfoForSecurityResult checkSecurityAboutOrder(GetOrderInfoForSecurity info);

    DeleteOrderResult deleteOrder(DeleteOrderInfo info);

    void initOrder(Order order);

    LeftTicketInfo getSoldTickets(SeatRequest seatRequest);

    AddOrderResult addNewOrder(Order order);

    UpdateOrderResult updateOrder(Order order);
}
