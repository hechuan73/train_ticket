package consign.repository;

import consign.entity.ConsignRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface ConsignRepository extends MongoRepository<ConsignRecord, String> {
    ArrayList<ConsignRecord> findByAccountId(UUID accountId);

    ConsignRecord findByOrderId(UUID accountId);

    ArrayList<ConsignRecord> findByConsignee(String consignee);

    ConsignRecord findById(UUID id);
}
