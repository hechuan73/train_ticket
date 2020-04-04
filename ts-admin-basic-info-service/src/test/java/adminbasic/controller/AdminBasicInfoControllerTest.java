package adminbasic.controller;

import adminbasic.entity.*;
import adminbasic.service.AdminBasicInfoService;
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

@RunWith(JUnit4.class)
public class AdminBasicInfoControllerTest {

    @InjectMocks
    private AdminBasicInfoController adminBasicInfoController;

    @Mock
    AdminBasicInfoService adminBasicInfoService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = adminBasicInfoController.home(headers);
        Assert.assertEquals("Welcome to [ AdminBasicInfo Service ] !", result);
    }

    @Test
    public void testGetAllContacts(){
        Mockito.when(adminBasicInfoService.getAllContacts(headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.getAllContacts(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteContacts(){
        Mockito.when(adminBasicInfoService.deleteContact("contactsId", headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.deleteContacts("contactsId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testModifyContacts(){
        Contacts mci = new Contacts();
        Mockito.when(adminBasicInfoService.modifyContact(mci, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.modifyContacts(mci, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddContacts(){
        Contacts c = new Contacts();
        Mockito.when(adminBasicInfoService.addContact(c, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.addContacts(c, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetAllStations(){
        Mockito.when(adminBasicInfoService.getAllStations(headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.getAllStations(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteStation(){
        Station s = new Station();
        Mockito.when(adminBasicInfoService.deleteStation(s, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.deleteStation(s, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testModifyStation(){
        Station s = new Station();
        Mockito.when(adminBasicInfoService.modifyStation(s, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.modifyStation(s, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddStation(){
        Station s = new Station();
        Mockito.when(adminBasicInfoService.addStation(s, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.addStation(s, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetAllTrains(){
        Mockito.when(adminBasicInfoService.getAllTrains(headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.getAllTrains(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteTrain(){
        Mockito.when(adminBasicInfoService.deleteTrain("id", headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.deleteTrain("id", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testModifyTrain(){
        TrainType t = new TrainType();
        Mockito.when(adminBasicInfoService.modifyTrain(t, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.modifyTrain(t, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddTrain(){
        TrainType t = new TrainType();
        Mockito.when(adminBasicInfoService.addTrain(t, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.addTrain(t, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetAllConfigs(){
        Mockito.when(adminBasicInfoService.getAllConfigs(headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.getAllConfigs(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteConfig(){
        Mockito.when(adminBasicInfoService.deleteConfig("name", headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.deleteConfig("name", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testModifyConfig(){
        Config c = new Config();
        Mockito.when(adminBasicInfoService.modifyConfig(c, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.modifyConfig(c, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddConfig(){
        Config c = new Config();
        Mockito.when(adminBasicInfoService.addConfig(c, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.addConfig(c, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetAllPrices(){
        Mockito.when(adminBasicInfoService.getAllPrices(headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.getAllPrices(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeletePrice(){
        PriceInfo pi = new PriceInfo();
        Mockito.when(adminBasicInfoService.deletePrice(pi, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.deletePrice(pi, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testModifyPrice(){
        PriceInfo pi = new PriceInfo();
        Mockito.when(adminBasicInfoService.modifyPrice(pi, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.modifyPrice(pi, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddPrice(){
        PriceInfo pi = new PriceInfo();
        Mockito.when(adminBasicInfoService.addPrice(pi, headers)).thenReturn(response);
        httpEntity = adminBasicInfoController.addPrice(pi, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }
}
