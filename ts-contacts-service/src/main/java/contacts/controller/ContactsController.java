package contacts.controller;

import contacts.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import contacts.service.ContactsService;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ContactsController {

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Contacts Service ] !";
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/contacts/admincreate", method = RequestMethod.POST)
    public AddContactsResult createNewContactsAdmin(@RequestBody Contacts aci){
        aci.setId(UUID.randomUUID());
        System.out.println("[ContactsService][Create Contacts In Admin]");
        aci = contactsService.createContacts(aci);
        AddContactsResult acr = new AddContactsResult();
        acr.setStatus(true);
        acr.setContacts(aci);
        acr.setMessage("Success");
        return acr;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/contacts/findAll", method = RequestMethod.GET)
    public GetAllContactsResult getAllContacts(){
        System.out.println("[Contacts Service][Get All Contacts]");
        return contactsService.getAllContacts();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/contacts/modifyContacts", method = RequestMethod.POST)
    public ModifyContactsResult modifyContacts(@RequestBody ModifyContactsInfo info){
        System.out.println("[Contacts Service][Modify Contacts] ContactsId:" + info.getContactsId());
        return contactsService.modify(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/contacts/deleteContacts", method = RequestMethod.POST)
    public DeleteContactsResult deleteContacts(@RequestBody DeleteContactsInfo info){
        return contactsService.delete(UUID.fromString(info.getContactsId()));
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/contacts/findContacts", method = RequestMethod.GET)
    public ArrayList<Contacts> findContactsByAccountId(@CookieValue String loginId,@CookieValue String loginToken){
        System.out.println("[Contacts Service][Find Contacts By Account Id:" + loginId);
        VerifyResult tokenResult = verifySsoLogin(loginToken);
        if(tokenResult.isStatus() == true){
            System.out.println("[ContactsService][VerifyLogin] Success");
            return contactsService.findContactsByAccountId(UUID.fromString(loginId));
        }else {
            System.out.println("[ContactsService][VerifyLogin] Fail");
            return new ArrayList<Contacts>();
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/contacts/getContactsById", method = RequestMethod.POST)
    public GetContactsResult getContactsByContactsId(@RequestBody GetContactsInfo gci){
        VerifyResult tokenResult = verifySsoLogin(gci.getLoginToken());
        GetContactsResult gcr = new GetContactsResult();
        if(tokenResult.isStatus() == true){
            System.out.println("[ContactsService][VerifyLogin] Success.");
            System.out.println("[ContactsService][Contacts Id Print] " + gci.getContactsId());
            Contacts contacts = contactsService.findContactsById(UUID.fromString(gci.getContactsId()));
            if(contacts == null){
                gcr.setStatus(false);
                gcr.setMessage("Contacts Not Exist.");
                gcr.setContacts(null);
            }else{
                gcr.setStatus(true);
                gcr.setMessage("Success.");
                gcr.setContacts(contacts);
            }
        }else{
            System.out.println("[ContactsService][VerifyLogin] Fail.");
            gcr.setStatus(false);
            gcr.setMessage("Not Login.");
            gcr.setContacts(null);
        }
        return gcr;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/contacts/create", method = RequestMethod.POST)
    public AddContactsResult createNewContacts(@RequestBody AddContactsInfo aci,@CookieValue String loginId,@CookieValue String loginToken){
        VerifyResult tokenResult = verifySsoLogin(loginToken);
        if(tokenResult.isStatus() == true){
            System.out.println("[ContactsService][VerifyLogin] Success");
            return contactsService.create(aci,loginId);
        }else{
            System.out.println("[ContactsService][VerifyLogin] Fail");
            AddContactsResult acr = new AddContactsResult();
            acr.setStatus(false);
            acr.setMessage("Not Login");
            acr.setContacts(null);
            return acr;
        }
    }

//    @CrossOrigin(origins = "*")
//    @RequestMapping(path = "/contacts/delete", method = RequestMethod.DELETE)
//    public DeleteContactsResult deleteContacts(@RequestBody DeleteContactsInfo dci){
//        VerifyResult tokenResult = verifySsoLogin(dci.getLoginToken());
//        if(tokenResult.isStatus() == true){
//            System.out.println("[ContactsService][VerifyLogin] Success");
//            return contactsService.delete(UUID.fromString(dci.getContactsId()));
//        }else{
//            System.out.println("[ContactsService][VerifyLogin] Fail");
//            DeleteContactsResult dcr = new DeleteContactsResult();
//            dcr.setMessage("Not Login");
//            dcr.setStatus(false);
//            return dcr;
//        }
//    }
//
//    @CrossOrigin(origins = "*")
//    @RequestMapping(path = "/contacts/update", method = RequestMethod.PUT)
//    public ModifyContactsResult saveContactsInfo(@RequestBody ModifyContactsInfo contactsInfo){
//        VerifyResult tokenResult = verifySsoLogin(contactsInfo.getLoginToken());
//        if(tokenResult.isStatus() == true){
//            System.out.println("[ContactsService][VerifyLogin] Success");
//            return contactsService.saveChanges(contactsInfo.getContacts());
//        }else{
//            System.out.println("[ContactsService][VerifyLogin] Fail");
//            ModifyContactsResult mcr = new ModifyContactsResult();
//            mcr.setStatus(false);
//            mcr.setMessage("Not Login");
//            mcr.setContacts(null);
//            return mcr;
//        }
//    }

    private VerifyResult verifySsoLogin(String loginToken){
        System.out.println("[ContactsService][VerifyLogin] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                     VerifyResult.class);
        return tokenResult;
    }

}
