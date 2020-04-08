package train.controller;

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
import train.entity.TrainType;
import train.service.TrainService;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class TrainControllerTest {

    @InjectMocks
    private TrainController trainController;

    @Mock
    private TrainService trainService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("Welcome to [ Train Service ] !", trainController.home(headers));
    }

    @Test
    public void testCreate1(){
        TrainType trainType = new TrainType();
        Mockito.when(trainService.create(trainType, headers)).thenReturn(true);
        httpEntity = trainController.create(trainType, headers);
        Assert.assertEquals(new ResponseEntity<>(new Response(1, "create success", true), HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreate2(){
        TrainType trainType = new TrainType();
        Mockito.when(trainService.create(trainType, headers)).thenReturn(false);
        httpEntity = trainController.create(trainType, headers);
        Assert.assertEquals(new ResponseEntity<>(new Response(0, "train type already exist", trainType), HttpStatus.OK), httpEntity);
    }

    @Test
    public void testRetrieve1(){
        Mockito.when(trainService.retrieve("id", headers)).thenReturn(null);
        httpEntity = trainController.retrieve("id", headers);
        Assert.assertEquals(new ResponseEntity<>(new Response(0, "here is no TrainType with the trainType id", "id"), HttpStatus.OK), httpEntity);
    }

    @Test
    public void testRetrieve2(){
        TrainType trainType = new TrainType();
        Mockito.when(trainService.retrieve("id", headers)).thenReturn(trainType);
        httpEntity = trainController.retrieve("id", headers);
        Assert.assertEquals(new ResponseEntity<>(new Response(1, "success", trainType), HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdate1(){
        TrainType trainType = new TrainType();
        Mockito.when(trainService.update(trainType, headers)).thenReturn(true);
        httpEntity = trainController.update(trainType, headers);
        Assert.assertEquals(new ResponseEntity<>(new Response(1, "update success", true), HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdate2(){
        TrainType trainType = new TrainType();
        Mockito.when(trainService.update(trainType, headers)).thenReturn(false);
        httpEntity = trainController.update(trainType, headers);
        Assert.assertEquals(new ResponseEntity<>(new Response(0, "there is no trainType with the trainType id", false), HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDelete1(){
        Mockito.when(trainService.delete("id", headers)).thenReturn(true);
        httpEntity = trainController.delete("id", headers);
        Assert.assertEquals(new ResponseEntity<>(new Response(1, "delete success", true), HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDelete2(){
        Mockito.when(trainService.delete("id", headers)).thenReturn(false);
        httpEntity = trainController.delete("id", headers);
        Assert.assertEquals(new ResponseEntity<>(new Response(0, "there is no train according to id", "id"), HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQuery1(){
        List<TrainType> trainTypes = new ArrayList<>();
        trainTypes.add(new TrainType());
        Mockito.when(trainService.query(headers)).thenReturn(trainTypes);
        httpEntity = trainController.query(headers);
        Assert.assertEquals(new ResponseEntity<>(new Response(1, "success", trainTypes), HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQuery2(){
        List<TrainType> trainTypes = new ArrayList<>();
        Mockito.when(trainService.query(headers)).thenReturn(trainTypes);
        httpEntity = trainController.query(headers);
        Assert.assertEquals(new ResponseEntity<>(new Response(0, "no content", trainTypes), HttpStatus.OK), httpEntity);
    }

}
