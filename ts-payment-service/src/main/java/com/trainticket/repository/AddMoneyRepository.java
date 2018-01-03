package com.trainticket.repository;

import com.trainticket.domain.AddMoney;
import com.trainticket.domain.Payment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2017/6/23.
 */
public interface AddMoneyRepository extends CrudRepository<AddMoney,String> {
}
