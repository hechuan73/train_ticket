package consign.service;

import consign.entity.ConsignRecord;
import consign.entity.ConsignRequest;
import consign.entity.InsertConsignRecordResult;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.UUID;

public interface ConsignService {
    InsertConsignRecordResult insertConsignRecord(ConsignRequest consignRequest, HttpHeaders headers);
    boolean updateConsignRecord(ConsignRequest consignRequest, HttpHeaders headers);
    ArrayList<ConsignRecord> queryByAccountId(UUID accountId, HttpHeaders headers);
    ArrayList<ConsignRecord> queryByConsignee(String consignee, HttpHeaders headers);
}
