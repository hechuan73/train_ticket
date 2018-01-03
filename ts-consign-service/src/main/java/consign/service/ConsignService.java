package consign.service;

import consign.domain.ConsignRecord;
import consign.domain.ConsignRequest;
import consign.domain.InsertConsignRecordResult;

import java.util.ArrayList;
import java.util.UUID;

public interface ConsignService {
    InsertConsignRecordResult insertConsignRecord(ConsignRequest consignRequest);
    boolean updateConsignRecord(ConsignRequest consignRequest);
    ArrayList<ConsignRecord> queryByAccountId(UUID accountId);
    ArrayList<ConsignRecord> queryByConsignee(String consignee);
}
