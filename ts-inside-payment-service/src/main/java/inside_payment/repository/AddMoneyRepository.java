package inside_payment.repository;

import inside_payment.entity.Money;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AddMoneyRepository extends CrudRepository<Money,String> {
    List<Money> findByUserId(String userId);
    List<Money> findAll();
}
