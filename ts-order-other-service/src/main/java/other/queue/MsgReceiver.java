package other.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import other.domain.Order;
import other.service.OrderOtherService;

@Component
@EnableBinding(Sink.class)
public class MsgReceiver {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final OrderOtherService orderOtherService;

    @Autowired
    public MsgReceiver(OrderOtherService orderOtherService) {
        this.orderOtherService = orderOtherService;
    }

    @StreamListener(Sink.INPUT)
    public void receiveQueueInfo(Order order) {
        logger.info("[Order Other Service][Receive Bean] Payload: {}", order);
        orderOtherService.processOrderFromQueue(order);
    }
}
