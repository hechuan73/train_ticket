package rebook.controller;

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
import org.springframework.http.*;
import rebook.entity.RebookInfo;
import rebook.service.RebookService;

@RunWith(JUnit4.class)
public class RebookControllerTest {

    @InjectMocks
    private RebookController rebookController;

    @Mock
    private RebookService service;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("Welcome to [ Rebook Service ] !", rebookController.home());
    }

    @Test
    public void testPayDifference(){
        RebookInfo info = new RebookInfo();
        Mockito.when(service.payDifference(info, headers)).thenReturn(response);
        httpEntity = rebookController.payDifference(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testRebook(){
        RebookInfo info = new RebookInfo();
        Mockito.when(service.rebook(info, headers)).thenReturn(response);
        httpEntity = rebookController.rebook(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
