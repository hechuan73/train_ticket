package contacts.service;

import contacts.entity.*;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

public interface ContactsService {

    Response createContacts(Contacts contacts, HttpHeaders headers);

    Response create(Contacts addContacts, HttpHeaders headers);

    Response delete(UUID contactsId, HttpHeaders headers);

    Response modify(Contacts contacts, HttpHeaders headers);

    Response getAllContacts(HttpHeaders headers);

    Response findContactsById(UUID id, HttpHeaders headers);

    Response findContactsByAccountId(UUID accountId, HttpHeaders headers);

}
