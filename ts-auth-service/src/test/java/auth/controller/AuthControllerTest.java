package auth.controller;

import auth.dto.AuthDto;
import auth.service.UserService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(JUnit4.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetHello(){
        Assert.assertEquals("hello", authController.getHello());
    }

    @Test
    public void testCreateDefaultUser(){
        AuthDto authDto = new AuthDto();
        Mockito.when(userService.createDefaultAuthUser(authDto)).thenReturn(null);
        HttpEntity<Response> result = authController.createDefaultUser(authDto);
        Assert.assertEquals(new ResponseEntity<>(new Response(1, "SUCCESS", authDto), HttpStatus.CREATED), result);
    }

}
