package assurance.controller;

import assurance.service.AssuranceService;
import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@RunWith(JUnit4.class)
public class AssuranceControllerTest {

    @InjectMocks
    private AssuranceController assuranceController;

    @Mock
    private AssuranceService assuranceService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = assuranceController.home(headers);
        Assert.assertEquals("Welcome to [ Assurance Service ] !", result);
    }

    @Test
    public void testGetAllAssurances(){
        Mockito.when(assuranceService.getAllAssurances(headers)).thenReturn(response);
        httpEntity = assuranceController.getAllAssurances(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetAllAssuranceType(){
        Mockito.when(assuranceService.getAllAssuranceTypes(headers)).thenReturn(response);
        httpEntity = assuranceController.getAllAssuranceType(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteAssurance(){
        UUID assuranceId = UUID.randomUUID();
        Mockito.when(assuranceService.deleteById(assuranceId, headers)).thenReturn(response);
        httpEntity = assuranceController.deleteAssurance(assuranceId.toString(), headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteAssuranceByOrderId(){
        UUID orderId = UUID.randomUUID();
        Mockito.when(assuranceService.deleteByOrderId(orderId, headers)).thenReturn(response);
        httpEntity = assuranceController.deleteAssuranceByOrderId(orderId.toString(), headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testModifyAssurance(){
        Mockito.when(assuranceService.modify("assuranceId", "orderId", 1, headers)).thenReturn(response);
        httpEntity = assuranceController.modifyAssurance("assuranceId", "orderId", 1, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreateNewAssurance(){
        Mockito.when(assuranceService.create(1, "orderId", headers)).thenReturn(response);
        httpEntity = assuranceController.createNewAssurance(1, "orderId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetAssuranceById(){
        UUID assuranceId = UUID.randomUUID();
        Mockito.when(assuranceService.findAssuranceById(assuranceId, headers)).thenReturn(response);
        httpEntity = assuranceController.getAssuranceById(assuranceId.toString(), headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testFindAssuranceByOrderId(){
        UUID orderId = UUID.randomUUID();
        Mockito.when(assuranceService.findAssuranceByOrderId(orderId, headers)).thenReturn(response);
        httpEntity = assuranceController.findAssuranceByOrderId(orderId.toString(), headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
