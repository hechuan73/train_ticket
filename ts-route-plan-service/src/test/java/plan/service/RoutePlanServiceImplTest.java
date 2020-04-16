package plan.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@RunWith(JUnit4.class)
public class RoutePlanServiceImplTest {

    @InjectMocks
    private RoutePlanServiceImpl routePlanServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchCheapestResult() {

    }

    @Test
    public void testSearchQuickestResult() {

    }

}
