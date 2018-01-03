package adminbasic.service;

import adminbasic.domin.bean.*;
import adminbasic.domin.info.*;
import adminbasic.domin.reuslt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminBasicInfoServiceImpl implements AdminBasicInfoService{

    @Autowired
    private RestTemplate restTemplate;

    private String adminID="1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f";


    @Override
    public  GetAllContactsResult getAllContacts(String loginId) {
        GetAllContactsResult result ;
        if(adminID.equals(loginId)){
            result = restTemplate.getForObject(
                    "http://ts-contacts-service:12347/contacts/findAll",
                    GetAllContactsResult.class);
        } else {
            result = new GetAllContactsResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + loginId);
        }

        return result;
    }

    @Override
    public DeleteContactsResult deleteContact(String loginId, DeleteContactsInfo dci) {
        DeleteContactsResult result;
        if(adminID.equals(loginId)){
            result = restTemplate.postForObject(
                    "http://ts-contacts-service:12347/contacts/deleteContacts",dci,
                    DeleteContactsResult.class);
        } else {
            result = new DeleteContactsResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + loginId);
        }
        return result;
    }

    @Override
    public ModifyContactsResult modifyContact(String loginId, ModifyContactsInfo mci) {
        ModifyContactsResult result;
        if(adminID.equals(loginId)){
            result = restTemplate.postForObject(
                    "http://ts-contacts-service:12347/contacts/modifyContacts",mci,
                    ModifyContactsResult.class);
        } else {
            result = new ModifyContactsResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + loginId);
        }
        return result;
    }


    @Override
    public AddContactsResult addContact(String loginId, Contacts c) {
        AddContactsResult result;
        if (adminID.equals(loginId)) {
            result = restTemplate.postForObject(
                    "http://ts-contacts-service:12347/contacts/admincreate",c,
                    AddContactsResult.class);

        } else {
            result = new AddContactsResult();
            result.setStatus(false);
            result.setMessage("The Contact add operation failed.");
        }
        return result;
    }

    //////////////station////////////////////////////////////////////////
    @Override
    public GetAllStationResult getAllStations(String loginId) {
        GetAllStationResult result = new GetAllStationResult();;
        if (adminID.equals(loginId)) {
            List<Station> l = new ArrayList<Station>();
            l= restTemplate.getForObject("http://ts-station-service:12345/station/query", l.getClass());
            result.setStatus(true);
            result.setMessage("Success");
            result.setStationList(l);
        } else {
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + loginId);
        }
        return result;
    }

    @Override
    public boolean addStation(Station s) {
        boolean result = false;
        if (adminID.equals(s.getLoginId())) {
            result = restTemplate.postForObject("http://ts-station-service:12345/station/create",s, Boolean.class);
            return result;
        }
        return result;
    }

    @Override
    public boolean deleteStation(Station s) {
        boolean result = false;
        if (adminID.equals(s.getLoginId())) {
            result = restTemplate.postForObject("http://ts-station-service:12345/station/delete",s, Boolean.class);
            return result;
        }
        return result;
    }

    @Override
    public boolean modifyStation(Station s) {
        boolean result = false;
        if (adminID.equals(s.getLoginId())) {
            result = restTemplate.postForObject("http://ts-station-service:12345/station/update",s, Boolean.class);
            return result;
        }
        return result;
    }

    //////////////train////////////////////////////////////////////////
    @Override
    public GetAllTrainResult getAllTrains(String loginId) {
        GetAllTrainResult result = new GetAllTrainResult();
        if (adminID.equals(loginId)) {
            List<TrainType> l = new ArrayList<TrainType>();
            l = restTemplate.getForObject("http://ts-train-service:14567/train/query", l.getClass());
            result.setStatus(true);
            result.setMessage("Success");
            result.setTrainList(l);
            return result;
        } else {
            result.setStatus(false);
            result.setMessage("The loginId is wrong:"+ loginId);
        }
        return result;
    }

    @Override
    public boolean addTrain(TrainType t) {
        boolean result = false;
        if (adminID.equals(t.getLoginId())) {
            result = restTemplate.postForObject("http://ts-train-service:14567/train/create",t, Boolean.class);
            return result;
        }
        return result;
    }

    @Override
    public boolean deleteTrain(TrainInfo2 t) {
        boolean result = false;
        if (adminID.equals(t.getLoginId())) {
            result = restTemplate.postForObject("http://ts-train-service:14567/train/delete",t, Boolean.class);
            return result;
        }
        return result;
    }

    @Override
    public boolean modifyTrain(TrainType t) {
        boolean result = false;
        if (adminID.equals(t.getLoginId())) {
            result = restTemplate.postForObject("http://ts-train-service:14567/train/update",t, Boolean.class);
            return result;
        }
        return result;
    }

    //////////////config////////////////////////////////////////////////
    @Override
    public GetAllConfigResult getAllConfigs(String loginId) {
        GetAllConfigResult result = new GetAllConfigResult();
        if (adminID.equals(loginId)) {
            List<Config> l = new ArrayList<Config>();
            l = restTemplate.getForObject("http://ts-config-service:15679/config/queryAll", l.getClass());
            result.setStatus(true);
            result.setMessage("Success");
            result.setConfigs(l);
            return result;
        } else {
            result.setStatus(false);
            result.setMessage("The loginId is wrong:"+ loginId);
        }
        return result;
    }

    @Override
    public String addConfig(Config c) {
        String result = null;
        if (adminID.equals(c.getLoginId())) {
            result = restTemplate.postForObject("http://ts-config-service:15679/config/create",c, String.class);
            return result;
        }
        return result;
    }

    @Override
    public String deleteConfig(ConfigInfo2 ci) {
        String result = null;
        if (adminID.equals(ci.getLoginId())) {
            result = restTemplate.postForObject("http://ts-config-service:15679/config/delete",ci, String.class);
            return result;
        }
        return result;
    }

    @Override
    public String modifyConfig(Config c) {
        String result = null;
        if (adminID.equals(c.getLoginId())) {
            result = restTemplate.postForObject("http://ts-config-service:15679/config/update",c, String.class);
            return result;
        }
        return result;
    }

    //////////////price////////////////////////////////////////////////
    @Override
    public GetAllPriceResult getAllPrices(String loginId) {
        GetAllPriceResult result = new GetAllPriceResult();
        if (adminID.equals(loginId)) {
            result = restTemplate.getForObject("http://ts-price-service:16579/price/queryAll", GetAllPriceResult.class);
            System.out.println("[!!!!GetAllPriceResult] " + result.getPriceConfig());
            return result;
        } else {
            result.setStatus(false);
            result.setMessage("The loginId is wrong:"+ loginId);
        }
        return result;
    }

    @Override
    public ReturnSinglePriceConfigResult addPrice(PriceInfo pi) {
        ReturnSinglePriceConfigResult result = new ReturnSinglePriceConfigResult();
        if (adminID.equals(pi.getLoginId())) {
            result = restTemplate.postForObject("http://ts-price-service:16579/price/create",pi, ReturnSinglePriceConfigResult.class);
            return result;
        } else {
            result.setStatus(false);
            result.setMessage("The loginId is wrong:"+ pi.getLoginId());
        }
        return result;
    }

    @Override
    public boolean deletePrice(PriceInfo pi) {
        boolean result = false;
        if (adminID.equals(pi.getLoginId())) {
            result = restTemplate.postForObject("http://ts-price-service:16579/price/delete",pi, Boolean.class);
            return result;
        }
        return result;
    }

    @Override
    public boolean modifyPrice(PriceInfo pi) {
        boolean result = false;
        if (adminID.equals(pi.getLoginId())) {
            result = restTemplate.postForObject("http://ts-price-service:16579/price/update",pi, Boolean.class);
            return result;
        }
        return result;
    }


}
