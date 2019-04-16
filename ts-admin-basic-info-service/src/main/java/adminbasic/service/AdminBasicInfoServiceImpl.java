package adminbasic.service;

import adminbasic.entity.*;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AdminBasicInfoServiceImpl implements AdminBasicInfoService {

    @Autowired
    private RestTemplate restTemplate;

    private String adminID = "1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f";


    @Override
    public Response getAllContacts(String loginId, HttpHeaders headers) {
        Response result;
        if (adminID.equals(loginId)) {
            HttpEntity requestEntity = new HttpEntity(headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-contacts-service:12347/api/v1/contactservice/ontacts",
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            result = re.getBody();
//            result = restTemplate.getForObject(
//                    "http://ts-contacts-service:12347/contacts/findAll",
//                    GetAllContactsResult.class);
        } else {
            result = new Response<>(0, "The loginId is Wrong: " + loginId, null);
        }
        return result;
    }

    @Override
    public Response deleteContact(String loginId, String contactsId, HttpHeaders headers) {
        Response result;
        if (adminID.equals(loginId)) {
            HttpEntity requestEntity = new HttpEntity(headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-contacts-service:12347/api/v1/contactservice/contacts/" + contactsId,
                    HttpMethod.DELETE,
                    requestEntity,
                    Response.class);
            result = re.getBody();
//            result = restTemplate.postForObject(
//                    "http://ts-contacts-service:12347/contacts/deleteContacts",dci,
//                    DeleteContactsResult.class);
        } else {
            result = new Response();
            result.setStatus(0);
            result.setMsg("The loginId is Wrong: " + loginId);
        }
        return result;
    }

    @Override
    public Response modifyContact(Contacts mci, HttpHeaders headers) {
        Response result;
        if (adminID.equals(mci.getLoginId())) {
            HttpEntity requestEntity = new HttpEntity(mci, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-contacts-service:12347/api/v1/contactservice/contacts",
                    HttpMethod.PUT,
                    requestEntity,
                    Response.class);
            result = re.getBody();
//            result = restTemplate.postForObject(
//                    "http://ts-contacts-service:12347/contacts/modifyContacts",mci,
//                    ModifyContactsResult.class);
        } else {
            result = new Response();
            result.setStatus(0);
            result.setMsg("The loginId is Wrong: " + mci.getLoginId());
        }
        return result;
    }


    @Override
    public Response addContact(String loginId, Contacts c, HttpHeaders headers) {
        Response result;
        if (adminID.equals(loginId)) {
            HttpEntity requestEntity = new HttpEntity(c, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-contacts-service:12347/api/v1/contactservice/contacts/admin",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
            result = re.getBody();
//            result = restTemplate.postForObject(
//                    "http://ts-contacts-service:12347/contacts/admincreate",c,
//                    AddContactsResult.class);

        } else {
            result = new Response();
            result.setStatus(0);
            result.setMsg("The Contact add operation failed.");
        }
        return result;
    }

    //////////////station////////////////////////////////////////////////
    @Override
    public Response getAllStations(String loginId, HttpHeaders headers) {
        if (adminID.equals(loginId)) {
            List<Station> l;
            HttpEntity requestEntity = new HttpEntity(headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-station-service:12345/api/v1/stationservice/tations",
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            l = (List<Station>) re.getBody().getData();
//            l= restTemplate.getForObject("http://ts-station-service:12345/station/query", l.getClass());
            return new Response<>(1, "Success", l);
        } else {
            return new Response(0, "The loginId is Wrong: " + loginId, null);
        }

    }

    @Override
    public Response addStation(Station s, HttpHeaders headers) {
        Response result;
        if (adminID.equals(s.getLoginId())) {
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
        return new Response<>(0, "Login Id is not admin id", s);
    }

    @Override
    public Response deleteStation(Station s, HttpHeaders headers) {
        Response result;
        if (adminID.equals(s.getLoginId())) {
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
        return new Response<>(0, "Login Id is not admin id", s);
    }

    @Override
    public Response modifyStation(Station s, HttpHeaders headers) {
        Response result;
        if (adminID.equals(s.getLoginId())) {
            HttpEntity requestEntity = new HttpEntity(s, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-station-service:12345/api/v1/stationservice/stations",
                    HttpMethod.PATCH,
                    requestEntity,
                    Response.class);
            result = re.getBody();
//            result = restTemplate.postForObject("http://ts-station-service:12345/station/update",s, Boolean.class);
            return result;
        }
        return new Response<>(0, "Login Id is not admin id", s);
    }

    //////////////train////////////////////////////////////////////////
    @Override
    public Response getAllTrains(String loginId, HttpHeaders headers) {

        if (adminID.equals(loginId)) {
            List<TrainType> l;
            HttpEntity requestEntity = new HttpEntity(headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-train-service:14567/api/v1/trainservice/trains",
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
            l = (List<TrainType>) re.getBody().getData();

//            List<TrainType> l = new ArrayList<TrainType>();
//            l = restTemplate.getForObject("http://ts-train-service:14567/train/query", l.getClass());
            return new Response<>(1, "Success", l);
        } else {
            return new Response<>(0, "The loginId is wrong:" + loginId, null);
        }
    }

    @Override
    public Response addTrain(TrainType t, HttpHeaders headers) {
        Response result;
        if (adminID.equals(t.getLoginId())) {
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
        return new Response<>(0, "The loginId is wrong:" + t.getLoginId(), null);

    }

    @Override
    public Response deleteTrain(String id, String loginId, HttpHeaders headers) {
        Response result;
        if (adminID.equals(loginId)) {
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
        return new Response<>(0, "The loginId is wrong:" + loginId, null);
    }

    @Override
    public Response modifyTrain(TrainType t, HttpHeaders headers) {
        Response result;
        if (adminID.equals(t.getLoginId())) {
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
        return new Response<>(0, "The loginId is wrong:" + t.getLoginId(), null);
    }

    //////////////config////////////////////////////////////////////////
    @Override
    public Response getAllConfigs(String loginId, HttpHeaders headers) {

        if (adminID.equals(loginId)) {
            HttpEntity requestEntity = new HttpEntity(headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-config-service:15679/api/v1/config/configs",
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);
//            List<Config> l = new ArrayList<Config>();
//            l = restTemplate.getForObject("http://ts-config-service:15679/config/queryAll", l.getClass());

            return re.getBody();
        } else {
            return new Response<>(0, "The loginId is wrong:" + loginId, null);
        }

    }

    @Override
    public Response addConfig(Config c, HttpHeaders headers) {

        if (adminID.equals(c.getLoginId())) {
            HttpEntity requestEntity = new HttpEntity(c, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-config-service:15679/api/v1/config/configs",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
//            result = restTemplate.postForObject("http://ts-config-service:15679/config/create",c, String.class);
            return re.getBody();
        }
        return new Response<>(0, "The loginId is wrong:" + c.getLoginId(), null);
    }

    @Override
    public Response deleteConfig(String name,String loginId, HttpHeaders headers) {

        if (adminID.equals(loginId)) {
            HttpEntity requestEntity = new HttpEntity(headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-config-service:15679/api/v1/config/configs/" + name,
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);

//            result = restTemplate.postForObject("http://ts-config-service:15679/config/delete",ci, String.class);
            return re.getBody();
        }
        return new Response<>(0, "The loginId is wrong:" + loginId, null);
    }

    @Override
    public Response modifyConfig(Config c, HttpHeaders headers) {

        if (adminID.equals(c.getLoginId())) {
            HttpEntity requestEntity = new HttpEntity(c, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-config-service:15679/api/v1/config/configs",
                    HttpMethod.PATCH,
                    requestEntity,
                    Response.class);
//            result = restTemplate.postForObject("http://ts-config-service:15679/config/update",c, String.class);
            return re.getBody();
        }
        return new Response<>(0, "The loginId is wrong:" + c.getLoginId(), null);
    }

    //////////////price////////////////////////////////////////////////
    @Override
    public Response getAllPrices(String loginId, HttpHeaders headers) {

        if (adminID.equals(loginId)) {
            HttpEntity requestEntity = new HttpEntity(headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-price-service:16579/api/v1/priceservice/prices",
                    HttpMethod.GET,
                    requestEntity,
                    Response.class);

//            result = restTemplate.getForObject("http://ts-price-service:16579/price/queryAll", GetAllPriceResult.class);
            System.out.println("[!!!!GetAllPriceResult] ");
            return re.getBody();
        } else {
            return new Response<>(0, "The loginId is wrong:" + loginId, null);
        }
    }

    @Override
    public Response addPrice(PriceInfo pi, HttpHeaders headers) {
        if (adminID.equals(pi.getLoginId())) {
            HttpEntity requestEntity = new HttpEntity(pi, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-price-service:16579/api/v1/priceservice/prices",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
//            result = restTemplate.postForObject("http://ts-price-service:16579/price/create",pi, ReturnSinglePriceConfigResult.class);
            return re.getBody();
        } else {
            return new Response<>(0, "The loginId is wrong:" + pi.getLoginId(), null);
        }
    }

    @Override
    public Response deletePrice(PriceInfo pi, HttpHeaders headers) {

        if (adminID.equals(pi.getLoginId())) {
            HttpEntity requestEntity = new HttpEntity(pi, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-price-service:16579/api/v1/priceservice/prices",
                    HttpMethod.DELETE,
                    requestEntity,
                    Response.class);

//            result = restTemplate.postForObject("http://ts-price-service:16579/price/delete",pi, Boolean.class);
            return re.getBody();
        }
        return new Response<>(0, "The loginId is wrong:" + pi.getLoginId(), null);
    }

    @Override
    public Response modifyPrice(PriceInfo pi, HttpHeaders headers) {
        if (adminID.equals(pi.getLoginId())) {
            HttpEntity requestEntity = new HttpEntity(pi, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-price-service:16579/api/v1/priceservice/prices",
                    HttpMethod.PUT,
                    requestEntity,
                    Response.class);
//            result = restTemplate.postForObject("http://ts-price-service:16579/price/update",pi, Boolean.class);
            return re.getBody();
        }
        return new Response<>(0, "The loginId is wrong:" + pi.getLoginId(), null);
    }
}
