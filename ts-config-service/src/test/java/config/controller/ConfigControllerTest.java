package config.controller;

import config.entity.Config;
import config.service.ConfigService;
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
public class ConfigControllerTest {

    @InjectMocks
    private ConfigController configController;

    @Mock
    private ConfigService configService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = configController.home(headers);
        Assert.assertEquals("Welcome to [ Config Service ] !", result);
    }

    @Test
    public void testQueryAll(){
        Mockito.when(configService.queryAll(headers)).thenReturn(response);
        httpEntity = configController.queryAll(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreateConfig(){
        Config info = new Config();
        Mockito.when(configService.create(info, headers)).thenReturn(response);
        httpEntity = configController.createConfig(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.CREATED), httpEntity);
    }

    @Test
    public void testUpdateConfig(){
        Config info = new Config();
        Mockito.when(configService.update(info, headers)).thenReturn(response);
        httpEntity = configController.updateConfig(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteConfig(){
        Mockito.when(configService.delete("configName", headers)).thenReturn(response);
        httpEntity = configController.deleteConfig("configName", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testRetrieve(){
        Mockito.when(configService.query("configName", headers)).thenReturn(response);
        httpEntity = configController.retrieve("configName", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
