package contacts.service;

import contacts.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import contacts.repository.ContactsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    private ContactsRepository contactsRepository;

    @Override
    public Contacts findContactsById(UUID id, HttpHeaders headers) {
        return contactsRepository.findById(id);
    }

    @Override
    public List<Contacts> findContactsByAccountId(UUID accountId, HttpHeaders headers) {
        ArrayList<Contacts> arr = contactsRepository.findByAccountId(accountId);
        System.out.println("[Contacts-Query-Service][Query-Contacts] Result Size:" + arr.size());
        return arr;
    }
//    @Override
//    public Contacts createContacts(Contacts contacts, HttpHeaders headers) {
//        Contacts contactsTemp = contactsRepository.findById(contacts.getId());
//        if (contactsTemp != null) {
//            System.out.println("[Contacts Service][Init Contacts] Already Exists Id:" + contacts.getId());
//        } else {
//            contactsRepository.save(contacts);
//        }
//        return contacts;
//    }

    @Override
    public Contacts create(Contacts addContacts , HttpHeaders headers) {
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
            contacts = null;
        } else {
            contactsRepository.save(contacts);
            System.out.println("[Contacts-Add&Delete-Service][AddContacts] Success.");
        }
        return contacts;
    }

    @Override
    public boolean delete(UUID contactsId, HttpHeaders headers) {
        contactsRepository.deleteById(contactsId);
        Contacts contacts = contactsRepository.findById(contactsId);
        if (contacts == null) {
            System.out.println("[Contacts-Add&Delete-Service][DeleteContacts] Success.");
            return true;
        } else {
            System.out.println("[Contacts-Add&Delete-Service][DeleteContacts] Fail.Reason not clear.");
            return false;
        }
    }

    @Override
    public Contacts modify(Contacts contacts, HttpHeaders headers) {
        Contacts oldContacts = findContactsById(contacts.getId(), headers);
        if (oldContacts == null) {
            System.out.println("[Contacts-Modify-Service][ModifyContacts] Fail.Contacts not found.");
        } else {
            oldContacts.setName(contacts.getName());
            oldContacts.setDocumentType(contacts.getDocumentType());
            oldContacts.setDocumentNumber(contacts.getDocumentNumber());
            oldContacts.setPhoneNumber(contacts.getPhoneNumber());
            contactsRepository.save(oldContacts);
            System.out.println("[Contacts-Modify-Service][ModifyContacts] Success.");
        }
        return oldContacts;
    }

    @Override
    public List<Contacts> getAllContacts(HttpHeaders headers) {
        ArrayList<Contacts> contacts = contactsRepository.findAll();
        return contacts;
    }

}


