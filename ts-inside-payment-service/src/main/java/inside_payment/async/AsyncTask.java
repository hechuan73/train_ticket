package inside_payment.async;

import java.util.concurrent.Future;
import inside_payment.entity.OutsidePaymentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author fdse
 */
@Component  
public class AsyncTask {  
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());  
    
    @Autowired
	private RestTemplate restTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(AsyncTask.class);

    @Async("mySimpleAsync")
    public Future<Boolean> sendAsyncCallToPaymentService(OutsidePaymentInfo outsidePaymentInfo) {
        AsyncTask.LOG.info("[Inside Payment Service][Async Task] Begin.");
        Boolean value = restTemplate.getForObject("http://rest-service-external:16100/greet", Boolean.class);
        AsyncTask.LOG.info("[Inside Payment Service][Async Task] Receive call Value directly back: {}", value);
        return new AsyncResult<>(value);
    }
    
}  
