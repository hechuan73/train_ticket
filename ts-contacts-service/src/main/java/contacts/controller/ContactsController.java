package contacts.controller;

import contacts.entity.*;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import contacts.service.ContactsService;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/contactservice")
public class ContactsController {


    @Autowired
    private ContactsService contactsService;

    @GetMapping(path = "/contacts/welcome")
    public String home() {
        return "Welcome to [ Contacts Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/contacts")
    public HttpEntity getAllContacts(@RequestHeader HttpHeaders headers) {
        System.out.println("[Contacts Service][Get All Contacts]");
        return ok(contactsService.getAllContacts(headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/contacts")
    public ResponseEntity<Response> createNewContacts(@RequestBody Contacts aci,
                                                      @RequestHeader HttpHeaders headers) {
        System.out.println("[ContactsService][VerifyLogin] Success");
        return new ResponseEntity<>(contactsService.create(aci, headers), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/contacts/admin")
    public HttpEntity<?> createNewContactsAdmin(@RequestBody Contacts aci, @RequestHeader HttpHeaders headers) {
        aci.setId(UUID.randomUUID());
        System.out.println("[ContactsService][Create Contacts In Admin]");
        return new ResponseEntity<>(contactsService.createContacts(aci, headers), HttpStatus.CREATED);
    }


    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/contacts/{contactsId}")
    public HttpEntity deleteContacts(@PathVariable String contactsId, @RequestHeader HttpHeaders headers) {
        return ok(contactsService.delete(UUID.fromString(contactsId), headers));
    }


    @CrossOrigin(origins = "*")
    @PutMapping(path = "/contacts")
    public HttpEntity modifyContacts(@RequestBody Contacts info, @RequestHeader HttpHeaders headers) {
        System.out.println("[Contacts Service][Modify Contacts] ContactsId:" + info.getId());
        return ok(contactsService.modify(info, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/contacts/account/{accountId}")
    public HttpEntity findContactsByAccountId(@PathVariable String accountId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Contacts Service][Find Contacts By Account Id:" + accountId);
        System.out.println("[ContactsService][VerifyLogin] Success");
        return ok(contactsService.findContactsByAccountId(UUID.fromString(accountId), headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/contacts/{id}")
    public HttpEntity getContactsByContactsId(@PathVariable String id, @RequestHeader HttpHeaders headers) {
        System.out.println("[ContactsService][Contacts Id Print] " + id);
        System.out.println("[ContactsService][VerifyLogin] Success.");
        return ok(contactsService.findContactsById(UUID.fromString(id), headers));
    }


//    private VerifyResult verifySsoLogin(String loginToken, @RequestHeader HttpHeaders headers) {
//        System.out.println("[Order Service][Verify Login] Verifying....");
//
//        HttpEntity requestTokenResult = new HttpEntity(null, headers);
//        ResponseEntity<VerifyResult> reTokenResult = restTemplate.exchange(
//                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
//                HttpMethod.GET,
//                requestTokenResult,
//                VerifyResult.class);
//        VerifyResult tokenResult = reTokenResult.getBody();
////        VerifyResult tokenResult = restTemplate.getForObject(
////                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
////                VerifyResult.class);
//
//
//        return tokenResult;
//    }

}
