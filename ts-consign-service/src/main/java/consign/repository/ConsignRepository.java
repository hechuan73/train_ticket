package consign.repository;

import consign.domain.ConsignRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface ConsignRepository extends MongoRepository<ConsignRecord,String> {
    //按照办理托运的用户ID进行查找
    ArrayList<ConsignRecord> findByAccountId(UUID accountId);

    //按照办理托运的用户ID进行查找
    ArrayList<ConsignRecord> findByConsignee(String consignee);

    //按照托运ID进行查找
    ConsignRecord findById(UUID id);
}
