package inside_payment.repository;

import inside_payment.domain.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */
public interface PaymentRepository extends CrudRepository<Payment,String> {
    Payment findById(String id);
    List<Payment> findByOrderId(String orderId);
    List<Payment> findAll();
    List<Payment> findByUserId(String userId);
}
