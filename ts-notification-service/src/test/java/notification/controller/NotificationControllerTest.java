package notification.controller;

import notification.entity.NotifyInfo;
import notification.service.NotificationService;
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

@RunWith(JUnit4.class)
public class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService service;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = notificationController.home();
        Assert.assertEquals("Welcome to [ Notification Service ] !", result);
    }

    @Test
    public void testPreserveSuccess(){
        NotifyInfo info = new NotifyInfo();
        Mockito.when(service.preserveSuccess(info, headers)).thenReturn(true);
        Assert.assertTrue(notificationController.preserve_success(info, headers));
    }

    @Test
    public void testOrderCreateSuccess(){
        NotifyInfo info = new NotifyInfo();
        Mockito.when(service.orderCreateSuccess(info, headers)).thenReturn(true);
        Assert.assertTrue(notificationController.order_create_success(info, headers));
    }

    @Test
    public void testOrderChangedSuccess(){
        NotifyInfo info = new NotifyInfo();
        Mockito.when(service.orderChangedSuccess(info, headers)).thenReturn(true);
        Assert.assertTrue(notificationController.order_changed_success(info, headers));
    }

    @Test
    public void testOrderCancelSuccess(){
        NotifyInfo info = new NotifyInfo();
        Mockito.when(service.orderCancelSuccess(info, headers)).thenReturn(true);
        Assert.assertTrue(notificationController.order_cancel_success(info, headers));
    }

}
