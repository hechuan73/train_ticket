package contacts.service;

import contacts.domain.*;
import java.util.ArrayList;
import java.util.UUID;

public interface ContactsService {

    Contacts createContacts(Contacts contacts);

    Contacts findContactsById(UUID id);

    ArrayList<Contacts> findContactsByAccountId(UUID accountId);

    AddContactsResult create(AddContactsInfo aci,String accountId);

    DeleteContactsResult delete(UUID contactsId);

    ModifyContactsResult modify(ModifyContactsInfo info);

    GetAllContactsResult getAllContacts();

}
