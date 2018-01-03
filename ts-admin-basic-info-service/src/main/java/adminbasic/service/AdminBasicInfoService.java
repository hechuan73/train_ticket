package adminbasic.service;

import adminbasic.domin.bean.Config;
import adminbasic.domin.bean.Contacts;
import adminbasic.domin.bean.Station;
import adminbasic.domin.bean.TrainType;
import adminbasic.domin.info.*;
import adminbasic.domin.reuslt.*;

public interface AdminBasicInfoService {

    ////////////contact/////////////////////////////////////////
    GetAllContactsResult getAllContacts(String loginId);

    AddContactsResult addContact(String loginId, Contacts c);

    DeleteContactsResult deleteContact(String loginId, DeleteContactsInfo dci);

    ModifyContactsResult modifyContact(String loginId, ModifyContactsInfo mci);

    ////////////////////////////station///////////////////////////////
    GetAllStationResult getAllStations(String loginId);

    boolean addStation(Station s);

    boolean deleteStation( Station s);

    boolean modifyStation( Station s);

    ////////////////////////////train///////////////////////////////
    GetAllTrainResult getAllTrains(String loginId);

    boolean addTrain(TrainType t);

    boolean deleteTrain(TrainInfo2 t);

    boolean modifyTrain( TrainType t);

    ////////////////////////////config///////////////////////////////
    GetAllConfigResult getAllConfigs(String loginId);

    String addConfig(Config c);

    String deleteConfig(ConfigInfo2 ci);

    String modifyConfig( Config c);

    ////////////////////////////price///////////////////////////////
    GetAllPriceResult getAllPrices(String loginId);

    ReturnSinglePriceConfigResult addPrice(PriceInfo pi);

    boolean deletePrice(PriceInfo pi);

    boolean modifyPrice(PriceInfo pi);

//    Contacts login(String name, String password);

}
