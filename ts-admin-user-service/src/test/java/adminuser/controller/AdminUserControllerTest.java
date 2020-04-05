package adminuser.controller;

import adminuser.dto.UserDto;
import adminuser.service.AdminUserService;
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
public class AdminUserControllerTest {

    @InjectMocks
    private AdminUserController adminUserController;

    @Mock
    private AdminUserService adminUserService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = adminUserController.home(headers);
        Assert.assertEquals("Welcome to [ AdminUser Service ] !", result);
    }

    @Test
    public void testGetAllUsers(){
        Mockito.when(adminUserService.getAllUsers(headers)).thenReturn(response);
        httpEntity = adminUserController.getAllUsers(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdateUser(){
        UserDto userDto = new UserDto();
        Mockito.when(adminUserService.updateUser(userDto, headers)).thenReturn(response);
        httpEntity = adminUserController.updateUser(userDto, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddUser(){
        UserDto userDto = new UserDto();
        Mockito.when(adminUserService.addUser(userDto, headers)).thenReturn(response);
        httpEntity = adminUserController.addUser(userDto, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteUser(){
        Mockito.when(adminUserService.deleteUser("userId", headers)).thenReturn(response);
        httpEntity = adminUserController.deleteUser("userId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
