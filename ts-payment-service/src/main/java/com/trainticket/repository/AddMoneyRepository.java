package com.trainticket.repository;

import com.trainticket.entity.Money;
import org.springframework.data.repository.CrudRepository;


public interface AddMoneyRepository extends CrudRepository<Money,String> {
}
