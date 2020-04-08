package contacts.controller;

import contacts.entity.Contacts;
import contacts.service.ContactsService;
import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@RunWith(JUnit4.class)
public class ContactsControllerTest {

    @InjectMocks
    private ContactsController contactsController;

    @Mock
    private ContactsService contactsService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = contactsController.home();
        Assert.assertEquals("Welcome to [ Contacts Service ] !", result);
    }

    @Test
    public void testGetAllContacts(){
        Mockito.when(contactsService.getAllContacts(headers)).thenReturn(response);
        httpEntity = contactsController.getAllContacts(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreateNewContacts(){
        Contacts aci = new Contacts();
        Mockito.when(contactsService.create(aci, headers)).thenReturn(response);
        httpEntity = contactsController.createNewContacts(aci, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.CREATED), httpEntity);
    }

    @Test
    public void testCreateNewContactsAdmin(){
        Contacts aci = new Contacts();
        Mockito.when(contactsService.createContacts(aci, headers)).thenReturn(response);
        httpEntity = contactsController.createNewContactsAdmin(aci, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.CREATED), httpEntity);
    }

    @Test
    public void testDeleteContacts(){
        UUID contactsId = UUID.randomUUID();
        Mockito.when(contactsService.delete(contactsId, headers)).thenReturn(response);
        httpEntity = contactsController.deleteContacts(contactsId.toString(), headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testModifyContacts(){
        Contacts info = new Contacts();
        Mockito.when(contactsService.modify(info, headers)).thenReturn(response);
        httpEntity = contactsController.modifyContacts(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testFindContactsByAccountId(){
        UUID accountId = UUID.randomUUID();
        Mockito.when(contactsService.findContactsByAccountId(accountId, headers)).thenReturn(response);
        httpEntity = contactsController.findContactsByAccountId(accountId.toString(), headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetContactsByContactsId(){
        UUID id = UUID.randomUUID();
        Mockito.when(contactsService.findContactsById(id, headers)).thenReturn(response);
        httpEntity = contactsController.getContactsByContactsId(id.toString(), headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
