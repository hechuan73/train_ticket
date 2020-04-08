package consignprice.service;

import consignprice.repository.ConsignPriceConfigRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

@RunWith(JUnit4.class)
public class ConsignPriceServiceImplTest {

    @InjectMocks
    private ConsignPriceServiceImpl consignPriceServiceImpl;

    @Mock
    private ConsignPriceConfigRepository repository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPriceByWeightAndRegion(){

    }

}
