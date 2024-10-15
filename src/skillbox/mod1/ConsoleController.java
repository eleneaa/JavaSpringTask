package skillbox.mod1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsoleController {

    private final ContactService contactService;

    @Autowired
    public ConsoleController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/addContact")
    public String addContact(@RequestBody Contact contact) {
        contactService.addContact(contact);
        return "Contact added successfully";
    }

    @DeleteMapping("/deleteContact/{email}")
    public String deleteContact(@PathVariable String email) {
        contactService.deleteContact(email);
        return "Contact deleted successfully";
    }

    @GetMapping("/displayAllContacts")
    public List<Contact> displayAllContacts() {
        List<Contact> allContacts = contactService.getAllContacts();
        for (Contact contact : allContacts) {
            System.out.println(contact.toString());
        }
        return allContacts;
    }
}
