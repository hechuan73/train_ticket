package order.service;

import order.domain.*;
import java.util.ArrayList;
import java.util.UUID;

public interface OrderService {

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

    void initOrder(Order order);

    DeleteOrderResult deleteOrder(DeleteOrderInfo info);

    LeftTicketInfo getSoldTickets(SeatRequest seatRequest);

    AddOrderResult addNewOrder(Order order);

    UpdateOrderResult updateOrder(Order order);
}
