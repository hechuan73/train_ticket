package contacts.service;

import contacts.entity.*;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import contacts.repository.ContactsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@Service
@Slf4j
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    private ContactsRepository contactsRepository;

    @Override
    public Response findContactsById(UUID id, HttpHeaders headers) {
        log.info("FIND CONTACTS BY ID: " + id);
        Contacts contacts = contactsRepository.findById(id);
        if (contacts != null) {
            return new Response<>(1, "Success", contacts);
        } else {
            return new Response<>(0, "No contacts accorrding to contacts id", id);
        }
    }

    @Override
    public Response findContactsByAccountId(UUID accountId, HttpHeaders headers) {
        ArrayList<Contacts> arr = contactsRepository.findByAccountId(accountId);
        System.out.println("[Contacts-Query-Service][Query-Contacts] Result Size:" + arr.size());
        if (arr != null && arr.size() > 0) {
            return new Response<>(1, "Success", arr);
        } else {
            return new Response<>(0, "No contacts according to accountId", accountId);
        }
    }

    @Override
    public Response createContacts(Contacts contacts, HttpHeaders headers) {
        Contacts contactsTemp = contactsRepository.findById(contacts.getId());
        if (contactsTemp != null) {
            System.out.println("[Contacts Service][Init Contacts] Already Exists Id:" + contacts.getId());
            return new Response<>(0, "Already Exists", contactsTemp);
        } else {
            contactsRepository.save(contacts);
            return new Response<>(1, "Create Success", contactsTemp);
        }
    }

    @Override
    public Response create(Contacts addContacts, HttpHeaders headers) {
        Contacts contacts = new Contacts();
        contacts.setId(UUID.randomUUID());
        contacts.setName(addContacts.getName());
        contacts.setPhoneNumber(addContacts.getPhoneNumber());
        contacts.setDocumentNumber(addContacts.getDocumentNumber());
        contacts.setAccountId(addContacts.getAccountId());
        contacts.setDocumentType(addContacts.getDocumentType());

        ArrayList<Contacts> accountContacts = contactsRepository.findByAccountId(addContacts.getAccountId());

        if (accountContacts.contains(contacts)) {
            System.out.println("[Contacts-Add&Delete-Service][AddContacts] Fail.Contacts already exists");
            return new Response<>(0, "Contacts already exists", null);
        } else {
            contactsRepository.save(contacts);
            System.out.println("[Contacts-Add&Delete-Service][AddContacts] Success.");
            return new Response<>(1, "Create contacts success", contacts);
        }
    }

    @Override
    public Response delete(UUID contactsId, HttpHeaders headers) {
        contactsRepository.deleteById(contactsId);
        Contacts contacts = contactsRepository.findById(contactsId);
        if (contacts == null) {
            System.out.println("[Contacts-Add&Delete-Service][DeleteContacts] Success.");
            return new Response<>(1, "Delete success", contactsId);
        } else {
            System.out.println("[Contacts-Add&Delete-Service][DeleteContacts] Fail.Reason not clear.");
            return new Response<>(0, "Delete failed", contactsId);
        }
    }

    @Override
    public Response modify(Contacts contacts, HttpHeaders headers) {
        Response oldContactResponse = findContactsById(contacts.getId(), headers);
        log.info(oldContactResponse.toString());
        Contacts oldContacts = (Contacts) oldContactResponse.getData();
        if (oldContacts == null) {
            System.out.println("[Contacts-Modify-Service][ModifyContacts] Fail.Contacts not found.");
            return new Response<>(0, "Contacts not found", oldContacts);
        } else {
            oldContacts.setName(contacts.getName());
            oldContacts.setDocumentType(contacts.getDocumentType());
            oldContacts.setDocumentNumber(contacts.getDocumentNumber());
            oldContacts.setPhoneNumber(contacts.getPhoneNumber());
            contactsRepository.save(oldContacts);
            System.out.println("[Contacts-Modify-Service][ModifyContacts] Success.");
            return new Response<>(1, "Modify success", oldContacts);
        }
    }

    @Override
    public Response getAllContacts(HttpHeaders headers) {
        ArrayList<Contacts> contacts = contactsRepository.findAll();
        if (contacts != null && contacts.size() > 0) {
            return new Response<>(1, "Success", contacts);
        } else {
            return new Response<>(0, "No content", null);
        }
    }

}


