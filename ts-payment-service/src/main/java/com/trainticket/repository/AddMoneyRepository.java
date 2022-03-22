package com.trainticket.repository;

import com.trainticket.entity.Money;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author fdse
 */
public interface AddMoneyRepository extends MongoRepository<Money,String> {
}
