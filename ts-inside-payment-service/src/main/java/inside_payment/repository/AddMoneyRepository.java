package inside_payment.repository;

import inside_payment.domain.AddMoney;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */
public interface AddMoneyRepository extends CrudRepository<AddMoney,String> {
    List<AddMoney> findByUserId(String userId);
    List<AddMoney> findAll();
}
