package contacts.service;

import contacts.entity.*;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

public interface ContactsService {

//    Contacts createContacts(Contacts contacts, HttpHeaders headers);

    Contacts create(Contacts addContacts , HttpHeaders headers);

    boolean delete(UUID contactsId, HttpHeaders headers);

    Contacts modify(Contacts contacts ,HttpHeaders headers);

    List<Contacts> getAllContacts(HttpHeaders headers);

    Contacts findContactsById(UUID id, HttpHeaders headers);

    List<Contacts> findContactsByAccountId(UUID accountId, HttpHeaders headers);

}
