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
        List<Contacts> contacts = contactsService.getAllContacts(headers);
        if (contacts != null && contacts.size() > 0) {
            return ok(new Response(1, "Success", contacts));
        } else {
            return ok(new Response(0, "No content", null));
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/contacts")
    public ResponseEntity<Response> createNewContacts(@RequestBody Contacts aci,
                                        @RequestHeader HttpHeaders headers) {
        System.out.println("[ContactsService][VerifyLogin] Success");
        Contacts contacts = contactsService.create(aci, headers);
        if (contacts == null) {
            return ok(new Response(0, "Contacts already exists", aci));
        } else {
            return new ResponseEntity<>(new Response(1, "Create contacts success", contacts),HttpStatus.CREATED);
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/contacts/{contactsId}")
    public HttpEntity deleteContacts(@PathVariable String contactsId, @RequestHeader HttpHeaders headers) {
        boolean isDeleteSuccess = contactsService.delete(UUID.fromString(contactsId), headers);
        if (isDeleteSuccess) {
            return ok(new Response(1, "Delete success", contactsId));
        } else {
            return ok(new Response(0, "Delete failed", contactsId));
        }
    }


    @CrossOrigin(origins = "*")
    @PutMapping(path = "/contacts")
    public HttpEntity modifyContacts(@RequestBody Contacts info, @RequestHeader HttpHeaders headers) {
        System.out.println("[Contacts Service][Modify Contacts] ContactsId:" + info.getId());
        Contacts contacts = contactsService.modify(info, headers);
        if (contacts == null) {
            return ok(new Response(0, "Contacts not found", info));
        } else {
            return ok(new Response(1, "Modify success", contacts));
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/contacts/account/{accountId}")
    public HttpEntity findContactsByAccountId(@PathVariable String accountId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Contacts Service][Find Contacts By Account Id:" + accountId);
        System.out.println("[ContactsService][VerifyLogin] Success");
        List<Contacts> contacts = contactsService.findContactsByAccountId(UUID.fromString(accountId), headers);
        if (contacts != null && contacts.size() > 0) {
            return ok(new Response(0, "No contacts according to accountId", accountId));
        } else {
            return ok(new Response(1, "Success", contacts));
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/contacts/{id}")
    public HttpEntity getContactsByContactsId(@PathVariable String id, @RequestHeader HttpHeaders headers) {
        System.out.println("[ContactsService][Contacts Id Print] " + id);
        Contacts contacts = contactsService.findContactsById(UUID.fromString(id), headers);
        System.out.println("[ContactsService][VerifyLogin] Success.");
        if (contacts != null) {
            return ok(new Response(1, "Success", contacts));
        } else {
            return ok(new Response(0, "No contacts accorrding to contacts id", id));
        }
    }

    //    @CrossOrigin(origins = "*")
//    @PostMapping(path = "/contacts/admincreate")
//    public HttpEntity<?> createNewContactsAdmin(@RequestBody Contacts aci, @RequestHeader HttpHeaders headers){
//        aci.setId(UUID.randomUUID());
//        System.out.println("[ContactsService][Create Contacts In Admin]");
//        aci = contactsService.createContacts(aci, headers);
//        return new ResponseEntity<>(aci, HttpStatus.CREATED);
//    }


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
