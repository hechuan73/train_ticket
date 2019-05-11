package adminbasic.service;

import adminbasic.entity.*;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class AdminBasicInfoServiceImpl implements AdminBasicInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response getAllContacts(HttpHeaders headers) {
        Response result;

        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-contacts-service:12347/api/v1/contactservice/contacts",
                HttpMethod.GET,
                requestEntity,
                Response.class);
        result = re.getBody();

        return result;
    }

    @Override
    public Response deleteContact(String contactsId, HttpHeaders headers) {
        Response result;

        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-contacts-service:12347/api/v1/contactservice/contacts/" + contactsId,
                HttpMethod.DELETE,
                requestEntity,
                Response.class);
        result = re.getBody();

        return result;
    }

    @Override
    public Response modifyContact(Contacts mci, HttpHeaders headers) {
        Response result;
        log.info("MODIFY CONTACTS: " + mci.toString());
        HttpEntity requestEntity = new HttpEntity(mci, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-contacts-service:12347/api/v1/contactservice/contacts",
                HttpMethod.PUT,
                requestEntity,
                Response.class);
        result = re.getBody();

        return result;
    }


    @Override
    public Response addContact(Contacts c, HttpHeaders headers) {
        Response result;

        HttpEntity requestEntity = new HttpEntity(c, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-contacts-service:12347/api/v1/contactservice/contacts/admin",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        result = re.getBody();

        return result;
    }

    //////////////station////////////////////////////////////////////////
    @Override
    public Response getAllStations(HttpHeaders headers) {

        List<Station> l;
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations",
                HttpMethod.GET,
                requestEntity,
                Response.class);

        return re.getBody();


    }

    @Override
    public Response addStation(Station s, HttpHeaders headers) {
        Response result;

        HttpEntity requestEntity = new HttpEntity(s, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        result = re.getBody();
//            result = restTemplate.postForObject("http://ts-station-service:12345/station/create",s, Boolean.class);
        return result;
    }

    @Override
    public Response deleteStation(Station s, HttpHeaders headers) {
        Response result;

        HttpEntity requestEntity = new HttpEntity(s, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations",
                HttpMethod.DELETE,
                requestEntity,
                Response.class);
        result = re.getBody();
//            result = restTemplate.postForObject("http://ts-station-service:12345/station/delete",s, Boolean.class);
        return result;

    }

    @Override
    public Response modifyStation(Station s, HttpHeaders headers) {
        Response result;

        HttpEntity requestEntity = new HttpEntity(s, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations",
                HttpMethod.PUT,
                requestEntity,
                Response.class);
        result = re.getBody();

//            result = restTemplate.postForObject("http://ts-station-service:12345/station/update",s, Boolean.class);
        return result;

    }

    //////////////train////////////////////////////////////////////////
    @Override
    public Response getAllTrains(HttpHeaders headers) {

        List<TrainType> l;
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-train-service:14567/api/v1/trainservice/trains",
                HttpMethod.GET,
                requestEntity,
                Response.class);

        return re.getBody();

    }

    @Override
    public Response addTrain(TrainType t, HttpHeaders headers) {
        Response result;

        HttpEntity requestEntity = new HttpEntity(t, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-train-service:14567/api/v1/trainservice/trains",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        result = re.getBody();
//            result = restTemplate.postForObject("http://ts-train-service:14567/train/create",t, Boolean.class);
        return result;

    }

    @Override
    public Response deleteTrain(String id, HttpHeaders headers) {
        Response result;

        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-train-service:14567/api/v1/trainservice/trains/" + id,
                HttpMethod.DELETE,
                requestEntity,
                Response.class);
        result = re.getBody();
//            result = restTemplate.postForObject("http://ts-train-service:14567/train/delete",t, Boolean.class);
        return result;
    }

    @Override
    public Response modifyTrain(TrainType t, HttpHeaders headers) {
        Response result;

        HttpEntity requestEntity = new HttpEntity(t, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-train-service:14567/api/v1/trainservice/trains",
                HttpMethod.PUT,
                requestEntity,
                Response.class);
        result = re.getBody();
//            result = restTemplate.postForObject("http://ts-train-service:14567/train/update",t, Boolean.class);
        return result;
    }

    //////////////config////////////////////////////////////////////////
    @Override
    public Response getAllConfigs(HttpHeaders headers) {


        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-config-service:15679/api/v1/configservice/configs",
                HttpMethod.GET,
                requestEntity,
                Response.class);

        return re.getBody();
    }

    @Override
    public Response addConfig(Config c, HttpHeaders headers) {


        HttpEntity requestEntity = new HttpEntity(c, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-config-service:15679/api/v1/configservice/configs",
                HttpMethod.POST,
                requestEntity,
                Response.class);
//            result = restTemplate.postForObject("http://ts-config-service:15679/config/create",c, String.class);
        return re.getBody();
    }

    @Override
    public Response deleteConfig(String name, HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-config-service:15679/api/v1/configservice/configs/" + name,
                HttpMethod.DELETE,
                requestEntity,
                Response.class);

//            result = restTemplate.postForObject("http://ts-config-service:15679/config/delete",ci, String.class);
        return re.getBody();
    }

    @Override
    public Response modifyConfig(Config c, HttpHeaders headers) {


        HttpEntity requestEntity = new HttpEntity(c, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-config-service:15679/api/v1/configservice/configs",
                HttpMethod.PUT,
                requestEntity,
                Response.class);
//            result = restTemplate.postForObject("http://ts-config-service:15679/config/update",c, String.class);
        return re.getBody();
    }

    //////////////price////////////////////////////////////////////////
    @Override
    public Response getAllPrices(HttpHeaders headers) {


        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-price-service:16579/api/v1/priceservice/prices",
                HttpMethod.GET,
                requestEntity,
                Response.class);

//            result = restTemplate.getForObject("http://ts-price-service:16579/price/queryAll", GetAllPriceResult.class);
        System.out.println("[!!!!GetAllPriceResult] ");
        return re.getBody();
    }

    @Override
    public Response addPrice(PriceInfo pi, HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(pi, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-price-service:16579/api/v1/priceservice/prices",
                HttpMethod.POST,
                requestEntity,
                Response.class);
//            result = restTemplate.postForObject("http://ts-price-service:16579/price/create",pi, ReturnSinglePriceConfigResult.class);
        return re.getBody();

    }

    @Override
    public Response deletePrice(PriceInfo pi, HttpHeaders headers) {


        HttpEntity requestEntity = new HttpEntity(pi, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-price-service:16579/api/v1/priceservice/prices",
                HttpMethod.DELETE,
                requestEntity,
                Response.class);

//            result = restTemplate.postForObject("http://ts-price-service:16579/price/delete",pi, Boolean.class);
        return re.getBody();

    }

    @Override
    public Response modifyPrice(PriceInfo pi, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(pi, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-price-service:16579/api/v1/priceservice/prices",
                HttpMethod.PUT,
                requestEntity,
                Response.class);
//            result = restTemplate.postForObject("http://ts-price-service:16579/price/update",pi, Boolean.class);
        return re.getBody();
    }
}
