package inside_payment.service;

import edu.fudan.common.util.Response;
import inside_payment.entity.AccountInfo;
import inside_payment.entity.Money;
import inside_payment.entity.Payment;
import inside_payment.repository.AddMoneyRepository;
import inside_payment.repository.PaymentRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(JUnit4.class)
public class InsidePaymentServiceImplTest {

    @InjectMocks
    private InsidePaymentServiceImpl insidePaymentServiceImpl;

    @Mock
    private AddMoneyRepository addMoneyRepository;

    @Mock
    private PaymentRepository paymentRepository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPay() {

    }

    @Test
    public void testCreateAccount1() {
        AccountInfo info = new AccountInfo();
        List<Money> list = new ArrayList<>();
        Mockito.when(addMoneyRepository.findByUserId(Mockito.anyString())).thenReturn(list);
        Mockito.when(addMoneyRepository.save(Mockito.any(Money.class))).thenReturn(null);
        Response result = insidePaymentServiceImpl.createAccount(info, headers);
        Assert.assertEquals(new Response<>(1, "Create Account Success", null), result);
    }

    @Test
    public void testCreateAccount2() {
        AccountInfo info = new AccountInfo();
        List<Money> list = new ArrayList<>();
        list.add(new Money());
        Mockito.when(addMoneyRepository.findByUserId(Mockito.anyString())).thenReturn(list);
        Response result = insidePaymentServiceImpl.createAccount(info, headers);
        Assert.assertEquals(new Response<>(0, "Create Account Failed, Account already Exists", null), result);
    }

    @Test
    public void testAddMoney1() {
        List<Money> list = new ArrayList<>();
        Mockito.when(addMoneyRepository.findByUserId(Mockito.anyString())).thenReturn(list);
        Mockito.when(addMoneyRepository.save(Mockito.any(Money.class))).thenReturn(null);
        Response result = insidePaymentServiceImpl.addMoney("user_id", "money", headers);
        Assert.assertEquals(new Response<>(1, "Add Money Success", null), result);
    }

    @Test
    public void testAddMoney2() {
        Mockito.when(addMoneyRepository.findByUserId(Mockito.anyString())).thenReturn(null);
        Response result = insidePaymentServiceImpl.addMoney("user_id", "money", headers);
        Assert.assertEquals(new Response<>(0, "Add Money Failed", null), result);
    }

    @Test
    public void testQueryAccount() {

    }

    @Test
    public void testQueryPayment1() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment());
        Mockito.when(paymentRepository.findAll()).thenReturn(payments);
        Response result = insidePaymentServiceImpl.queryPayment(headers);
        Assert.assertEquals(new Response<>(1, "Query Payment Success", payments), result);
    }

    @Test
    public void testQueryPayment2() {
        Mockito.when(paymentRepository.findAll()).thenReturn(null);
        Response result = insidePaymentServiceImpl.queryPayment(headers);
        Assert.assertEquals(new Response<>(0, "Query Payment Failed", null), result);
    }

    @Test
    public void testDrawBack1() {
        List<Money> list = new ArrayList<>();
        Mockito.when(addMoneyRepository.findByUserId(Mockito.anyString())).thenReturn(list);
        Mockito.when(addMoneyRepository.save(Mockito.any(Money.class))).thenReturn(null);
        Response result = insidePaymentServiceImpl.drawBack("user_id", "money", headers);
        Assert.assertEquals(new Response<>(1, "Draw Back Money Success", null), result);
    }

    @Test
    public void testDrawBack2() {
        Mockito.when(addMoneyRepository.findByUserId(Mockito.anyString())).thenReturn(null);
        Response result = insidePaymentServiceImpl.drawBack("user_id", "money", headers);
        Assert.assertEquals(new Response<>(0, "Draw Back Money Failed", null), result);
    }

    @Test
    public void testPayDifference() {

    }

    @Test
    public void testQueryAddMoney1() {
        List<Money> monies = new ArrayList<>();
        monies.add(new Money());
        Mockito.when(addMoneyRepository.findAll()).thenReturn(monies);
        Response result = insidePaymentServiceImpl.queryAddMoney(headers);
        Assert.assertEquals(new Response<>(1, "Query Money Success", null), result);
    }

    @Test
    public void testQueryAddMoney2() {
        Mockito.when(addMoneyRepository.findAll()).thenReturn(null);
        Response result = insidePaymentServiceImpl.queryAddMoney(headers);
        Assert.assertEquals(new Response<>(0, "", null), result);
    }

    @Test
    public void testInitPayment1() {
        Payment payment = new Payment();
        Mockito.when(paymentRepository.findById(Mockito.anyString())).thenReturn(null);
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(null);
        insidePaymentServiceImpl.initPayment(payment, headers);
        Mockito.verify(paymentRepository, times(1)).save(Mockito.any(Payment.class));
    }

    @Test
    public void testInitPayment2() {
        Payment payment = new Payment();
        Mockito.when(paymentRepository.findById(Mockito.anyString())).thenReturn(payment);
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(null);
        insidePaymentServiceImpl.initPayment(payment, headers);
        Mockito.verify(paymentRepository, times(0)).save(Mockito.any(Payment.class));
    }

}
