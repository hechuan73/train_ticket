package adminbasic.controller;

import adminbasic.entity.*;
import adminbasic.service.AdminBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/adminbasicservice")
public class AdminBasicInfoController {

    @Autowired
    AdminBasicInfoService adminBasicInfoService;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminBasicInfo Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/adminbasic/contacts/{id}")
    public HttpEntity getAllContacts(@PathVariable String loginId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Find All Contacts by admin: " + loginId);
        return ok(adminBasicInfoService.getAllContacts(loginId, headers));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/adminbasic/contacts/{loginId}/{contactsId}")
    public HttpEntity deleteContacts(@PathVariable String loginId, @PathVariable String contactsId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Delete Contacts by admin: " + loginId);
        return ok(adminBasicInfoService.deleteContact(loginId, contactsId, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/adminbasic/contacts")
    public HttpEntity modifyContacts(@RequestBody Contacts mci, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Modify Contacts by admin: " + mci.getLoginId());
        return ok(adminBasicInfoService.modifyContact(mci, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/adminbasic/contacts")
    public HttpEntity addContacts(@RequestBody Contacts c, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Modify Contacts by admin: " + c.getLoginId());
        return ok(adminBasicInfoService.addContact(c.getLoginId(), c, headers));
    }

    /////////////////////////station/////////////////////////////////////////////////////////////////////////////////
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/adminbasic/stations/{loginId}")
    public HttpEntity getAllStations(@PathVariable String loginId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Find All Station by admin: " + loginId);
        return ok(adminBasicInfoService.getAllStations(loginId, headers));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/adminbasic/stations")
    public HttpEntity deleteStation(@RequestBody Station s, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Delete Station by admin: " + s.getLoginId());
        return ok(adminBasicInfoService.deleteStation(s, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/adminbasic/stations")
    public HttpEntity modifyStation(@RequestBody Station s, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Modify Station by admin: " + s.getLoginId());
        return ok(adminBasicInfoService.modifyStation(s, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/adminbasic/stations")
    public HttpEntity addStation(@RequestBody Station s, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Modify Station by admin: " + s.getLoginId());
        return ok(adminBasicInfoService.addStation(s, headers));
    }

    /////////////////////////train/////////////////////////////////////////////////////////////////////////////////
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/adminbasic/trains/{loginId}")
    public HttpEntity getAllTrains(@PathVariable String loginId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Find All Train by admin: " + loginId);
        return ok(adminBasicInfoService.getAllTrains(loginId, headers));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/adminbasic/trains/{id}/{loginId}")
    public HttpEntity deleteTrain(@PathVariable String id, @PathVariable String loginId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Delete Train by admin: " + loginId);
        return ok(adminBasicInfoService.deleteTrain(id, loginId, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/adminbasic/trains")
    public HttpEntity modifyTrain(@RequestBody TrainType t, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Modify Train by admin: " + t.getLoginId());
        return ok(adminBasicInfoService.modifyTrain(t, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/adminbasic/trains")
    public HttpEntity addTrain(@RequestBody TrainType t, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Modify Train by admin: " + t.getLoginId());
        return ok(adminBasicInfoService.addTrain(t, headers));
    }

    /////////////////////////config/////////////////////////////////////////////////////////////////////////////////
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/adminbasic/configs/{loginId}")
    public HttpEntity getAllConfigs(@PathVariable String loginId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Find All Config by admin: " + loginId);
        return ok(adminBasicInfoService.getAllConfigs(loginId, headers));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/adminbasic/configs/{name}/{loginId}")
    public HttpEntity deleteConfig(@PathVariable String name, @PathVariable String loginId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Delete Config by admin: " + loginId);
        return ok(adminBasicInfoService.deleteConfig(name, loginId, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/adminbasic/configs")
    public HttpEntity modifyConfig(@RequestBody Config c, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Modify Config by admin: " + c.getLoginId());
        return ok(adminBasicInfoService.modifyConfig(c, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/adminbasic/configs")
    public HttpEntity addConfig(@RequestBody Config c, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Modify Config by admin: " + c.getLoginId());
        return ok(adminBasicInfoService.addConfig(c, headers));
    }

    /////////////////////////price/////////////////////////////////////////////////////////////////////////////////
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/adminbasic/prices/{loginId}")
    public HttpEntity getAllPrices(@PathVariable String loginId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Find All Price by admin: " + loginId);
        return ok(adminBasicInfoService.getAllPrices(loginId, headers));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/adminbasic/prices")
    public HttpEntity deletePrice(@RequestBody PriceInfo pi, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Delete Price by admin: " + pi.getLoginId());
        return ok(adminBasicInfoService.deletePrice(pi, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/adminbasic/prices")
    public HttpEntity modifyPrice(@RequestBody PriceInfo pi, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Modify Price by admin: " + pi.getLoginId());
        return ok(adminBasicInfoService.modifyPrice(pi, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/adminbasic/prices")
    public HttpEntity addPrice(@RequestBody PriceInfo pi, @RequestHeader HttpHeaders headers) {
        System.out.println("[Admin Basic Info Service][Add Price by admin: " + pi.getLoginId());
        return ok(adminBasicInfoService.addPrice(pi, headers));
    }


}
