package security.service;

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
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import security.entity.SecurityConfig;
import security.repository.SecurityRepository;

import java.util.ArrayList;
import java.util.UUID;

@RunWith(JUnit4.class)
public class SecurityServiceImplTest {

    @InjectMocks
    private SecurityServiceImpl securityServiceImpl;

    @Mock
    private SecurityRepository securityRepository;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllSecurityConfig1() {
        ArrayList<SecurityConfig> securityConfigs = new ArrayList<>();
        securityConfigs.add(new SecurityConfig());
        Mockito.when(securityRepository.findAll()).thenReturn(securityConfigs);
        Response result = securityServiceImpl.findAllSecurityConfig(headers);
        Assert.assertEquals(new Response<>(1, "Success", securityConfigs), result);
    }

    @Test
    public void testFindAllSecurityConfig2() {
        Mockito.when(securityRepository.findAll()).thenReturn(null);
        Response result = securityServiceImpl.findAllSecurityConfig(headers);
        Assert.assertEquals(new Response<>(0, "No Content", null), result);
    }

    @Test
    public void testAddNewSecurityConfig1() {
        SecurityConfig sc = new SecurityConfig();
        Mockito.when(securityRepository.findByName(Mockito.anyString())).thenReturn(sc);
        Response result = securityServiceImpl.addNewSecurityConfig(sc, headers);
        Assert.assertEquals(new Response<>(0, "Security Config Already Exist", null), result);
    }

    @Test
    public void testAddNewSecurityConfig2() {
        SecurityConfig sc = new SecurityConfig();
        Mockito.when(securityRepository.findByName(Mockito.anyString())).thenReturn(null);
        Mockito.when(securityRepository.save(Mockito.any(SecurityConfig.class))).thenReturn(null);
        Response result = securityServiceImpl.addNewSecurityConfig(sc, headers);
        Assert.assertEquals("Success", result.getMsg());
    }

    @Test
    public void testModifySecurityConfig1() {
        SecurityConfig sc = new SecurityConfig();
        Mockito.when(securityRepository.findById(Mockito.any(UUID.class))).thenReturn(null);
        Response result = securityServiceImpl.modifySecurityConfig(sc, headers);
        Assert.assertEquals(new Response<>(0, "Security Config Not Exist", null), result);
    }

    @Test
    public void testModifySecurityConfig2() {
        SecurityConfig sc = new SecurityConfig();
        Mockito.when(securityRepository.findById(Mockito.any(UUID.class))).thenReturn(sc);
        Mockito.when(securityRepository.save(Mockito.any(SecurityConfig.class))).thenReturn(null);
        Response result = securityServiceImpl.modifySecurityConfig(sc, headers);
        Assert.assertEquals(new Response<>(1, "Success", sc), result);
    }

    @Test
    public void testDeleteSecurityConfig1() {
        UUID id = UUID.randomUUID();
        Mockito.doNothing().doThrow(new RuntimeException()).when(securityRepository).deleteById(Mockito.any(UUID.class));
        Mockito.when(securityRepository.findById(Mockito.any(UUID.class))).thenReturn(null);
        Response result = securityServiceImpl.deleteSecurityConfig(id.toString(), headers);
        Assert.assertEquals(new Response<>(1, "Success", id.toString()), result);
    }

    @Test
    public void testDeleteSecurityConfig2() {
        UUID id = UUID.randomUUID();
        SecurityConfig sc = new SecurityConfig();
        Mockito.doNothing().doThrow(new RuntimeException()).when(securityRepository).deleteById(Mockito.any(UUID.class));
        Mockito.when(securityRepository.findById(Mockito.any(UUID.class))).thenReturn(sc);
        Response result = securityServiceImpl.deleteSecurityConfig(id.toString(), headers);
        Assert.assertEquals("Reason Not clear", result.getMsg());
    }

    @Test
    public void testCheck() {

    }

}
