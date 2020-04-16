package execute.service;

import execute.serivce.ExecuteServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ExecuteServiceImplTest {

    @InjectMocks
    private ExecuteServiceImpl executeServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity requestEntity = new HttpEntity(headers);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTicketExecute() {

    }

    @Test
    public void testTicketCollect() {

    }


}
