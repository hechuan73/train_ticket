package security.controller;

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
import security.entity.SecurityConfig;
import security.service.SecurityService;

@RunWith(JUnit4.class)
public class SecurityControllerTest {

    @InjectMocks
    private SecurityController securityController;

    @Mock
    private SecurityService securityService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("welcome to [Security Service]", securityController.home(headers));
    }

    @Test
    public void testFindAllSecurityConfig(){
        Mockito.when(securityService.findAllSecurityConfig(headers)).thenReturn(response);
        httpEntity = securityController.findAllSecurityConfig(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreate(){
        SecurityConfig info = new SecurityConfig();
        Mockito.when(securityService.addNewSecurityConfig(info, headers)).thenReturn(response);
        httpEntity = securityController.create(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdate(){
        SecurityConfig info = new SecurityConfig();
        Mockito.when(securityService.modifySecurityConfig(info, headers)).thenReturn(response);
        httpEntity = securityController.update(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDelete(){
        Mockito.when(securityService.deleteSecurityConfig("id", headers)).thenReturn(response);
        httpEntity = securityController.delete("id", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCheck(){
        Mockito.when(securityService.check("accountId", headers)).thenReturn(response);
        httpEntity = securityController.check("accountId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
