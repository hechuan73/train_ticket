package auth.controller;

import auth.dto.BasicAuthDto;
import auth.entity.User;
import auth.service.TokenService;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(JUnit4.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;
    @Mock
    private TokenService tokenService;

    private HttpHeaders headers = new HttpHeaders();
    private Response response = new Response();
    private ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetHello(){
        Assert.assertEquals("Hello", userController.getHello());
    }

    @Test
    public void testGetToken(){
        BasicAuthDto dao = new BasicAuthDto();
        Mockito.when(tokenService.getToken(dao, headers)).thenReturn(response);
        ResponseEntity<Response> result = userController.getToken(dao, headers);
        Assert.assertEquals(responseEntity, result);
    }

    @Test
    public void testGetAllUser(){
        List<User> userList = new ArrayList<>();
        Mockito.when(userService.getAllUser(headers)).thenReturn(userList);
        ResponseEntity<List<User>> responseEntity2 = new ResponseEntity<>(userList,null, HttpStatus.OK);
        ResponseEntity<List<User>> result = userController.getAllUser(headers);
        Assert.assertEquals(responseEntity2, result);
    }

    @Test
    public void testDeleteUserById(){
        UUID userId = UUID.randomUUID();
        Mockito.when(userService.deleteByUserId(userId, headers)).thenReturn(response);
        ResponseEntity<Response> result = userController.deleteUserById(userId.toString(), headers);
        Assert.assertEquals(responseEntity, result);
    }

}
