package travelplan.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@RunWith(JUnit4.class)
public class TravelPlanServiceImplTest {

    @InjectMocks
    private TravelPlanServiceImpl travelPlanServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity requestEntity = new HttpEntity(headers);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTransferSearch() {

    }

    @Test
    public void testGetCheapest() {

    }

    @Test
    public void testGetQuickest() {

    }

    @Test
    public void testGetMinStation() {

    }

}
