package adminbasic.service;

import adminbasic.entity.*;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import javax.annotation.Resource;

public interface AdminBasicInfoService {

    ////////////contact/////////////////////////////////////////
    Response getAllContacts(  HttpHeaders headers);

    Response addContact(  Contacts c, HttpHeaders headers);

    Response deleteContact( String contactsId, HttpHeaders headers);

    Response modifyContact(Contacts mci, HttpHeaders headers);

    ////////////////////////////station///////////////////////////////
    Response getAllStations(  HttpHeaders headers);

    Response addStation(Station s, HttpHeaders headers);

    Response deleteStation(Station s, HttpHeaders headers);

    Response modifyStation(Station s, HttpHeaders headers);

    ////////////////////////////train///////////////////////////////
    Response getAllTrains(  HttpHeaders headers);

    Response addTrain(TrainType t, HttpHeaders headers);

    Response deleteTrain(String id,   HttpHeaders headers);

    Response modifyTrain(TrainType t, HttpHeaders headers);

    ////////////////////////////config///////////////////////////////
    Response getAllConfigs(  HttpHeaders headers);

    Response addConfig(Config c, HttpHeaders headers);


    Response deleteConfig(String name, HttpHeaders headers);

    Response modifyConfig(Config c, HttpHeaders headers);

    ////////////////////////////price///////////////////////////////
    Response getAllPrices(  HttpHeaders headers);

    Response addPrice(PriceInfo pi, HttpHeaders headers);

    Response deletePrice(PriceInfo pi, HttpHeaders headers);

    Response modifyPrice(PriceInfo pi, HttpHeaders headers);

//    Contacts login(String name, String password);

}
