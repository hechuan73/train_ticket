package user.service;

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
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import user.dto.UserDto;
import user.entity.User;
import user.repository.UserRepository;
import user.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(JUnit4.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() {

    }

    @Test
    public void testGetAllUsers1() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Response result = userServiceImpl.getAllUsers(headers);
        Assert.assertEquals(new Response<>(1, "Success", users), result);
    }

    @Test
    public void testGetAllUsers2() {
        Mockito.when(userRepository.findAll()).thenReturn(null);
        Response result = userServiceImpl.getAllUsers(headers);
        Assert.assertEquals(new Response<>(0, "NO User", null), result);
    }

    @Test
    public void testFindByUserName1() {
        User user = new User();
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(user);
        Response result = userServiceImpl.findByUserName("user_name", headers);
        Assert.assertEquals(new Response<>(1, "Find User Success", user), result);
    }

    @Test
    public void testFindByUserName2() {
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(null);
        Response result = userServiceImpl.findByUserName("user_name", headers);
        Assert.assertEquals(new Response<>(0, "No User", null), result);
    }

    @Test
    public void testFindByUserId1() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        Mockito.when(userRepository.findByUserId(Mockito.any(UUID.class))).thenReturn(user);
        Response result = userServiceImpl.findByUserId(userId.toString(), headers);
        Assert.assertEquals(new Response<>(1, "Find User Success", user), result);
    }

    @Test
    public void testFindByUserId2() {
        UUID userId = UUID.randomUUID();
        Mockito.when(userRepository.findByUserId(Mockito.any(UUID.class))).thenReturn(null);
        Response result = userServiceImpl.findByUserId(userId.toString(), headers);
        Assert.assertEquals(new Response<>(0, "No User", null), result);
    }

    @Test
    public void testDeleteUser1() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        Mockito.when(userRepository.findByUserId(Mockito.any(UUID.class))).thenReturn(user);
        HttpEntity<Response> httpEntity = new HttpEntity<>(headers);
        Mockito.when(restTemplate.exchange("http://ts-auth-service:12340/api/v1" + "/users/" + userId,
                HttpMethod.DELETE,
                httpEntity,
                Response.class)).thenReturn(null);
        Mockito.doNothing().doThrow(new RuntimeException()).when(userRepository).deleteByUserId(Mockito.any(UUID.class));
        Response result = userServiceImpl.deleteUser(userId, headers);
        Assert.assertEquals(new Response<>(1, "DELETE SUCCESS", null), result);
    }

    @Test
    public void testDeleteUser2() {
        UUID userId = UUID.randomUUID();
        Mockito.when(userRepository.findByUserId(Mockito.any(UUID.class))).thenReturn(null);
        Response result = userServiceImpl.deleteUser(userId, headers);
        Assert.assertEquals(new Response<>(0, "USER NOT EXISTS", null), result);
    }

    @Test
    public void testUpdateUser1() {
        UserDto userDto = new UserDto();
        User oldUser = new User();
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(oldUser);
        Mockito.doNothing().doThrow(new RuntimeException()).when(userRepository).deleteByUserId(Mockito.any(UUID.class));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(null);
        Response result = userServiceImpl.updateUser(userDto, headers);
        Assert.assertEquals("SAVE USER SUCCESS", result.getMsg());
    }

    @Test
    public void testUpdateUser2() {
        UserDto userDto = new UserDto();
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(null);
        Response result = userServiceImpl.updateUser(userDto, headers);
        Assert.assertEquals(new Response(0, "USER NOT EXISTS", null), result);
    }

    @Test
    public void testDeleteUserAuth() {
        UUID userId = UUID.randomUUID();
        HttpEntity<Response> httpEntity = new HttpEntity<>(headers);
        Mockito.when(restTemplate.exchange("http://ts-auth-service:12340/api/v1" + "/users/" + userId,
                HttpMethod.DELETE,
                httpEntity,
                Response.class)).thenReturn(null);
        userServiceImpl.deleteUserAuth(userId, headers);
        Mockito.verify(restTemplate, Mockito.times(1))
                .exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class));
    }

}
