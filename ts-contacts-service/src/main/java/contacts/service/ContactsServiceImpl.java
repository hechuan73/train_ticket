package contacts.service;

import contacts.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import contacts.repository.ContactsRepository;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class ContactsServiceImpl implements ContactsService{

    @Autowired
    private ContactsRepository contactsRepository;

    @Override
    public Contacts findContactsById(UUID id){
        return contactsRepository.findById(id);
    }

    @Override
    public ArrayList<Contacts> findContactsByAccountId(UUID accountId){
        ArrayList<Contacts> arr = contactsRepository.findByAccountId(accountId);
        System.out.println("[Contacts-Query-Service][Query-Contacts] Result Size:" + arr.size());
        return arr;
    }

    @Override
    public Contacts createContacts(Contacts contacts){
        Contacts contactsTemp = contactsRepository.findById(contacts.getId());
        if(contactsTemp != null){
            System.out.println("[Contacts Service][Init Contacts] Already Exists Id:" + contacts.getId());
        }else{
            contactsRepository.save(contacts);
        }
        return contacts;
    }

    @Override
    public AddContactsResult create(AddContactsInfo aci,String accountId){
        Contacts contacts = new Contacts();
        contacts.setId(UUID.randomUUID());
        contacts.setName(aci.getName());
        contacts.setPhoneNumber(aci.getPhoneNumber());
        contacts.setDocumentNumber(aci.getDocumentNumber());
        contacts.setAccountId(UUID.fromString(accountId));
        contacts.setDocumentType(aci.getDocumentType());

        ArrayList<Contacts> accountContacts = contactsRepository.findByAccountId(UUID.fromString(accountId));
        AddContactsResult acr = new AddContactsResult();
        if(accountContacts.contains(contacts)){
            System.out.println("[Contacts-Add&Delete-Service][AddContacts] Fail.Contacts already exists");
            acr.setStatus(false);
            acr.setMessage("Contacts Already Exists");
            acr.setContacts(null);
        }else{
            contactsRepository.save(contacts);
            System.out.println("[Contacts-Add&Delete-Service][AddContacts] Success.");
            acr.setStatus(true);
            acr.setMessage("Success");
            acr.setContacts(contacts);
        }
        return acr;
    }

    @Override
    public DeleteContactsResult delete(UUID contactsId){
        contactsRepository.deleteById(contactsId);
        Contacts contacts = contactsRepository.findById(contactsId);
        DeleteContactsResult dcr = new DeleteContactsResult();
        if(contacts == null){
            System.out.println("[Contacts-Add&Delete-Service][DeleteContacts] Success.");
            dcr.setStatus(true);
            dcr.setMessage("Success");
        }else{
            System.out.println("[Contacts-Add&Delete-Service][DeleteContacts] Fail.Reason not clear.");
            dcr.setStatus(false);
            dcr.setMessage("Reason Not clear");
        }
        return dcr;
    }

    @Override
    public ModifyContactsResult modify(ModifyContactsInfo info){
        Contacts oldContacts = findContactsById(UUID.fromString(info.getContactsId()));
        ModifyContactsResult mcr = new ModifyContactsResult();
        if(oldContacts == null){
            System.out.println("[Contacts-Modify-Service][ModifyContacts] Fail.Contacts not found.");
            mcr.setStatus(false);
            mcr.setMessage("Contacts not found");
            mcr.setContacts(null);
        }else{
            oldContacts.setName(info.getName());
            oldContacts.setDocumentType(info.getDocumentType());
            oldContacts.setDocumentNumber(info.getDocumentNumber());
            oldContacts.setPhoneNumber(info.getPhoneNumber());
            contactsRepository.save(oldContacts);
            System.out.println("[Contacts-Modify-Service][ModifyContacts] Success.");
            mcr.setStatus(true);
            mcr.setMessage("Success");
            mcr.setContacts(oldContacts);
        }
        return mcr;
    }

    @Override
    public GetAllContactsResult getAllContacts(){
        ArrayList<Contacts> contacts = contactsRepository.findAll();
        GetAllContactsResult result = new GetAllContactsResult();
        result.setStatus(true);
        result.setMessage("Success");
        result.setContacts(contacts);
        return result;
    }

}


