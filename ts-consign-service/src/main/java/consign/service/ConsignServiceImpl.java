package consign.service;

import consign.entity.ConsignRecord;
import consign.entity.Consign;
import consign.entity.GetPriceDomain;
import consign.repository.ConsignRepository;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class ConsignServiceImpl implements ConsignService {
    @Autowired
    ConsignRepository repository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Response insertConsignRecord(Consign consignRequest, HttpHeaders headers) {
        System.out.println("[Consign servie] [ Insert new consign record]");

        ConsignRecord consignRecord = new ConsignRecord();
        //设置record属性
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

        //获得价格
        HttpEntity requestEntity = new HttpEntity(null, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-consign-price-service:16110/api/v1/consignpriceservice/consignprice/" + consignRequest.getWeight() + "/" + consignRequest.isWithin(),
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response getPriceResponse = re.getBody();

//        double price = restTemplate.postForObject(
//                "http://ts-consign-price-service:16110/consignPrice/getPrice", entity ,double.class);
        consignRecord.setPrice((Double) getPriceResponse.getData());
        //存储
        ConsignRecord result = repository.save(consignRecord);

        if (result != null) {
            return new Response(1, "You have consigned successfully! The price is" + result.getPrice(), +result.getPrice());
        } else {
            return new Response(0, "Consign failed! Please try again later!", consignRequest);
        }
    }

    @Override
    public Response updateConsignRecord(Consign consignRequest, HttpHeaders headers) {
        System.out.println("[Consign servie] [ Update consign record]");

        ConsignRecord originalRecord = repository.findById(consignRequest.getId());
        if (originalRecord == null)
            return new Response(0, "Update failed, There is no Consign Record", consignRequest);
        originalRecord.setAccountId(consignRequest.getAccountId());
        originalRecord.setHandleDate(consignRequest.getHandleDate());
        originalRecord.setTargetDate(consignRequest.getTargetDate());
        originalRecord.setFrom(consignRequest.getFrom());
        originalRecord.setTo(consignRequest.getTo());
        originalRecord.setConsignee(consignRequest.getConsignee());
        originalRecord.setPhone(consignRequest.getPhone());
        //重新计算价格
        if (originalRecord.getWeight() != consignRequest.getWeight()) {
            HttpEntity requestEntity = new HttpEntity(null, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-consign-price-service:16110/api/v1/consignpriceservice/consignprice/" + consignRequest.getWeight() + "/" +consignRequest.isWithin(),
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            Response priceResponse = re.getBody();
//            double price = restTemplate.postForObject(
//                    "http://ts-consign-price-service:16110/consignPrice/getPrice", entity ,double.class);
            originalRecord.setPrice((Double) priceResponse.getData());
        } else {
            originalRecord.setPrice(originalRecord.getPrice());
        }
        originalRecord.setWeight(consignRequest.getWeight());
        repository.save(originalRecord);
        return new Response(1, "Update consign success", originalRecord);
    }

    @Override
    public Response queryByAccountId(UUID accountId, HttpHeaders headers) {
        List<ConsignRecord> consignRecords = repository.findByAccountId(accountId);
        if (consignRecords != null && consignRecords.size() > 0)
            return new Response(1, "Find consign by account id success", consignRecords);
        else
            return new Response(0, "No Content according to accountId", accountId);
    }

    @Override
    public Response queryByConsignee(String consignee, HttpHeaders headers) {
        List<ConsignRecord> consignRecords = repository.findByConsignee(consignee);
        if (consignRecords != null && consignRecords.size() > 0)
            return new Response(1, "Find consign by consignee success", consignRecords);
        else
            return new Response(0, "No Content according to consignee", consignee);
    }
}
