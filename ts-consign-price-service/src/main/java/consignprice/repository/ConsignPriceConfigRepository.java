package consignprice.repository;

import consignprice.entity.ConsignPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ConsignPriceConfigRepository extends MongoRepository<ConsignPrice, String> {
    @Query("{ 'index': ?0 }")
    ConsignPrice findByIndex(int index);

}
