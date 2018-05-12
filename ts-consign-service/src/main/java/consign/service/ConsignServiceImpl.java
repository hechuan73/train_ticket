package consign.service;

import consign.domain.ConsignRecord;
import consign.domain.ConsignRequest;
import consign.domain.GetPriceDomain;
import consign.domain.InsertConsignRecordResult;
import consign.repository.ConsignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class ConsignServiceImpl implements ConsignService {
    @Autowired
    ConsignRepository repository;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public InsertConsignRecordResult insertConsignRecord(ConsignRequest consignRequest){
        System.out.println("[Consign servie] [ Insert new consign record]");

        ConsignRecord consignRecord = new ConsignRecord();

        consignRecord.setId(UUID.randomUUID());
        consignRecord.setAccountId(consignRequest.getAccountId());
        System.out.printf("The handle date is %s", consignRequest.getHandleDate());
        System.out.printf("The target date is %s", consignRequest.getTargetDate());
        consignRecord.setHandleDate(consignRequest.getHandleDate());
        consignRecord.setTargetDate(consignRequest.getTargetDate());
        consignRecord.setFrom(consignRequest.getFrom());
        consignRecord.setTo(consignRequest.getTo());
        consignRecord.setConsignee(consignRequest.getConsignee());
        consignRecord.setPhone(consignRequest.getPhone());
        consignRecord.setWeight(consignRequest.getWeight());

        GetPriceDomain domain = new GetPriceDomain();
        domain.setWeight(consignRequest.getWeight());
        domain.setWithinRegion(consignRequest.isWithin());
        double price = restTemplate.postForObject(
                "http://ts-consign-price-service:16110/consignPrice/getPrice", domain ,double.class);
        consignRecord.setPrice(price);
        //存储
        ConsignRecord result = repository.save(consignRecord);

        InsertConsignRecordResult returnResult = new InsertConsignRecordResult();
        if(result != null){
            returnResult.setStatus(true);
            returnResult.setMessage("You have consigned successfully! The price is " + result.getPrice());
        }
        else{
            returnResult.setStatus(false);
            returnResult.setMessage("Consign failed! Please try again later!");
        }
        return returnResult;
    }

    @Override
    public boolean updateConsignRecord(ConsignRequest consignRequest){
        System.out.println("[Consign servie] [ Update consign record]");

        ConsignRecord originalRecord = repository.findById(consignRequest.getId());
        if(originalRecord == null)
            return false;
        originalRecord.setAccountId(consignRequest.getAccountId());
        originalRecord.setHandleDate(consignRequest.getHandleDate());
        originalRecord.setTargetDate(consignRequest.getTargetDate());
        originalRecord.setFrom(consignRequest.getFrom());
        originalRecord.setTo(consignRequest.getTo());
        originalRecord.setConsignee(consignRequest.getConsignee());
        originalRecord.setPhone(consignRequest.getPhone());

        if(originalRecord.getWeight() != consignRequest.getWeight()){
            GetPriceDomain domain = new GetPriceDomain();
            domain.setWeight(consignRequest.getWeight());
            domain.setWithinRegion(consignRequest.isWithin());
            double price = restTemplate.postForObject(
                    "http://ts-consign-price-service:16110/consignPrice/getPrice", domain ,double.class);
            originalRecord.setPrice(price);
        }
        else{
            originalRecord.setPrice(originalRecord.getPrice());
        }
        originalRecord.setWeight(consignRequest.getWeight());
        repository.save(originalRecord);
        return true;
    }

    @Override
    public ArrayList<ConsignRecord> queryByAccountId(UUID accountId) {
        return repository.findByAccountId(accountId);
    }

    @Override
    public ArrayList<ConsignRecord> queryByConsignee(String consignee) {
        return repository.findByConsignee(consignee);
    }
}
