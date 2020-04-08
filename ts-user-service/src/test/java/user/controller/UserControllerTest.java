package user.controller;

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
import user.dto.UserDto;
import user.service.UserService;

import java.util.UUID;

@RunWith(JUnit4.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("Hello", userController.testHello());
    }

    @Test
    public void testGetAllUser(){
        Mockito.when(userService.getAllUsers(headers)).thenReturn(response);
        httpEntity = userController.getAllUser(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetUserByUserName(){
        Mockito.when(userService.findByUserName("userName", headers)).thenReturn(response);
        httpEntity = userController.getUserByUserName("userName", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetUserByUserId(){
        Mockito.when(userService.findByUserId("userId", headers)).thenReturn(response);
        httpEntity = userController.getUserByUserId("userId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testRegisterUser(){
        UserDto userDto = new UserDto();
        Mockito.when(userService.saveUser(userDto, headers)).thenReturn(response);
        httpEntity = userController.registerUser(userDto, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.CREATED), httpEntity);
    }

    @Test
    public void testDeleteUserById(){
        UUID userId = UUID.randomUUID();
        Mockito.when(userService.deleteUser(userId, headers)).thenReturn(response);
        httpEntity = userController.deleteUserById(userId.toString(), headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdateUser(){
        UserDto user = new UserDto();
        Mockito.when(userService.updateUser(user, headers)).thenReturn(response);
        httpEntity = userController.updateUser(user, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
