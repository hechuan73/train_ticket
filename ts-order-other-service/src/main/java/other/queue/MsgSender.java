package other.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import other.domain.Order;

@Component
@EnableBinding(Source.class)
public class MsgSender {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Source source;

    @Autowired
    public MsgSender(Source source) {
        this.source = source;
    }

    public void sendLoginInfoToSso(Order order) {

        logger.info("[Order Other Service][Sending Bean] Send Login Into To SSO");
        logger.info("[Order Other Service][Sending Bean] Sending Data: {}", order);

        source.output().send(MessageBuilder.withPayload(order).build());
    }
}
